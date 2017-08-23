package com.xiaotu.common.redis;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @类名 RedisDataSource
 * @日期 2015年11月2日
 * @作者 高海军
 * @功能
 */
//@Repository("redisDataSource")
public class SharedRedisDataSource
{
//	@Autowired
	private ShardedJedisPool shardedJedisPool;
	
	public ShardedJedis getRedisClient()
	{
		try
		{
			ShardedJedis shardJedis = shardedJedisPool.getResource();
			return shardJedis;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void returnResource(ShardedJedis shardedJedis)
	{
		shardedJedisPool.returnResource(shardedJedis);
	}
	
	public void returnResource(ShardedJedis shardedJedis, boolean broken)
	{
		if (broken)
		{
			shardedJedisPool.returnBrokenResource(shardedJedis);
		}
		else
		{
			shardedJedisPool.returnResource(shardedJedis);
		}
	}
}
