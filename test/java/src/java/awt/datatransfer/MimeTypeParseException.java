/*
 * @(#)MimeTypeParseException.java	1.9 00/02/17
 *
 * Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package java.awt.datatransfer;


/**
 *    A class to encapsulate MimeType parsing related exceptions
 *
 * @serial exclude
 */
public class MimeTypeParseException extends Exception {

    // use serialVersionUID from JDK 1.2.2 for interoperability
    private static final long serialVersionUID = -5604407764691570741L;

    /**
     * Constructs a MimeTypeParseException with no specified detail message. 
     */
    public MimeTypeParseException() {
     	super();
    }

    /**
     * Constructs a MimeTypeParseException with the specified detail message. 
     *
     * @param   s   the detail message.
     */
    public MimeTypeParseException(String s) {
        super(s);
    }
} // class MimeTypeParseException