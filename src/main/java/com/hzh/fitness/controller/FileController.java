package com.hzh.fitness.controller;

import com.hzh.fitness.common.GlobalConstant;
import com.hzh.fitness.exception.GlobalException;
import com.hzh.fitness.utils.FileUtils;
import com.hzh.fitness.utils.ImageType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author hzh
 */
@Controller
public class FileController {

    protected static Log logger = LogFactory.getLog(FileController.class);

    @RequestMapping("/images/head/{img}")
    public void getHead(@PathVariable String img, HttpServletResponse response) throws Exception {
        if (!ImageType.isImageName(img)) {
            throw new GlobalException("invalid image name");
        }
        ImageType type = ImageType.getEnum(img);
        String path = GlobalConstant.IMAGE_ROOT + "/head/" + img;
        logger.info("图片路径: " + path);
        String mime = String.format("%s;charset=utf-8", type.getMime());
        response.setContentType(mime);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(FileUtils.fileToBytes(path));
        outputStream.flush();
        outputStream.close();
    }

    @RequestMapping("/images/articleImg/{img}")
    public void getArticleImg(@PathVariable String img, HttpServletResponse response) throws Exception {
        if (ImageType.isImageName(img)) {
            ImageType type = ImageType.getEnum(img);
            String path = GlobalConstant.IMAGE_ROOT + "/articleImg/" + img;
            logger.info("图片路径: " + path);
            String mime = String.format("%s;charset=utf-8", type.getMime());
            response.setContentType(mime);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtils.fileToBytes(path));
            outputStream.flush();
            outputStream.close();
        } else if ("mp4".equals(img.split("\\.")[1])) {
            File file = new File(GlobalConstant.IMAGE_ROOT + "/articleImg/" + img);
            response.setContentType("video/mp4;charset=utf-8");
            OutputStream out = response.getOutputStream();
            out.write(FileUtils.fileToBytes(file));
            out.flush();
            out.close();
        } else {
            throw new GlobalException("invalid image name");
        }

    }

    @RequestMapping("/files/{filename}")
    public void getFile(@PathVariable String filename, HttpServletResponse response) throws Exception {
        String path = GlobalConstant.IMAGE_ROOT + "/" + filename;
        logger.info("文件路径: " + path);
        File file = new File(path);
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(filename, "utf-8"));
        OutputStream out = response.getOutputStream();
        out.write(FileUtils.fileToBytes(file));
        out.flush();
        out.close();
    }

    @RequestMapping("/videos/{filename}")
    public void getVideo(@PathVariable String filename, HttpServletResponse response) throws Exception {
        File file = new File(GlobalConstant.VIDEO_ROOT + "/" + filename);
        response.setContentType("video/mp4;charset=utf-8");
        OutputStream out = response.getOutputStream();
        out.write(FileUtils.fileToBytes(file));
        out.flush();
        out.close();
    }
}
