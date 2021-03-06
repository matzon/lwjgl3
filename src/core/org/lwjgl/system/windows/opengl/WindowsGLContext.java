package org.lwjgl.system.windows.opengl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLContext;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.WGLARBCreateContext.*;
import static org.lwjgl.opengl.WGLARBCreateContextProfile.*;
import static org.lwjgl.opengl.WGLARBMakeCurrentRead.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.windows.WGL.*;
import static org.lwjgl.system.windows.WinBase.*;
import static org.lwjgl.system.windows.WindowsPlatform.*;

public class WindowsGLContext extends GLContext {

	private final long hglrc;

	public WindowsGLContext(ContextCapabilities capabilities, long hglrc) {
		super(capabilities);

		this.hglrc = hglrc;
	}

	@Override
	public long getHandle() {
		return hglrc;
	}

	@Override
	protected void makeCurrentImpl(long target) {
		if ( wglMakeCurrent(target, hglrc) == FALSE )
			windowsThrowException("Failed to make the OpenGL context current.");
	}

	@Override
	protected void makeCurrentImpl(long targetDraw, long targetRead) {
		if ( wglMakeContextCurrentARB(targetDraw, targetRead, hglrc) == FALSE )
			windowsThrowException("Failed to make the OpenGL context current.");
	}

	@Override
	public boolean isCurrent() {
		return wglGetCurrentContext() == hglrc;
	}

	/**
	 * Creates a {@link WindowsGLContext} from the current OpenGL context of the current thread.
	 *
	 * @return the new {@link WindowsGLContext}
	 *
	 * @throws IllegalStateException if no OpenGL context is current in the current thread.
	 */
	public static WindowsGLContext createFromCurrent() {
		long hglrc = wglGetCurrentContext();
		if ( hglrc == NULL )
			throw new IllegalStateException("No OpenGL context is current in the current thread.");

		ContextCapabilities capabilities = GL.createCapabilities(false);

		return new WindowsGLContext(capabilities, hglrc);
	}

	/**
	 * Creates an OpenGL context on the given device context and returns a {@code WindowContext} instance that describes it.
	 *
	 * @param hdc the device context
	 *
	 * @return the new WindowsContext
	 */
	public static WindowsGLContext create(long hdc) {
		WindowsGLContext context = createLegacy(hdc);
		try {
			return createARB(hdc);
		} finally {
			context.destroy();
		}
	}

	private static WindowsGLContext createLegacy(long hdc) {
		long hglrc = wglCreateContext(hdc);
		if ( hglrc == NULL )
			throw new RuntimeException("Failed to create OpenGL context.");

		try {
			int result = wglMakeCurrent(hdc, hglrc);
			if ( result == FALSE )
				throw new RuntimeException("Failed to make the new OpenGL context current.");

			return createFromCurrent();
		} catch (RuntimeException e) {
			wglDeleteContext(hglrc);
			throw e;
		}
	}

	private static WindowsGLContext createARB(long hdc) {
		IntBuffer attribs = BufferUtils.createIntBuffer(16);

		/*attribs.put(WGL_CONTEXT_MAJOR_VERSION_ARB);
		attribs.put(4);

		attribs.put(WGL_CONTEXT_MINOR_VERSION_ARB);
		attribs.put(2);*/

		attribs.put(WGL_CONTEXT_FLAGS_ARB);
		attribs.put(WGL_CONTEXT_DEBUG_BIT_ARB);

		attribs.put(WGL_CONTEXT_PROFILE_MASK_ARB);
		attribs.put(WGL_CONTEXT_COMPATIBILITY_PROFILE_BIT_ARB);

		attribs.put(0);
		attribs.flip();

		long hglrc = wglCreateContextAttribsARB(hdc, NULL, attribs);
		if ( hglrc == NULL )
			throw new RuntimeException("Failed to create OpenGL context.");

		try {
			int result = wglMakeCurrent(hdc, hglrc);
			if ( result == FALSE )
				throw new RuntimeException("Failed to make the new OpenGL context current.");

			return createFromCurrent();
		} catch (RuntimeException e) {
			wglDeleteContext(hglrc);
			throw e;
		}
	}

	@Override
	public void destroyImpl() {
		int result = wglDeleteContext(hglrc);
		if ( result == FALSE )
			throw new RuntimeException("Failed to delete OpenGL context.");
	}
}