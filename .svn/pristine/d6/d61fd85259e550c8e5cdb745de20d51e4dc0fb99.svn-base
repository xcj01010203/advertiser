package com.xiaotu.common.util;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;

/**
 * @类名 KeyLocker
 * @日期 2017年2月22日
 * @作者 高海军
 * @功能 针对某个关键字加同步锁
 */
public class KeyLocker
{
	private static final int LOCKER_NUM = 1000;
	private static final ReentrantLock[] LOCKS = new ReentrantLock[LOCKER_NUM];
	private static final BigDecimal MAX_VALUE = new BigDecimal(LOCKER_NUM);
	
	static
	{
		for (int i = 0; i < LOCKER_NUM; i++)
			LOCKS[i] = new ReentrantLock();
	}
	
	public static <V> V lock(String key, Callable<V> calller)
	{
		int index = new BigDecimal(
				StringUtils.isEmpty(key) ? 0 : key.hashCode()).abs()
						.remainder(MAX_VALUE).intValue();// 取得锁对象的索引
		LOCKS[index].lock();
		try
		{
			return calller.call();
		}
		catch (Exception e)
		{
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			throw new RuntimeException(e);
		}
		finally
		{
			LOCKS[index].unlock();
		}
	}
}
