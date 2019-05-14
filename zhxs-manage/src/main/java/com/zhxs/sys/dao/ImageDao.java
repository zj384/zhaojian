package com.zhxs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhxs.sys.entity.SysImage;
import com.zhxs.sys.vo.ImageResult;

public interface ImageDao {
	int insertImage(SysImage sysImage);
	List<ImageResult> findImage(@Param("userid")String userid, @Param("clazzid")String clazzid, @Param("startIndex")Integer startIndex, @Param("limit")Integer limit);
	String findClassIdByUserIdAndLevel(@Param("userid")String userid, @Param("level")Integer level);
	ImageResult findImageById(Integer id);
	int updateImage(SysImage sysImage);
	List<ImageResult> findPageImage(@Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);
	int selectImageCount();
	int deleteImageById(Integer id);
}
