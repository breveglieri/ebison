/*
 * @(#)AuthPermission.java	1.47 02/02/01
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.security.auth;

/**
 * This class is for authentication permissions.
 * An AuthPermission contains a name
 * (also referred to as a "target name")
 * but no actions list; you either have the named permission
 * or you don't.
 *
 * <p> The target name is the name of a security configuration parameter
 * (see below).  Currently the AuthPermission object is used to
 * guard access to the Policy, Subject, LoginContext,
 * and Configuration objects.
 *
 * <p> The possible target names for an Authentication Permission are:
 *
 * <pre>
 *	doAs -			allow the caller to invoke the
 *				<code>Subject.doAs</code> methods.
 *
 *	doAsPrivileged -	allow the caller to invoke the
 *				<code>Subject.doAsPrivileged</code> methods.
 *
 *	getSubject -		allow for the retrieval of the
 *				Subject(s) associated with the
 *				current Thread.
 *
 *	getSubjectFromDomainCombiner -	allow for the retrieval of the
 *				Subject associated with the
 *				a <code>SubjectDomainCombiner</code>.
 *
 *	setReadOnly -		allow the caller to set a Subject
 *				to be read-only.
 *
 *	modifyPrincipals -	allow the caller to modify the <code>Set</code>
 *				of Principals associated with a
 *				<code>Subject</code>
 *
 *	modifyPublicCredentials - allow the caller to modify the
 *				<code>Set</code> of public credentials
 *				associated with a <code>Subject</code>
 *
 *	modifyPrivateCredentials - allow the caller to modify the
 *				<code>Set</code> of private credentials
 *				associated with a <code>Subject</code>
 *
 *	refreshCredential -	allow code to invoke the <code>refresh</code>
 *				method on a credential which implements
 *				the <code>Refreshable</code> interface.
 *
 *	destroyCredential -	allow code to invoke the <code>destroy</code>
 *				method on a credential <code>object</code>
 *				which implements the <code>Destroyable</code>
 *				interface.
 *
 *	createLoginContext.{name} -  allow code to instantiate a
 *				<code>LoginContext</code> with the
 *				specified <i>name</i>.  <i>name</i>
 *				is used as the index into the login
 *				<code>Configuration</code>.  <i>name</i>
 *				can be wildcarded (set to '*') to allow
 *				for any name.
 *
 *	getLoginConfiguration - allow for the retrieval of the system-wide
 *				login Configuration.
 *
 *	setLoginConfiguration - allow for the setting of the system-wide
 *				login Configuration.
 *
 *	refreshLoginConfiguration - allow for the refreshing of the system-wide
 *				login Configuration.
 * </pre>
 *
 * <p> The following target name has been deprecated in favor of
 * <code>createLoginContext.{name}</code>.
 *
 * <pre>
 *	createLoginContext -	allow code to instantiate a
 *				<code>LoginContext</code>.
 * </pre>
 *
 * <p> <code>javax.security.auth.Policy</code> has been
 * deprecated in favor of <code>java.security.Policy</code>.
 * Therefore, the following target names have also been deprecated:
 *
 * <pre>
 *	getPolicy -		allow the caller to retrieve the system-wide
 *				Subject-based access control policy.
 *
 *	setPolicy -		allow the caller to set the system-wide
 *				Subject-based access control policy.
 *
 *	refreshPolicy -		allow the caller to refresh the system-wide
 *				Subject-based access control policy.
 * </pre>
 *
 * @version 1.47, 02/01/02
 */
public final class AuthPermission extends
java.security.BasicPermission {

    /**
     * Creates a new AuthPermission with the specified name.
     * The name is the symbolic name of the AuthPermission.
     *
     * <p>
     *
     * @param name the name of the AuthPermission
     */
    public AuthPermission(String name) {
	// for backwards compatibility --
	// createLoginContext is deprecated in favor of createLoginContext.*
	super("createLoginContext".equals(name) ?
		"createLoginContext.*" : name);
    }

    /**
     * Creates a new AuthPermission object with the specified name.
     * The name is the symbolic name of the AuthPermission, and the
     * actions String is currently unused and should be null.
     *
     * <p>
     *
     * @param name the name of the AuthPermission <p>
     *
     * @param actions should be null.
     */
    public AuthPermission(String name, String actions) {
	// for backwards compatibility --
	// createLoginContext is deprecated in favor of createLoginContext.*
	super("createLoginContext".equals(name) ?
		"createLoginContext.*" : name, actions);
    }
}
