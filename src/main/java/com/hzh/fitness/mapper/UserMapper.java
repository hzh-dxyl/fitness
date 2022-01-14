package com.hzh.fitness.mapper;

import com.hzh.fitness.po.User;

/**
 * @author hzh
 */
public interface UserMapper {

    int insertUser(User user);

    User selectUserByPhone(String phone);

    User selectUserById(int id);

    int deleteUserById(int id);

    int deleteUserByPhone(String phone);

    String selectHex(String imgHex);

    int updateUser(User user);

    User[] selectAllUsers();
}