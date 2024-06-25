package com.yokit.resource_management.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * room
 * @author 
 */
@Data
public class Room implements Serializable {
    /**
     * 会议室id
     */
    private Integer roomId;

    /**
     * 会议室名称
     */
    private String roomName;

    /**
     * 会议室描述
     */
    private String roomDesc;

    /**
     * 容纳人数
     */
    private Integer personNumber;

    /**
     * 会议室是否可用
     */
    private Object isActive;

    /**
     * 会议室图片地址
     */
    private String roomPic;

    /**
     * 资源id
     */
    private Integer resourceId;

    private static final long serialVersionUID = 1L;
}