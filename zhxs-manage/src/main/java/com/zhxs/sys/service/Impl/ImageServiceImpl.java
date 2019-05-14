package com.zhxs.sys.service.Impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.zhxs.common.annotation.RequestLog;
import com.zhxs.common.exception.ServiceException;
import com.zhxs.sys.dao.ImageDao;
import com.zhxs.sys.entity.SysImage;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.ImageService;
import com.zhxs.sys.vo.ImageResult;
@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageDao imageDao;

	@RequestLog("图片信息保存")
	@Override
	public int saveImage(SysImage sysImage) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null) {
			throw new ServiceException("请先登录!");
		}
		if (sysImage == null) {
			throw new ServiceException("请输入信息!");
		}
		if (sysImage.getClazzid() == null) {
			throw new ServiceException("请选择班级");
		}
		sysImage.setUserid(user.getId());
		
		int rows = 0;
		try {
			rows = imageDao.insertImage(sysImage);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("图片保存失败!");
		}
		return rows;
	}
	@RequestLog("浏览图片")
	@Override
	public List<ImageResult> findImage(Integer level, Integer isUser, Integer page, Integer limit) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		int startIndex = limit * (page-1);
		if (user == null) {
			throw new ServiceException("请先登录!");
		}
		String clazzid = imageDao.findClassIdByUserIdAndLevel(user.getId(), level);
		
		if (StringUtils.isEmpty(clazzid) && isUser == 0) {
			throw new ServiceException("您未添加此班级");
		}
		List<ImageResult> findImage = null;
		try {
			if (isUser == 0) {
				startIndex = page>1?(startIndex+1):startIndex;
				findImage = imageDao.findImage(null, clazzid, startIndex, limit);
			} else if(isUser == 1) {
				findImage = imageDao.findImage(user.getId(), null, startIndex, limit);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("服务器错误!");
		}
		return findImage;
	}
	@Override
	public ImageResult findImageById(Integer id) {
		ImageResult image =  imageDao.findImageById(id);
		return image;
	}
	@RequestLog("修改图片信息")
	@Override
	public int updateImage(SysImage sysImage) {
		if (sysImage == null) {
			throw new ServiceException("请输入信息!");
		}
		int rows = 0;
		try {
			rows = imageDao.updateImage(sysImage);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("图片保存失败!");
		}
		return rows;
	}
	// 后台分页查询所有图片
	@RequestLog("后台查询所有图片")
	@Override
	public List<ImageResult> findImagePage(Integer curr, Integer limit) {
		int startIndex = limit * (curr-1);
		List<ImageResult> findPageImage = imageDao.findPageImage(startIndex, limit);
		return findPageImage;
	}
	// 图片总数
	@Override
	public int findImageCount() {
		int count = imageDao.selectImageCount();
		return count;
	}
	@RequestLog("删除图片")
	@Override
	public int deleteImgById(Integer id) {
		if (id == null || id < 0) {
			throw new ServiceException("图片id获取失败");
		}
		int rows = 0;
		try {
			rows = imageDao.deleteImageById(id);
		} catch (Exception e) {
			throw new ServiceException("删除失败");
		}
		return rows;
	}

}
