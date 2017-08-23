package com.xiaotu.common.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xiaotu.common.util.PropertiesUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @类名 RedisDataSource
 * @日期 2015年11月2日
 * @作者 高海军
 * @功能
 */
@Repository("redisDataSource")
public class RedisDataSource
{
	@Autowired
	private JedisPool jedisPool;
	
	public Jedis getRedisClient()
	{
		try
		{
			Jedis jedis = jedisPool.getResource();
			String idx = PropertiesUtil.getProperty("redis.dbindex");
			if (StringUtils.isNotEmpty(idx))
				jedis.select(Integer.parseInt(idx));
			return jedis;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void returnResource(Jedis jedis)
	{
		jedisPool.returnResource(jedis);
	}
	
	public void returnResource(Jedis jedis, boolean broken)
	{
		if (broken)
		{
			jedisPool.returnBrokenResource(jedis);
		}
		else
		{
			jedisPool.returnResource(jedis);
		}
	}
}
