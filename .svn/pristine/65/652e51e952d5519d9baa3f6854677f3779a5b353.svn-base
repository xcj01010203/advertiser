package com.xiaotu.common.mvc;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.xiaotu.common.db.MyBatisDao;
import com.xiaotu.common.db.Page;

/**
 * @类名 BaseService
 * @日期 2014-12-17
 * @作者
 * @功能 业务层基类
 */
@Service
public class BaseService
{
	public static final String BEAN_NAME = "baseService";
	
	@Autowired
	@Qualifier("transactionManager")
	private DataSourceTransactionManager transactionManager;
	
	@Autowired
	protected MyBatisDao myBatisDao;
	
	public int save(String key, Object object)
	{
		return myBatisDao.save(this.getKey(key), object);
	}
	
	public int delete(String key, Serializable id)
	{
		return myBatisDao.delete(this.getKey(key), id);
	}
	
	public int delete(String key, Object object)
	{
		return myBatisDao.delete(this.getKey(key), object);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Object params)
	{
		return (T) myBatisDao.get(this.getKey(key), params);
	}
	
	public <T> List<T> getList(String key)
	{
		return myBatisDao.getList(this.getKey(key));
	}
	
	public <T> List<T> getList(String key, Object params)
	{
		return myBatisDao.getList(this.getKey(key), params);
	}
	
	public <K, V> Map<K, V> getMap(String key, Object params, String mapKey)
	{
		return myBatisDao.getMap(this.getKey(key), params, mapKey);
	}
	
	public <K, V> Map<K, V> getMap(String key, String mapKey)
	{
		return myBatisDao.getMap(this.getKey(key), mapKey);
	}
	
	public int update(String key, Object params)
	{
		return myBatisDao.update(this.getKey(key), params);
	}
	
	public <T> List<T> queryPageList(String key, Object params, Page page)
	{
		return myBatisDao.queryPageList(this.getKey(key), params, page);
	}
	
	public Page queryPage(String key, Object params, Page page)
	{
		return myBatisDao.queryPage(this.getKey(key), params, page);
	}
	
	private String getKey(String key)
	{
		if (StringUtils.isNotEmpty(key) && key.contains("."))
			return key;
		String namespace = this.getKey();
		return StringUtils.isEmpty(namespace) ? key : (namespace + "." + key);
	}
	
	protected TransactionStatus getTransactionStatus()
	{
		return transactionManager
				.getTransaction(new DefaultTransactionDefinition()); // 获得事务状态
	}
	
	protected void rollback(TransactionStatus status)
	{
		this.transactionManager.rollback(status);
	}
	
	protected void commit(TransactionStatus status)
	{
		this.transactionManager.commit(status);
	}
	
	/**
	 * 获取mapper的namespace
	 * @return namespace
	 */
	protected String getKey()
	{
		return null;
	}
}
