/*
 * @(#)FragmentMessage.java	1.6 01/12/03
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.corba.se.internal.iiop.messages;

/**
 * This interface captures the FragmentMessage contract.
 *
 * @author Ram Jeyaraman 05/14/2000
 * @version 1.0
 */

public interface FragmentMessage extends Message {
    int getRequestId();
    int getHeaderLength();
}
