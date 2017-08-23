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
 * 题材数据分析
 * @author xuchangjian 2017年7月18日上午9:32:40
 */
@Service
public class SubjectDataAnalyseService extends BaseService {

	@Override
	protected String getKey() {
		return "SubjectDataAnalyseMapper";
	}

	/**
	 * 市场定位
	 * @author xuchangjian 2017年7月18日上午9:34:52
	 * @return
	 */
	public Map<String, Object> querySubjectMarketPos(Integer areaId, 
			List<Integer> channelIdList, List<Integer> channelLevelList, 
			String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if ((channelIdList == null || channelIdList.size() == 0)
				&& (channelLevelList == null || channelLevelList.size() == 0))
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
		params.put("channelIdList", channelIdList);
		params.put("channelLevelList", channelLevelList);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> marketPos = this.getList("selectSubjectMarketPos", params);
		
		resultMap.put("marketPos", marketPos);
		return resultMap;
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
	public Map<String, Object> querySubjectRank(Integer areaId, 
			List<Integer> channelIdList,  List<Integer> channelLevelList, 
			String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if ((channelIdList == null || channelIdList.size() == 0)
				&& (channelLevelList == null || channelLevelList.size() == 0))
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
		params.put("channelIdList", channelIdList);
		params.put("channelLevelList", channelLevelList);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> subjectRank = this.getList("selectSubjectRank", params);
		
		DecimalFormat df = new DecimalFormat("0.000");
		for (Map<String, Object> subjectInfo : subjectRank)
		{
			Double rate = Double.parseDouble(subjectInfo.get("rate").toString());
			subjectInfo.put("rate", df.format(rate));
		}
		
		resultMap.put("subjectRank", subjectRank);
		return resultMap;
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
	public Map<String, Object> querySubjectCity(Integer areaId, 
			List<Integer> channelIdList, List<Integer> channelLevelList, 
			String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if ((channelIdList == null || channelIdList.size() == 0)
				&& (channelLevelList == null || channelLevelList.size() == 0))
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
		params.put("channelIdList", channelIdList);
		params.put("channelLevelList", channelLevelList);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> subjectCity = this.getList("selectSubjectCity", params);
		
		//计算偏好值
		Double groupRate = 0.0;	
		int groupIndex = 0;
		for (int i = 0; i < subjectCity.size(); i++)
		{
			Map<String, Object> map = subjectCity.get(i);
			Integer myAreaId = (Integer) map.get("areaid");
			Double rate = Double.parseDouble(map.get("rate").toString());
			if (myAreaId.equals(areaId))
			{
				groupRate = rate;
				groupIndex = i;
				break;
			}
		}
		
		for (Map<String, Object> map : subjectCity)
		{
			Double rate = Double.parseDouble(map.get("rate").toString());
			Double favor = 0.0;
			if (groupRate > 0)
			{
				favor = BigDecimalUtil.divide(rate, groupRate);
			}
			map.put("favor", favor);
		}
		if (subjectCity.size() > 0) {
			subjectCity.remove(groupIndex);
		}
		if (subjectCity.size() > 20)
		{
			subjectCity = subjectCity.subList(0, 20);
		}
		
		resultMap.put("subjectCity", subjectCity);
		return resultMap;
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
	public Map<String, Object> querySubjectAgeSpread(Integer areaId, 
			List<Integer> channelIdList, List<Integer> channelLevelList, 
			String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if ((channelIdList == null || channelIdList.size() == 0)
				&& (channelLevelList == null || channelLevelList.size() == 0))
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
		params.put("channelIdList", channelIdList);
		params.put("channelLevelList", channelLevelList);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> ageSpread = this.getList("selectSubjectAgaSpread", params);
		
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
	public Map<String, Object> querySubjectEarnSpread(Integer areaId, 
			List<Integer> channelIdList, List<Integer> channelLevelList, 
			String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if ((channelIdList == null || channelIdList.size() == 0)
				&& (channelLevelList == null || channelLevelList.size() == 0))
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
		params.put("channelIdList", channelIdList);
		params.put("channelLevelList", channelLevelList);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> earnSpread = this.getList("querySubjectEarnSpread", params);
		
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
	public Map<String, Object> querySubjectEduSpread(Integer areaId, 
			List<Integer> channelIdList, List<Integer> channelLevelList, 
			String startDate, String endDate, String startTime, String endTime)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if ((channelIdList == null || channelIdList.size() == 0)
				&& (channelLevelList == null || channelLevelList.size() == 0))
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
		params.put("channelIdList", channelIdList);
		params.put("channelLevelList", channelLevelList);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> eduSpread = this.getList("querySubjectEduSpread", params);
		
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
	public Map<String, Object> querySubjectPeopleSpread(Integer areaId, 
			List<Integer> channelIdList, List<Integer> channelLevelList, 
			String startDate, String endDate, String startTime, String endTime) 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if ((channelIdList == null || channelIdList.size() == 0)
				&& (channelLevelList == null || channelLevelList.size() == 0))
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
		params.put("channelIdList", channelIdList);
		params.put("channelLevelList", channelLevelList);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("startTime", startTime.replace(":", "") + "00");
		params.put("endTime", endTime.replace(":", "") + "00");
		List<Map<String, Object>> ageSpread = this.getList("selectSubjectAgaSpread", params);
		List<Map<String, Object>> earnSpread = this.getList("querySubjectEarnSpread", params);
		List<Map<String, Object>> eduSpread = this.getList("querySubjectEduSpread", params);
		
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

		resultMap.put("earnSpread", earnSpread);
		resultMap.put("ageSpread", ageSpread);
		resultMap.put("eduSpread", eduSpread);
		return resultMap;
	}
}
