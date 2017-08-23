package com.xiaotu.advertiser.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.user.model.LogManageModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.mvc.BaseService;

@Service
public class LogManageService extends BaseService{

	/**
	 * 记录日志
	 * @param logModel
	 * @return
	 */
	public void saveLog(LogManageModel logModel){
    	this.save("insertLog", logModel);
	}
	
	
	/**
	 * 查询日志
	 * @param userName
	 * @return
	 */
	public Map<String, Object> queryUser(String startTime,String endTime,String userName,String status,Page page){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("userName", userName);
		map.put("status", status);
		List<Map<String, Object>> logManageList =this.queryPageList("selectLog", map,page);
		resultMap.put("logManageList", logManageList);
		resultMap.put("totalPage", page.getTotalPage());
		resultMap.put("totalRows", page.getTotalRows());
		return resultMap;
		
	}
	
	@Override
	protected String getKey() {
		// TODO 自动生成的方法存根
		return "LogManageMapper";
	}
}
