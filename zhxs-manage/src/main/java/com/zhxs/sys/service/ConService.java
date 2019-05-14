package com.zhxs.sys.service;

import java.util.List;

import com.zhxs.sys.entity.SysLog;

public interface ConService {

	List<SysLog> findAllLogs(Integer curr, Integer limit);

	int getLogCount();

	int deleteLogsByIds(Integer[] ids);

}
