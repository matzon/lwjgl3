package org.lwjgl.system.glfw;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.PointerBuffer;
import org.lwjgl.api.Monitor;
import org.lwjgl.api.VideoMode;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * GLFW Monitor implementation
 * @author Brian Matzon <brian@matzon.dk>
 */
public class GLFWMonitor extends Monitor {

    private long glfwMonitor = -1;

    private int width;
    private int height;

    private GLFWVideoMode desktopVideoMode;

    public GLFWMonitor() {
    }

    private GLFWMonitor(final long glfwMonitor) {
        this.glfwMonitor = glfwMonitor;

        ByteBuffer widthBuffer = BufferUtils.createByteBuffer(4);
        ByteBuffer heightBuffer = BufferUtils.createByteBuffer(4);
        GLFW.glfwGetMonitorPhysicalSize(glfwMonitor, widthBuffer, heightBuffer);
        width = widthBuffer.get();
        height = heightBuffer.get();

        ByteBuffer currentVideoMode = GLFWvidmode.malloc();
        GLFW.glfwGetVideoMode(glfwMonitor, currentVideoMode);

        desktopVideoMode = new GLFWVideoMode(currentVideoMode);
    }

    @Override
    public List<Monitor> getAvailable() {
        ArrayList<Monitor> monitorList = new ArrayList<Monitor>();
        PointerBuffer monitors = GLFW.glfwGetMonitors();
        if(monitors == null || monitors.remaining() <= 0) {
            LWJGLUtil.log("GLFWMonitor: no monitors attached!");
        } else {
            while(monitors.hasRemaining()) {
                long monitorPointer = monitors.get();
                LWJGLUtil.log("GLFWMonitor: adding new monitor @ " + monitorPointer);
                monitorList.add(new GLFWMonitor(monitorPointer));
            }
        }

        return monitorList;
    }

    @Override
    public Monitor getPrimary() {
        return new GLFWMonitor(GLFW.glfwGetPrimaryMonitor());
    }

    @Override
    public String getName() {
        return GLFW.glfwGetMonitorName(glfwMonitor);
    }

    @Override
    public List<VideoMode> getAvailableVideoModes() {
        ArrayList<VideoMode> videoModeList = new ArrayList<VideoMode>();

        IntBuffer videoModesCountBuffer = BufferUtils.createIntBuffer(1);
        ByteBuffer videoModes = GLFW.glfwGetVideoModes(glfwMonitor, videoModesCountBuffer);

        int videoModesCount = videoModesCountBuffer.get();
        if(videoModes == null || videoModesCount <= 0) {
            LWJGLUtil.log("GLFWMonitor: no available videomodes!");
        } else {
            for(int i=0; i<videoModesCount; i++) {
                videoModes.position(i*GLFWvidmode.SIZEOF);
                int w = GLFWvidmode.widthGet(videoModes);
                int h = GLFWvidmode.heightGet(videoModes);
                int r = GLFWvidmode.redBitsGet(videoModes);
                int g = GLFWvidmode.greenBitsGet(videoModes);
                int b = GLFWvidmode.blueBitsGet(videoModes);
                videoModeList.add(new GLFWVideoMode(w, h, r, g, b));
            }
        }

        return videoModeList;
    }

    @Override
    public VideoMode getDesktopVideoMode() {
        return desktopVideoMode;
    }

    @Override
    public VideoMode getVideoMode() {
        ByteBuffer currentVideoMode = GLFWvidmode.malloc();
        GLFW.glfwGetVideoMode(glfwMonitor, currentVideoMode);
        return new GLFWVideoMode(currentVideoMode);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setVideoMode(VideoMode mode) {
    }

    @Override
    public void setvideoConfiguration(float gamma, float brightness, float contrast) {
        GLFW.glfwSetGamma(glfwMonitor, gamma);
    }

    @Override
    public void sync(int fps) {
    }

    @Override
    public String getAdapter() {
        return "";
    }
}
