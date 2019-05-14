package com.zhxs.sys.service;

import java.util.List;

import com.zhxs.sys.entity.SysImage;
import com.zhxs.sys.vo.ImageResult;

public interface ImageService {
	int saveImage(SysImage sysImage);
	List<ImageResult> findImage(Integer level, Integer isUser, Integer page, Integer limit);
	ImageResult findImageById(Integer id);
	int updateImage(SysImage sysImage);
	List<ImageResult> findImagePage(Integer curr, Integer limit);
	int findImageCount();
	int deleteImgById(Integer id);
}
