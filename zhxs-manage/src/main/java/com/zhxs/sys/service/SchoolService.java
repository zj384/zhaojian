package com.zhxs.sys.service;

import java.util.List;

import com.zhxs.sys.entity.SysCity;
import com.zhxs.sys.entity.SysSchool;
import com.zhxs.sys.entity.SysSchoolType;

public interface SchoolService {
	// 根据条件查询学校信息
	List<SysSchool> findAllObject(SysSchool sysSchool);
	// 根据id查询城市
	List<SysCity> findCityByParentId(Integer parentId);
	// 查询所有学校类型
	List<SysSchoolType> findSchoolType();
	// 添加学校信息
	int insertSchool(SysSchool sysSchool);
	// 根据id修改学校信息
	int updateSchool(SysSchool sysSchool);
	// 删除学校信息
	int deleteSchoolById(Integer id);
	List<SysSchool> FindSchoolByCountyId(Integer countyid, Integer sctypeid);
}
