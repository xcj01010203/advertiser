package com.xiaotu.common.util;

import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @类名 PropertiesUtil
 * @日期 2014-12-11
 * @作者
 * @功能 获取属性文件的内容并进行全局变量的初始化
 */
public class PropertiesUtil extends PropertyPlaceholderConfigurer
{
	public static final Logger logger = LoggerFactory
			.getLogger(PropertiesUtil.class);
	private static Properties properties;
	
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException
	{
		super.processProperties(beanFactoryToProcess, props);
		properties = props;
		if (logger.isDebugEnabled())
		{
			Iterator<Object> it = props.keySet().iterator();
			while (it.hasNext())
			{
				Object key = it.next();
				logger.debug(key + "=" + props.getProperty(key.toString()));
			}
		}
	}
	
	public static String getProperty(String key)
	{
		return properties.getProperty(key);
	}
	
	public static Integer getPropertyByInt(String key)
	{
		String value = getProperty(key);
		if (StringUtils.isEmpty(value))
			return null;
		return Integer.parseInt(value);
	}
}
