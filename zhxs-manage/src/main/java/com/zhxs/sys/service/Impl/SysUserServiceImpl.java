package com.zhxs.sys.service.Impl;

import java.util.List;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.zhxs.common.annotation.RequestLog;
import com.zhxs.common.exception.ServiceException;
import com.zhxs.common.util.ShiroUtils;
import com.zhxs.common.vo.UUIDUtils;
import com.zhxs.sys.dao.UserDao;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.UserService;
import com.zhxs.sys.vo.UserAndClass;

@Service
public class SysUserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@RequestLog("用户登录")
	@Override
	public String loginUserOrAdmin(String username, String password) {
		// 1.获取Subject对象
		Subject subject = SecurityUtils.getSubject();
		// 2.通过Subject提交用户信息,交给shiro框架进行认证操作
		// 2.1对用户进行封装
		UsernamePasswordToken token = new UsernamePasswordToken(username, // 身份信息
				password);// 凭证信息
		// 2.2对用户信息进行身份认证
		subject.login(token);
		// 分析:
		// 1)token会传给shiro的SecurityManager
		// 2)SecurityManager将token传递给认证管理器
		// 3)认证管理器会将token传递给realm
		if ("admin".equals(username)) {
			return "/doManageUI";
		}
		return "/doIndexUI";
	}
	// 新增用户
	@Override
	public int saveUser(SysUser user) {
		// 信息校验
		if (user == null) {
			throw new ServiceException("信息为空");
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			throw new ServiceException("用户名不能为空");
		}
		if (StringUtils.isEmpty(user.getName())) {
			throw new ServiceException("姓名不能为空");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			throw new ServiceException("密码不能为空");
		}
		SysUser oldUser = userDao.findUserByUserName(user.getUsername());
		if (oldUser != null) {
			throw new ServiceException("该用户已经注册");
		}
		// 进行MD5加密 呵呵
		String salt = UUID.randomUUID().toString();
		user.setId(UUIDUtils.newShortUUID().toString());
		user.setSalt(salt);
		SimpleHash sHash = new SimpleHash("MD5", user.getPassword(), salt);
		user.setPassword(sHash.toHex());
		int rows = userDao.insertUser(user);

		return rows;
	}
	@RequestLog("修改用户信息")
	@Override
	public int updateUserAndClass(UserAndClass userAndClass) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null) {
			throw new ServiceException("请先登录!");
		}
		user.setName(userAndClass.getName());
		user.setNickname(userAndClass.getNickname());

		String userid = user.getId();
		try {
			userDao.updateUserById(user);
			if (!StringUtils.isEmpty(userAndClass.getXiaoxue())) {
				userDao.insertClassUser(userAndClass.getXiaoxue(), userid, 101);
			}
			if (!StringUtils.isEmpty(userAndClass.getChuzhong())) {
				userDao.insertClassUser(userAndClass.getChuzhong(), userid, 102);
			}
			if (!StringUtils.isEmpty(userAndClass.getGaozhong())) {
				userDao.insertClassUser(userAndClass.getGaozhong(), userid, 103);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("修改失败");
		}
		return 0;
	}

	@RequestLog("查询用户信息")
	@Override
	public List<SysUser> FindAllUser(SysUser sysUser) {
		List<SysUser> list = userDao.findAllUser(sysUser);
		if (list == null || list.size() == 0) {
			throw new ServiceException("没有该用户");
		}
		return list;
	}
	
	@RequestLog("删除用户")
	@Override
	public int deleteUser(String id) {
		int rows = 0;
		try {
			rows = userDao.deleteUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}
	@Override
	public int getUserCount() {
		int count = userDao.getUserCount();
		return count;
	}
	@Override
	public void updatePassword(String oldPassword, String password) {
		if (StringUtils.isEmpty(oldPassword)) {
			throw new ServiceException("请输入原密码!");
		}
		if (StringUtils.isEmpty(password)) {
			throw new ServiceException("请输入新密码!");
		}
		SysUser user = ShiroUtils.getPrincipal();
		String oldPwd = new SimpleHash("MD5", oldPassword, user.getSalt()).toHex();
		String salt = UUID.randomUUID().toString();
		if (oldPwd.equals(user.getPassword())) {
			String newPassword = new SimpleHash("MD5", password, salt).toHex();
			user.setPassword(newPassword);
			user.setSalt(salt);
		} else {
			throw new ServiceException("原密码错误");
		}
		try {
			userDao.updatePassword(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("服务器错误,密码修改失败!");
		}
	}




}
