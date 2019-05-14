package com.zhxs.sys.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhxs.common.exception.ServiceException;
import com.zhxs.sys.dao.LogDao;
import com.zhxs.sys.entity.SysLog;
import com.zhxs.sys.service.ConService;

@Service
public class ConServiceImpl implements ConService {
	@Autowired
	private LogDao logDao;

	@Override
	public List<SysLog> findAllLogs(Integer curr, Integer limit) {
		int startIndex = limit * (curr-1) + 1;
		List<SysLog> list = logDao.findPageLog(startIndex, limit);
		return list;
	}

	@Override
	public int getLogCount() {
		int count = logDao.getCount();
		return count;
	}

	@Override
	public int deleteLogsByIds(Integer[] ids) {
		if (ids == null || ids.length == 0) {
			throw new ServiceException("请先选择需要删除的日志");
		}
		int rows = 0;
		try {
			rows = logDao.deleteObjects(ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}

}
