package com.xiaotu.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源实体类
 * @author xuchangjian 2017年7月11日下午2:53:10
 */
public class DynamicDataSource extends AbstractRoutingDataSource
{
	public static final String DATA_SOURCE_MYSQL = "MySQL";
	public static final String DATA_SOURCE_PG = "PostgreSQL";
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
	
	public static void setDataSource(String dataSource)
	{
		contextHolder.set(dataSource);
	}
	
	public static String getDataSource()
	{
		return contextHolder.get();
	}
	
	@Override
	protected Object determineCurrentLookupKey()
	{
		return getDataSource();
	}
	
}
