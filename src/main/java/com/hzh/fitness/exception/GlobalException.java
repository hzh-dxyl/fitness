package com.hzh.fitness.exception;

/**
 * @author hzh
 */
public class GlobalException extends RuntimeException {

    private String msg;

    public GlobalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
