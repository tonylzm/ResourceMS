package com.yokit.resource_management.framework.service;

import com.yokit.resource_management.entity.Room;
import com.yokit.resource_management.framework.dao.RoomDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tonylin
 */
@Service
public class RoomService {
    @Resource
    private RoomDao roomDao;

    public List<Room> selectAll() {
        return roomDao.selectAllRoom();
    }


    public void insertRoom(Room room) {
        roomDao.insertSelective(room);
    }

    public List<Room> selectRoomById(String searchName) {

      return roomDao.selectRoomByName(searchName);
    }

    public void update(Room room) {
        roomDao.updateByPrimaryKeySelective(room);
    }

    public void deleteRoomById(Integer roomId_) {
        roomDao.deleteByPrimaryKey(roomId_);
    }

    public void updateRoom(Room room) {
        roomDao.updateByPrimaryKeySelective(room);
    }
}
