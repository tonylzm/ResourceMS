package com.yokit.resource_management.framework.dao;

import com.yokit.resource_management.entity.Login;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author tonylin
 */
public interface LoginDao {
  int deleteByPrimaryKey(Integer loginId);

  int insert(Login record);

  int insertSelective(Login record);

  Login selectByPrimaryKey(Integer loginId);

  int updateByPrimaryKeySelective(Login record);

  int updateByPrimaryKey(Login record);

  int selectTotalCount();

  Date selectLoginTimeByUserId(Integer userId);

  int selectTodayCountByUserId(Integer userId);

  List<Login> selectLoginAll();

  String getPassword(@Param("userId")Integer userId);
  boolean passwordChange(@Param("newPassword") String newPassword,@Param("userId")Integer userId);

}
