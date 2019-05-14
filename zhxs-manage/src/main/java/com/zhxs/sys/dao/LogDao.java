package com.zhxs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhxs.sys.entity.SysLog;

public interface LogDao {

	List<SysLog> findPageLog(@Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);

	int getCount();
	
	int insertLog(SysLog sysLog);
	
	int deleteObjects(@Param("ids")Integer... ids);

}
