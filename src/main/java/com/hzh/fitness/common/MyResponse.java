package com.hzh.fitness.common;

/**
 * @author hzh
 */
public class MyResponse<T> {
    /**
     * 状态码,成功为0,失败为1
     */
    private int status;
    /**
     * 消息,成功消息或者失败消息
     */
    private String msg;
    /**
     * token,权限凭证
     */
    private String token;
    /**
     * 返回的数据
     */
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private MyResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private MyResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private MyResponse(int status, String msg, T data, String token) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.token = token;
    }

    /**
     * 状态码 + 成功提示信息
     */
    public static <T> MyResponse<T> createResponseBySuccess(String msg) {
        return new MyResponse<>(0, msg);
    }

    /**
     * 状态码 + 成功提示信息 + 数据
     */
    public static <T> MyResponse<T> createResponseBySuccess(String msg, T data) {
        return new MyResponse<>(0, msg, data);
    }

    /**
     * 状态码 + 成功提示信息 + 数据
     */
    public static <T> MyResponse<T> createResponseBySuccess(String msg, T data, String token) {
        return new MyResponse<>(0, msg, data, token);
    }

    /**
     * 状态码 + 错误信息
     */
    public static <T> MyResponse<T> createResponseByError(String msg) {
        return new MyResponse<>(1, msg);
    }
}