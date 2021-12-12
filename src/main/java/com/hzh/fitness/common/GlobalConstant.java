package com.hzh.fitness.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hzh
 */
@Component
public class GlobalConstant {

    public static String PROJECT_ROOT;

    public static String IMAGE_ROOT;

    @Value("${projectRoot}")
    public void setProjectRoot(String projectRoot) {
        PROJECT_ROOT = projectRoot;
    }

    @Value("${imageRoot}")
    public void setImageRoot(String imageRoot) {
        IMAGE_ROOT = imageRoot;
    }
}
