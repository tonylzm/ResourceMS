package com.yokit.resource_management.framework.service;


import com.github.pagehelper.PageHelper;
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

  public List<Reservation> SelectAllByStatue(String statue) {
    return reservationDao.selReservationAllByState(statue);
  }

  public void updateReserve(Reservation reservation) {
    reservationDao.updateByPrimaryKeySelective(reservation);
  }

  public List<ResersvationRoomCar> selRoom(String state, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return reservationDao.selRoom(state);
  }

  public List<ResersvationRoomCar> selCar(String state, int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return reservationDao.selCar(state);
  }

  public boolean checkReserved(Integer userId) {
    //检查用户是否有预约记录，且预约状态如果为已驳回那就返回false
   boolean allRejected = true;
  List<ResersvationRoomCar> reservations = reservationDao.selByUserId(userId);
  for (ResersvationRoomCar reservation : reservations) {
    if (!reservation.getReserveState().equals("已驳回")) {
      allRejected = false;
      break;
    }
  }
  return allRejected;
  }
}
