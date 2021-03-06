/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.system;

/** A provider of native function addresses. */
public interface FunctionProvider {

	/**
	 * Returns the function address of the specified function. If the function
	 * is not supported by the platform or context, 0L is returned.
	 *
	 * @param functionName the function name to query
	 *
	 * @return the function address or 0L if the function is not supported
	 */
	long getFunctionAddress(String functionName);

	void destroy();

}