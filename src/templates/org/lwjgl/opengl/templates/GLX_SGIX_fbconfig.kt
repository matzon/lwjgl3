/* 
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.opengl.templates

import org.lwjgl.generator.*
import org.lwjgl.opengl.*
import org.lwjgl.system.linux.*

fun GLX_SGIX_fbconfig() = "GLXSGIXFBConfig".nativeClassGLX("GLX_SGIX_fbconfig", SGIX) {
	javaImport("org.lwjgl.system.linux.*")

	nativeImport(
		"<GL/glx.h>"
	)

	javaDoc(
		"""
		Native bindings to the ${link("http://www.opengl.org/registry/specs/SGIX/fbconfig.txt", templateName)} extension.

		This extension introduces a new way to describe the capabilities of a GLX drawable (i.e., to describe the depth of color buffer components and the type
		and size of ancillary buffers), removes the "similarity" requirement when making a context current to a drawable, and supports  RGBA rendering to
		one-and two-component Windows and GLX Pixmaps.
		"""
	)

	IntConstant.block(
		"Accepted by the {@code attribute} parameter of {@link #glXGetFBConfigAttribSGIX}, and by the {@code attrib_list} parameter of {@link #glXChooseFBConfigSGIX}:",

		"DRAWABLE_TYPE_SGIX" _ 0x8010,
		"RENDER_TYPE_SGIX" _ 0x8011,
		"X_RENDERABLE_SGIX" _ 0x8012
	)

	IntConstant.block(
		"""
		Accepted by the {@code attribute} parameter of {@link #glXGetFBConfigAttribSGIX}, the {@code attrib_list} parameter of {@link #glXChooseFBConfigSGIX},
		by the {@code attribute} parameter of {@link GLXSGIXPbuffer#glXQueryGLXPbufferSGIX} and by the {@code attribute} parameter of
		{@link GLXEXTImportContext#glXQueryContextInfoEXT}:
		""",

		"FBCONFIG_ID_SGIX" _ 0x8013
	)

	IntConstant.block(
		"Accepted by the {@code attribute} parameter of {@link #glXGetFBConfigAttribSGIX}:",

		"SCREEN_EXT" _ 0x800C
	)

	IntConstant.block(
		"""
		Returned by {@link #glXGetFBConfigAttribSGIX} (when {@code attribute} is set to {@link #GLX_DRAWABLE_TYPE_SGIX}) and accepted by the {@code attrib_list}
		parameter of {@link #glXChooseFBConfigSGIX} (following the {@link #GLX_DRAWABLE_TYPE_SGIX} token):
		""",

		"WINDOW_BIT_SGIX" _ 0x00000001,
		"PIXMAP_BIT_SGIX" _ 0x00000002
	)

	IntConstant.block(
		"""
		Returned by {@link #glXGetFBConfigAttribSGIX} (when {@code attribute} is set to {@link #GLX_RENDER_TYPE_SGIX}) and accepted by the {@code attrib_list}
		parameter of {@link #glXChooseFBConfigSGIX} (following the {@link #GLX_RENDER_TYPE_SGIX} token):
		""",

		"RGBA_BIT_SGIX" _ 0x00000001,
		"COLOR_INDEX_BIT_SGIX" _ 0x00000002
	)

	val RenderTypes = IntConstant.block(
		"Accepted by the {@code render_type} parameter of {@link #glXCreateContextWithConfigSGIX}:",

		"RGBA_TYPE_SGIX" _ 0x8014,
		"COLOR_INDEX_TYPE_SGIX" _ 0x8015
	).toJavaDocLinks()

	int.func(
		"GetFBConfigAttribSGIX",
		"Gets the value of a GLX attribute for a {@code GLXFBConfigSGIX}.",

		DISPLAY,
		GLXFBConfigSGIX.IN("config", "the {@code GLXFBConfigSGIX} being queried"),
		int.IN("attribute", "the attribute to query"),
		Check(1) _ int_p.OUT("value", "returns the attribute value")
	)

	GLXFBConfigSGIX_p.func(
		"ChooseFBConfigSGIX",
		"Gets {@code GLXFBConfigSGIX}s that match a list of attributes or to get the list of GLXFBConfigSGIXs that are available on the specified screen.",

		DISPLAY,
		int.IN("screen", "the screen number"),
		mods(const, nullable) _ int_p.IN("attrib_list", "an optional list of attributes, terminated with {@link org.lwjgl.system.linux.X#None}"),
		autoSizeResult _ int_p.OUT("nelements", "the number of {@code GLXFBConfigSGIX} returned")
	)

	GLXPixmap.func(
		"CreateGLXPixmapWithConfigSGIX",
		"Creates a GLX pixmap using a {@code GLXFBConfigSGIX}.",

		DISPLAY,
		GLXFBConfig.IN("config", "the {@code GLXFBConfigSGIX}"),
		Pixmap.IN("pixmap", "the pixmap")
	)

	GLXContext.func(
		"CreateContextWithConfigSGIX",
		"Creates a GLX context using a {@code GLXFBConfigSGIX}.",

		DISPLAY,
		GLXFBConfig.IN("config", "the {@code GLXFBConfigSGIX}"),
		int.IN("render_type", "the render type", RenderTypes),
		GLXContext.IN("share_list", "the GLX context to share objects with"),
		Bool.IN("direct", "direct rendering request")
	)

	XVisualInfo_p.func(
		"GetVisualFromFBConfigSGIX",
		"Retrieves the associated visual of a {@code GLXFBConfigSGIX}.",

		DISPLAY,
		GLXFBConfig.IN("config", "the {@code GLXFBConfigSGIX}")
	)

	GLXFBConfigSGIX.func(
		"GetFBConfigFromVisualSGIX",
		"Retrieves the {@code GLXFBConfigSGIX} associated with a visual.",

		DISPLAY,
		XVisualInfo_p.IN("vis", "the visual")
	)

}