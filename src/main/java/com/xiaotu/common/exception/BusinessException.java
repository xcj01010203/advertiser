package com.xiaotu.common.exception;

/**
 * @类名 BusinessException
 * @日期 2017年2月9日
 * @作者 高海军
 * @功能
 */
@SuppressWarnings("serial")
public class BusinessException extends RuntimeException
{
	public BusinessException()
	{
		super();
	}
	
	public BusinessException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public BusinessException(String message)
	{
		super(message);
	}
	
}
