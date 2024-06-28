package com.yokit.resource_management.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author tonylin
 * @create 2024/6/30 21:17
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
