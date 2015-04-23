/*
 * @(#)IDLTypeHelper.java	1.4 00/02/02
 *
 * Copyright 1999, 2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package org.omg.CORBA;


/**
* org/omg/CORBA/IDLTypeHelper.java
* Generated by the IDL-to-Java compiler (portable), version "3.0"
* from ir.idl
* 03 June 1999 11:33:44 o'clock GMT+00:00
*/

abstract public class IDLTypeHelper
{
  private static String  _id = "IDL:omg.org/CORBA/IDLType:2.3";

  public static void insert (org.omg.CORBA.Any a, org.omg.CORBA.IDLType that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.CORBA.IDLType extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.omg.CORBA.IDLTypeHelper.id (), "IDLType");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.CORBA.IDLType read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_IDLTypeStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.CORBA.IDLType value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static org.omg.CORBA.IDLType narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.omg.CORBA.IDLType)
      return (org.omg.CORBA.IDLType)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      return new org.omg.CORBA._IDLTypeStub (delegate);
    }
  }

}