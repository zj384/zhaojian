package com.zhxs.sys.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.zhxs.common.annotation.RequestLog;
import com.zhxs.common.exception.ServiceException;
import com.zhxs.sys.dao.ImageDao;
import com.zhxs.sys.dao.LoveDao;
import com.zhxs.sys.entity.SysImage;
import com.zhxs.sys.entity.SysLove;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.service.ImageService;
import com.zhxs.sys.vo.FindImgCon;
import com.zhxs.sys.vo.ImageCondition;
import com.zhxs.sys.vo.ImageResult;
@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private LoveDao loveDao;

	@RequestLog("图片信息保存")
	@Override
	@Transactional
	public int saveImage(SysImage sysImage) {
		if (sysImage == null) {
			throw new ServiceException("请输入信息!");
		}
		if (sysImage.getUserid() == null) {
			throw new ServiceException("请先登录!");
		}
		if (sysImage.getClazzid() == null) {
			throw new ServiceException("请选择班级");
		}
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
	public List<ImageResult> findImage(ImageCondition imageCondition,SysUser user) {
		int startIndex = imageCondition.getLimit() * (imageCondition.getPage()-1);
		String userid = user.getId();

		String clazzid = imageDao.findClassIdByUserIdAndLevel(userid, imageCondition.getLevel());
		
		if (StringUtils.isEmpty(clazzid) && imageCondition.getIsUser() == 0) {
			throw new ServiceException("您未添加此班级");
		}
		List<ImageResult> findImage = null;
		FindImgCon findImgCon = new FindImgCon();
		findImgCon.setIslove(imageCondition.getIslove());//是否根据点赞数条件查询
		findImgCon.setLimit(imageCondition.getLimit());
		findImgCon.setStartIndex(startIndex);
		findImgCon.setClazzid(clazzid);
		findImgCon.setUserid(userid);
		String loveId = null; 
		ImageResult imageResult = null;
		try {
			if (imageCondition.getIsUser() == 0) {
				findImgCon.setIsuser(0);
				// 根据班级id查询
				findImage = imageDao.findImage(findImgCon);
				for (int i = 0; i < findImage.size(); i++) {
					imageResult = findImage.get(i);
					loveId = userid + imageResult.getId();
					String islove = loveDao.findLoveById(loveId);
					imageResult.setIslove(islove);
					findImage.set(i, imageResult);
				}
			} else if(imageCondition.getIsUser() == 1) {
				findImgCon.setIsuser(1);
				// 根据用户id查询
				findImage = imageDao.findImage(findImgCon);
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
	@Transactional
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
	@Transactional
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
	@Override
	@Transactional
	public void saveLove(Integer imageId, Integer isAdd, SysUser user) {
		ImageResult image =  imageDao.findImageById(imageId);
		Integer loveNum = image.getLove();
		SysLove sysLove = new SysLove();
		String loveId = user.getId() + imageId;
		sysLove.setId(loveId);
		sysLove.setImageid(imageId);
		sysLove.setUserid(user.getId());
		if (isAdd == 1) {
			loveNum++;
			loveDao.saveLove(sysLove);
		} else {
			loveNum--;
			loveDao.deleteById(loveId);
		}
		SysImage sysImage = new SysImage();
		sysImage.setId(imageId);
		sysImage.setLove(loveNum);
		imageDao.updateImageLove(sysImage);
	}

}
