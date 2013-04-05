/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package org.lwjgl.system;

import org.lwjgl.api.Monitor;

/**
 * Interface for LWJGL backends
 *
 * @author Brian Matzon <brian@matzon.dk>
 */
public interface Backend {
    public void initialize();
    public Monitor getMonitorImplementation();
    public void destroy();
}
