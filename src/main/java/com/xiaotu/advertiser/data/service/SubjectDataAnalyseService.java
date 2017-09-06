package com.xiaotu.advertiser.data.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.data.controller.filter.SubjectDataFilter;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.BigDecimalUtil;
import com.xiaotu.common.util.ListUtils;

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
	public Map<String, Object> querySubjectMarketPos(SubjectDataFilter filter)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> params = this.getFilterParamMap(filter);
		List<Map<String, Object>> marketPos = this.getList("selectSubjectMarketPos", params);
		
		resultMap.put("marketPos", marketPos);
		return resultMap;
	}
	
	/**
	 * 查询题材收视排行
	 * @author xuchangjian 2017年7月18日下午1:46:56
	 * @param filter 查询条件
	 * @return
	 */
	public Map<String, Object> querySubjectRank(SubjectDataFilter filter)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> params = this.getFilterParamMap(filter);
		List<Map<String, Object>> subjectRank = this.getList("selectSubjectRank", params);
		subjectRank = this.removeRateNullData(subjectRank);
		
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
	 * @param filter 查询条件
	 * @return
	 */
	public Map<String, Object> querySubjectCity(SubjectDataFilter filter)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Integer cityGroupId = null;
		Integer cityId = null;
		Integer[] cityGroupIdArray = new Integer[] {19901, 19902, 19903, 19904, 19905};
		if (Arrays.asList(cityGroupIdArray).contains(filter.getAreaId()))
		{
			cityGroupId = filter.getAreaId();
		}
		else
		{
			cityId = filter.getAreaId();
		}
		Map<String, Object> params = this.getFilterParamMap(filter);
		params.put("cityGroupId", cityGroupId);
		params.put("cityId", cityId);
		List<Map<String, Object>> subjectCity = this.getList("selectSubjectCity", params);
		subjectCity = this.removeRateNullData(subjectCity);
		
		//计算偏好值
		Double groupRate = 0.0;	
		int groupIndex = 0;
		for (int i = 0; i < subjectCity.size(); i++)
		{
			Map<String, Object> map = subjectCity.get(i);
			Integer myAreaId = (Integer) map.get("areaid");
			Double rate = Double.parseDouble(map.get("rate").toString());
			if (myAreaId.equals(filter.getAreaId()))
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
				favor = BigDecimalUtil.divide(rate, groupRate, 5);
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
	 * @param filter 查询条件
	 * @return
	 */
	public Map<String, Object> querySubjectAgeSpread(SubjectDataFilter filter)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> params = this.getFilterParamMap(filter);
		List<Map<String, Object>> ageSpread = this.getList("selectSubjectAgaSpread", params);
		ageSpread = this.removeRateNullData(ageSpread);
		
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
	 * @param filter 查询条件
	 * @return
	 */
	public Map<String, Object> querySubjectEarnSpread(SubjectDataFilter filter)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> params = this.getFilterParamMap(filter);
		List<Map<String, Object>> earnSpread = this.getList("querySubjectEarnSpread", params);
		earnSpread = this.removeRateNullData(earnSpread);
		
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
	 * @param filter 查询条件
	 * @return
	 */
	public Map<String, Object> querySubjectEduSpread(SubjectDataFilter filter)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> params = this.getFilterParamMap(filter);
		List<Map<String, Object>> eduSpread = this.getList("querySubjectEduSpread", params);
		eduSpread = this.removeRateNullData(eduSpread);
		
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
	 * 查询题材的人群性别分布
	 * @author xuchangjian 2017年7月18日下午3:16:45
	 * @param filter 查询条件
	 * @return
	 */
	public Map<String, Object> querySubjectSexSpread(SubjectDataFilter filter)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> params = this.getFilterParamMap(filter);
		List<Map<String, Object>> sexSpread = this.getList("querySubjectSexSpread", params);
		sexSpread = this.removeRateNullData(sexSpread);
		
		double groupRate = this.get("selectRate", params);
		
		//计算偏好值
		for (Map<String, Object> map : sexSpread)
		{
			Double rate = Double.parseDouble(map.get("rate").toString());
			Double favor = 0.0;
			if (groupRate > 0) {
				favor = BigDecimalUtil.divide(rate, groupRate, 5);
			}
			map.put("favor", favor);	
		}
		
		resultMap.put("sexSpread", sexSpread);
		return resultMap;
	}
	
	/**
	 * 查询题材的人群分布（带有年龄、收入、教育水平、年龄三个维度的数据）
	 * @author xuchangjian 2017年7月18日下午3:16:45
	 * @param filter 查询条件
	 * @return
	 */
	public Map<String, Object> querySubjectPeopleSpread(SubjectDataFilter filter) 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> params = this.getFilterParamMap(filter);
		List<Map<String, Object>> ageSpread = this.getList("selectSubjectAgaSpread", params);
		List<Map<String, Object>> earnSpread = this.getList("querySubjectEarnSpread", params);
		List<Map<String, Object>> eduSpread = this.getList("querySubjectEduSpread", params);
		List<Map<String, Object>> sexSpread = this.getList("querySubjectSexSpread", params);
		
		ageSpread = this.removeRateNullData(ageSpread);
		earnSpread = this.removeRateNullData(earnSpread);
		eduSpread = this.removeRateNullData(eduSpread);
		sexSpread = this.removeRateNullData(sexSpread);
		
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

			//计算偏好值
			for (Map<String, Object> map : sexSpread)
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
		resultMap.put("sexSpread", sexSpread);
		return resultMap;
	}
	
	/**
	 * 获取查询条件
	 * @author xuchangjian 2017年9月1日下午4:43:13
	 * @param filter
	 * @return
	 */
	private Map<String, Object> getFilterParamMap(SubjectDataFilter filter) {
		if (filter.getAreaId() == null)
		{
			throw new BusinessException("请选择收视地区");
		}
		if ((filter.getChannelIdList() == null || filter.getChannelIdList().size() == 0)
				&& (filter.getChannelLevelList() == null || filter.getChannelLevelList().size() == 0))
		{
			throw new BusinessException("请选择频道");
		}
		if (StringUtils.isBlank(filter.getStartDate()))
		{
			throw new BusinessException("请选择开始日期");
		}
		if (StringUtils.isBlank(filter.getEndDate()))
		{
			throw new BusinessException("请选择结束日期");
		}
		if (StringUtils.isBlank(filter.getStartTime()))
		{
			throw new BusinessException("请选择开始时间");
		}
		if (StringUtils.isBlank(filter.getEndTime()))
		{
			throw new BusinessException("请选择结束时间");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaId", filter.getAreaId());
		params.put("channelIdList", ListUtils.filterListNull(filter.getChannelIdList()));
		params.put("channelLevelList", ListUtils.filterListNull(filter.getChannelLevelList()));
		params.put("startDate", filter.getStartDate());
		params.put("endDate", filter.getEndDate());
		params.put("startTime", filter.getStartTime().replace(":", "") + "00");
		params.put("endTime", filter.getEndTime().replace(":", "") + "00");
		params.put("subjectIdList", ListUtils.filterListNull(filter.getSubjectIdList()));
		params.put("ageType", filter.getAgeType());
		params.put("sexType", filter.getSexType());
		params.put("eduType", filter.getEduType());
		params.put("earnType", filter.getEarnType());
		
		return params;
	}
	
	/**
	 * 移除list中收视率数据为空的数据
	 * @author xuchangjian 2017年9月5日下午4:32:51
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> removeRateNullData(List<Map<String, Object>> list) {
		List<Map<String, Object>> toRemoveList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			if (map.get("rate") == null) {
				toRemoveList.add(map);
			}
		}
		list.removeAll(toRemoveList);
		return list;
	}
}
