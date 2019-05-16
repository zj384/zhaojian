package com.zhxs.sys.dao;

import com.zhxs.sys.entity.SysLove;

public interface LoveDao {
	void saveLove(SysLove sysLove);
	String findLoveById(String id);
	void deleteById(String loveId);
}
