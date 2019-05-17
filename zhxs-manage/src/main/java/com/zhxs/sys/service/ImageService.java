package com.zhxs.sys.service;

import java.util.List;

import com.zhxs.sys.entity.SysImage;
import com.zhxs.sys.entity.SysUser;
import com.zhxs.sys.vo.ImageCondition;
import com.zhxs.sys.vo.ImageResult;

public interface ImageService {
	int saveImage(SysImage sysImage);
	List<ImageResult> findImage(ImageCondition imageCondition, SysUser user);
	ImageResult findImageById(Integer id);
	int updateImage(SysImage sysImage);
	List<ImageResult> findImagePage(Integer curr, Integer limit);
	int findImageCount();
	int deleteImgById(Integer id);
	void saveLove(Integer imageId, Integer isAdd, SysUser user);
}
