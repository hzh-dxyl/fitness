package com.hzh.fitness.service;

import com.hzh.fitness.po.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author hzh
 */
public interface UserService {

    User registerUser(User user, byte[] imgData) throws Exception;

    User login(User user) throws Exception;

    User modifyUser(User user, byte[] imgData) throws Exception;

    User searchUser(String phone) throws Exception;

    User[] getAllUsers() throws Exception;
}
