/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.demo.api;

import org.lwjgl.Sys;
import org.lwjgl.api.Monitor;
import org.lwjgl.api.VideoMode;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Basic Monitor API test
 * @author Brian Matzon <brian@matzon.dk>
 */
@Test
public class MonitorTest {

    @BeforeTest
    public void init() {
        Sys.touch();
    }

    @Test
    public void testConnectedMonitors() {
        List<Monitor> monitors = getConnectedMonitors();
        assertNotEquals(0, monitors.size(), "Expected more than 0 connected monitors");

        for (Monitor monitor : monitors) {
            assertNotNull(monitor.getName(), "Expected monitor name");
            assertNotNull(monitor.getAdapter(), "Expected adapter name");
        }
    }

    @Test
    public void testPrimaryMonitor() {
        Monitor monitor = getPrimaryMonitor();
        assertNotNull(monitor, "Expected a primary monitor");
        assertNotNull(monitor.getName(), "Expected monitor name");
    }

    @Test
    public void testConnectedMonitorsVideoModes() {
        List<Monitor> monitors = getConnectedMonitors();
        for (Monitor monitor : monitors) {
            List<VideoMode> videoModes = monitor.getAvailableVideoModes();
            for (VideoMode videoMode : videoModes) {
                assertTrue(videoMode.getWidth() > 0, "Expected > 0 video mode width");
                assertTrue(videoMode.getHeight() > 0, "Expected > 0 video mode height");
                assertTrue(videoMode.getBitsPerPixel() > 0, "Expected > 0 video mode bpp");
                assertTrue(videoMode.getFrequency() > -1, "Expected > -1 video mode frequency");
            }
        }
    }

    @Test
    public void testPrimaryMonitorVideoModes() {
        Monitor monitor = getPrimaryMonitor();
        List<VideoMode> videoModes = monitor.getAvailableVideoModes();
        for (VideoMode videoMode : videoModes) {
            assertTrue(videoMode.getWidth() > 0, "Expected > 0 video mode width");
            assertTrue(videoMode.getHeight() > 0, "Expected > 0 video mode height");
            assertTrue(videoMode.getBitsPerPixel() > 0, "Expected > 0 video mode bpp");
            assertTrue(videoMode.getFrequency() > -1, "Expected > -1 video mode frequency");
        }
    }


    public static Monitor getPrimaryMonitor() {
        return Sys.getBackend().getMonitorImplementation().getPrimary();
    }

    public static List<Monitor> getConnectedMonitors() {
        return Sys.getBackend().getMonitorImplementation().getAvailable();
    }
}
