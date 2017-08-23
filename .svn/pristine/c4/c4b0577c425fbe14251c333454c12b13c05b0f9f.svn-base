package com.xiaotu.common.db.interceptor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;
import javax.xml.bind.PropertyException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaotu.common.db.DynamicDataSource;
import com.xiaotu.common.db.IDialect;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.db.util.ReflectUtil;

// 只拦截select部分
@Intercepts({ @Signature(
		type = Executor.class,
		method = "query",
		args = { MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class }) })
public class PaginationInterceptor implements Interceptor
{
	
	private static Logger log = LoggerFactory
			.getLogger(PaginationInterceptor.class);
	
	private IDialect dialect;
	
	private final Map<String, IDialect> dialectMap = new HashMap<String, IDialect>();
	
	private void setMetaParamters(BoundSql countBS, BoundSql boundSql)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException
	{
		Field metaParamsField = ReflectUtil.getFieldByFieldName(boundSql,
				"metaParameters");
		if (metaParamsField != null)
		{
			MetaObject mo = (MetaObject) ReflectUtil
					.getValueByFieldName(boundSql, "metaParameters");
			ReflectUtil.setValueByFieldName(countBS, "metaParameters", mo);
		}
	}
	
	public Object intercept(Invocation invocation) throws Throwable
	{
		try
		{
			MappedStatement mappedStatement = (MappedStatement) invocation
					.getArgs()[0];
			Object parameter = invocation.getArgs()[1];
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			String originalSql = null;
			Object invocationObj = null;
			if (boundSql != null)
			{
				originalSql = boundSql.getSql() == null
						|| "".equals(boundSql.getSql()) ? null
								: boundSql.getSql().trim();
				invocationObj = invocation.getArgs()[2];
			}
			
			log.debug("originalSql : " + originalSql);
			
			if (boundSql != null && originalSql != null && invocationObj != null
					&& invocationObj instanceof Page)
			{
				originalSql = originalSql.trim();
				Page page = (Page) invocationObj;
				Object parameterObject = boundSql.getParameterObject();
				int totalRows = page.getTotalRows();
				// 得到总记录数
				if (totalRows == 0)
				{ // 对符合条件的数据进行统计 生成总页数
					StringBuffer countSql = new StringBuffer(
							originalSql.length() + 100);
					countSql.append("select count(1) from (")
							.append(originalSql).append(") t");
					Connection conn = mappedStatement.getConfiguration()
							.getEnvironment().getDataSource().getConnection();
					PreparedStatement countStmt = conn
							.prepareStatement(countSql.toString());
					BoundSql countBS = new BoundSql(
							mappedStatement.getConfiguration(),
							countSql.toString(),
							boundSql.getParameterMappings(), parameterObject);
					this.setMetaParamters(countBS, boundSql);
					
					for (ParameterMapping mapping : boundSql
							.getParameterMappings())
					{
						String prop = mapping.getProperty();
						if (boundSql.hasAdditionalParameter(prop))
						{
							countBS.setAdditionalParameter(prop,
									boundSql.getAdditionalParameter(prop));
						}
					}
					
					setParameters(countStmt, mappedStatement, countBS,
							parameterObject);
					
					ResultSet rs = countStmt.executeQuery();
					if (rs.next())
					{
						totalRows = rs.getInt(1);
					}
					page.setTotalRows(totalRows);
					rs.close();
					countStmt.close();
					conn.close();
				}
				// 分页查询 本地化对象 修改数据库注意修改实现
				String pageSql = generatePageSql(mappedStatement
						.getConfiguration().getEnvironment().getDataSource(),
						originalSql, page);
				
				log.debug("pageSql : " + pageSql);
				
				invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET,
						RowBounds.NO_ROW_LIMIT);
				BoundSql newBoundSql = new BoundSql(
						mappedStatement.getConfiguration(), pageSql,
						boundSql.getParameterMappings(),
						boundSql.getParameterObject());
				this.setMetaParamters(newBoundSql, boundSql);
				MappedStatement newMs = copyFromMappedStatement(mappedStatement,
						new BoundSqlSqlSource(newBoundSql));
				invocation.getArgs()[0] = newMs;
			}
		}
		catch (Exception e)
		{
			log.error("分页拦截插件.", e);
			return invocation.proceed();
		}
		
		return invocation.proceed();
	}
	
	public static class BoundSqlSqlSource implements SqlSource
	{
		BoundSql boundSql;
		
		public BoundSqlSqlSource(BoundSql boundSql)
		{
			this.boundSql = boundSql;
		}
		
		public BoundSql getBoundSql(Object parameterObject)
		{
			return boundSql;
		}
	}
	
	public Object plugin(Object arg0)
	{
		return Plugin.wrap(arg0, this);
	}
	
	/**
	 * 根据数据库方言，生成Sql
	 * @param sql
	 * @param page
	 * @return
	 * @throws PropertyException
	 */
	@SuppressWarnings("static-access")
	private String generatePageSql(DataSource dataSource, String originalSql,
			Page page) throws PropertyException
	{
		// String pageSql = new MysqlDialect().getLimitString(originalSql,
		// (page.getCurrentPage() - 1) * page.getPageSize(),
		// page.getPageSize());
		
		IDialect currDialect = dialect;
		if (dataSource instanceof DynamicDataSource)
		{
			String currDataSourceName = ((DynamicDataSource) dataSource)
					.getDataSource();
			if (!StringUtils.isEmpty(currDataSourceName)
					&& dialectMap.containsKey(currDataSourceName))
				currDialect = dialectMap.get(currDataSourceName);
		}
		
		String pageSql = currDialect.getLimitSQL(originalSql,
				(page.getCurrentPage() - 1) * page.getPageSize(),
				page.getPageSize());
		return pageSql.toString();
	}
	
	public void setProperties(Properties p)
	{
		String defaultDialectClass = (String) p.get("defaultDialectClass");
		
		try
		{
			dialect = (IDialect) Class.forName(defaultDialectClass)
					.newInstance();
			
			String dialectNames = (String) p.get("dialectNames");
			if (StringUtils.isEmpty(dialectNames))
				return;
			String[] dialectNameArr = dialectNames.split(",");
			
			for (String dialectName : dialectNameArr)
			{
				dialectMap.put(dialectName, (IDialect) Class
						.forName((String) p.get(dialectName)).newInstance());
			}
		}
		catch (Exception e)
		{
			log.error(ExceptionUtils.getStackTrace(e));
			throw new RuntimeException("cannot create dialect instance", e);
		}
	}
	
	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.
	 * DefaultParameterHandler
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException
	{
		ErrorContext.instance().activity("setting parameters")
				.object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		if (parameterMappings != null)
		{
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration
					.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null
					: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++)
			{
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT)
				{
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(
							propertyName);
					if (parameterObject == null)
					{
						value = null;
					}
					else if (typeHandlerRegistry
							.hasTypeHandler(parameterObject.getClass()))
					{
						value = parameterObject;
					}
					else if (boundSql.hasAdditionalParameter(propertyName))
					{
						value = boundSql.getAdditionalParameter(propertyName);
					}
					else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
							&& boundSql.hasAdditionalParameter(prop.getName()))
					{
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null)
						{
							value = configuration.newMetaObject(value)
									.getValue(propertyName.substring(
											prop.getName().length()));
						}
					}
					else
					{
						value = metaObject == null ? null
								: metaObject.getValue(propertyName);
					}
					// TypeHandler<Object> typeHandler =
					// parameterMapping.getTypeHandler();
					TypeHandler<Object> typeHandler = (TypeHandler<Object>) parameterMapping
							.getTypeHandler();
					if (typeHandler == null)
					{
						throw new ExecutorException(
								"There was no TypeHandler found for parameter "
										+ propertyName + " of statement "
										+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value,
							parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	private MappedStatement copyFromMappedStatement(MappedStatement ms,
			SqlSource newSqlSource)
	{
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
				ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		// builder.keyProperty(ms.getKeyProperty());
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		MappedStatement newMs = builder.build();
		return newMs;
	}
	
}
