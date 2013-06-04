/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.system;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.system.linux.LinuxLibrary;
import org.lwjgl.system.windows.WindowsLibrary;

import java.lang.reflect.Method;

public final class APIUtil {

	private static final ThreadLocal<APIBuffer> API_BUFFERS = new ThreadLocal<APIBuffer>() {
		protected APIBuffer initialValue() {
			return new APIBuffer();
		}
	};

	private APIUtil() {
	}

	/**
	 * Returns the callback method for the given callback class. The
	 * callback method should be named "invoke" and the parameter types
	 * should not contain the user data parameter.
	 *
	 * @param clazz          the callback class
	 * @param parameterTypes the callback function parameters
	 *
	 * @return the callback method
	 */
	@SuppressWarnings("unchecked")
	public static Method apiCallbackMethod(Class clazz, Class<?>... parameterTypes) {
		try {
			return clazz.getMethod("invoke", parameterTypes);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	/** Returns a thread-local APIBuffer. */
	public static APIBuffer apiBuffer() {
		return API_BUFFERS.get().reset();
	}

	public static DynamicLinkLibrary apiCreateLibrary(String name) {
		switch ( LWJGLUtil.getPlatform() ) {
			case WINDOWS:
				return new WindowsLibrary(name);
			case LINUX:
				return new LinuxLibrary(name);
			case MACOSX:
				throw new UnsupportedOperationException("not implemented yet");
			default:
				throw new IllegalStateException();
		}
	}

}