package com.yokit.resource_management.framework.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yokit.resource_management.dto.ResponseDto;
import com.yokit.resource_management.entity.Login;
import com.yokit.resource_management.entity.User;
import com.yokit.resource_management.framework.service.LoginService;
import com.yokit.resource_management.framework.service.UserService;
import com.yokit.resource_management.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author fengzeng
 * @create 2021/6/27 15:53
 */
@RestController
public class LoginController {
  @Resource
  private UserService userService;
  @Resource
  private JwtUtil jwtUtil;

  @Resource
  private LoginService loginService;


  /**
   * 登陆接口
   *
   * @param req
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseDto userLogin(@RequestBody Map req, HttpServletRequest request) {

    //数据库查询用户是否存在
    String username = (String) req.get("username");
    String password = (String) req.get("password");
    Optional<User> userOptional = Optional.ofNullable(userService.authLogin(username, password));
    //如果不存在，返回用户名密码错误
    if (!userOptional.isPresent()){
      return new ResponseDto(200,  "用户名或密码错误");
    }
    //存在则创建user对象
    User user = userOptional.get();

    //根据roleId 设置用户名称
    String userName = "员工";
    if (user.getRoleId() == 1) {
      userName = "管理员";
    }
    //获取当前登录ip
    String userIp = request.getRemoteAddr();

    //获取登录总次数
    int totalCount = loginService.selectTotalCount();


    //获取上次登录时间
    Date loginTime = loginService.selectLoginTimeByUserId(user.getUserId());

    Map map = new HashMap();
    map.put("username", user.getLoginName());
    map.put("roleName", userName);
    map.put("userId", user.getUserId());
    map.put("token", jwtUtil.createToken(user));
    map.put("loginTime", loginTime);
    map.put("userIp", userIp);
    map.put("totalCount", totalCount);


    //处理完成后向数据库插入一条登录信息
    Login login = new Login();
    login.setLoginTime(new Date());
    login.setUserId(user.getUserId());
    loginService.insert(login);

    //获取当前用户当前登录次数
    int todayCount = loginService.selectTodayCountByUserId(user.getUserId());
    map.put("todayCount", todayCount);

    return new ResponseDto(200, "登录成功", map);
  }

  /**
   * 分页输出登录信息
   * @param pageNum
   * @param pageSize
   * @return
   */
  @RequestMapping(value = "/LoginList", method = RequestMethod.GET)
  public Map getLoginList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<Login> logins = loginService.selectAll();
    PageInfo pageInfo = PageInfo.of(logins);

    Map map = new HashMap();
    map.put("status", 200);
    map.put("msg", "获取登录信息列表成功");
    map.put("data", pageInfo.getList());
    map.put("total", pageInfo.getTotal());
    return map;
  }

  /**
   * 更改密码
   *  onu.userId 用户id
   *  onu.old_password 老密码
   *  onu.new_password 新密码
   * @param onu Map映射
   * @return 返回具体是否成功信息
   */
  @RequestMapping(value="/changepassword",method = RequestMethod.POST)
  public ResponseDto passwordChange
//        (@RequestParam("old_password")String oldPassword,@RequestParam("new_password")String newPassword,@RequestParam("user_id")Integer userId){
      (@RequestBody Map onu){
    String oldPassword=(String) onu.get("old_password");
    String newPassword=(String) onu.get("new_password");
    Integer userId=(Integer) onu.get("user_id");
    boolean verify= loginService.verifyPassword(oldPassword,userId);
    boolean change=false;
    if(verify){change=loginService.passwordChange(newPassword, userId);}
    else{return new ResponseDto(201,"原密码不正确",null);}
    if(change){return new ResponseDto(200,"成功修改密码",null);}
    else{return new ResponseDto(201,"修改密码失败",null);}
  }

}
