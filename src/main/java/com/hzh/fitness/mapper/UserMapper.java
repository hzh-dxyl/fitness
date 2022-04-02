package com.hzh.fitness.mapper;

import com.hzh.fitness.po.User;
import org.apache.ibatis.annotations.Param;

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

    int selectImgCount(String hex);

    int insertFollower(@Param("id") int id, @Param("follower") int follower);

    int deleteFollower(@Param("id") int id, @Param("follower") int follower);
}
