package com.yokit.resource_management.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengzeng
 * @create 2021/6/10 21:17
 */
@Configuration
public class AppConfig {
  @Value("${app.secretKey}")
  private String appKey;

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }
}
