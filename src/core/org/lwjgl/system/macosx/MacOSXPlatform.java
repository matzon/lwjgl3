/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.system.macosx;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.system.Platform;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.windows.WinBase.GetLastError;

public class MacOSXPlatform implements Platform {

	public MacOSXPlatform() {
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

    public static void macOSXCheckHandle(long handle, String msg) {
		if ( handle == NULL )
            macOSXThrowException(msg);
	}

	public static void macOSXCheckResult(int result, String action) {
		if ( LWJGLUtil.DEBUG && result == 0 )
			throw new RuntimeException(action + " failed (error code = " + GetLastError() + ")");
	}

	public static void macOSXThrowException(String msg) {
		throw new RuntimeException(msg + " (error code = " + GetLastError() + ")");
	}

}