package com.hzh.fitness.common;

import com.hzh.fitness.mapper.ArticleMapper;
import com.hzh.fitness.mapper.UserMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author hzh
 * 定期清理无用的图片
 */
@Component
public class ImgClean {

    protected static final Log logger = LogFactory.getLog(ImgClean.class);
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected ArticleMapper articleMapper;


    @Scheduled(cron = "0 0 4 ? * sat")//每周六凌晨4点执行一次清理无用图片
    public void cleanImgScheduled() {
        logger.info("清理无用图片");
        int count = 0;
        String path = GlobalConstant.IMAGE_ROOT;
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            return;
        }
        File[] files = fileDir.listFiles();
        count += cleanImg(files);
        logger.info("清理了" + count + "张图片");

    }

    private int cleanImg(File[] files) {
        if (files == null) {
            return 0;
        }
        int count = 0;
        for (File file : files) {
            if (file.getName().contains("default")) {
                logger.debug("跳过" + file.getName());
                continue;
            }
            if (file.isFile()) {
                String hex = file.getName().substring(0, file.getName().indexOf('.'));
                int c = 0;
                c += userMapper.selectImgCount(hex);
                c += articleMapper.selectImgCount(hex);
                if (c == 0 && file.delete()) {
                    count += 1;
                }
            } else {
                count += cleanImg(file.listFiles());
            }
        }
        return count;
    }

}
