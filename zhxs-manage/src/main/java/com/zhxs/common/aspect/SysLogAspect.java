package com.zhxs.common.aspect;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhxs.common.annotation.RequestLog;
import com.zhxs.common.util.IPUtils;
import com.zhxs.common.util.UserThreadLocal;
import com.zhxs.sys.dao.LogDao;
import com.zhxs.sys.entity.SysLog;

@Order(1)
@Aspect
@Component
public class SysLogAspect {
	private Logger log=Logger.getLogger(SysLogAspect.class);
    @Autowired
	private LogDao LogDao;
	@Pointcut("@annotation(com.zhxs.common.annotation.RequestLog)")
	public void logPointCut(){}
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint //连接点
    		jointPoint) throws Throwable{
    	long startTime=System.currentTimeMillis();
    	//执行目标方法(result为目标方法的执行结果)
    	Object result=jointPoint.proceed();
    	long endTime=System.currentTimeMillis();
    	long totalTime=endTime-startTime;
    	log.info("方法执行的总时长为:"+totalTime);
    	saveSysLog(jointPoint,totalTime);
    	return result;
    }
    private void saveSysLog(ProceedingJoinPoint point,
    		  long totleTime) throws NoSuchMethodException, SecurityException, JsonProcessingException{
    	//1.获取日志信息
    	MethodSignature ms=
    	(MethodSignature)point.getSignature();
    	Class<?> targetClass=
    	point.getTarget().getClass();
    	String className=targetClass.getName();
    	//获取接口声明的方法
    	String methodName=ms.getMethod().getName();
    	Class<?>[] parameterTypes=ms.getMethod().getParameterTypes();
    	//获取目标对象方法
    	Method targetMethod=
    	targetClass.getDeclaredMethod(methodName,parameterTypes);
    	//获取登陆用户
    	String username=UserThreadLocal.get().getUsername();
    	//获取方法参数
    	Object[] paramsObj=point.getArgs();
    	//将参数转换为字符串
    	String params=new ObjectMapper()
    	.writeValueAsString(paramsObj);
    	//2.封装日志信息
    	SysLog log=new SysLog();
    	log.setUsername(username);//登陆的用户
    	//假如目标方法对象上有注解,我们获取注解定义的操作值
    	RequestLog requestLog=
    	targetMethod.getDeclaredAnnotation(RequestLog.class);
    	log.setOperation(requestLog.value());
    	log.setMethod(className+"."+methodName);//className.methodName()
    	log.setParams(params);//method params
    	log.setIp(IPUtils.getIpAddr());//ip 地址
    	log.setTime(totleTime);//
    	log.setCreatedTime(new Date());
    	//3.保存日志信息
    	LogDao.insertLog(log);
    }
}
