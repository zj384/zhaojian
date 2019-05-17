package com.zhxs.sys.service;

import java.util.List;

import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.vo.UserAndClass;

public interface UserService {
	int saveUser(SysUser user);

	int updateUserAndClass(UserAndClass userAndClass, SysUser user);

	List<SysUser> FindAllUser(SysUser sysUser);

	int deleteUser(String id);

	int getUserCount();

	void updatePassword(String oldPassword, String password, String userid);

	String findUserByUP(String username, String password);


}
