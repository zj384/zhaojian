package com.zhxs.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhxs.common.vo.JsonResult;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.IndexService;

@Controller
@RequestMapping("/sys/")
public class indexController {
	@Autowired
	private IndexService indexservice;
	
	@RequestMapping("doIndexBodyUI")
	public String doIndexBodyUI() {
		return "sys/indexBody";
	}
	// 加载设置页面
	@RequestMapping("doAboutUI")
	public String doAboutUI() {
		return "sys/about";
	}
	// 加载图片显示页面
	@RequestMapping("doAlbumUI")
	public String doAlbumUI() {
		return "sys/album";
	}
	// 加载图片上传页面
	@RequestMapping("doUploadUI")
	public String doUploadUI() {
		return "sys/upload";
	}
	// 加载图片修改页面
	@RequestMapping("doUpdateImage")
	public String doUpdateImage() {
		return "sys/details";
	}
	// 获取当前用户
	@RequestMapping("doFindUser")
	@ResponseBody
	public JsonResult doFindUser() {
		SysUser user = indexservice.getUser();
		return new JsonResult(user);
	}
}
