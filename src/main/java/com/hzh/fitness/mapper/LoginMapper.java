package com.hzh.fitness.mapper;

import com.hzh.fitness.common.LoginInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author hzh
 */
public interface LoginMapper {

    ArrayList<LoginInfo> selectLogin();

    int insertLogins(List<LoginInfo> collection);

    int truncateLogin();
}
