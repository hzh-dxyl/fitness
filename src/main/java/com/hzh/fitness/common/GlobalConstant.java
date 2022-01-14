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
    public static String PROJECT_HOST;
    public static String PYTHON_HOST;
    public static final String[] DEFAULT_HEAD_HEX = {
            "7f714737bbcded399458436308f508f5bb77201b",
            "e0da8634f28b156faa986d6651a392dd4a915979",
            "34a4a70f005bb646ed20bf94f621407f98c9c3d5",
            "15ff0209e28791a8f85d09ac4a6d1f5ae9acf07a"
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

    @Value("${projectHost}")
    public static void setProjectHost(String projectHost) {
        PROJECT_HOST = projectHost;
    }

    @Value("${pythonHost}")
    public static void setPythonHost(String pythonHost) {
        PYTHON_HOST = pythonHost;
    }
}
