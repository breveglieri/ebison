/*
 * @(#)PrivilegedExceptionAction.java	1.6 00/02/02
 *
 * Copyright 1998-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package java.security;


/**
 * A computation to be performed with privileges enabled, that throws one or
 * more checked exceptions.  The computation is performed by invoking
 * <code>AccessController.doPrivileged</code> on the
 * <code>PrivilegedExceptionAction</code> object.  This interface is
 * used only for computations that throw checked exceptions;
 * computations that do not throw
 * checked exceptions should use <code>PrivilegedAction</code> instead.
 *
 * @see AccessController
 * @see AccessController#doPrivileged(PrivilegedExceptionAction)
 * @see AccessController#doPrivileged(PrivilegedExceptionAction,
 *                                              AccessControlContext)
 * @see PrivilegedAction
 */

public interface PrivilegedExceptionAction {
    /**
     * Performs the computation.  This method will be called by
     * <code>AccessController.doPrivileged</code> after enabling privileges.
     *
     * @return a class-dependent value that may represent the results of the
     *	       computation.  Each class that implements
     *	       <code>PrivilegedExceptionAction</code> should document what
     *         (if anything) this value represents.
     * @throws Exception an exceptional condition has occurred.  Each class
     *	       that implements <code>PrivilegedExceptionAction</code> should
     *         document the exceptions that its run method can throw.
     * @see AccessController#doPrivileged(PrivilegedExceptionAction)
     * @see AccessController#doPrivileged(PrivilegedExceptionAction,AccessControlContext)
     */

    Object run() throws Exception;
}