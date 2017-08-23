package com.xiaotu.advertiser.implant.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.implant.controller.dto.GoodsImplantDto;
import com.xiaotu.advertiser.implant.controller.dto.RoleImplantDto;
import com.xiaotu.advertiser.project.model.PlayRoleModel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.BigDecimalUtil;
import com.xiaotu.common.util.PropertiesUtil;
import com.xiaotu.common.util.SessionUtil;

/**
 * 广告植入自动分析结果
 * @author xuchangjian 2017年7月5日上午10:03:59
 */
@Service
public class ImplantAnalyseResultService extends BaseService {

	@Override
	protected String getKey() {
		return "ImplantAnalyseResultMapper";
	}

	/**
	 * 获取按角色分类的广告统计信息
	 * @author xuchangjian 2017年7月5日上午10:45:11
	 * @param goodsIdList
	 * @return
	 */
	public Map<String, Object> queryRoleImplant(List<String> goodsIdList, Integer pageSize, Integer currentPage)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProjectModel project = SessionUtil.getSessionProject();
		
		//归类为"其他"的最大比例
		double otherRate = Double.parseDouble(PropertiesUtil.getProperty("otherRate"));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", project.getId());
		params.put("goodsIdList", goodsIdList);
		List<Map<String, Object>> roleImplant = this.getList("selectRoleImplant", params);
		
		//角色的广告统计列表
		List<RoleImplantDto> roleImplantDtoList = new ArrayList<RoleImplantDto>();
		for (Map<String, Object> map : roleImplant)
		{
			String roleId = (String) map.get("roleId");
			String roleName = (String) map.get("roleName");
			String goodsId = (String) map.get("goodsId");
			String goods = (String) map.get("goods");
			int roundCount = Integer.parseInt(map.get("roundCount").toString());
			
			GoodsImplantDto goodsImplantDto = new GoodsImplantDto();
			goodsImplantDto.setId(goodsId);
			goodsImplantDto.setGoods(goods);
			goodsImplantDto.setRoundCount(roundCount);
			
			RoleImplantDto roleImplantDto = new RoleImplantDto();
			roleImplantDto.setId(roleId);
			roleImplantDto.setName(roleName);
			
			if (!roleImplantDtoList.contains(roleImplantDto))
			{
				List<GoodsImplantDto> goodsImplantDtoList = new ArrayList<GoodsImplantDto>();
				goodsImplantDtoList.add(goodsImplantDto);
				
				roleImplantDto.setGoodsImplantList(goodsImplantDtoList);
				roleImplantDto.setTotalRoundCount(roundCount);
				
				roleImplantDtoList.add(roleImplantDto);
			}
			else
			{
				roleImplantDto = roleImplantDtoList.get(roleImplantDtoList.indexOf(roleImplantDto));
				roleImplantDto.getGoodsImplantList().add(goodsImplantDto);
				roleImplantDto.setTotalRoundCount(roleImplantDto.getTotalRoundCount() + roundCount);
			}
		}
		
		//把比例过小的品类归为“其他”
		for (RoleImplantDto roleImplantDto : roleImplantDtoList)
		{
			List<GoodsImplantDto> goodsImplantDtoList = roleImplantDto.getGoodsImplantList();
			int totalRoundCount = roleImplantDto.getTotalRoundCount();
			
			List<GoodsImplantDto> toRemoveGoodsImplantDtoList = new ArrayList<GoodsImplantDto>();
			GoodsImplantDto otherGoodsImplantDto = new GoodsImplantDto();
			otherGoodsImplantDto.setId("");
			otherGoodsImplantDto.setGoods("其他");
			otherGoodsImplantDto.setRoundCount(0);
			
			for (GoodsImplantDto goodsImplantDto : goodsImplantDtoList)
			{
				int goodsRoundCount = goodsImplantDto.getRoundCount();
				double rate = BigDecimalUtil.divide((double)goodsRoundCount, (double)totalRoundCount);
				if (rate <= otherRate)
				{
					toRemoveGoodsImplantDtoList.add(goodsImplantDto);
					
					otherGoodsImplantDto.setId(otherGoodsImplantDto.getId() + "," + goodsImplantDto.getId());
					otherGoodsImplantDto.setRoundCount(otherGoodsImplantDto.getRoundCount() + goodsRoundCount);
				}
			}
			
			goodsImplantDtoList.removeAll(toRemoveGoodsImplantDtoList);
			if (otherGoodsImplantDto.getRoundCount() > 0)
			{
				otherGoodsImplantDto.setId(otherGoodsImplantDto.getId().substring(1, otherGoodsImplantDto.getId().length()));
				goodsImplantDtoList.add(otherGoodsImplantDto);
			}
		}
		
		//把角色列表按照总场数倒序排序
		Collections.sort(roleImplantDtoList, new Comparator<RoleImplantDto>() {
			@Override
			public int compare(RoleImplantDto o1, RoleImplantDto o2) {
				return o2.getTotalRoundCount() - o1.getTotalRoundCount();
			}
		});
		
		Page page = new Page();
		page.setPageSize(pageSize);
		page.setCurrentPage(currentPage);
		
		page.setTotalRows(roleImplantDtoList.size());
		
		int start = (currentPage - 1) * pageSize;
		int end = currentPage * pageSize - 1;
		if (end >= roleImplantDtoList.size()) {
			end = roleImplantDtoList.size();
		}
		
		resultMap.put("totalRows", page.getTotalRows());
		resultMap.put("totalPage", page.getTotalPage());
		resultMap.put("roleList", roleImplantDtoList.subList(start, end));
		return resultMap;
	}
	
	/**
	 * 获取按产品分类的广告统计信息
	 * @author xuchangjian 2017年7月5日上午10:45:11
	 * @param goodsIdList
	 * @return
	 */
	public Map<String, Object> queryGoodsImplant(List<String> goodsIdList)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProjectModel project = SessionUtil.getSessionProject();
		//归类为"其他"的最大比例
		double otherRate = Double.parseDouble(PropertiesUtil.getProperty("otherRate"));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", project.getId());
		params.put("goodsIdList", goodsIdList);
		List<Map<String, Object>> goodsImplant = this.getList("selectGoodsImplant", params);
		
		int totalRoundCount = 0;
		for (Map<String, Object> map : goodsImplant)
		{
			int roundCount = Integer.parseInt(map.get("roundCount").toString());
			totalRoundCount += roundCount;
		}
		
		List<Map<String, Object>> toRemoveGoods = new ArrayList<Map<String, Object>>();
		int otherRoundCount = 0;
		List<String> otherIdList = new ArrayList<String>();
		for (Map<String, Object> map : goodsImplant)
		{
			String id = (String) map.get("id");
			int roundCount = Integer.parseInt(map.get("roundCount").toString());
			
			double rate = BigDecimalUtil.divide((double)roundCount, (double)totalRoundCount);
			map.put("rate", rate);
			if (rate <= otherRate)
			{
				toRemoveGoods.add(map);
				otherRoundCount += roundCount;
				otherIdList.add(id);
			}
			
			List<String> idList = new ArrayList<String>();
			idList.add(id);
			map.put("idList", idList);
		}
		goodsImplant.removeAll(toRemoveGoods);
		
		if (otherIdList.size() > 0) {
			Map<String, Object> otherGoods = new HashMap<String, Object>();
			otherGoods.put("id", "");
			otherGoods.put("idList", otherIdList);
			otherGoods.put("goods", "其他");
			otherGoods.put("roundCount", otherRoundCount);
			otherGoods.put("rate", BigDecimalUtil.divide(otherRoundCount, totalRoundCount));
			goodsImplant.add(otherGoods);
		}
		
		
		Collections.sort(goodsImplant, new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				if (o1.get("goods").equals("其他")) {
					return 1;
				}
				
				return Integer.parseInt(o2.get("roundCount").toString()) - Integer.parseInt(o1.get("roundCount").toString());
			}
		});
		resultMap.put("goodsList", goodsImplant);
		return resultMap;
	}
	
	/**
	 * 查询按场次产品分类的广告统计
	 * @author xuchangjian 2017年7月5日下午5:59:01
	 * @param seriesNoList
	 * @return
	 */
	public Map<String, Object> queryRoundGoodsImplant(List<String> seriesNoList, List<String> goodsIdList, String roleId, Integer pageSize, Integer currentPage)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Page page = null;
		if (pageSize != null && currentPage != null) 
		{
			page = new Page();
			page.setPageSize(pageSize);
			page.setCurrentPage(currentPage);
		}
		
		ProjectModel project = SessionUtil.getSessionProject();
		
		Map<String, Object> implantParams = new HashMap<String, Object>();
		implantParams.put("projectId", project.getId());
		implantParams.put("seriesNoList", seriesNoList);
		implantParams.put("goodsIdList", goodsIdList);
		implantParams.put("roleId", roleId);
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (page != null) 
		{
			resultList = this.queryPageList("selectImplant", implantParams, page);
		}
		else
		{
			resultList = this.getList("selectImplant", implantParams);
		}
		
		//查询项目中的所有角色
		Map<String, Object> roleParams = new HashMap<String, Object>();
		roleParams.put("projectId", project.getId());
		List<PlayRoleModel> roundRoleList = this.getList("PlayRoleMapper.selectPlayRoleList", roleParams);
		//把角色封装成以id为key，角色信息为value的Map形式
		Map<String, PlayRoleModel> roleMap = new HashMap<String, PlayRoleModel>();
		for (PlayRoleModel role : roundRoleList)
		{
			roleMap.put(role.getId(), role);
		}
		
		//封装最后的场次数据，key为集次，value为集次下的场次列表
		Map<Integer, List<Map<String, Object>>> seriesList = new HashMap<Integer, List<Map<String, Object>>>();
		for (Map<String, Object> result : resultList)
		{
			String roundId = (String) result.get("roundId");	//场次ID
			int seriesNo = (int) result.get("seriesNo");
			String roleIds = (String) result.get("roleIds");
			
			//计算出主要角色名称列表
			List<String> majorRoleNameList = new ArrayList<String>();
			if (!StringUtils.isBlank(roleIds))
			{
				for (String myRoleId : roleIds.split(","))
				{
					PlayRoleModel role = roleMap.get(myRoleId);
					if (role.getType() == 1)
					{
						majorRoleNameList.add(role.getName());
					}
				}
			}
			
			//场次信息Map
			Map<String, Object> roundMap = new HashMap<String, Object>();
			roundMap.putAll(result);
			roundMap.remove("id");
			roundMap.remove("roundId");
			roundMap.remove("roldIds");
			
			roundMap.put("id", roundId);
			roundMap.put("majorRoleNameList", majorRoleNameList);
			
			
			if (!seriesList.containsKey(seriesNo))
			{
				List<Map<String, Object>> roundList = new ArrayList<Map<String, Object>>();
				roundList.add(roundMap);
				
				seriesList.put(seriesNo, roundList);
			}
			else
			{
				seriesList.get(seriesNo).add(roundMap);
			}
		}
		
		resultMap.put("roundList", seriesList);
		if (page != null)
		{
			resultMap.put("totalPage", page.getTotalPage());
			resultMap.put("totalRows", page.getTotalRows());
		}
		return resultMap;
	}
	
	/**
	 * 获取分析结果
	 * @author xuchangjian 2017年7月28日上午10:21:49
	 * @param roundId
	 * @return
	 */
	public Map<String, Object> queryRoundResult(String roundId) 
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (StringUtils.isBlank(roundId)) {
			throw new BusinessException("请提供场次ID");
		}
		
		Double minWeight = Double.parseDouble(PropertiesUtil.getProperty("roundMinWeight"));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roundId", roundId);
		params.put("minWeight", minWeight);
		
		List<Map<String, Object>> resultList = this.getList("selectRoundResult", params);
		
		resultMap.put("resultList", resultList);
		return resultMap;
	}
}
