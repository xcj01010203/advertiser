package com.xiaotu.advertiser.common.aop;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaotu.advertiser.user.model.LogManageModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.advertiser.user.service.LogManageService;
import com.xiaotu.advertiser.user.service.MenuService;
import com.xiaotu.common.util.IPUtil;
import com.xiaotu.common.util.RequestUtils;
import com.xiaotu.common.util.SessionUtil;

public class LogsAspect
{
	
	private static final Logger logs = LoggerFactory
			.getLogger(LogsAspect.class);
	
	@Autowired
	private LogManageService logservice;
	@Autowired
	private MenuService menuService;
	
	/**
	 * 打印正确日志 入库
	 * @param joinpoint
	 */
	public void after(JoinPoint joinpoint){
		try{
			
			LogManageModel log = setLogEntity();
			log.setStatus("0");
			log.setCommite("");
			// 保存日志数据
			logservice.saveLog(log);
			
		}catch (Exception e){
			logs.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	/**
	 * 打印错误日志入库
	 * @param joinpoint
	 * @param e
	 */
	public void doThrow(JoinPoint joinpoint, Throwable e){
		try{
			LogManageModel log = setLogEntity();
			log.setStatus("1");
			log.setCommite(e.getMessage());
			// 保存进数据库
			logservice.saveLog(log);
			
		}catch (Exception ex){
			logs.error(ExceptionUtils.getStackTrace(ex));
		}
	}
	
	
	/**
	 * 获取日志实体属性值
	 * @return
	 */
	public LogManageModel setLogEntity(){
		// 日志实体对象
		LogManageModel log = new LogManageModel();
		// 获取登录用户账户
		UserModel user = SessionUtil.getSessionUser();
		// 获取URL地址
		String menuUrl = RequestUtils.getRequestURI();
		if (user != null){
			log.setUserId(user.getId());
			log.setIp(IPUtil.getIpAddress(RequestUtils.getRequest()));
			log.setMenuId(menuService.queryMenuUrlByMenuId(menuUrl));
			// 获取系统时间
			//log.setCreateTime(DateUtils.getDateString(new Date(),DateUtils.SECONDS_FORMAT));
		}else{
			logs.error("用户session已过期");
		}
			return log;
	} 
}
