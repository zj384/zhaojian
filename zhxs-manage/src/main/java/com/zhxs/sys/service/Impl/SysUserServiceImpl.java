package com.zhxs.sys.service.Impl;

import java.util.List;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.druid.util.StringUtils;
import com.zhxs.common.annotation.RequestLog;
import com.zhxs.common.exception.ServiceException;
import com.zhxs.common.util.ObjectMapperUtil;
import com.zhxs.common.util.SetUserClass;
import com.zhxs.common.vo.UUIDUtils;
import com.zhxs.sys.dao.UserDao;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.UserService;
import com.zhxs.sys.vo.UserAndClass;

import redis.clients.jedis.JedisCluster;

@Service
public class SysUserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private JedisCluster jedisCluster;

	// 新增用户
	@Override
	@Transactional
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
	@Transactional
	public int updateUserAndClass(UserAndClass userAndClass,SysUser user) {
		if (user == null) {
			throw new ServiceException("请先登录!");
		}
		user.setName(userAndClass.getName());
		user.setNickname(userAndClass.getNickname());
		
		String userid = user.getId();
		try {
			userDao.updateUserById(user);
			String xiaoxue = userAndClass.getXiaoxue();
			String chuzhong = userAndClass.getChuzhong();
			String gaozhong = userAndClass.getGaozhong();
			if (!StringUtils.isEmpty(xiaoxue)) {
				SetUserClass.setInfo(xiaoxue, userid, 101, userDao);
			}
			if (!StringUtils.isEmpty(chuzhong)) {
				SetUserClass.setInfo(chuzhong, userid, 102, userDao);
			}
			if (!StringUtils.isEmpty(gaozhong)) {
				SetUserClass.setInfo(gaozhong, userid, 103, userDao);
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
	@Transactional
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
	@Transactional
	public void updatePassword(String oldPassword, String password, String userid) {
		if (StringUtils.isEmpty(oldPassword)) {
			throw new ServiceException("请输入原密码!");
		}
		if (StringUtils.isEmpty(password)) {
			throw new ServiceException("请输入新密码!");
		}
		SysUser user = userDao.findUserByUserId(userid);
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
	@Override
	public String findUserByUP(String username, String password) {
		SysUser userDB = userDao.findUserByUserName(username);
		if (userDB == null) {
			throw new ServiceException("没有该账户!");
		}
		String md5Pass = new SimpleHash("MD5", password, userDB.getSalt()).toHex();
		if (!md5Pass.equals(userDB.getPassword())) {
			throw new ServiceException("!");
		}
		// 用户名密码正确
		String token = "ZHXS_TICKET" + System.currentTimeMillis() + username;
		token = DigestUtils.md5DigestAsHex(token.getBytes());
		// 必须进行脱敏处理
		userDB.setPassword("******");
		String userJSON = ObjectMapperUtil.toJSON(userDB);
		jedisCluster.setex(token, 7 * 24 * 3600, userJSON);
		return token;
	}
}
