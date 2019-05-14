package com.zhxs.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhxs.common.vo.JsonResult;
import com.zhxs.sys.entity.SysCity;
import com.zhxs.sys.entity.SysSchool;
import com.zhxs.sys.entity.SysSchoolType;
import com.zhxs.sys.service.SchoolService;

@RequestMapping("/school/")
@Controller
public class SchoolController {
	@Autowired
	private SchoolService schoolService;
	
	// 加载学校编辑页面
	@RequestMapping("doEditSchoolUI")
	public String doEditSchoolUI() {
		return "views/addEdit";
	}

	// 加载学校列表页面
	@RequestMapping("doListSchoolUI")
	public String doListSchoolUI() {
		return "views/schoolList";
	}
	
	// 根据条件查询学校信息
	@RequestMapping("doFindAllObject")
	@ResponseBody
	public JsonResult dofindAllObject(SysSchool sysSchool){
		List<SysSchool> pi = schoolService.findAllObject(sysSchool);
		return new JsonResult(pi);
	}
	
	@RequestMapping("doFindSchoolByCountyId")
	@ResponseBody
	public JsonResult doFindSchoolByCountyId(Integer countyid, Integer sctypeid){
		List<SysSchool> pi = schoolService.FindSchoolByCountyId(countyid, sctypeid);
		return new JsonResult(pi);
	}
	
	// 根据id查询学校城市
	@RequestMapping("doFindCity")
	@ResponseBody
	public JsonResult doFindCity(Integer parentid){
		List<SysCity> list = schoolService.findCityByParentId(parentid);
		return new JsonResult(list);
	}
	
	// 根据id查询学校类型
	@RequestMapping("doFindSchoolType")
	@ResponseBody
	public JsonResult doFindSchoolType(){
		List<SysSchoolType> list = schoolService.findSchoolType();
		return new JsonResult(list);
	}
	// 添加
	@RequestMapping("doSaveSchool")
	@ResponseBody
	public JsonResult doSaveSchool(SysSchool sysSchool){
		schoolService.insertSchool(sysSchool);
		return new JsonResult("添加成功");
	}
	// 修改
	@RequestMapping("doUpdateSchool")
	@ResponseBody
	public JsonResult doUpdateSchool(SysSchool sysSchool){
		schoolService.updateSchool(sysSchool);
		return new JsonResult("修改成功");
	}
	
	@RequestMapping("doDeleteSchoolById")
	@ResponseBody
	public JsonResult doDeleteSchoolById(Integer id){
		schoolService.deleteSchoolById(id);
		return new JsonResult("删除成功");
	}
	
	
}
