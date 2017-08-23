package com.xiaotu.advertiser.common.aop;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.util.StringUtil;

/**
 * @类名 ClientResponseAspect
 * @日期 2015年5月28日
 * @作者 高海军
 * @功能 客户端相应切面实现类
 */
public class ClientResponseAspect
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClientResponseAspect.class);
	
	/**
	 * 对controller的方法进行横切，调用方法前后都做相应处理
	 * @param joinPoint 连接点
	 * @return 执行结果
	 */
	public ClientResponse aroundMethod(ProceedingJoinPoint joinPoint)
	{
		LOGGER.debug("before method");
		ClientResponse response = new ClientResponse();
		try
		{
			Object result = joinPoint.proceed();
			this.resultHandle(joinPoint, response, result);
			response.setData(result);
			this.printResponseData(response);
		}
		catch (BusinessException e)
		{
			response.setStatus(ClientResponse.ERROR);
			response.setMessage(e.getMessage());
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		catch (Throwable e)
		{
			response.setStatus(ClientResponse.ERROR);
			response.setMessage("服务端异常");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		LOGGER.debug("after method");
		return response;
	}
	
	protected void printResponseData(ClientResponse response)
	{
		try
		{
			if (LOGGER.isTraceEnabled())
				LOGGER.debug(
						"response json：" + StringUtil.object2Json(response));
		}
		catch (Exception e)
		{
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	protected void resultHandle(ProceedingJoinPoint joinPoint,
			ClientResponse response, Object result) throws Exception
	{
		if (result != null && result instanceof Exception)
			throw (Exception) result;
	}
}
