package com.zhxs.sys.service;

import org.springframework.web.multipart.MultipartFile;

import com.zhxs.sys.vo.FileResult;

public interface FileService {
	//上传图片
	FileResult uploadPic(MultipartFile uploadFile);
}
