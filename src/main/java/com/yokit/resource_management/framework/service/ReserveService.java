package com.yokit.resource_management.framework.service;


import com.yokit.resource_management.dto.ResersvationRoomCar;
import com.yokit.resource_management.entity.Reservation;
import com.yokit.resource_management.framework.dao.ReservationDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fengzeng
 * @create 2021/6/25 3:02
 */
@Service
public class ReserveService {
  @Resource
  private ReservationDao reservationDao;

  public void createReserve(Reservation reservation) {
    reservationDao.insertSelective(reservation);
  }
  public List<ResersvationRoomCar> selCarByUserId(@Param("userId")Integer userId, @Param("state")String state){
    return reservationDao.selCarByUserId(userId,state);
  }
  public List<ResersvationRoomCar > selRoomByUserId(@Param("userId")Integer userId,@Param("state")String state){
    return reservationDao.selRoomByUserId(userId,state);
  }


  public List<Reservation> SelectAll() {
    return reservationDao.SelectReservationAll();
  }
}
