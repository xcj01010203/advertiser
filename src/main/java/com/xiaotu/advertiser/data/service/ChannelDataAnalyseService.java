package com.xiaotu.advertiser.data.service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.BigDecimalUtil;

/**
 * 数据分析
 * @author xuchangjian 2017年7月13日下午5:12:03
 */
@Service
public class ChannelDataAnalyseService extends BaseService {

	@Override
	protected String getKey() {
		return "ChannelDataAnalyseMapper";
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
	public Map<String, Object> queryChannelRank(Integer areaId, Integer channelId, String startDate, String endDate,String startTime, String endTime) 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		DecimalFormat df = new DecimalFormat("0.000");

		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if (channelId == null)
		{
			throw new BusinessException("请选择频道");
		}
		if (StringUtils.isBlank(startDate))
		{
			throw new BusinessException("请选择开始日期");
		}
		if (StringUtils.isBlank(endDate))
		{
			throw new BusinessException("请选择结束日期");
		}
		if (StringUtils.isBlank(startTime))
		{
			throw new BusinessException("请选择开始时间");
		}
		if (StringUtils.isBlank(endTime))
		{
			throw new BusinessException("请选择结束时间");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaId", areaId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> channelRank = this.getList("selectChannelRank", params);
		
		int currChannelIndex = 0;
		for (int i = 0; i < channelRank.size(); i++)
		{
			int ichannelid = (Integer) channelRank.get(i).get("ichannelid");
			Double rate = Double.parseDouble(channelRank.get(i).get("rate").toString());
			
			channelRank.get(i).put("rate", df.format(rate));
			channelRank.get(i).put("index", i + 1);
			
			if (ichannelid == channelId)
			{
				currChannelIndex = i;
			}
		}
		
		//最多只返回十条记录，如果当前频道在前十名之后，则把第十名记录改为当前频道
		int cutIndex = 10;
		if (channelRank.size() < 10) {
			cutIndex = channelRank.size();
		}
		
		List<Map<String, Object>> dealedChannelRank = channelRank.subList(0,  cutIndex);
		if (currChannelIndex > 9) {
			dealedChannelRank.set(9, channelRank.get(currChannelIndex));
		}
		
		resultMap.put("channelRank", dealedChannelRank);
		return resultMap;
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
	public Map<String, Object> queryChannelSubjectMark(Integer areaId, Integer channelId, String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if (channelId == null)
		{
			throw new BusinessException("请选择频道");
		}
		if (StringUtils.isBlank(startDate))
		{
			throw new BusinessException("请选择开始日期");
		}
		if (StringUtils.isBlank(endDate))
		{
			throw new BusinessException("请选择结束日期");
		}
		if (StringUtils.isBlank(startTime))
		{
			throw new BusinessException("请选择开始时间");
		}
		if (StringUtils.isBlank(endTime))
		{
			throw new BusinessException("请选择结束时间");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaId", areaId);
		params.put("channelId", channelId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> subjectMark = this.getList("selectChannelSubjectMark", params);
		
		resultMap.put("subjectMark", subjectMark);
		return resultMap;
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
	public Map<String, Object> queryChannelCity(Integer areaId, Integer channelId, String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if (channelId == null)
		{
			throw new BusinessException("请选择频道");
		}
		if (StringUtils.isBlank(startDate))
		{
			throw new BusinessException("请选择开始日期");
		}
		if (StringUtils.isBlank(endDate))
		{
			throw new BusinessException("请选择结束日期");
		}
		if (StringUtils.isBlank(startTime))
		{
			throw new BusinessException("请选择开始时间");
		}
		if (StringUtils.isBlank(endTime))
		{
			throw new BusinessException("请选择结束时间");
		}
		
		Integer cityGroupId = null;
		Integer cityId = null;
		Integer[] cityGroupIdArray = new Integer[] {19901, 19902, 19903, 19904, 19905};
		if (Arrays.asList(cityGroupIdArray).contains(areaId))
		{
			cityGroupId = areaId;
		}
		else
		{
			cityId = areaId;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityGroupId", cityGroupId);
		params.put("cityId", cityId);
		params.put("channelId", channelId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> channelCity = this.getList("selectChannelCity", params);
		
		//计算偏好值
		Double groupRate = 0.0;
		int groupIndex = 0;
		for (int i = 0; i < channelCity.size(); i++)
		{
			Map<String, Object> map = channelCity.get(i);
			Integer myAreaId = (Integer) map.get("iareaid");
			Double rate = Double.parseDouble(map.get("rate").toString());
			if (myAreaId.equals(areaId))
			{
				groupRate = rate;
				groupIndex = i;
				break;
			}
		}
		
		for (Map<String, Object> map : channelCity)
		{
			Double rate = Double.parseDouble(map.get("rate").toString());
			Double favor = 0.0;
			if (groupRate > 0)
			{
				favor = BigDecimalUtil.divide(rate, groupRate);
			}
			map.put("favor", favor);
		}
		
		if (channelCity.size() > 0) {
			channelCity.remove(groupIndex);
		}
		if (channelCity.size() > 20)
		{
			channelCity = channelCity.subList(0, 20);
		}
		
		resultMap.put("channelCity", channelCity);
		return resultMap;
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
	public Map<String, Object> queryChannelAgeSpread(Integer areaId, Integer channelId, String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if (channelId == null)
		{
			throw new BusinessException("请选择频道");
		}
		if (StringUtils.isBlank(startDate))
		{
			throw new BusinessException("请选择开始日期");
		}
		if (StringUtils.isBlank(endDate))
		{
			throw new BusinessException("请选择结束日期");
		}
		if (StringUtils.isBlank(startTime))
		{
			throw new BusinessException("请选择开始时间");
		}
		if (StringUtils.isBlank(endTime))
		{
			throw new BusinessException("请选择结束时间");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaId", areaId);
		params.put("channelId", channelId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> ageSpread = this.getList("selectChannelAgeSpread", params);
		
		double groupRate = this.get("selectRate", params);
		
		//计算偏好值
		for (Map<String, Object> map : ageSpread)
		{
			Double rate = Double.parseDouble(map.get("rate").toString());
			Double favor = 0.0;
			if (groupRate > 0) {
				favor = BigDecimalUtil.divide(rate, groupRate, 5);
			}
			map.put("favor", favor);	
		}
		
		resultMap.put("ageSpread", ageSpread);
		return resultMap;
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
	public Map<String, Object> queryChannelEarnSpread(Integer areaId, Integer channelId, String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if (channelId == null)
		{
			throw new BusinessException("请选择频道");
		}
		if (StringUtils.isBlank(startDate))
		{
			throw new BusinessException("请选择开始日期");
		}
		if (StringUtils.isBlank(endDate))
		{
			throw new BusinessException("请选择结束日期");
		}
		if (StringUtils.isBlank(startTime))
		{
			throw new BusinessException("请选择开始时间");
		}
		if (StringUtils.isBlank(endTime))
		{
			throw new BusinessException("请选择结束时间");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaId", areaId);
		params.put("channelId", channelId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> earnSpread = this.getList("selectChannelEarnSpread", params);
		double groupRate = this.get("selectRate", params);
		
		//计算偏好值
		for (Map<String, Object> map : earnSpread)
		{
			Double rate = Double.parseDouble(map.get("rate").toString());
			Double favor = 0.0;
			if (groupRate > 0) {
				favor = BigDecimalUtil.divide(rate, groupRate, 5);
			}
			map.put("favor", favor);	
		}
		
		resultMap.put("earnSpread", earnSpread);
		return resultMap;
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
	public Map<String, Object> queryChannelEduSpread(Integer areaId, Integer channelId, String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if (channelId == null)
		{
			throw new BusinessException("请选择频道");
		}
		if (StringUtils.isBlank(startDate))
		{
			throw new BusinessException("请选择开始日期");
		}
		if (StringUtils.isBlank(endDate))
		{
			throw new BusinessException("请选择结束日期");
		}
		if (StringUtils.isBlank(startTime))
		{
			throw new BusinessException("请选择开始时间");
		}
		if (StringUtils.isBlank(endTime))
		{
			throw new BusinessException("请选择结束时间");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaId", areaId);
		params.put("channelId", channelId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> eduSpread = this.getList("selectChannelEduSpread", params);
		double groupRate = this.get("selectRate", params);
		
		//计算偏好值
		for (Map<String, Object> map : eduSpread)
		{
			Double rate = Double.parseDouble(map.get("rate").toString());
			Double favor = 0.0;
			if (groupRate > 0) {
				favor = BigDecimalUtil.divide(rate, groupRate, 5);
			}
			map.put("favor", favor);	
		}
		resultMap.put("eduSpread", eduSpread);
		return resultMap;
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
	public Map<String, Object> queryChannelPeopleSpread(Integer areaId, Integer channelId, String startDate, String endDate, String startTime, String endTime) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if (channelId == null)
		{
			throw new BusinessException("请选择频道");
		}
		if (StringUtils.isBlank(startDate))
		{
			throw new BusinessException("请选择开始日期");
		}
		if (StringUtils.isBlank(endDate))
		{
			throw new BusinessException("请选择结束日期");
		}
		if (StringUtils.isBlank(startTime))
		{
			throw new BusinessException("请选择开始时间");
		}
		if (StringUtils.isBlank(endTime))
		{
			throw new BusinessException("请选择结束时间");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaId", areaId);
		params.put("channelId", channelId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> ageSpread = this.getList("selectChannelAgeSpread", params);
		List<Map<String, Object>> earnSpread = this.getList("selectChannelEarnSpread", params);
		List<Map<String, Object>> eduSpread = this.getList("selectChannelEduSpread", params);
		Double groupRate = this.get("selectRate", params);
		
		if (groupRate != null) {
			//计算年龄偏好值
			for (Map<String, Object> map : ageSpread)
			{
				Double rate = Double.parseDouble(map.get("rate").toString());
				Double favor = 0.0;
				if (groupRate > 0) {
					favor = BigDecimalUtil.divide(rate, groupRate, 5);
				}
				map.put("favor", favor);	
			}
			
			//计算收入偏好值
			for (Map<String, Object> map : earnSpread)
			{
				Double rate = Double.parseDouble(map.get("rate").toString());
				Double favor = 0.0;
				if (groupRate > 0) {
					favor = BigDecimalUtil.divide(rate, groupRate, 5);
				}
				map.put("favor", favor);	
			}
			
			//计算教育水平偏好值
			for (Map<String, Object> map : eduSpread)
			{
				Double rate = Double.parseDouble(map.get("rate").toString());
				Double favor = 0.0;
				if (groupRate > 0) {
					favor = BigDecimalUtil.divide(rate, groupRate, 5);
				}
				map.put("favor", favor);	
			}
		}
		
		resultMap.put("ageSpread", ageSpread);
		resultMap.put("earnSpread", earnSpread);
		resultMap.put("eduSpread", eduSpread);
		return resultMap;
	}
}
