package com.zhxs.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhxs.common.vo.JsonResult;
import com.zhxs.sys.entity.SysLog;
import com.zhxs.sys.service.ConService;
@RequestMapping("/con/")
@Controller
public class ConController {
	
	@Autowired
	private ConService conService;
	
	@RequestMapping("doConsoleUI")
	public String doConsoleUI() {
		return "views/console";
	}
	@RequestMapping("doFindAllLogs")
	@ResponseBody
	public JsonResult doFindAllLogs(Integer curr, Integer limit) {
		List<SysLog> list = conService.findAllLogs(curr, limit);
		return new JsonResult(list);
	}
	@RequestMapping("doLogsCount")
	@ResponseBody
	public JsonResult doLogsCount() {
		int count = conService.getLogCount();
		return new JsonResult(count);
	}
	@RequestMapping("doDeleteLogsByIds")
	@ResponseBody
	public JsonResult doDeleteLogsByIds(Integer... ids) {
		conService.deleteLogsByIds(ids);
		return new JsonResult("删除成功");
	}
}
