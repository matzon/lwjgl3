/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.system.glfw;

import org.lwjgl.api.VideoMode;

import java.nio.ByteBuffer;

/**
 * GLFW VideoMode implementation
 * @author Brian Matzon <brian@matzon.dk>
 */
public class GLFWVideoMode implements VideoMode {
    private final int width;
    private final int height;
    private final int red;
    private final int green;
    private final int blue;

    public GLFWVideoMode(int width, int height, int red, int green, int blue) {
        this.width = width;
        this.height = height;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public GLFWVideoMode(ByteBuffer videoMode) {
        this.width = GLFWvidmode.widthGet(videoMode);
        this.height = GLFWvidmode.heightGet(videoMode);
        this.red = GLFWvidmode.redBitsGet(videoMode);
        this.green = GLFWvidmode.greenBitsGet(videoMode);
        this.blue = GLFWvidmode.blueBitsGet(videoMode);
    }

    @Override
    public boolean isFullscreenCapable() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getBitsPerPixel() {
        return 8;
    }

    @Override
    public int getFrequency() {
        return 0;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
