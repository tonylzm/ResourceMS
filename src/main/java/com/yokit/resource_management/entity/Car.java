package com.yokit.resource_management.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * car
 * @author 
 */
@Data
public class Car implements Serializable {
    /**
     * 汽车id 唯一标识
     */
    private Integer carId;

    /**
     * 所属资源id
     */
    private Integer resourceId;

    /**
     * 汽车名称
     */
    private String carName;

    /**
     * 汽车描述
     */
    private String carDesc;

    /**
     * 是否可用，0不可用，默认1可用，
     */
    private Object isActive;

    /**
     * 汽车图片地址
     */
    private String carPic;

    private static final long serialVersionUID = 1L;
}