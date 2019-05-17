package com.zhxs.sys.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhxs.common.util.UserThreadLocal;
import com.zhxs.common.vo.JsonResult;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.UserService;
import com.zhxs.sys.vo.UserAndClass;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisCluster jedisCluster;

	@RequestMapping("doListUserUI")
	public String doListUserUI() {
		return "/views/userList";
	}
	
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username, String password, HttpServletResponse response) {
		try {
			String token = userService.findUserByUP(username, password);
			//下行代表表示用户名和密码正确
			if(!StringUtils.isEmpty(token)) {
				Cookie cookie = new Cookie("ZHXS_TICKET", token);
				cookie.setMaxAge(7 * 24 * 3600);
				cookie.setPath("/"); //一般默认写/
				response.addCookie(cookie);
				return new JsonResult("/doIndexUI");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.fail("用户名或密码错误!");
	}
	
	@RequestMapping("doLogout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		String token = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if ("ZHXS_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
				}
		}
		jedisCluster.del(token);
		Cookie cookie = new Cookie("ZHXS_TICKET", "");
		// 0:立即删除 -1:关闭会话是删除
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "rediect:login";
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
		SysUser user = UserThreadLocal.get();
		userService.updateUserAndClass(userAndClass, user);
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
		String userid = UserThreadLocal.get().getId();
		userService.updatePassword(oldPassword, password, userid);
		return new JsonResult("密码修改成功");
	}
	
	

}
