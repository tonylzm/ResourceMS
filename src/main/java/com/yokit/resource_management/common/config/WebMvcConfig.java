package com.yokit.resource_management.common.config;

import com.yokit.resource_management.common.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fengzeng
 * @create 2021/6/10 21:18
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


  @Value("${user.file.path}")
  private String imagPath;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(securityInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/image/**").addResourceLocations("file:" + imagPath);
  }

  /**
   * 在spring ioc 容器启动时将SecurityInterceptor对象放入Ioc进行管理
   * * beanId = securityInterceptor
   * @return SecurityInterceptor
   */
  @Bean
  public SecurityInterceptor securityInterceptor() {
    return new SecurityInterceptor();
  }
}
