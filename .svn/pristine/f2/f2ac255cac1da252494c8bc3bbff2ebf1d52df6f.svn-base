package com.xiaotu.common.util;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @类名 RequestUtils
 * @日期 2016年1月18日
 * @作者 高海军
 * @功能
 */
public class RequestUtils
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RequestUtils.class);
	
	private static final String PROXS[] = { "X-Forwarded-For",
			"Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
			"HTTP_X_FORWARDED_FOR", "x-real-ip" };// 能获取到ip的字段集合
	
	public static String getQueryParam(String key)
	{
		String val = getRequest().getParameter(key);
		if (StringUtils.isEmpty(val))
			return StringUtils.EMPTY;
		return val;
	}
	
	public static String getRequestURI()
	{
		HttpServletRequest request = getRequest();
		String uri = request.getRequestURI();
		if (!StringUtils.isEmpty(request.getContextPath()))
			uri = uri.replace(request.getContextPath(), StringUtils.EMPTY);
		return uri;
	}
	
	public static HttpServletRequest getRequest()
	{
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}
	
	public static String getRemoteIP()
	{
		HttpServletRequest request = getRequest();
		if (null == request)
			return null;
		
		String ip = null;
		for (String prox : PROXS)
		{
			ip = request.getHeader(prox);
			if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip))
				continue;
			else
				break;
		}
		
		if (StringUtils.isBlank(ip))
			return request.getRemoteAddr();
		
		return ip;
	}
	
	public static String getBodyString(HttpServletRequest request)
			throws Exception
	{
		BufferedReader br = null;
		String str = StringUtils.EMPTY;
		try
		{
			if (request == null)
				request = getRequest();
			String inputLine;
			br = request.getReader();
			while ((inputLine = br.readLine()) != null)
			{
				str += inputLine;
			}
		}
		catch (Exception e)
		{
			LOGGER.warn(ExceptionUtils.getStackTrace(e));
		}
		finally
		{
			if (br != null)
				br.close();
		}
		return str;
	}
	
	public static void setRequestAttribute(String key, Object value)
	{
		getRequest().setAttribute(key, value);
	}
}
