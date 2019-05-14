package com.zhxs.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhxs.common.vo.JsonResult;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.UserService;
import com.zhxs.sys.vo.UserAndClass;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("doListUserUI")
	public String doListUserUI() {
		return "/views/userList";
	}
	
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username, String password) {
		String view = userService.loginUserOrAdmin(username, password);
		return new JsonResult(view);
	}

	@RequestMapping("doFindAllUser")
	@ResponseBody
	public JsonResult doFindAllUser(SysUser sysUser) {
		List<SysUser> user = userService.FindAllUser(sysUser);
		return new JsonResult(user);
	}


	@RequestMapping("doSaveUser")
	@ResponseBody
	public JsonResult doSaveUser(SysUser user) {
		userService.saveUser(user);
		return new JsonResult("注册成功");
	}

	@RequestMapping("doUpdateUserInfo")
	@ResponseBody
	public JsonResult doUpdateUserInfo(UserAndClass userAndClass) {
		userService.updateUserAndClass(userAndClass);
		return new JsonResult("修改成功!");
	}
	@RequestMapping("doDeleteUserById")
	@ResponseBody
	public JsonResult doDeleteUserById(String id) {
		userService.deleteUser(id);
		return new JsonResult("用户删除成功!");
	}
	
	@RequestMapping("doGetUserCount")
	@ResponseBody
	public JsonResult doGetUserCount() {
		int userCount = userService.getUserCount();
		return new JsonResult(userCount);
	}
	@RequestMapping("doupdataPassword")
	@ResponseBody
	public JsonResult doupdataPassword(String oldPassword, String password) {
		userService.updatePassword(oldPassword, password);
		return new JsonResult("密码修改成功");
	}
	
	

}
