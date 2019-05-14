package com.zhxs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhxs.sys.entity.SysCity;

public interface CityDao {
	List<SysCity> findSonByProvinceId(@Param("parentid")Integer parentid);
}
