/*
 * @(#)CORBAObjectImpl.java	1.13 02/02/06
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

package com.sun.corba.se.internal.corba;

//
// Bare implementation of CORBA Object.
//
public class CORBAObjectImpl extends org.omg.CORBA_2_3.portable.ObjectImpl {
    public String[] _ids() {
	String[] typeids = new String[1];
	typeids[0] = "IDL:omg.org/CORBA/Object:1.0";
	return typeids;
    }
}
