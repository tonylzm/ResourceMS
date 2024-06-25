package com.yokit.resource_management.framework.service;

import com.yokit.resource_management.entity.User;
import com.yokit.resource_management.framework.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fengzeng
 * @create 2021/6/10 22:59
 */
@Service
public class UserService {
  @Resource
  private UserDao userDao;

  public User authLogin(String loginName, String password) {
    return userDao.selectByUsernameAndPassword(loginName, password);
  }

  public void insertUser(User user) {
    userDao.insertSelective(user);
  }

  public User checkUserName(String username) {
    return userDao.selectByUserName(username);
  }

  public void updateUser(User user) {
    userDao.updateByPrimaryKeySelective(user);
  }

    public List<User> SelectUserAll() {
    return userDao.SelectUserAll();
    }

    public User selectUserById(Integer userId) {
    return userDao.selectByPrimaryKey(userId);
    }
}
