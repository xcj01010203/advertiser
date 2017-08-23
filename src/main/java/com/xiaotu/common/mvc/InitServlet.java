package com.xiaotu.common.mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xiaotu.common.util.SpringContextHolder;

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet
{
	
	private static Logger log = LoggerFactory.getLogger(InitServlet.class);
	
	@Override
	public void init() throws ServletException
	{
		try
		{
			freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_SLF4J);
			ApplicationContext context = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());
			(new SpringContextHolder()).setApplicationContext(context);
		}
		catch (Exception e)
		{
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}
}
