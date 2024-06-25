package com.yokit.resource_management;

import com.yokit.resource_management.entity.Login;
import com.yokit.resource_management.framework.dao.LoginDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ResourceManagementApplicationTests {

  @Resource
  private LoginDao loginDao;

  @Test
  void contextLoads() {
  }

  @Test
  public void testLoginTime() {
    Login login = loginDao.selectByPrimaryKey(1);
    System.out.println(login.getLoginTime());
  }
}
