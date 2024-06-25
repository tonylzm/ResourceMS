package com.yokit.resource_management.common.interceptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yokit.resource_management.common.annotation.JwtToken;
import com.yokit.resource_management.dto.ResponseDto;
import com.yokit.resource_management.entity.User;
import com.yokit.resource_management.utils.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author fengzeng
 * @create 2021/6/10 21:22
 */
public class SecurityInterceptor implements HandlerInterceptor {

  @Resource
  private JwtUtil jwtUtil;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    String token = request.getHeader("Authorization");
    //如果请求不是映射到方法则直接通过
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    //获取到请求的方法
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    Method method = handlerMethod.getMethod();

    //判断是否需要进行JWT验证
    if (method.isAnnotationPresent(JwtToken.class)) {

      //从annotation包中获取注解对象,判断它是否需要验证
      JwtToken jwtToken = method.getAnnotation(JwtToken.class);
      if (jwtToken.required()) {
        //执行认证
        if (token == null) {
          response.setStatus(401);
          ResponseDto responseObject = new ResponseDto(403, "未授权，请登录");
          objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
          String s = objectMapper.writeValueAsString(responseObject);
          response.setCharacterEncoding("utf-8");
          response.getWriter().write(s);
          return false;
        }
        //如果token存在，则验证token
        User user = jwtUtil.validateToken(token);
        if (user.getUserId() == -1) {
          return false;
        }
        request.setAttribute("user", user);
      }
      return true;
    }
    return true;
  }
}
