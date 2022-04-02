package com.hzh.fitness.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzh.fitness.common.LoginInfo;
import com.hzh.fitness.common.LoginList;
import com.hzh.fitness.common.MyResponse;
import com.hzh.fitness.exception.GlobalException;
import com.hzh.fitness.po.User;
import com.hzh.fitness.service.UserService;
import com.hzh.fitness.utils.ImageType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hzh
 */
@RestController
@RequestMapping("/users")
public class UserController {

    protected static final Log logger = LogFactory.getLog(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    LoginList loginList;

    /**
     * 用户登录
     *
     * @param user 用户登录信息
     * @return 对应用户信息和token
     */
    @PostMapping("/login")
    public MyResponse<User> login(@RequestBody User user) throws Exception {
        if (user.getPhone().length() != 11 || user.getPwdHex().length() != 40) {
            return MyResponse.createResponseByError("wrong format data");
        }
        User ret = userService.login(user);
        LoginInfo info = new LoginInfo(30, ret.getId());
        info.createToken();
        loginList.putInfo(info);
        return MyResponse.createResponseBySuccess("success", ret, info.getToken());
    }

    /**
     * 用户注册
     *
     * @param object 注册用户信息，包括user和imgData
     * @return 注册后的用户信息
     */
    @PostMapping("")
    public MyResponse<User> registerUser(@RequestBody JSONObject object) throws Exception {
        User user = object.getObject("user", User.class);
        byte[] imgData = object.getBytes("imgData");
        if (imgData != null && !ImageType.isImageName(user.getImg())) {
            return MyResponse.createResponseByError("not an image");
        }
        User ret = userService.registerUser(user, imgData);
        if (ret != null) {
            return MyResponse.createResponseBySuccess("success", ret);
        } else {
            return MyResponse.createResponseByError("failure");
        }
    }

    /**
     * 修改用户信息
     *
     * @param id     用户id
     * @param object 用户信息json字符串，不需要传全部
     * @return 修改后的用户信息
     */
    @PutMapping("/{id}")
    @PatchMapping("/{id}")
    public MyResponse<User> modifyUserWithToken(@PathVariable int id, @RequestBody JSONObject object) throws Exception {
        if (object == null) {
            return MyResponse.createResponseByError("empty requestBody");
        }
        User user = object.getObject("user", User.class);
        byte[] imgData = object.getBytes("imgData");
        if (user == null) {
            return MyResponse.createResponseByError("empty requestBody");
        }
        user.setId(id);
        User ret = userService.modifyUser(user, imgData);
        if (ret != null) {
            return MyResponse.createResponseBySuccess("success", user);
        } else {
            return MyResponse.createResponseByError("error");
        }
    }

    /**
     * 根据手机号查找用户
     *
     * @param phone 手机号
     * @return 对应用户信息
     * @throws Exception 抛出异常
     */
    @GetMapping("/{phone}")
    public MyResponse<User> searchUser(@PathVariable String phone) throws Exception {
        if (phone.length() != 11) {
            return MyResponse.createResponseByError("wrong format data");
        }
        User ret = userService.searchUser(phone);
        ret.setPwdHex(null);
        ret.setImgHex(null);
        return MyResponse.createResponseBySuccess("success", ret);
    }

    @PostMapping("/{id}/followers/{follower}")
    public MyResponse<JSONObject> addFollowerWithToken(@PathVariable int id, @PathVariable int follower) throws Exception {
        int i = userService.addFollower(id, follower);
        if (i == 1) {
            return MyResponse.createResponseBySuccess("success");
        } else {
            throw new GlobalException("failure");
        }
    }

    @DeleteMapping("/{id}/followers/{follower}")
    public MyResponse<JSONObject> deleteFollowerWithToken(@PathVariable int id, @PathVariable int follower) throws Exception {
        int i = userService.deleteFollower(id, follower);
        if (i == 1) {
            return MyResponse.createResponseBySuccess("success");
        } else {
            throw new GlobalException("failure");
        }
    }

    /**
     * 开发用，获取所有用户信息
     *
     * @return 所有用户信息
     * @throws Exception 抛出异常
     */
    @GetMapping("")
    public MyResponse<User[]> allUsers() throws Exception {
        return MyResponse.createResponseBySuccess("success", userService.getAllUsers());
    }
}
