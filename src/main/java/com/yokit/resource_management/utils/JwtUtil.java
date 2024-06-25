package com.yokit.resource_management.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yokit.resource_management.common.config.AppConfig;
import com.yokit.resource_management.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author fengzeng
 * @create 2021/6/10 21:35
 */
@Component
public class JwtUtil {

  @Resource
  private AppConfig appConfig;

  /**
   * 传入用户对象，生成token
   * @param user
   * @return token
   */
  public  String createToken(User user) {
    //objectMapper用于序列化对象
    ObjectMapper objectMapper = new ObjectMapper();
    //将密钥进行base64加密
    String base64 = Base64.getEncoder().encodeToString(appConfig.getAppKey().getBytes(StandardCharsets.UTF_8));
    //将base64加密后的密钥生产密钥对象SecretKey
    SecretKey secretKey = Keys.hmacShaKeyFor(base64.getBytes(StandardCharsets.UTF_8));
    //去除敏感信息
    user.setLoginName(null);
    try {
      //将用户对象user序列化为json格式
      String userJson = objectMapper.writeValueAsString(user);
      //生产带有用户信息的token
      String jwtToken = Jwts.builder().setSubject(userJson).signWith(secretKey).compact();
      return jwtToken;
    } catch (Exception e) {
      return "token生成失败，请稍后再试";
    }
  }


  /**
   * 传入token，解密token，获取token中的负载（payload）信息
   * @param token
   * @return
   */
  public  User validateToken(String token) {
    //objectMapper用于序列化对象
    ObjectMapper objectMapper = new ObjectMapper();
    User user = new User();
    //将密钥进行base64加密
    String base64 = Base64.getEncoder().encodeToString(appConfig.getAppKey().getBytes(StandardCharsets.UTF_8));
    //生成密钥对象secretKey
    SecretKey secretKey = Keys.hmacShaKeyFor(base64.getBytes(StandardCharsets.UTF_8));
    //token解密，如果token校验失败，则会触发JwtException
    try {
      //Jws对象就是我们要的数据
      Jws<Claims> claimsJws = Jwts.parserBuilder()
              .setSigningKey(secretKey)
              .build()
              .parseClaimsJws(token);
      //获取Jws对象的内容Subject
      String subjectJson = claimsJws.getBody().getSubject();
      //对subjectJson进行反序列化成user对象
      user = objectMapper.readValue(subjectJson, User.class);
      //校验成功则返回user对象
      return user;
    } catch (JwtException | JsonProcessingException e) {
      e.printStackTrace();
      user.setUserId(-1);
      return user;
    }
  }
}
