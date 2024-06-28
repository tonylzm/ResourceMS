package com.yokit.resource_management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tonylin
 */

@SpringBootApplication
@MapperScan("com.yokit.resource_management.framework.dao")
public class ResourceManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(ResourceManagementApplication.class, args);
  }

}
