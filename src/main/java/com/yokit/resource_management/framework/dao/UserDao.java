package com.yokit.resource_management.framework.dao;

import com.yokit.resource_management.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Integer userId);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    User selectByUsernameAndPassword(@Param("loginName") String loginName, @Param("password") String password);

    User selectByUserName(String username);

    List<User> SelectUserAll();
}