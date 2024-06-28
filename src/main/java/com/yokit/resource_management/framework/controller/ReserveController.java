package com.yokit.resource_management.framework.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yokit.resource_management.dto.ResersvationRoomCar;
import com.yokit.resource_management.dto.ResponseDto;
import com.yokit.resource_management.entity.Reservation;
import com.yokit.resource_management.framework.service.ReserveService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tonylin
 * @create 2021/6/25 3:02
 */
@RestController
public class ReserveController {
  static final String TYPE_CAR = "car";
  static final String TYPE_ROOM = "room";
  @Resource
  private ReserveService reserveService;

  /**
   * 分页查询预约表的所有信息
   * @param pageNum
   * @param pageSize
   * @return
   */
  @RequestMapping(value = "/ReservationList", method = RequestMethod.GET)
  public Map getReservationList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
    PageHelper.startPage(pageNum,pageSize);
    List<Reservation> all = reserveService.SelectAll();
    PageInfo<Reservation> of = PageInfo.of(all);
    Map map = new HashMap();
    map.put("status", 200);
    map.put("msg", "获取预约列表成功");
    map.put("data", of.getList());
    map.put("total", of.getTotal());
    return map;
  }


    /**
     * 分页查询预约表的所有信息，根据状态查询
     * @param pageNum
     * @param pageSize
     * @param statue
     * @return
     */
  @RequestMapping(value = "/ReservationList/{statue}", method = RequestMethod.GET)
    public Map getReservationListByStatue(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @PathVariable("statue") String statue) {
        PageHelper.startPage(pageNum,pageSize);
        List<Reservation> all = reserveService.SelectAllByStatue(statue);
        PageInfo<Reservation> of = PageInfo.of(all);
        Map map = new HashMap();
        map.put("status", 200);
        map.put("msg", "获取预约列表成功");
        map.put("data", of.getList());
        map.put("total", of.getTotal());
        return map;
    }



  /**
   * 创建新的预约
   * @param userId 用户id
   * @param req 请求体
   * @param type 预约资源类型
   * @return
   * @throws ParseException
   */
  @PostMapping("/reserve/{type}/{userId}")
  public ResponseDto createReserve(@PathVariable String userId, @RequestBody Map<String, String> req, @PathVariable String type) throws ParseException {

    Reservation reservation = new Reservation();

    //获取用户id
    Integer userId_ = Integer.parseInt(userId);
    if (TYPE_CAR.equals(type)) {
      Integer carId_ = Integer.parseInt(req.get("carId"));
      reservation.setCarId(carId_);
    }
    if (TYPE_ROOM.equals(type)) {
      Integer roomId_ = Integer.parseInt(req.get("roomId"));
      reservation.setRoomId(roomId_);
    }

    //获取申请理由
    String applyReason = req.get("applyReason");

    //获取预约时间
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startTime = simpleDateFormat.parse(req.get("startTime"));
    Date endTime = simpleDateFormat.parse(req.get("endTime"));

    reservation.setUserId(userId_);
    reservation.setApplyReason(applyReason);
    reservation.setCreateTime(new Date());
    reservation.setStartTime(startTime);
    reservation.setEndTime(endTime);
    reservation.setReserveState("申请中");

    reserveService.createReserve(reservation);
    return new ResponseDto(200, "申请预约成功", reservation);
  }

  /**
   *双表联查预约信息
   * @param userId
   * @param state
   * @return
   */
  @GetMapping("/reserve/{userId}")
  public ResponseDto selByUserId(@PathVariable("userId") String userId, @RequestParam("state") String state){

    List<Object> objects = new ArrayList<>();
    Integer userId_ = Integer.parseInt(userId);
    List<ResersvationRoomCar> rooms= reserveService.selRoomByUserId(userId_,state);
    List<ResersvationRoomCar > cars = reserveService.selCarByUserId(userId_,state);
    //由于传回来的数据是数组类型的，所以将他们的元素都单独取出来，存储在list里面
    for (ResersvationRoomCar r:rooms
         ) {
      objects.add(r);
    }
    for (ResersvationRoomCar c:cars
         ) {
      objects.add(c);
    }
      return new ResponseDto(200,"获取预约信息成功",objects);
  }

  @GetMapping("/reserve/{types}/{Id}")
  //在预约表中查询会议室的预约信息，输出预约信息
    public ResponseDto selByRoomIds(@PathVariable("types") String types,@PathVariable("Id") String Id,@RequestParam("date") String date){
    Integer Id_ = Integer.parseInt(Id);
    //如果是car类型，就调用selByCarId方法，如果是room类型，就调用selByRoomId方法
    if("car".equals(types)){
      List<ResersvationRoomCar> cars = reserveService.selectCar(Id_,date);
      return new ResponseDto(200,"获取预约信息成功",cars);
    }else if("room".equals(types)){
      List<ResersvationRoomCar> rooms = reserveService.selectRoom(Id_,date);
      return new ResponseDto(200,"获取预约信息成功",rooms);
    }
    return new ResponseDto(500,"获取预约信息失败");

  }


    /**
     * 分页查询预约表的所有信息
     * @param statue
     * @param pageNum
     * @param pageSize
     * @return
     */

    @GetMapping("/reserves/{statue}")
    public Map<String, Object> selByReserve(@PathVariable String statue, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        // 分别调用 selRoom 和 selCar 方法，并传递分页参数
        List<ResersvationRoomCar> rooms = reserveService.selRoom(statue, pageNum, pageSize);
        List<ResersvationRoomCar> cars = reserveService.selCar(statue, pageNum, pageSize);

        // 将返回的结果存储在一个集合中
        List<Object> objects = new ArrayList<>();
        objects.addAll(rooms);
        objects.addAll(cars);

        // 使用 PageInfo 包装结果
        PageInfo<Object> pageInfo = new PageInfo<>(objects);

        // 准备返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("status", 200);
        result.put("msg", "获取预约列表成功");
        result.put("data", pageInfo.getList());
        result.put("total", pageInfo.getTotal());

        return result;
    }

    /**
     * 重新申请预约
     * @param reserveId
     * @param req
     * @return
     * @throws ParseException
     */

    @PutMapping("/reserves/{reserveId}")
    //重新申请预约，更新预约记录中的字段
    public ResponseDto updateReserve(@PathVariable String reserveId, @RequestBody Map<String, String> req) throws ParseException {
        Integer reserveId_ = Integer.parseInt(reserveId);
        Reservation reservation = new Reservation();
        reservation.setReserveId(reserveId_);
        reservation.setApplyReason(req.get("applyReason"));
         reservation.setReserveState("申请中");
        reserveService.updateReserve(reservation);
        return new ResponseDto(200, "更新成功");
    }


    /**
     * 通过或者驳回预约
     * @param type
     * @param reserveId
     * @param req
     * @return
     */
    @PutMapping("/reserve/{type}/{reserveId}")
    //通过或者驳回预约，只更新字段，不产生新的预约记录
    public ResponseDto updateReserve(@PathVariable String type, @PathVariable String reserveId, @RequestBody Map<String, String> req) {
        Integer reserveId_ = Integer.parseInt(reserveId);
        Reservation reservation = new Reservation();
        reservation.setReserveId(reserveId_);
        if ("pass".equals(type)) {
            reservation.setReserveState("已通过");
        } else if ("reject".equals(type)) {
            reservation.setReserveState("已驳回");
            reservation.setRejectReason(req.get("rejectReason"));
        }
      reserveService.updateReserve(reservation);
        return new ResponseDto(200, "更新成功");
    }


    /**
     * 删除预约
     * @param userId
     * @return
     */
    @GetMapping("/reserved/{userId}")
    //查询用户是否有预约记录，如果有一个在申请中，或者已通过，就返回true
    public ResponseDto checkReserved(@PathVariable String userId) {
        Integer userId_ = Integer.parseInt(userId);
        boolean reserved = reserveService.checkReserved(userId_);
        return new ResponseDto(200, "查询成功", reserved);
    }

    //查询表中已通过申请的开始时间和结束时间
    @GetMapping("/car/{Id}")
    public ResponseDto selByCarId(@PathVariable("Id") String carId,@RequestParam("date") String date) {
        Integer carId_ = Integer.parseInt(carId);
        List<ResersvationRoomCar> reservations = reserveService.selByCarId(carId_, date);
        return new ResponseDto(200, "查询成功", reservations);
    }

    @GetMapping("/room/{Id}")
    public ResponseDto selByRoomId(@PathVariable("Id") String roomId,@RequestParam("date") String date) {
        Integer roomId_ = Integer.parseInt(roomId);
        List<ResersvationRoomCar> reservations = reserveService.selByRoomId(roomId_, date);
        return new ResponseDto(200, "查询成功", reservations);
    }


}
