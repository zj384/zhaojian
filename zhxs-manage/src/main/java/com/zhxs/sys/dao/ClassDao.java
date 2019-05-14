package com.zhxs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhxs.sys.entity.SysClass;

public interface ClassDao {
	int insertClass(SysClass sysClass);

//	List<SysClass> findClassByUsername(String id);
	
//	int findClassByUserIdAndLevel(@Param("userid")String userid, @Param("level")Integer level);
	
	String findIdByClass(SysClass sysClass);

	String[] findClassByUserId(String userid);

	List<SysClass> findClassByClassId(@Param("clazzse")String[] clazzse);
}
