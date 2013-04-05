/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.glfw;

import org.lwjgl.*;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.glfw.GLFW;
import org.lwjgl.system.glfw.GLFWvidmode;

import java.lang.System;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Generic GLFW test
 * @author Brian Matzon <brian@matzon.dk>
 */
public class GLFWTest {

    static {
        Sys.touch();
    }

    public void versionTest() {
        IntBuffer major = BufferUtils.createIntBuffer(1);
        IntBuffer minor = BufferUtils.createIntBuffer(1);
        IntBuffer rev = BufferUtils.createIntBuffer(1);

        GLFW.glfwInit();
        GLFW.glfwGetVersion(major, minor, rev);
        GLFW.glfwTerminate();

        System.out.println(String.format("Initialized with GLFW version: %d.%d.%d", major.get(), minor.get(), rev.get()));
    }

    public void videoModeTest() {
        GLFW.glfwInit();

        long primaryMonitor = GLFW.glfwGetPrimaryMonitor();
        String monitorName = GLFW.glfwGetMonitorName(primaryMonitor);
        System.out.println("Primary monitor name: " + monitorName);

        PointerBuffer monitors = GLFW.glfwGetMonitors();
        System.out.println("Monitors: " + monitors.capacity());
        for (int i = 0; i < monitors.capacity(); i++) {
            System.out.println("monitor[" + i + "] name: " + GLFW.glfwGetMonitorName(monitors.get(i)));
        }

        for(int i=0; i<monitors.capacity(); i++) {
            long monitor = monitors.get(i);
            monitorName = GLFW.glfwGetMonitorName(monitor);

            ByteBuffer currentVideoMode = GLFWvidmode.malloc();
            GLFW.glfwGetVideoMode(monitor, currentVideoMode);
            System.out.println("current video mode for monitor: " + monitorName);
            System.out.println("  height: " + GLFWvidmode.heightGet(currentVideoMode));
            System.out.println("  width:  " + GLFWvidmode.widthGet(currentVideoMode));
            System.out.println("  red:    " + GLFWvidmode.redBitsGet(currentVideoMode));
            System.out.println("  green:  " + GLFWvidmode.greenBitsGet(currentVideoMode));
            System.out.println("  blue:   " + GLFWvidmode.blueBitsGet(currentVideoMode));

            System.out.println("all modes for monitor:");
            IntBuffer count = BufferUtils.createIntBuffer(1);
            ByteBuffer modes = GLFW.glfwGetVideoModes(monitor, count);

            int totalVideoModes = count.get(0);

            System.out.println("number of modes: " + totalVideoModes);

            for(int j=0; j<totalVideoModes; j++) {
                modes.position(j*GLFWvidmode.SIZEOF);
                System.out.println("mode[" + j + "]:");
                System.out.println("  height: " + GLFWvidmode.heightGet(modes));
                System.out.println("  width:  " + GLFWvidmode.widthGet(modes));
                System.out.println("  red:    " + GLFWvidmode.redBitsGet(modes));
                System.out.println("  green:  " + GLFWvidmode.greenBitsGet(modes));
                System.out.println("  blue:   " + GLFWvidmode.blueBitsGet(modes));
            }
        }
        GLFW.glfwTerminate();
    }

    public static void main(String[] args) {
        GLFWTest test = new GLFWTest();
        test.versionTest();
        test.videoModeTest();
    }

}
