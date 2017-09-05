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
	 * @param areaId	区域ID
	 * @param channelIdList	频道ID列表
	 * @param channelLevelList	频道级别列表
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
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
	 * @param areaId	区域ID
	 * @param channelIdList	频道ID列表
	 * @param channelLevelList	频道级别列表
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
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
	 * @param areaId	区域ID
	 * @param channelIdList	频道ID列表
	 * @param channelLevelList	频道级别列表
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
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
	 * @param areaId	区域ID
	 * @param channelIdList	频道ID列表
	 * @param channelLevelList	频道级别列表
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
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
	 * @param areaId	区域ID
	 * @param channelIdList	频道ID列表
	 * @param channelLevelList	频道级别列表
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
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
	 * @param areaId	区域ID
	 * @param channelIdList	频道ID列表
	 * @param channelLevelList	频道级别列表
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return
	 */
	@RequestMapping("/querySubjectEduSpread")
	public Object querySubjectEduSpread(@RequestBody SubjectDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.subjectDataAnalyseService.querySubjectEduSpread(filter);
	}

	/**
	 * 查询题材的人群分布
	 * @author xuchangjian 2017年7月18日下午3:16:45
	 * @param areaId	区域ID
	 * @param channelIdList	频道ID列表
	 * @param channelLevelList	频道级别列表
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return
	 */
	@RequestMapping("/querySubjectPeopleSpread")
	public Object querySubjectPeopleSpread(@RequestBody SubjectDataFilter filter) 
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.subjectDataAnalyseService.querySubjectPeopleSpread(filter);
	}
}
