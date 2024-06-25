package com.yokit.resource_management.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * resource
 * @author 
 */
@Data
public class Resource implements Serializable {
    /**
     * 资源id
     */
    private Integer resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源是否有效
     */
    private Object isActive;

    private static final long serialVersionUID = 1L;
}