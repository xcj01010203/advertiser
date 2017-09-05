package com.xiaotu.advertiser.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.data.controller.filter.ChannelDataFilter;
import com.xiaotu.advertiser.data.service.ChannelDataAnalyseService;
import com.xiaotu.common.db.DynamicDataSource;
import com.xiaotu.common.redis.CacheHandler;

/**
 * 频道数据分析
 * @author xuchangjian 2017年7月13日下午4:32:22
 */
@RestController
@RequestMapping("/channelDataAnalyse")
public class ChannelDataAnalyseController {
	
	@Autowired
	private ChannelDataAnalyseService channelDataAnayseService;
	
	@Autowired
	private CacheHandler cacheHandler;
	
	/**
	 * 获取有收视数据的最新一天
	 * @author xuchangjian 2017年7月28日下午12:14:29
	 * @return
	 */
	@RequestMapping("/queryUpdatedDate")
	public Object queryUpdatedDate()
	{
		return this.cacheHandler.getCache(Constants.KEY_LAST_DAY);
	}

	/**
	 * 查询频道收视排行
	 * @author xuchangjian 2017年7月13日下午5:07:19
	 * @param areaId	区域ID
	 * @param channelId	频道ID
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return
	 */
	@RequestMapping("/queryChannelRank")
	public Object queryChannelRank(@RequestBody ChannelDataFilter filter) 
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.channelDataAnayseService.queryChannelRank(filter);
	}

	/**
	 * 查询频道题材市场
	 * @author xuchangjian 2017年7月14日下午2:35:21
	 * @param areaId	区域ID
	 * @param channelId	频道ID
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return
	 */
	@RequestMapping("/queryChannelSubjectMark")
	public Object queryChannelSubjectMark(@RequestBody ChannelDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.channelDataAnayseService.queryChannelSubjectMark(filter);
	}
	
	/**
	 * 查询频道分城贡献
	 * @author xuchangjian 2017年7月14日下午5:06:32
	 * @param areaId	区域ID
	 * @param channelId	频道ID
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return
	 */
	@RequestMapping("/queryChannelCity")
	public Object queryChannelCity(@RequestBody ChannelDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.channelDataAnayseService.queryChannelCity(filter);
	}
	
	/**
	 * 查询频道的人群分布（带有年龄、收入、教育水平三个维度的数据）
	 * @author xuchangjian 2017年7月14日下午5:53:13
	 * @param areaId	区域ID
	 * @param channelId	频道ID
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return
	 */
	@RequestMapping("/queryChannelPeopleSpread")
	public Object queryChannelPeopleSpread(@RequestBody ChannelDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.channelDataAnayseService.queryChannelPeopleSpread(filter);
	}
	
	/**
	 * 查询频道的人群年龄分布
	 * @author xuchangjian 2017年7月14日下午5:53:13
	 * @param areaId	区域ID
	 * @param channelId	频道ID
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return
	 */
	@RequestMapping("/queryChannelAgeSpread")
	public Object queryChannelAgeSpread(@RequestBody ChannelDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.channelDataAnayseService.queryChannelAgeSpread(filter);
	}
	
	/**
	 * 查询频道的人群收入分布
	 * @author xuchangjian 2017年7月14日下午5:53:13
	 * @param areaId	区域ID
	 * @param channelId	频道ID
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return
	 */
	@RequestMapping("/queryChannelEarnSpread")
	public Object queryChannelEarnSpread(@RequestBody ChannelDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.channelDataAnayseService.queryChannelEarnSpread(filter);
	}
	
	/**
	 * 查询频道的人群教育水平分布
	 * @author xuchangjian 2017年7月14日下午5:53:13
	 * @param areaId	区域ID
	 * @param channelId	频道ID
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return
	 */
	@RequestMapping("/queryChannelEduSpread")
	public Object queryChannelEduSpread(@RequestBody ChannelDataFilter filter)
	{
		DynamicDataSource.setDataSource(DynamicDataSource.DATA_SOURCE_PG);
		return this.channelDataAnayseService.queryChannelEduSpread(filter);
	}
}
