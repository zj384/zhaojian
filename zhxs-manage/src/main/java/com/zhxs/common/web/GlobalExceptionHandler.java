package com.zhxs.common.web;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhxs.common.vo.JsonResult;
/**全局异常处理类*/
@ControllerAdvice
public class GlobalExceptionHandler {
	//JDK中的自带的日志API
	private Logger log=Logger.getLogger(GlobalExceptionHandler.class.getName());
	
	
	@ExceptionHandler(RuntimeException.class)
    @ResponseBody
	public JsonResult doHandleRuntimeException(
			RuntimeException e){
    	e.printStackTrace();//也可以写日志
		log.info(e.getMessage());
		return new JsonResult(e);//封装异常信息
	}
}


