package com.yokit.resource_management.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * login
 * @author 
 */
@Data
public class Login implements Serializable {
    private Integer loginId;

    private Integer userId;

    private Date loginTime;

    private static final long serialVersionUID = 1L;
}