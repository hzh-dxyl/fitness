package com.hzh.fitness.service;

import com.hzh.fitness.po.User;

/**
 * @author hzh
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param user    用户对象
     * @param imgData 新头像字节数组
     * @return 注册好的用户对象
     * @throws Exception 抛出异常
     */
    User registerUser(User user, byte[] imgData) throws Exception;

    /**
     * 用户登陆
     *
     * @param user 用户对象，要有phone, pwdHex
     * @return 用户信息
     * @throws Exception 抛出异常
     */
    User login(User user) throws Exception;

    /**
     * @param user    要修改的用户数据，可以是要修改的可以是全部
     * @param imgData 新头像的字节数组
     * @return 修改后用户数据
     * @throws Exception 抛出异常
     */
    User modifyUser(User user, byte[] imgData) throws Exception;

    /**
     * 获取用户数据，通过phone查找
     *
     * @param phone 用户的phone
     * @return 要查找的用户数据
     * @throws Exception 抛出异常
     */
    User searchUser(String phone) throws Exception;

    /**
     * 获取所有用户信息
     *
     * @return 所有用户数组
     * @throws Exception 抛出异常
     */
    User[] getAllUsers() throws Exception;

    int addFollower(int id, int follower) throws Exception;

    int deleteFollower(int id, int follower) throws Exception;
}
