package com.yokit.resource_management.framework.service;


import com.github.pagehelper.PageHelper;
import com.yokit.resource_management.dto.ResersvationRoomCar;
import com.yokit.resource_management.entity.Reservation;
import com.yokit.resource_management.framework.dao.ReservationDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

  public List selByCarId(Integer carId, String date) {
    //返回开始时间和结束时间，如果有多个时间段，那就返回多个时间段
    //将list中每个时间段的开始时间和结束时间拼接成字符串，用-分隔，删除日期部分，返回一个新list
    List<ResersvationRoomCar> reservations = reservationDao.seltime(carId, date);
    return getList(reservations);

  }


  public List selByRoomId(Integer roomId, String date) {
    //返回开始时间和结束时间，如果有多个时间段，那就返回多个时间段
    //将list中每个时间段的开始时间和结束时间拼接成字符串，用-分隔，删除日期部分，返回一个新list
    List<ResersvationRoomCar> reservations = reservationDao.selRoomtime(roomId, date);
    return getList(reservations);
  }

  public List selectRoom(Integer roomId, String date) {
    return reservationDao.selRoomtime(roomId, date);
  }
  public List selectCar(Integer carId, String date) {
    return reservationDao.seltime(carId, date);
  }

  private List getList(List<ResersvationRoomCar> reservations) {
    List<String> times = new ArrayList<>();
    for (ResersvationRoomCar reservation : reservations) {
      //格式化时间，只保留时分秒
      String startTime = reservation.getStartTime().toString().substring(11, 19);
      String endTime = reservation.getEndTime().toString().substring(11, 19);
      times.add(startTime + "-" + endTime);
    }
    return times;
  }

}
