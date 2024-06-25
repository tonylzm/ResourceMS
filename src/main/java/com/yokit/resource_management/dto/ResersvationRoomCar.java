package com.yokit.resource_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 怀雪
 * @create 2021-06-30 9:18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResersvationRoomCar {
    private Integer reserveId;
    private String carName;
    private String roomName;
    private String carPic;
    private String roomPic;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private String applyReason;
    private Object reserveState;


}
