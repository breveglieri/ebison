/*
 * @(#)IIOPAddress.java	1.13 01/12/04
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

//Source file: J:/ws/serveractivation/src/share/classes/com.sun.corba.se.internal.ior/IIOPAddress.java

package com.sun.corba.se.internal.ior;

import org.omg.CORBA_2_3.portable.OutputStream ;

/**
 * @author 
 */
public interface IIOPAddress 
{
    public void write( OutputStream os ) ;

    public String getHost() ;

    public int getPort() ;
}
