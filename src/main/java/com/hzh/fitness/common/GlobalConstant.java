package com.hzh.fitness.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hzh
 */
@Component
public class GlobalConstant {

    public static String PYTHON_PORT;
    public static String PYTHON_PATH;

    public static String PROJECT_ROOT;

    public static String IMAGE_ROOT;
    public static final String[] DEFAULT_HEAD_HEX={

    };

    @Value("${projectRoot}")
    public void setProjectRoot(String projectRoot) {
        PROJECT_ROOT = projectRoot;
    }

    @Value("${imageRoot}")
    public void setImageRoot(String imageRoot) {
        IMAGE_ROOT = imageRoot;
    }

    @Value("${pythonPath}")
    public void setPythonPath(String pythonPath) {
        PYTHON_PATH = pythonPath;
    }

    @Value("${pythonPort}")
    public void setPythonPort(String pythonPort) {
        PYTHON_PORT = pythonPort;
    }
}
