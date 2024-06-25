package com.yokit.resource_management.framework.dao;

import com.yokit.resource_management.entity.Car;

import java.util.List;

public interface CarDao {
  int deleteByPrimaryKey(Integer carId);

  int insert(Car record);

  int insertSelective(Car record);

  Car selectByPrimaryKey(Integer carId);

  int updateByPrimaryKeySelective(Car record);

  int updateByPrimaryKey(Car record);

  List<Car> selectAllCar();

  List<Car> selectCarByName(String searchCar);
}