/*
 * @(#)SolarisSystem.java	1.5 01/12/03
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.security.auth.module;

import javax.security.auth.*;
import javax.security.auth.login.*;

/**
 * <p> This class implementation retrieves and makes available Solaris
 * UID/GID/groups information for the current user.
 * 
 * @version 1.8, 01/11/00
 */
public class SolarisSystem {

    private native void getSolarisInfo();

    private static boolean loadedLibrary = false;

    protected String username;
    protected long uid;
    protected long gid;
    protected long[] groups;

    /**
     * Instantiate a <code>SolarisSystem</code> and load
     * the native library to access the underlying system information.
     */
    public SolarisSystem() {
	if (loadedLibrary == false) {
	    System.loadLibrary("jaas_unix");
	    loadedLibrary = true;
	}
	getSolarisInfo();
    }

    /**
     * Get the username for the current Solaris user.
     *
     * <p>
     *
     * @return the username for the current Solaris user.
     */
    public String getUsername() {
	return username;
    }

    /**
     * Get the UID for the current Solaris user.
     *
     * <p>
     *
     * @return the UID for the current Solaris user.
     */
    public long getUid() {
	return uid;
    }

    /**
     * Get the GID for the current Solaris user.
     *
     * <p>
     *
     * @return the GID for the current Solaris user.
     */
    public long getGid() {
	return gid;
    }

    /**
     * Get the supplementary groups for the current Solaris user.
     *
     * <p>
     *
     * @return the supplementary groups for the current Solaris user.
     */
    public long[] getGroups() {
	return groups;
    }
}
