package com.xiaotu.advertiser.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.data.controller.filter.SubjectDataFilter;
import com.xiaotu.advertiser.data.service.SubjectDataAnalyseService;
import com.xiaotu.common.db.DynamicDataSource;

/**
 * 题材数据分析
 * @author xuchangjian 2017年7月18日上午9:31:25
 */
@RestController
@RequestMapping("/subjectDataAnalyse")
public class SubjectDataAnalyseController {

	@Autowired
	private SubjectDataAnalyseService subjectDataAnalyseService;
	
	/**
	 * 查询题材市场定位
	 * @author xuchangjian 2017年7月18日上午9:34:52
	 * @param filter 查询条件
	 * @return
	 */
	@RequestMapping("/querySubjectMarketPos")
	public Object querySubjectMarketPos(@RequestBody SubjectDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.subjectDataAnalyseService.querySubjectMarketPos(filter);
	}
	
	/**
	 * 查询题材收视排行
	 * @author xuchangjian 2017年7月18日下午1:46:56
	 * @param filter 查询条件
	 * @return
	 */
	@RequestMapping("/querySubjectRank")
	public Object querySubjectRank(@RequestBody SubjectDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.subjectDataAnalyseService.querySubjectRank(filter);
	}

	/**
	 * 题材分城贡献
	 * @author xuchangjian 2017年7月18日下午1:46:56
	 * @param filter 查询条件
	 * @return
	 */
	@RequestMapping("/querySubjectCity")
	public Object querySubjectCity(@RequestBody SubjectDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.subjectDataAnalyseService.querySubjectCity(filter);
	}
	
	/**
	 * 查询题材的人群年龄分布
	 * @author xuchangjian 2017年7月18日下午3:16:45
	 * @param filter 查询条件
	 * @return
	 */
	@RequestMapping("/querySubjectAgeSpread")
	public Object querySubjectAgeSpread(@RequestBody SubjectDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.subjectDataAnalyseService.querySubjectAgeSpread(filter);
	}
	
	/**
	 * 查询题材的人群收入分布
	 * @author xuchangjian 2017年7月18日下午3:16:45
	 * @param filter 查询条件
	 * @return
	 */
	@RequestMapping("/querySubjectEarnSpread")
	public Object querySubjectEarnSpread(@RequestBody SubjectDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.subjectDataAnalyseService.querySubjectEarnSpread(filter);
	}
	
	/**
	 * 查询题材的人群教育水平分布
	 * @author xuchangjian 2017年7月18日下午3:16:45
	 * @param filter 查询条件
	 * @return
	 */
	@RequestMapping("/querySubjectEduSpread")
	public Object querySubjectEduSpread(@RequestBody SubjectDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.subjectDataAnalyseService.querySubjectEduSpread(filter);
	}
	
	/**
	 * 查询题材的人群性别分布
	 * @author xuchangjian 2017年7月18日下午3:16:45
	 * @param filter 查询条件
	 * @return
	 */
	@RequestMapping("/querySubjectSexSpread")
	public Object querySubjectSexSpread(@RequestBody SubjectDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.subjectDataAnalyseService.querySubjectSexSpread(filter);
	}

	/**
	 * 查询题材的人群分布（带有年龄、收入、教育水平、年龄三个维度的数据）
	 * @author xuchangjian 2017年7月18日下午3:16:45
	 * @param filter 查询条件
	 * @return
	 */
	@RequestMapping("/querySubjectPeopleSpread")
	public Object querySubjectPeopleSpread(@RequestBody SubjectDataFilter filter) 
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.subjectDataAnalyseService.querySubjectPeopleSpread(filter);
	}
}
