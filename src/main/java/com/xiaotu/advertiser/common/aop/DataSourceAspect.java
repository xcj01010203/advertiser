package com.xiaotu.advertiser.common.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaotu.common.db.DynamicDataSource;

/**
 * @类名 DataSourceAspect
 * @日期 2017年7月14日
 * @作者 高海军
 * @功能 数据源切换切面
 */
public class DataSourceAspect
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataSourceAspect.class);
	
	public void setDataSource()
	{
		DynamicDataSource.setDataSource(null);
		LOGGER.debug("set default datasource");
	}
}
