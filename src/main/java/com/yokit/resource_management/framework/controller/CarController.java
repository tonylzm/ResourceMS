package com.yokit.resource_management.framework.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yokit.resource_management.dto.ResponseDto;
import com.yokit.resource_management.entity.Car;
import com.yokit.resource_management.framework.service.CarService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengzeng
 * @create 2021/6/25 3:00
 */
@RestController
public class CarController {

  @Resource
  private CarService carService;
  private String searchcar;

  /**
   * 获取所有车辆信息，分页进行输出
   * @param pageNum
   * @param pageSize
   * @return
   */
  @RequestMapping(value = "/carList", method = RequestMethod.GET)
  public Map getCarList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<Car> cars = carService.selectAll();
    PageInfo pageInfo = PageInfo.of(cars);

    Map map = new HashMap();
    map.put("status", 200);
    map.put("msg", "获取车辆列表成功");
    map.put("data", pageInfo.getList());
    map.put("total", pageInfo.getTotal());
    return map;
  }

  /**
   * 按名称模糊查找车辆
   *
   * @param
   * @return
   */
  @RequestMapping(value = "/searchCar", method = RequestMethod.GET)
  public Map getCarByName(@RequestParam(value = "search") String seaCar,@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
    this.searchcar = seaCar;
    String searchCar = searchcar;
    PageHelper.startPage(pageNum, pageSize);
    List<Car> cars = carService.selectCarByName(searchCar);
    PageInfo pageInfo = PageInfo.of(cars);
    Map map = new HashMap();
    map.put("data", pageInfo.getList());
    map.put("status", 200);
    map.put("msg", "查询车辆成功");
    map.put("total", pageInfo.getTotal());
    return map;
  }


  /**
   * 更新车辆状态信息
   * @param carId
   * @param checked
   * @return
   */
  @PutMapping("/car/{carId}/{checked}")
  public ResponseDto updateCarStatus(@PathVariable String carId, @PathVariable String checked) {
    Integer carId_ = Integer.parseInt(carId);

    Integer isActive = checked.equalsIgnoreCase("true") ? 1 : 0;

    Car car = new Car();
    car.setCarId(carId_);
    car.setIsActive(isActive);

    carService.updateCarStatus(car);

    return new ResponseDto(200, "更新数据成功");
  }


  /**
   * @Description 添加车辆
   * @Date ${time} ${date}
   * @Param
   * @return
   **/
  @RequestMapping(value = "/addcar", method = RequestMethod.POST)
  public ResponseDto addCar(@RequestBody Map<String, Object> addCar) {
    String carName = (String) addCar.get("carName");
    Object carDescObj = addCar.get("carDesc");
    String carPic = (String) addCar.get("carPic");

    String carDesc;
    if (carDescObj instanceof List) {
      // 如果 carDesc 是一个列表，将其转换为字符串
      carDesc = String.join(", ", (List<String>) carDescObj);
    } else {
      carDesc = (String) carDescObj;
    }

    Car car = new Car();
    car.setCarDesc(carDesc);
    car.setCarName(carName);
    car.setCarPic(carPic);
    car.setResourceId(101);

    carService.insertCar(car);

    return new ResponseDto(200, "添加车辆成功");
  }

/**
 * @Description 删除车辆
 * @Date
 * @Param
 * @return
 **/
@DeleteMapping("/car/{carId}")
  public ResponseDto deleteCar(@PathVariable String carId){
  Integer carId_ = Integer.parseInt(carId);
  carService.deleteCar(carId_);
  return new ResponseDto(200, "删除成功");

}


}

