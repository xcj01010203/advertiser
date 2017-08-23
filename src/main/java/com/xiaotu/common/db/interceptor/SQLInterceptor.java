package com.xiaotu.common.db.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaotu.common.db.util.ReflectUtil;

/**
 * 运行时SQL输出
 */
@Intercepts({ @Signature(
		type = StatementHandler.class,
		method = "prepare",
		args = { Connection.class }) })
public class SQLInterceptor implements Interceptor
{
	
	private static Logger log = LoggerFactory.getLogger(SQLInterceptor.class);
	
	public Object intercept(Invocation invocation) throws Throwable
	{
		
		log.debug("--------------SQLInterceptor intercept------------------");
		
		RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
				.getTarget();
		BoundSql boundSql = statementHandler.getBoundSql();
		BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtil
				.getValueByFieldName(statementHandler, "delegate");
		MappedStatement mappedStatement = (MappedStatement) ReflectUtil
				.getValueByFieldName(delegate, "mappedStatement");
		
		log.debug(
				"-----------------------mappedStatement.getId()--------------------------"
						+ mappedStatement.getId());
		log.debug(
				"-----------------------boundSql.getSql()--------------------------"
						+ boundSql.getSql());
		
		return invocation.proceed();
	}
	
	@Override
	public Object plugin(Object arg0)
	{
		return Plugin.wrap(arg0, this);
	}
	
	@Override
	public void setProperties(Properties properties)
	{
	}
}
