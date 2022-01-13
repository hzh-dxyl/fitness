package com.hzh.fitness.common;

import com.hzh.fitness.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author hzh
 */
@Component
public class LoginList {

    @Autowired
    private LoginMapper mapper;

    private final HashMap<String, LoginInfo> list = new HashMap<>();

    public void putInfo(LoginInfo info) {
        for (LoginInfo info1 : list.values()) {
            if (info1.getUserId() == info.getUserId()) {
                list.remove(info1.getToken());
                break;
            }
        }
        list.put(info.getToken(), info);
    }

    public String putInfo(int id, int durationDays) {
        LoginInfo info = new LoginInfo(durationDays, id);
        info.createToken();
        putInfo(info);
        return info.getToken();
    }

    public LoginInfo getInfo(String token) {
        return list.get(token);
    }

    public boolean checkLogin(String token) {
        LoginInfo info = list.get(token);
        if (info == null) {
            return false;
        } else {
            return info.getExpireTimestamp() >= System.currentTimeMillis();
        }
    }

    @PostConstruct
    public void readFromDB() {
        ArrayList<LoginInfo> loginInfos = mapper.selectLogin();
        if (loginInfos != null) {
            for (LoginInfo info : loginInfos) {
                list.put(info.getToken(), info);
            }
        }
    }

    @PreDestroy
    public void writeToDB() {
        mapper.truncateLogin();
        mapper.insertLogins(new ArrayList<>(list.values()));
    }

}
