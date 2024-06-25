package com.yokit.resource_management.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * reservation
 * @author 
 */
@Data
public class Reservation extends Login implements Serializable {
    /**
     * 预约信息id 唯一标识
     */
    private Integer reserveId;

    /**
     * 预约用户id
     */
    private Integer userId;

    /**
     * 用户预约车辆id
     */
    private Integer carId;

    /**
     * 用户预约会议室id
     */
    private Integer roomId;

    /**
     * 预约开始时间
     */
    private Date startTime;

    /**
     * 预约结束时间
     */
    private Date endTime;

    /**
     * 预约申请时间
     */
    private Date createTime;

    /**
     * 预约状态
     */
    private Object reserveState;

    /**
     * 预约申请理由
     */
    private String applyReason;

    /**
     * 驳回理由
     */
    private String rejectReason;



    private static final long serialVersionUID = 1L;
}