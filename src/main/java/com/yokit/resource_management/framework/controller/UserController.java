package com.yokit.resource_management.framework.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yokit.resource_management.dto.ResponseDto;
import com.yokit.resource_management.entity.User;
import com.yokit.resource_management.framework.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author tonylin
 * @create 2024/6/30 15:17
 */
@RestController
public class UserController {
  @Resource
  private UserService userService;



  /**
   * 用户注册
   * @param req
   * @return
   */
  @RequestMapping(value = "/regist",method = RequestMethod.POST)
  public ResponseDto register(@RequestBody Map<String ,String > req) {
    String loginName = req.get("username");
    String password = req.get("password");
    User user = new User();
    user.setLoginName(loginName);
    user.setPassword(password);
    user.setUserName("员工");
    userService.insertUser(user);
    return new ResponseDto(200, "注册成功");
  }

  @RequestMapping("/user/check/{username}")
  public ResponseDto checkUserName(@PathVariable String username) {
    Optional<User> userOptional = Optional.ofNullable(userService.checkUserName(username));
    if (userOptional.isPresent()) {
      return new ResponseDto(400, "该用户名已存在");
    }
    return new ResponseDto(200, "操作成功");
  }

  @RequestMapping(value = "/UserList", method = RequestMethod.GET)
  public Map getRoomList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<User> users = userService.SelectUserAll();
    PageInfo pageInfo = PageInfo.of(users);
    Map map = new HashMap();
    map.put("data", pageInfo.getList());
    map.put("status", 200);
    map.put("msg", "获取用户列表成功");
    map.put("total", pageInfo.getTotal());
    return map;
  }

  @PutMapping("/user/{userId}")
    public ResponseDto updateUser(@PathVariable Integer userId, @RequestBody Map<String, String> req) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(req.get("userName"));
        user.setLoginName(req.get("loginName"));
        user.setPassword(req.get("password"));
        user.setMobile(req.get("mobile"));
        user.setAge(Integer.parseInt(req.get("age")));
        user.setSex(req.get("sex"));

        user.setEmail(req.get("email"));
        userService.updateUser(user);
        return new ResponseDto(200, "更新成功");
    }

    @DeleteMapping("/user/{userId}")
    public ResponseDto deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return new ResponseDto(200, "删除成功");
    }

    @GetMapping("/user/{userId}")
    public ResponseDto getUser(@PathVariable Integer userId) {
        User user = userService.selectUserById(userId);
        return new ResponseDto(200, "查询成功", user);
    }
}
