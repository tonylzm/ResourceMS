package com.yokit.resource_management.framework.dao;

import com.yokit.resource_management.dto.ResersvationRoomCar;
import com.yokit.resource_management.entity.Reservation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReservationDao {
    
    int deleteByPrimaryKey(Integer reserveId);

    int insert(Reservation record);

    int insertSelective(Reservation record);

    Reservation selectByPrimaryKey(Integer reserveId);

    int updateByPrimaryKeySelective(Reservation record);

    int updateByPrimaryKey(Reservation record);

    /**
     * 通过car_id连接进行双表查询，通过user_id和申请状态static进行筛选
     * @param userId
     * @param state
     * @return
     */
    List<ResersvationRoomCar > selCarByUserId(@Param("userId") Integer userId,@Param("state") String state);

    /**
     * 通过room_id连接进行双表查询，通过user_id和申请状态static进行筛选
     * @param userId
     * @param state
     * @return
     */
    List<ResersvationRoomCar > selRoomByUserId(@Param("userId") Integer userId,@Param("state")String state);

    List<Reservation> SelectReservationAll();
}