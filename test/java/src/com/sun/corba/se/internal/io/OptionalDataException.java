/*
 * @(#)OptionalDataException.java	1.18 01/12/03
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

package com.sun.corba.se.internal.io;

/**
 * Unexpected data appeared in an ObjectInputStream trying to read
 * an Object.
 * This exception occurs when the stream contains primitive data
 * instead of the object expected by readObject.
 * The eof flag in the exception is true to indicate that no more
 * primitive data is available.
 * The count field contains the number of bytes available to read.
 *
 * @author  unascribed
 * @version 1.7, 11/02/98
 * @since   JDK1.1
 */
public class OptionalDataException extends java.io.IOException {
    /*
     * Create an <code>OptionalDataException</code> with a length.
     */
    OptionalDataException(int len) {
	eof = false;
	length = len;
    }

    /*
     * Create an <code>OptionalDataException</code> signifing no
     * more primitive data is available.
     */
    OptionalDataException(boolean end) {
	length = 0;
	eof = end;
    }

    /**
     * The number of bytes of primitive data available to be read
     * in the current buffer.
     */
    public int length;

    /**
     * True if there is no more data in the buffered part of the stream.
     */
    public boolean eof;
}
