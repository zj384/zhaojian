package com.zhxs.sys.service;

import java.util.List;

import com.zhxs.sys.entity.SysClass;
import com.zhxs.sys.entity.SysUser;

public interface ClassService {
	String addClass(SysClass sysClass);

	List<SysClass> findClass(SysUser user);
}
