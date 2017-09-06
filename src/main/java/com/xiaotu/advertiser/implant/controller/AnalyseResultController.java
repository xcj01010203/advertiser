package com.xiaotu.advertiser.implant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.implant.service.AutoAnalysisService;
import com.xiaotu.common.db.DynamicDataSource;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.util.SessionUtil;

/**
 * @类名 AnalyseResultController
 * @日期 2017年7月12日
 * @作者 高海军
 * @功能 自动分析任务Controller
 */
@RestController
@RequestMapping("/analyseResult")
public class AnalyseResultController
{
	
	@Autowired
	private AutoAnalysisService service;
	
	/**
	 * 执行自动分析任务
	 * @return
	 */
	@RequestMapping("/saveAnalysisResult")
	public Object saveAnalysisResult(Page page)
	{
		service.saveAnalysisResult(page);
		return null;
	}
	
	/**
	 * 查询自动分析任务
	 * @return 查询结果
	 */
	@RequestMapping("/queryAnalysisJob")
	public Object queryAnalysisJob()
	{
		return service.get("ImplantAnalyseJobMapper.selectJob",
				SessionUtil.getSessionProject());
	}
	
	@RequestMapping("/setDS")
	public Object setDS()
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return null;
	}	
}
