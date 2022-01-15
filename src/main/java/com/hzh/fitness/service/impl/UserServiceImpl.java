package com.hzh.fitness.service.impl;

import com.hzh.fitness.common.GlobalConstant;
import com.hzh.fitness.exception.GlobalException;
import com.hzh.fitness.mapper.UserMapper;
import com.hzh.fitness.po.User;
import com.hzh.fitness.service.UserService;
import com.hzh.fitness.utils.FileUtils;
import com.hzh.fitness.utils.ImageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author hzh
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User registerUser(User user, byte[] imgData) throws Exception {
        int result;
        if (imgData == null) {
            int r = new Random().nextInt(4);
            user.setImg("head/default" + r + ".png");
            user.setImgHex(GlobalConstant.DEFAULT_HEAD_HEX[r]);
        } else {
            String hex = FileUtils.sha1String(imgData);
            user.setImg("head/" + hex + "." + ImageType.getExt(user.getImg()));
            user.setImgHex(hex);
            hex = userMapper.selectHex(user.getImgHex());
            if (hex == null) {
                FileUtils.bytesToFile(imgData, GlobalConstant.IMAGE_ROOT + "/head/", user.getImg());
            }
        }
        result = userMapper.insertUser(user);
        if (result == 1) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User login(User user) throws Exception {
        User checkUser = userMapper.selectUserByPhone(user.getPhone());
        if (checkUser == null) {
            throw new GlobalException("invalid phone");
        } else if (!checkUser.getPwdHex().equals(user.getPwdHex())) {
            throw new GlobalException("wrong pwd");
        }
        return checkUser;
    }

    @Override
    public User modifyUser(User user, byte[] imgData) throws Exception {
        if (imgData != null) {
            String hex = FileUtils.sha1String(imgData);
            user.setImgHex(hex);
            user.setImg("head/" + hex + "." + ImageType.getExt(user.getImg()));
            hex = userMapper.selectHex(user.getImgHex());
            if (hex == null) {
                FileUtils.bytesToFile(imgData, GlobalConstant.IMAGE_ROOT + "/head/", user.getImg());
            }
        } else {
            user.setImg(null);
            user.setImgHex(null);
        }
        int status = userMapper.updateUser(user);
        if (status == 1) {
            if (user.getId() != 0) {
                user = userMapper.selectUserById(user.getId());
            } else {
                user = userMapper.selectUserByPhone(user.getPhone());
            }
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User searchUser(String phone) throws Exception {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public User[] getAllUsers() throws Exception {
        return userMapper.selectAllUsers();
    }
}
