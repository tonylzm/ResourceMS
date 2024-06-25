package com.yokit.resource_management.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    /**
     * 用户id，唯一标识
     */
    private Integer userId;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户角色,0为管理员，1为员工
     */
    private Integer roleId;


    private static final long serialVersionUID = 1L;

}