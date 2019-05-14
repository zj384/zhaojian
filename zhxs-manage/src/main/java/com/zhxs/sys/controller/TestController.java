package com.zhxs.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@RequestMapping("/getMsg")
	@ResponseBody
	public String findItemCatNameById(Long itemCatId) {
		return "8091";
	}
}
