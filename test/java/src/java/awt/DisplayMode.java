/*
 * @(#)DisplayMode.java	1.3 01/12/03
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.awt;

/**
 * The <code>DisplayMode</code> class encapsulates the bit depth, height,
 * width, and refresh rate of a <code>GraphicsDevice</code>.  Display modes
 * are hardware-dependent and may not always be available.
 * @see GraphicsDevice
 * @author Michael Martak
 * @since 1.4
 */
public final class DisplayMode {
    
    private Dimension size;
    private int bitDepth;
    private int refreshRate;
        
    /**
     * Create a new display mode object with the supplied parameters
     * @width the width of the display, in pixels
     * @height the height of the display, in pixels
     * @bitDepth the bit depth of the display, in bits per pixel.  This can be
     * <code>BIT_DEPTH_MULTI</code> if multiple bit depths are available.
     * @refreshRate the refresh rate of the display, in hertz.  This can be
     * <code>REFRESH_RATE_UNKNOWN</code> if the information is not available.
     * @see #BIT_DEPTH_MULTI
     * @see #REFRESH_RATE_UNKNOWN
     */
    public DisplayMode(int width, int height, int bitDepth, int refreshRate) {
        this.size = new Dimension(width, height);
        this.bitDepth = bitDepth;
        this.refreshRate = refreshRate;
    }
    
    /**
     * @return the height of the display, in pixels
     */
    public int getHeight() {
        return size.height;
    }
    
    /**
     * @return the width of the display, in pixels
     */
    public int getWidth() {
        return size.width;
    }
    
    /**
     * Value of the bit depth if multiple bit depths are supported in this
     * dislay mode.
     * @see #getBitDepth
     */
    public final static int BIT_DEPTH_MULTI = -1;
    /**
     * @return the bit depth of the display, in bits per pixel.  This may be
     * <code>BIT_DEPTH_MULTI</code> if multiple bit depths are supported in
     * this display mode.
     * @see #BIT_DEPTH_MULTI
     */
    public int getBitDepth() {
        return bitDepth;
    }
    
    /**
     * Value of the refresh rate if not known
     * @see #getRefreshRate
     */
    public final static int REFRESH_RATE_UNKNOWN = 0;
    /**
     * @return the refresh rate of the display, in hertz.  This may be
     * <code>REFRESH_RATE_UNKNOWN</code> if the information is not available.
     * @see #REFRESH_RATE_UNKNOWN
     */
    public int getRefreshRate() {
        return refreshRate;
    }

    /**
     * @return whether the two display modes are equal
     */
    public boolean equals(DisplayMode dm) {
        return (getHeight() == dm.getHeight()
            && getWidth() == dm.getWidth()
            && getBitDepth() == dm.getBitDepth()
            && getRefreshRate() == dm.getRefreshRate());
    }
    
    /**
     * @return a hash code value for this object
     */
    public int hashCode() {
        return getWidth() + getHeight() + getBitDepth() * 7
            + getRefreshRate() * 13;
    }
    
}
