package com.zhxs.common.util;

import org.apache.shiro.SecurityUtils;

import com.zhxs.sys.entity.SysUser;

public class ShiroUtils {

	 public static SysUser getPrincipal(){
		 return (SysUser)SecurityUtils
		.getSubject().getPrincipal();
	 }
}
