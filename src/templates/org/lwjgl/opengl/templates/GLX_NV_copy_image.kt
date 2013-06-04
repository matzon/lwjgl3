/* 
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.opengl.templates

import org.lwjgl.generator.*
import org.lwjgl.opengl.*
import org.lwjgl.system.linux.*

fun GLX_NV_copy_image() = "GLXNVCopyImage".nativeClassGLX("GLX_NV_copy_image", NV) {
	javaImport("org.lwjgl.linux.system")

	javaDoc(
		"""
		Native bindings to the ${link("http://www.opengl.org/registry/specs/NV/copy_image.txt", templateName)} extension.

		This extension enables efficient image data transfer between image objects (i.e. textures and renderbuffers) without the need to bind the objects or
		otherwise configure the rendering pipeline. The GLX version allows copying between images in different contexts, even if those contexts are in different
		sharelists or even on different physical devices.
		"""
	)

	GLvoid.func(
		"CopyImageSubDataNV",
		"""
		Behaves identically to the core function {@link NVCopyImage#glCopyImageSubDataNV}, except that the {@code srcCtx} and {@code dstCtx} parameters specify
		the contexts in which to look up the source and destination objects, respectively. A value of $NULL for either context indicates that the value which is
		returned by {@link {@link GLX#glXGetCurrentContext}} should be used instead. Both contexts must share the same address space.
		""",

		DISPLAY,
		nullable _ GLXContext.IN("srcCtx", "the source context"),
		GLuint.IN("srcName", ""),
		GLenum.IN("srcTarget", ""),
		GLint.IN("srcLevel", ""),
		GLint.IN("srcX", ""),
		GLint.IN("srcY", ""),
		GLint.IN("srcZ", ""),
		nullable _ GLXContext.IN("dstCtx", "the destination context"),
		GLuint.IN("dstName", ""),
		GLenum.IN("dstTarget", ""),
		GLint.IN("dstLevel", ""),
		GLint.IN("dstX", ""),
		GLint.IN("dstY", ""),
		GLint.IN("dstZ", ""),
		GLsizei.IN("width", ""),
		GLsizei.IN("height", ""),
		GLsizei.IN("depth", "")
	)

}