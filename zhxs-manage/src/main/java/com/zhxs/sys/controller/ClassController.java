package com.zhxs.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhxs.common.util.UserThreadLocal;
import com.zhxs.common.vo.JsonResult;
import com.zhxs.sys.entity.SysClass;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.ClassService;

@Controller
@RequestMapping("/class/")
public class ClassController {
	@Autowired
	private ClassService classService;
	
	@RequestMapping("doAddClass")
	@ResponseBody
	public JsonResult doAddClass(SysClass sysClass) {
		String classId = classService.addClass(sysClass);
		return new JsonResult(classId);
	}
	@RequestMapping("doFindclass")
	@ResponseBody
	public JsonResult doFindclass() {
		SysUser user = UserThreadLocal.get();
		List<SysClass> list = classService.findClass(user);
		return new JsonResult(list);
	}
}
