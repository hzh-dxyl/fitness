package com.hzh.fitness.listener;

import com.hzh.fitness.common.GlobalConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author hzh
 */
@WebListener()
public class StartListener implements ServletContextListener {

    private final static Log logger = LogFactory.getLog(StartListener.class);

    /**
     * python程序子进程对象
     */
    private static Process proc = null;

    public StartListener() {
    }

    /**
     * 初始化时创建运行python子进程
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        new Thread() {
            @Override
            public void run() {
                try {
                    logger.debug("start python program");
                    String[] args = {"python", GlobalConstant.PYTHON_PATH, "0.0.0.0 " + GlobalConstant.PYTHON_PORT};
                    proc = new ProcessBuilder().redirectErrorStream(true).command(args).start();
                    BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        if ("listening".equals(line))
                            logger.debug(line);
                        if (!"b".equals(line.charAt(0)))
                            System.out.println(line);
                    }
                    in.close();
                    proc.waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        logger.debug("shut down the python program");
        proc.destroy();
    }

}
