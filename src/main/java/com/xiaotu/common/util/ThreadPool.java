package com.xiaotu.common.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @类名 ThreadPool
 * @日期 2015年7月3日
 * @作者 高海军
 * @功能 发送消息线程池
 */
public class ThreadPool
{
	private static final int POOL_SIZE = 500;
	
	private static final int SMALL_POOL_SIZE = 4;
	
	private static final ExecutorService POOL = Executors
			.newFixedThreadPool(POOL_SIZE);
	
	private static final ExecutorService SMALL_POOL = Executors
			.newFixedThreadPool(SMALL_POOL_SIZE);
	
	public static void execute(Runnable runnable)
	{
		POOL.execute(runnable);
	}
	
	public static void executeBySmall(Runnable runnable)
	{
		SMALL_POOL.execute(runnable);
	}
	
	public static <V> V futureGet(Callable<V> callable, int timeOut)
			throws InterruptedException, ExecutionException, TimeoutException
	{
		Future<V> future = POOL.submit(callable);
		return future.get(timeOut, TimeUnit.MILLISECONDS);
	}
	
	public static void shutdown()
	{
		if (POOL != null)
			POOL.shutdown();
	}
	
}
