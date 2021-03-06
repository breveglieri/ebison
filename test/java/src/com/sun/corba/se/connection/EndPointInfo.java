/*
 * @(#)EndPointInfo.java	1.3 01/12/03
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.corba.se.connection;

/**
 * This interface is used to communicate endpoint information
 * between instances of <code>ORBSocketFactory</code> and its
 * associated ORB. 
 *
 */

public interface EndPointInfo
{
    public String getType();

    public String getHost();

    public int    getPort();
}
