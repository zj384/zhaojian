package com.zhxs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhxs.sys.entity.SysSchoolType;

public interface SchoolTypeDao {
	 String findSchoolTypeById(@Param("id")Integer id);
	 List<SysSchoolType> findAllSchoolType();
}
