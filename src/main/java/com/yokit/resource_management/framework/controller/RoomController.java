package com.yokit.resource_management.framework.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yokit.resource_management.dto.ResponseDto;
import com.yokit.resource_management.entity.Room;
import com.yokit.resource_management.framework.service.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tonylin
 * @create 2021/6/25 2:58
 */
@RestController
public class RoomController {


    @Resource
    private RoomService roomService;
    private String seaRoom;


    /**
     * @return
     * @Description 查询所有会议室
     * @Date ${time} ${date}
     * @Param pageNum 分页页数  pageSize 分页数
     **/
    @RequestMapping(value = "/roomList", method = RequestMethod.GET)
    public Map getRoomList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Room> rooms = roomService.selectAll();
        PageInfo pageInfo = PageInfo.of(rooms);
        Map map = new HashMap();
        map.put("data", pageInfo.getList());
        map.put("status", 200);
        map.put("msg", "获取会议室列表成功");
        map.put("total", pageInfo.getTotal());
        return map;
    }

    /**
     * 按名称模糊查找会议室
     *
     * @param seaRoom  searchroom
     * @return
     */
    @RequestMapping(value = "/searchRoom", method = RequestMethod.GET)
    public Map getRoomByName(@RequestParam(value = "search") String seaRoom,@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        this.seaRoom = seaRoom;
        String searchName = seaRoom;
        PageHelper.startPage(pageNum, pageSize);
        List<Room> rooms = roomService.selectRoomById(searchName);
        PageInfo pageInfo = PageInfo.of(rooms);
        Map map = new HashMap();
        map.put("data", pageInfo.getList());
        map.put("status", 200);
        map.put("msg", "查询会议室列表成功");
        map.put("total", pageInfo.getTotal());
        return map;
    }


    /**
     * @return
     * @Description 添加会议室
     * @Date
     * @Param adrm addroom
     **/
    @RequestMapping(value = "/addrooms", method = RequestMethod.POST)
    public ResponseDto addrooms(@RequestBody Map<String, Object> addRoom) {

        String roomName = (String) addRoom.get("roomName");
        Object roomDescObj =  addRoom.get("roomDesc");
        String roomPic = (String) addRoom.get("roomPic");

        String roomDesc;
        if (roomDescObj instanceof List) {
            // 如果 carDesc 是一个列表，将其转换为字符串
            roomDesc = String.join(", ", (List<String>) roomDescObj);
        } else {
            roomDesc = (String) roomDescObj;
        }
        Room room = new Room();
        room.setRoomName(roomName);
        room.setRoomDesc(roomDesc);
        room.setRoomPic(roomPic);
        room.setResourceId(102);
        roomService.insertRoom(room);
        return new ResponseDto(200, "添加会议室成功");
    }


    /**
     * @return
     * @Description 根据roomId更新会议室
     * @Date ${time} ${date}
     * @Param
     **/
    @PutMapping("/room/{roomId}")
    public ResponseDto updateRoom(@PathVariable String roomId, @RequestBody Room room) {
        Integer roomId_ = Integer.parseInt(roomId);
        room.setRoomId(roomId_);
        roomService.update(room);
        return new ResponseDto(200, "更新成功");

    }
    
    
    /**
     * @Description 根据roomId删除会议室
     * @Date ${time} ${date}
     * @Param 
     * @return 
     **/
    @DeleteMapping("/room/{roomId}")
    public ResponseDto deleteRoom(@PathVariable String roomId) {
        Integer roomId_ = Integer.parseInt(roomId);
        roomService.deleteRoomById(roomId_);

        return new ResponseDto(200, "删除成功");

    }
}


