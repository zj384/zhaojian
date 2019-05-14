package com.zhxs.sys.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhxs.common.annotation.RequestLog;
import com.zhxs.common.exception.ServiceException;
import com.zhxs.sys.dao.CityDao;
import com.zhxs.sys.dao.SchoolDao;
import com.zhxs.sys.dao.SchoolTypeDao;
import com.zhxs.sys.entity.SysCity;
import com.zhxs.sys.entity.SysSchool;
import com.zhxs.sys.entity.SysSchoolType;
import com.zhxs.sys.service.SchoolService;

@Service
public class SysSchoolServiceImpl implements SchoolService {
	@Autowired
	private SchoolDao schoolDao;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private SchoolTypeDao schoolTypeDao;
	
	@Override
	public List<SysSchool> findAllObject(SysSchool sysSchool) {
//		PageHelper.startPage(startPage, pageSize);
		List<SysSchool> list = schoolDao.findAllObject(sysSchool);
//		PageInfo<SysSchool> pi = new PageInfo<>(list);
		return list;
	}

	@Override
	public List<SysCity> findCityByParentId(Integer parentId) {
		List<SysCity> list = cityDao.findSonByProvinceId(parentId);
		return list;
	}

	@Override
	public List<SysSchoolType> findSchoolType() {
		List<SysSchoolType> list = schoolTypeDao.findAllSchoolType();
		return list;
	}
	@RequestLog("增加学校信息")
	@Override
	public int insertSchool(SysSchool sysSchool) {
		int rows = 0;
		try {
			rows = schoolDao.insertSchool(sysSchool);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("添加失败");
		}
		return rows;
	}
	@RequestLog("删除学校信息")
	@Override
	public int deleteSchoolById(Integer id) {
		int rows = 0;
		try {
			rows = schoolDao.deleteSchoolById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("删除失败");
		}
		return rows;
	}
	@RequestLog("修改学校信息")
	@Override
	public int updateSchool(SysSchool sysSchool) {
		int rows = 0;
		try {
			rows = schoolDao.updateSchool(sysSchool);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("修改失败");
		}
		return rows;
	}

	@Override
	public List<SysSchool> FindSchoolByCountyId(Integer countyid, Integer sctypeid) {
		List<SysSchool> sysSchools = schoolDao.findSchoolByCountyId(countyid, sctypeid);
		return sysSchools;
	}

}
