package com.zhxs.common.util;

import com.zhxs.sys.dao.UserDao;
import com.zhxs.sys.entity.SysUserClass;

public class SetUserClass {
	public static void setInfo(String clazzid, String userid, Integer level, UserDao userDao) {
		SysUserClass userClass = new SysUserClass();
		userClass.setId(clazzid+userid+level);
		userClass.setClazzid(clazzid);
		userClass.setUserid(userid);
		userClass.setLevel(level);
		userDao.deleteUserClassById(userClass);
		userDao.insertClassUser(userClass);
	}
}
