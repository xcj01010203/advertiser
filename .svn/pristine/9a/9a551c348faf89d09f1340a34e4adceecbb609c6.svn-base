package com.xiaotu.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @类名 SpringContextHolder
 * @日期 2015年8月5日
 * @作者 高海军
 * @功能 spring上下文
 */
@Service
public class SpringContextHolder implements ApplicationContextAware
{
	private static ApplicationContext applicationContext;
	
	
	
	public static void setApplicationContext()
	{
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"application-context.xml");
		SpringContextHolder ips = (SpringContextHolder) ac
				.getBean("springContextHolder");
		ips.setApplicationContext(ac);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException
	{
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
	
	public static Object getBean(String beanName)
	{
		return applicationContext.getBean(beanName);
	}
	
	public static <T> T getBean(String beanName, Class<T> clazz)
	{
		return applicationContext.getBean(beanName, clazz);
	}
	
}
