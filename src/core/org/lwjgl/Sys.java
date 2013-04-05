/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl;

import org.lwjgl.system.Backend;
import org.lwjgl.system.Platform;
import org.lwjgl.system.glfw.GLFWBackend;
import org.lwjgl.system.windows.WindowsPlatform;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Sys class. The static initializers in this class must run before
 * anything else executes in LWJGL.
 */
public final class Sys {

	/** The native library name */
	private static final String JNI_LIBRARY_NAME = "lwjgl";
	private static final String POSTFIX64BIT     = "64";

    private static final Backend backend;
	private static final Platform platform;
	private static final String   nativeLibrary;

	/** Current version of library */
	private static final String VERSION = "3.0.0";

	static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if(backend != null) {
                    backend.destroy();
                }
            }
        });

		nativeLibrary = loadLibrary(JNI_LIBRARY_NAME);

        backend = new GLFWBackend();
        backend.initialize();

        platform = new WindowsPlatform();
	}

	private Sys() {
	}

	/** Dummy method to trigger the static initializers. */
	public static void touch() {
		// Intentionally empty
	}

	/** The Platform implementation. */
	public static Platform getPlatform() {
		return platform;
	}

	/** The native LWJGL library file that was loaded. */
	public static String getNativeLibrary() {
		return nativeLibrary;
	}

	private static String loadLibrary(String libraryName) {
		// actively try to load 64bit libs on 64bit architectures first
		String osArch = System.getProperty("os.arch");
		boolean is64bit = "amd64".equals(osArch) || "x86_64".equals(osArch);

		if ( is64bit ) {
			try {
				return doLoadLibrary(libraryName + POSTFIX64BIT);
			} catch (UnsatisfiedLinkError e) {
				LWJGLUtil.log("Failed to load 64 bit library: " + e.getMessage());
			}
		}

		// fallback to loading the "old way"
		try {
			return doLoadLibrary(libraryName);
		} catch (UnsatisfiedLinkError e) {
			if ( platform.has64Bit() ) {
				try {
					return doLoadLibrary(libraryName + POSTFIX64BIT);
				} catch (UnsatisfiedLinkError e2) {
					LWJGLUtil.log("Failed to load 64 bit library: " + e2.getMessage());
				}
			}
			// Throw original error
			throw e;
		}
	}

	private static String doLoadLibrary(final String libraryName) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			public String run() {
				String libraryPath = System.getProperty("org.lwjgl.librarypath");

				if ( libraryPath != null )
					System.load(libraryPath + File.separator + System.mapLibraryName(libraryName));
				else
					System.loadLibrary(libraryName);

				return libraryName;
			}
		});
	}

    public static Backend getBackend() {
        return backend;
    }

}