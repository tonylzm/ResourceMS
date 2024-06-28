package com.yokit.resource_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tonylin
 * @create 2024/6/25 13:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResersvationRoomCar {
    private Integer reserveId;
    private String carName;
    private String userId;
    private String loginName;
    private String roomName;
    private String carPic;
    private String roomPic;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private String applyReason;
    private String rejectReason;
    private Object reserveState;
}
