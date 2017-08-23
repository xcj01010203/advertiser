package com.xiaotu.advertiser.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.user.service.LogManageService;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.exception.BusinessException;

@RestController
@RequestMapping("/logManage")
public class LogManageController{
	
	@Autowired
	private LogManageService logService ;
	
	/**
	 * 日志查询
	 * @param request
	 * @param userName
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/queryLog")
	public @ResponseBody Object queryLog(String startTime,String endTime,String userName,String status,Page page) throws BusinessException{
		return logService.queryUser(startTime,endTime,userName,status,page);
	}
	
}
