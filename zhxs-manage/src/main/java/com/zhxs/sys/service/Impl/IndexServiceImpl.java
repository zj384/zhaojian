package com.zhxs.sys.service.Impl;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.zhxs.common.exception.ServiceException;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.IndexService;

@Service
public class IndexServiceImpl implements IndexService {

	@Override
	public SysUser getUser() {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null) {
			throw new ServiceException("请先登录!");
		}
		return user;
	}
	
}
