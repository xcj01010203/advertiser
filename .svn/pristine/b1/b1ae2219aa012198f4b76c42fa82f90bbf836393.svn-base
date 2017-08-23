package com.xiaotu.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.exception.BusinessException;

public class SessionUtil
{
	
	/**
	 * 获取用户登录信息
	 * @return
	 */
	public static UserModel getSessionUser()
	{
		UserModel user = (UserModel) RequestUtils.getRequest().getSession()
				.getAttribute(Constants.SESSION_USER);
		if (user == null)
			throw new BusinessException("用户信息已过期");
		return user;
	}
	
	/**
	 * 获取session中当前项目信息
	 * @return
	 */
	public static ProjectModel getSessionProject()
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		ProjectModel project = (ProjectModel) request.getSession()
				.getAttribute(Constants.SESSION_PROJECT);
		if (project == null)
			throw new BusinessException("项目信息已过期");
		return project;
	}
	
	public static HttpSession getSession(HttpServletRequest request,
			String sessionKey)
	{
		HttpSession session = request.getSession();
		session.getAttribute(sessionKey);
		return session;
	}
	
	public static HttpSession setSession(HttpSession session, String sessionKey,
			Object obj)
	{
		session.setAttribute(sessionKey, obj);
		return session;
	}
}
