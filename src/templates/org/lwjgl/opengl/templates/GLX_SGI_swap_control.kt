/* 
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.opengl.templates

import org.lwjgl.generator.*
import org.lwjgl.opengl.*

fun GLX_SGI_swap_control() = "GLXSGISwapControl".nativeClassGLX("GLX_SGI_swap_control", SGI) {
	javaImport("org.lwjgl.linux.system")

	nativeImport (
		"OpenGL.h",
		"GLX.h"
	)

	javaDoc(
		"""
		Native bindings to the ${link("http://www.opengl.org/registry/specs/SGI/swap_control.txt", templateName)} extension.

		This extension allows an application to specify a minimum periodicity of color buffer swaps, measured in video frame periods.
		"""
	)

	GLint.func(
		"SwapIntervalSGI",
		"""
		Specifies the minimum number of video frame periods per buffer swap. (e.g. a value of two means that the color buffers will be swapped at most every
		other video frame.)  A return value of zero indicates success; otherwise an error occurred.  The interval takes effect when {@link GLX#glXSwapBuffers}
		is first called subsequent to the {@code glXSwapIntervalSGI} call.

		A video frame period is the time required by the monitor to display a full frame of video data.  In the case of an interlaced monitor, this is typically
		the time required to display both the even and odd fields of a frame of video data.
		""",

		GLint.IN("interval", "the swap interval")
	)
}