package com.xiaotu.advertiser.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.common.util.TextStitchUtil;

/**
 * 登录认证的拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	/** 
     * Handler执行之前调用这个方法 
     */ 
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception{
		//验证用户session信息是否有效
		if (request.getSession().getAttribute(Constants.SESSION_USER) != null){
			return true;
		}
		//不符合条件的，跳转到登录界面
		TextStitchUtil.outPutText(request, response,Constants.LOGIN_URL);
		return false;
	}
	
	/** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception{
	}
	
	/** 
     * Handler执行完成之后调用这个方法 
     */  
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception{
	}
	
}