package com.xiaotu.common.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

import com.xiaotu.common.db.Page;
import com.xiaotu.common.db.util.UUIDUtils;

/**
 * MyBatis的Dao基类
 * @author
 */
@Component
public class MyBatisDao extends SqlSessionDaoSupport
{
	public static final String FIELD_NAME_UUID = "id";
	
	public int save(String key, Object object)
	{
		if (object != null)
		{
			if (object instanceof List<?>)
				for (Object item : (List<?>) object)
					UUIDUtils.setUUID(item, FIELD_NAME_UUID);
			else
				UUIDUtils.setUUID(object, FIELD_NAME_UUID);
		}
		return getSqlSession().insert(key, object);
	}
	
	public int save(String key, Object object, String tabId)
	{
		if (object != null)
		{
			if (object instanceof List<?>)
				for (Object item : (List<?>) object)
					if ("".equals(tabId) || tabId == null)
					{
						UUIDUtils.setUUID(item, tabId);
					}
					else
					{
						UUIDUtils.setUUID(item, FIELD_NAME_UUID);
					}
			else
				UUIDUtils.setUUID(object, FIELD_NAME_UUID);
		}
		return getSqlSession().insert(key, object);
	}
	
	public int delete(String key, Serializable id)
	{
		return getSqlSession().delete(key, id);
	}
	
	public int delete(String key, Object object)
	{
		return getSqlSession().delete(key, object);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Object params)
	{
		return (T) getSqlSession().selectOne(key, params);
	}
	
	public <T> List<T> getList(String key)
	{
		return getSqlSession().selectList(key);
	}
	
	public <T> List<T> getList(String key, Object params)
	{
		return getSqlSession().selectList(key, params);
	}
	
	public <K, V> Map<K, V> getMap(String key, Object params, String mapKey)
	{
		return getSqlSession().selectMap(key, params, mapKey);
	}
	
	public <K, V> Map<K, V> getMap(String key, String mapKey)
	{
		return getSqlSession().selectMap(key, mapKey);
	}
	
	public int update(String key, Object params)
	{
		return getSqlSession().update(key, params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> queryPageList(String key, Object params, Page page)
	{
		List list = null;
		if (page != null)
		{
			list = getSqlSession().selectList(key, params, page);
		}
		else
		{
			list = getSqlSession().selectList(key, params);
		}
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public Page queryPage(String key, Object params, Page page)
	{
		List list = queryPageList(key, params, page);
		if (page != null)
		{
			page.setResult(list);
		}
		return page;
	}
	
}
