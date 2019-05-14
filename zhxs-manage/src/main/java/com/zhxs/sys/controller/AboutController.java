package com.zhxs.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about/")
public class AboutController {
	@RequestMapping("doInfoUI")
	private String doInfoUI() {
		return "sys/info";
	}
	@RequestMapping("doAddSchoolUI")
	private String doAddSchoolUI() {
		return "sys/addSchool";
	}
	// 加载修改密码
	@RequestMapping("doAltPwdUI")
	public String doAltPwdUI() {
		return "sys/password";
	}
}
