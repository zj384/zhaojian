package com.zhxs.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhxs.common.util.UserThreadLocal;

@Controller
public class PageController {
	@RequestMapping("/*")
	public String modele() {
		return "index";
	}
	// 加载主页
		@RequestMapping("/doIndexUI")
		public String doIndexUI() {
			return "index";
		}

	@RequestMapping("/doManageUI")
	public String doManageUI() {
		String username = UserThreadLocal.get().getUsername();
		if ("zhaojian".equals(username)) {
			return "manage";
		} else {
			return "index";
		}
	}
	@RequestMapping("/doLoginUI")
	public String doLoginUI() {
		return "login";
	}
	@RequestMapping("/doRegUI")
	public String doRegUI() {
		return "reg";
	}
}
