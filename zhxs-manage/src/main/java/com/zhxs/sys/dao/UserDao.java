package com.zhxs.sys.dao;

import java.util.List;

import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.entity.SysUserClass;

public interface UserDao {
	SysUser findUserByUserName(String username);
	int insertUser(SysUser user);
	int updateUserById(SysUser user);
	int insertClassUser(SysUserClass sysUserClass);
	List<SysUser> findAllUser(SysUser sysUser);
	int deleteUserById(String id);
	int getUserCount();
	void updatePassword(SysUser user);
	SysUser findUserByUserId(String userid);
	void deleteUserClassById(SysUserClass userClass);
}
