package com.zhxs.sys.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.zhxs.common.annotation.RequestLog;
import com.zhxs.common.exception.ServiceException;
import com.zhxs.common.vo.UUIDUtils;
import com.zhxs.sys.dao.ClassDao;
import com.zhxs.sys.entity.SysClass;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.ClassService;

@Service
public class ClassServiceImpl implements ClassService {
	@Autowired
	private ClassDao classDao;

	// 添加用户的班级
	@RequestLog("用户添加班级")
	@Override
	public String addClass(SysClass sysClass) {
		if (sysClass == null) {
			throw new ServiceException("数据为空");
		}
		String id = classDao.findIdByClass(sysClass);
		if (!StringUtils.isEmpty(id)) {
			return id;
		}
		id = UUIDUtils.newShortUUID().toString();
		sysClass.setId(id);
		try {
			classDao.insertClass(sysClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("新增失败!");
		}
		return id;
	}
	@Override
	public List<SysClass> findClass(SysUser user) {
		if (user == null) {
			throw new ServiceException("请先登录");
		}
		String[] clazzse = classDao.findClassByUserId(user.getId());
		List<SysClass> list = null;
		if (clazzse != null && clazzse.length != 0) {
			list = classDao.findClassByClassId(clazzse);
		}
		if (list == null || list.size() == 0) {
			throw new ServiceException("您未添加任何班级!");
		}
		return list;
	}
	
	
}
