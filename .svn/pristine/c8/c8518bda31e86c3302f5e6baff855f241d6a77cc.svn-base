package com.xiaotu.common.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.reflect.TypeToken;
import com.xiaotu.common.util.GsonUtils;

/**
 * @类名 TVRateCache
 * @日期 2015年10月21日
 * @作者 高海军
 * @功能 缓存处理类
 */
@Repository
public class CacheHandler
{
	@Autowired
	private RedisClientTemplate redisClient;
	
	/**
	 * 设置缓存
	 * @param key 键值
	 * @param value 缓存数据
	 * @param timeout 缓存时间，单位s
	 */
	public <T> void setCache(String key, T value, int timeout)
	{
		redisClient.setex(key, timeout, GsonUtils.toJson(value));
	}
	
	public <T> void setCache(String key, T value)
	{
		redisClient.set(key, GsonUtils.toJson(value));
	}
	
	public void setCache(String key, String value)
	{
		redisClient.set(key, value);
	}
	
	public void setCache(String key, String value, int timeout)
	{
		redisClient.setex(key, timeout, value);
	}
	
	public <T> void addListCache(String key, T value)
	{
		if (value == null)
			return;
		redisClient.rpush(key, GsonUtils.toJson(value));
	}
	
	@SuppressWarnings({ "unchecked" })
	public <V> List<Map<String, V>> getListCache(String key, long start,
			long end)
	{
		List<String> list = redisClient.lrange(key, start, end);
		if (list == null || list.isEmpty())
			return null;
		List<Map<String, V>> rowList = new ArrayList<Map<String, V>>();
		for (String row : list)
			rowList.add((Map<String, V>) GsonUtils.fromJson(row, Map.class));
		return rowList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> getList(String key, long start, long end, Class clazz)
	{
		List<String> list = redisClient.lrange(key, start, end);
		if (list == null || list.isEmpty())
			return null;
		List<T> rowList = new ArrayList<T>();
		for (String row : list)
			rowList.add((T) GsonUtils.fromJson(row, clazz));
		return rowList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> hMGet(String key, Class clazz, String... fields)
	{
		List<String> list = redisClient.hmget(key, fields);
		if (list == null || list.isEmpty())
			return null;
		List<T> rowList = new ArrayList<T>();
		for (String row : list)
			rowList.add((T) GsonUtils.fromJson(row, clazz));
		return rowList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <V> Map<String, V> hGetAll(String key, Class clazz)
	{
		Map<String, String> map = redisClient.hgetAll(key);
		if (map == null || map.isEmpty())
			return null;
		Map<String, V> dataMap = new HashMap<String, V>();
		for (Map.Entry<String, String> entry : map.entrySet())
			dataMap.put(entry.getKey(),
					(V) GsonUtils.fromJson(entry.getValue(), clazz));
		return dataMap;
	}
	
	public Set<String> hKeys(String key)
	{
		return redisClient.hkeys(key);
	}
	
	public long getListCacheSize(String key)
	{
		return redisClient.llen(key);
	}
	
	public long ttl(String key)
	{
		return redisClient.ttl(key);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public <T> T getCache(String key, Class clazz)
	{
		String value = redisClient.get(key);
		if (value == null)
			return null;
		return GsonUtils.fromJson(value, clazz);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public <T> T getCache(String key, TypeToken typeToken)
	{
		String value = redisClient.get(key);
		if (value == null)
			return null;
		return GsonUtils.fromJson(value, typeToken);
	}
	
	@SuppressWarnings("rawtypes")
	public <T> T hGet(String key, String field, Class clazz)
	{
		String value = redisClient.hget(key, field);
		if (value == null)
			return null;
		return GsonUtils.fromJson(value, clazz);
	}
	
	public <V> void hSet(String key, String field, V value)
	{
		if (value == null)
			return;
		redisClient.hset(key, field, GsonUtils.toJson(value));
	}
	
	public <V> void hMSet(String key, Map<String, V> valueMap)
	{
		if (valueMap == null)
			return;
		Iterator<String> keyIt = valueMap.keySet().iterator();
		Map<String, String> resMap = new HashMap<String, String>();
		while (keyIt.hasNext())
		{
			String k = keyIt.next();
			resMap.put(k, GsonUtils.toJson(valueMap.get(k)));
		}
		redisClient.hmset(key, resMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> sRandMember(String key, int count, Class clazz)
	{
		List<String> list = redisClient.srandmember(key, count);
		if (list == null)
			return null;
		List<T> dataList = new ArrayList<T>();
		for (String data : list)
			dataList.add((T) GsonUtils.fromJson(data, clazz));
		return dataList;
	}
	
	public long sCard(String key)
	{
		return redisClient.scard(key);
	}
	
	public <T> void sAdd(String key, T value)
	{
		if (value == null)
			return;
		redisClient.sadd(key, GsonUtils.toJson(value));
	}
	
	public <V> long hSetNX(String key, String field, V value)
	{
		if (value == null)
			return 0;
		return redisClient.hsetnx(key, field, GsonUtils.toJson(value));
	}
	
	public void hDel(String key, String field)
	{
		redisClient.hdel(key, field);
	}
	
	public boolean hExists(String key, String field)
	{
		return redisClient.hexists(key, field);
	}
	
	public String getCache(String key)
	{
		return redisClient.get(key);
	}
	
	public boolean cacheExist(String key)
	{
		return redisClient.exists(key);
	}
	
	public void clearCache()
	{
		redisClient.flushDB();
	}
	
	public void removeCache(String key)
	{
		redisClient.del(key);
	}
}
