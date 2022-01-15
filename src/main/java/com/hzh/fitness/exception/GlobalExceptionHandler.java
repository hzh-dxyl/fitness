package com.hzh.fitness.exception;

import com.alibaba.fastjson.JSONObject;
import com.hzh.fitness.common.MyResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

/**
 * @author hzh
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public MyResponse<JSONObject> commonExceptionHandler(Exception e) {
        return MyResponse.createResponseByError("server error");
    }

    @ExceptionHandler(FileNotFoundException.class)
    public MyResponse<JSONObject> fileNotFoundExceptionHandler() {
        return MyResponse.createResponseByError("file not found");
    }

    @ExceptionHandler(GlobalException.class)
    public MyResponse<JSONObject> globalExceptionHandler(GlobalException e) {
        return MyResponse.createResponseByError(e.getMessage());
    }

}
