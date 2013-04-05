/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.api;

/**
 * This Interface encapsulates the properties for a given video mode.
 *
 * @see org.lwjgl.api.Monitor#getAvailableVideoModes()
 *
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @author Brian Matzon <brian@matzon.dk>
 */
public interface VideoMode {
    /**
     * True if this instance can be used for fullscreen modes
     */
    public boolean isFullscreenCapable();

    /**
     * @return width of this VideoMode
     */
    public int getWidth();

    /**
     * @return height of this VideoMode
     */
    public int getHeight();

    /**
     * @return bits per pixel of this VideoMode
     */
    public int getBitsPerPixel();

    /**
     * @return frequency of this VideoMode
     */
    public int getFrequency();
}