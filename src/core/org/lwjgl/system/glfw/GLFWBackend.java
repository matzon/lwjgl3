package org.lwjgl.system.glfw;

import org.lwjgl.api.Monitor;
import org.lwjgl.system.Backend;

/**
 * Generic GLFW test
 * @author Brian Matzon <brian@matzon.dk>
 */
public class GLFWBackend implements Backend {

    @Override
    public Monitor getMonitorImplementation() {
        return new GLFWMonitor();
    }

    @Override
    public void initialize() {
        GLFW.glfwInit();
    }

    @Override
    public void destroy() {
        GLFW.glfwTerminate();
    }
}
