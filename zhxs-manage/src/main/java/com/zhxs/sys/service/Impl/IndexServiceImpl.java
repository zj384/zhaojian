package com.zhxs.sys.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhxs.sys.dao.UserDao;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.IndexService;

@Service
public class IndexServiceImpl implements IndexService {
	@Autowired
	private UserDao userDao;
	@Override
	public SysUser findUserById(String userid) {
		SysUser user = userDao.findUserByUserId(userid);
		return user;
	}
}
