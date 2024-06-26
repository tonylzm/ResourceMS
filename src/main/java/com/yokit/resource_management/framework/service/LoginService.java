package com.yokit.resource_management.framework.service;

import com.yokit.resource_management.entity.Login;
import com.yokit.resource_management.framework.dao.LoginDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author fengzeng
 * @create 2021/6/27 15:59
 */
@Service
public class LoginService {
  @Resource
  private LoginDao loginDao;

  public int selectTotalCount() {
    return loginDao.selectTotalCount();
  }

  public Date selectLoginTimeByUserId(Integer userId) {
    return loginDao.selectLoginTimeByUserId(userId);
  }

  public int selectTodayCountByUserId(Integer userId) {
    return loginDao.selectTodayCountByUserId(userId);
  }

  public void insert(Login login) {
    loginDao.insertSelective(login);
  }

  public List<Login> selectAll() {
    return loginDao.selectLoginAll();
  }

  public boolean verifyPassword(String oldPassword,Integer userId) {
    String password=loginDao.getPassword(userId);
    if(oldPassword.equals(password)){
      return true;
    }
    return false;
  }
  public boolean passwordChange(String newPassword,Integer userId){
    return  loginDao.passwordChange(newPassword,userId);
  }
}
