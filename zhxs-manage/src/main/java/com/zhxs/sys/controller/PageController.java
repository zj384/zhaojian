package com.zhxs.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	@RequestMapping("/page/{moduleName}")
	public String modele(@PathVariable String moduleName) {
		return moduleName;
	}
	// 加载主页
		@RequestMapping("doIndexUI")
		public String doIndexUI() {
			return "index";
		}

	@RequestMapping("/doManageUI")
	public String doManageUI() {
		return "manage";
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
