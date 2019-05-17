package com.zhxs.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhxs.common.util.ObjectMapperUtil;
import com.zhxs.common.util.UserThreadLocal;
import com.zhxs.sys.entity.SysUser;

import redis.clients.jedis.JedisCluster;

/**
 * 核心:获取用户的访问数据/路径request/response
 * 
 * @author hehda
 *
 */
@Component
public class UserInterceptor implements HandlerInterceptor {
	@Autowired
	private JedisCluster jedisCluster;
	// 完成用户检验,如果没有登录则跳转用户登录页面
	// 如果用户已经登录则放行
	// boolean: true 放行 false: 拦截+重定向
	/**
	 * 用户拦截器步骤:
	 * 	1.首先获取用户的cookie数据
	 * 	2.判断用户是否已经登录.
	 * 		如果用户没有登录,则重定向到用户登录页面.
	 * 		如果用户已经登录,则判断redis中是否有数据.
	 * 			有:表示用户之前登录成功予以放行
	 * 			无:表示用户登录失败,之后重定向到登录页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1.获取用户cookis数据
		String token = null;
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			response.sendRedirect("/doLoginUI");
			return false;
		}
		for (Cookie cookie : cookies) {
			if ("ZHXS_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		if (!StringUtils.isEmpty(token)) {
			// 判断redis中是否有数据
			String json = jedisCluster.get(token);
			if (!StringUtils.isEmpty(json)) {
				SysUser user = ObjectMapperUtil.toObject(json, SysUser.class);
				UserThreadLocal.set(user);
				return true;
			}
		}
		// 如果程序进行到这里,表示用户一定没有登录
		response.sendRedirect("/doLoginUI");
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove(); // 删除线程数据
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
