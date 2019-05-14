package com.zhxs.sys.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhxs.sys.entity.SysSchool;

public interface SchoolDao {
	List<SysSchool> findAllObject(SysSchool sysSchool);
	int insertSchool(SysSchool sysSchool);
	int updateSchool(SysSchool sysSchool);
	int deleteSchoolById(Integer id);
	List<SysSchool> findSchoolByCountyId(@Param("countyid")Integer countyid, @Param("sctypeid")Integer sctypeid);
}
