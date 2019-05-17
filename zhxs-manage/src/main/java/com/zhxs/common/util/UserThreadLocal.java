package com.zhxs.common.util;

import com.zhxs.sys.entity.SysUser;

// 该对象保存的是用户信息
public class UserThreadLocal {
	// 由JVM直接创建
	private static final ThreadLocal<SysUser> thread = new ThreadLocal<>();
	public static void set(SysUser user) {
		thread.set(user);
	}
	public static SysUser get() {
		return thread.get();
	}
	public static void remove() {
		thread.remove();
	}
}
