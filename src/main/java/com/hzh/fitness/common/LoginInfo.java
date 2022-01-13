package com.hzh.fitness.common;

import com.hzh.fitness.utils.FileUtils;

/**
 * @author hzh
 */
public class LoginInfo {
    private long createTimestamp;
    private long expireTimestamp;
    private int id;
    private int userId;
    private String token;

    public LoginInfo() {
    }

    public LoginInfo(long expireTimeMills, int userId) {
        this.createTimestamp = System.currentTimeMillis();
        this.expireTimestamp = createTimestamp + expireTimeMills;
        this.userId = userId;
        createToken();
    }

    public LoginInfo(int expireTimeDays, int userId) {
        this.createTimestamp = System.currentTimeMillis();
        this.expireTimestamp = createTimestamp + (long) expireTimeDays * 24 * 60 * 60 * 1000;
        this.userId = userId;
        createToken();
    }

    public String createToken() {
        this.token = FileUtils.sha1String(String.valueOf(id) + createTimestamp + expireTimestamp);
        return token;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public long getExpireTimestamp() {
        return expireTimestamp;
    }

    public void setExpireTimestamp(long expireTimestamp) {
        this.expireTimestamp = expireTimestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
