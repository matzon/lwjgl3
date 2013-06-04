/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.system.linux;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.system.Platform;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.windows.WinBase.GetLastError;

public class LinuxPlatform implements Platform {

	public LinuxPlatform() {
	}

	@Override
	public boolean has64Bit() {
		return true;
	}

    @Override
    public long getTime() {
        return System.currentTimeMillis();
    }

    @Override
    public long getTimerResolution() {
        return 1000;
    }

    public static void linuxCheckHandle(long handle, String msg) {
		if ( handle == NULL )
			linuxThrowException(msg);
	}

	public static void linuxCheckResult(int result, String action) {
		if ( LWJGLUtil.DEBUG && result == 0 )
			throw new RuntimeException(action + " failed (error code = " + GetLastError() + ")");
	}

	public static void linuxThrowException(String msg) {
		throw new RuntimeException(msg + " (error code = " + GetLastError() + ")");
	}

}