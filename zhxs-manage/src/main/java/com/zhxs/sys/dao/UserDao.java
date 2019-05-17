package com.zhxs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhxs.sys.entity.SysUser;

public interface UserDao {
	SysUser findUserByUserName(String username);
	int insertUser(SysUser user);
	int updateUserById(SysUser user);
	int insertClassUser(@Param("clazzid")String clazzid, @Param("userid")String userid, @Param("level")Integer level);
	List<SysUser> findAllUser(SysUser sysUser);
	int deleteUserById(String id);
	int getUserCount();
	void updatePassword(SysUser user);
	SysUser findUserByUserId(String userid);
}
