/*
 * @(#)CodeSetComponentInfo.java	1.28 02/01/25
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.sun.corba.se.internal.core;

import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.CompletionStatus;

import com.sun.corba.se.internal.orbutil.MinorCodes;

public final class CodeSetComponentInfo {

    /**
     * CodeSetComponent is part of an IOR multi-component profile.  Two
     * instances constitute a CodeSetComponentInfo (one for char and one
     * for wchar data)
     */
    public static final class CodeSetComponent {
        int nativeCodeSet;
	int[] conversionCodeSets;

        public CodeSetComponent() {}
	
	public CodeSetComponent(int nativeCodeSet, int[] conversionCodeSets) {
	    this.nativeCodeSet = nativeCodeSet;
	    if (conversionCodeSets == null)
		this.conversionCodeSets = new int[0];
	    else 
		this.conversionCodeSets = conversionCodeSets;
	}

	public void read(MarshalInputStream in) {
	    nativeCodeSet = in.read_ulong();
	    int len = in.read_long();
	    conversionCodeSets = new int[len];
	    in.read_ulong_array(conversionCodeSets, 0, len);

	}
	
	public void write(MarshalOutputStream out) {
	    out.write_ulong(nativeCodeSet);
	    out.write_long(conversionCodeSets.length);
	    out.write_ulong_array(conversionCodeSets, 0, conversionCodeSets.length);
	}

        public String toString() {
            StringBuffer sbuf = new StringBuffer("CodeSetComponent: ");

            sbuf.append("native: ");
            sbuf.append(Integer.toHexString(nativeCodeSet));
            sbuf.append(" conversion: ");
            if (conversionCodeSets == null)
                sbuf.append("null");
            else {
                for (int i = 0; i < conversionCodeSets.length; i++) {
                    sbuf.append(Integer.toHexString(conversionCodeSets[i]));
                    sbuf.append(' ');
                }
            }

            return sbuf.toString();
        }
    }

    private CodeSetComponent forCharData;
    private CodeSetComponent forWCharData;

    public String toString() {
        StringBuffer sbuf = new StringBuffer("CodeSetComponentInfo: ");

        sbuf.append("char data ");
        sbuf.append(forCharData.toString());
        sbuf.append("wchar data ");
        sbuf.append(forWCharData.toString());

        return sbuf.toString();
    }

    public CodeSetComponentInfo() {
        forCharData = CodeSetComponentInfo.JAVASOFT_DEFAULT_CODESETS.forCharData;
        forWCharData = CodeSetComponentInfo.JAVASOFT_DEFAULT_CODESETS.forWCharData;
    }

    public CodeSetComponentInfo(CodeSetComponent charData,
                                CodeSetComponent wcharData) {
        forCharData = charData;
        forWCharData = wcharData;
    }

    public void read(MarshalInputStream in) {
        forCharData = new CodeSetComponent();
	forCharData.read(in);
        forWCharData = new CodeSetComponent();
	forWCharData.read(in);
    }

    public void write(MarshalOutputStream out) {
	forCharData.write(out);
	forWCharData.write(out);
    }

    public CodeSetComponent getCharComponent() {
        return forCharData;
    }

    public CodeSetComponent getWCharComponent() {
        return forWCharData;
    }

    /**
     * CodeSetContext goes in a GIOP service context
     */
    public static final class CodeSetContext {
	private int char_data;
	private int wchar_data;
	
        public CodeSetContext() {}

	public CodeSetContext(int charEncoding, int wcharEncoding) {
	    char_data = charEncoding;
	    wchar_data = wcharEncoding;
	}
	
	public void read(MarshalInputStream in) {
	    char_data = in.read_ulong();
	    wchar_data = in.read_ulong();
	}
	
	public void write(MarshalOutputStream out) {
	    out.write_ulong(char_data);
	    out.write_ulong(wchar_data);
	}

        public int getCharCodeSet() {
            return char_data;
        }
        
        public int getWCharCodeSet() {
            return wchar_data;
        }

        public String toString() {
            StringBuffer sbuf = new StringBuffer();
            sbuf.append("CodeSetContext char set: ");
            sbuf.append(Integer.toHexString(char_data));
            sbuf.append(" wchar set: ");
            sbuf.append(Integer.toHexString(wchar_data));
            return sbuf.toString();
        }
    }

    /**
     * Our default code set scheme is as follows:
     *
     * char data:
     *
     * Native code set:  ISO 8859-1 (8-bit)
     * Conversion sets:  UTF-8, ISO 646 (7-bit)
     *
     * wchar data:
     *
     * Native code set:  UTF-16
     * Conversion sets:  UCS-2
     *
     * Pre-Merlin/J2EE 1.3 JavaSoft ORBs listed ISO646 for char and
     * UCS-2 for wchar, and provided no conversion sets.  They also
     * didn't do correct negotiation or provide the fallback sets.
     * UCS-2 is still in the conversion list for backwards compatibility.
     *
     * The fallbacks are UTF-8 for char and UTF-16 for wchar.
     *
     * In GIOP 1.1, interoperability with wchar is limited to 2 byte fixed
     * width encodings since its wchars aren't preceded by a length.
     * Thus, I've chosen not to include UTF-8 in the conversion set
     * for wchar data.
     *
     */
    public static final CodeSetComponentInfo JAVASOFT_DEFAULT_CODESETS;
    static {
        CodeSetComponent charData 
            = new CodeSetComponent(OSFCodeSetRegistry.ISO_8859_1.getNumber(),
                                   new int[] {
                                       OSFCodeSetRegistry.UTF_8.getNumber(),
                                       OSFCodeSetRegistry.ISO_646.getNumber()
                                   });

        CodeSetComponent wcharData
            = new CodeSetComponent(OSFCodeSetRegistry.UTF_16.getNumber(),
                                   new int[]
                                   {
                                       OSFCodeSetRegistry.UCS_2.getNumber()
                                   });

        JAVASOFT_DEFAULT_CODESETS = new CodeSetComponentInfo(charData, wcharData);
    }

    /**
     * Creates a CodeSetComponent from a String which contains a comma
     * delimited list of OSF Code Set Registry numbers.  An INITIALIZE
     * exception is thrown if any of the numbers are not known by our
     * registry.  Used by corba.ORB init.
     *
     * The numbers can either be decimal or hex.
     */
    public static CodeSetComponent createFromString(String str) {
        if (str == null || str.length() == 0)
            throw new INITIALIZE("Empty or null code set String");

        StringTokenizer stok = new StringTokenizer(str, ", ", false);
        int nativeSet = 0;
        int conversionInts[] = null;

        try {

            // The first value is the native code set
            nativeSet = Integer.decode(stok.nextToken()).intValue();

            if (OSFCodeSetRegistry.lookupEntry(nativeSet) == null)
                throw new INITIALIZE("Unknown native code set: " + nativeSet);

            List conversionList = new ArrayList(10);

            // Now process the other values as part of the
            // conversion code set list.
            while (stok.hasMoreTokens()) {
                
                // decode allows us to specify hex, decimal, etc
                Integer value = Integer.decode(stok.nextToken());

                if (OSFCodeSetRegistry.lookupEntry(value.intValue()) == null)
                    throw new INITIALIZE("Unknown conversion code set: "
                                         + value);

                conversionList.add(value);
            }

            conversionInts = new int[conversionList.size()];

            for (int i = 0; i < conversionInts.length; i++)
                conversionInts[i] = ((Integer)conversionList.get(i)).intValue();

        } catch (NumberFormatException nfe) {
            throw new INITIALIZE("Invalid code set number: " + nfe.getMessage());
        }

        // If the entire input string was empty or some other
        // error came up, throw an exception
        if (nativeSet == 0)
            throw new INITIALIZE("Invalid code set String \"" 
                                 + str
                                 + "\": Requires at least a native code set");

        // Otherwise return the CodeSetComponent representing
        // the given values
        return new CodeSetComponent(nativeSet, conversionInts);
    }

    /**
     * Code sets for local cases without a connection.
     */
    public static final CodeSetContext LOCAL_CODE_SETS
        = new CodeSetContext(OSFCodeSetRegistry.ISO_8859_1.getNumber(),
                             OSFCodeSetRegistry.UTF_16.getNumber());
}

