package com.yokit.resource_management.framework.service;

import com.yokit.resource_management.entity.Car;
import com.yokit.resource_management.entity.Room;
import com.yokit.resource_management.framework.dao.CarDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fengzeng
 * @create 2021/6/25 2:59
 */
@Service
public class CarService {

  @Resource
  private CarDao carDao;

  public List<Car> selectAll() {
    return carDao.selectAllCar();
  }

  public void updateCarStatus(Car car) {
    carDao.updateByPrimaryKeySelective(car);
  }

    public void deleteCar(Integer carId_) {
    carDao.deleteByPrimaryKey(carId_);
    }

  public void insertCar(Car car) {
    carDao.insertSelective(car);
  }
  public List<Car> selectCarByName(String searchCar) {

    return carDao.selectCarByName(searchCar);
  }
}
