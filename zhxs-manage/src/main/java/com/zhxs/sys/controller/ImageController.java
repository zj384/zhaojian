package com.zhxs.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhxs.common.vo.JsonResult;
import com.zhxs.sys.entity.SysImage;
import com.zhxs.sys.service.FileService;
import com.zhxs.sys.service.ImageService;
import com.zhxs.sys.vo.FileResult;
import com.zhxs.sys.vo.ImageCondition;
import com.zhxs.sys.vo.ImageResult;

@Controller
@RequestMapping("/pic/")
public class ImageController {
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ImageService imageService;
	@RequestMapping("doListImageUI")
	public String doListImageUI() {
		return "/views/imageslist";
	}
	// 图片上传
	@RequestMapping("doUploadPic")
	@ResponseBody
	public FileResult doUploadPic(MultipartFile file) {
		return fileService.uploadPic(file);
	}
	
	@RequestMapping("doSaveImage")
	@ResponseBody
	public JsonResult doSaveImage(SysImage sysImage) {
		imageService.saveImage(sysImage);
		return new JsonResult("图片信息上传成功!");
	}
	@RequestMapping("doUpdateImage")
	@ResponseBody
	public JsonResult doUpdateImage(SysImage sysImage) {
		imageService.updateImage(sysImage);
		return new JsonResult("图片信息修改成功!");
	}
	
	@RequestMapping("doFindImage")
	@ResponseBody
	public JsonResult doFindImage(ImageCondition imageCondition) {
		List<ImageResult> findImage = imageService.findImage(imageCondition);
		return new JsonResult(findImage);
	}
	@RequestMapping("doFindImgById")
	@ResponseBody
	public JsonResult doFindImgById(Integer id) {
		ImageResult findImage = imageService.findImageById(id);
		return new JsonResult(findImage);
	}
	@RequestMapping("doFindImgPage")
	@ResponseBody
	public JsonResult doFindImgPage(Integer curr, Integer limit) {
		List<ImageResult> findImage = imageService.findImagePage(curr, limit);
		return new JsonResult(findImage);
	}
	@RequestMapping("doFindImgCount")
	@ResponseBody
	public JsonResult doFindImgCount() {
		int count = imageService.findImageCount();
		return new JsonResult(count);
	}
	@RequestMapping("doDeleteImgById")
	@ResponseBody
	public JsonResult doDeleteImgById(Integer id) {
		imageService.deleteImgById(id);
		return new JsonResult("删除成功");
	}
	@RequestMapping("changeLove")
	@ResponseBody
	public JsonResult changeLoge(Integer imageId, Integer isAdd) {
		imageService.saveLove(imageId,isAdd);
		return new JsonResult("ok");
		
	}
}
