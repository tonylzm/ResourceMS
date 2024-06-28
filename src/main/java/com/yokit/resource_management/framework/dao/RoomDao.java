package com.yokit.resource_management.framework.dao;

import com.yokit.resource_management.entity.Room;
import com.yokit.resource_management.entity.User;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RoomDao {
    int deleteByPrimaryKey(Integer roomId);

    int insert(Room record);

    int insertSelective(Room record);

    Room selectByPrimaryKey(Integer roomId);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);

    List<Room> selectAllRoom();

    List<Room> selectRoomByName(String searchName);
}