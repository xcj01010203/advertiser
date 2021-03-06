package com.xiaotu.advertiser.project.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.dictionary.model.GoodsModel;
import com.xiaotu.advertiser.project.controller.dto.PlayMarkDto;
import com.xiaotu.advertiser.project.model.PlayMarkModel;
import com.xiaotu.advertiser.project.model.PlayRoleModel;
import com.xiaotu.advertiser.project.model.PlayRoundModel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.advertiser.project.model.map.PlayMarkGoodsMapModel;
import com.xiaotu.advertiser.project.model.map.PlayMarkRoleMapModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.BigDecimalUtil;
import com.xiaotu.common.util.ExcelUtils;
import com.xiaotu.common.util.SessionUtil;

/**
 * 标记信息
 * @author xuchangjian 2017年6月26日下午5:50:51
 */
@Service
public class PlayMarkService extends BaseService {

	@Autowired
	PlayLabelService playLabelService;
	
	@Override
	protected String getKey() {
		return "PlayMarkMapper";
	}
	private static Map<String, String> CREW_PROPS_MAP = new LinkedHashMap<String, String>();//需要导出的字段
    static{
    	CREW_PROPS_MAP.put("集-场", "seriesRoundNo");
    	CREW_PROPS_MAP.put("关键词",  "word");
    	CREW_PROPS_MAP.put("产品名称",  "goods");
    	CREW_PROPS_MAP.put("角色",  "roleNameList");
    	CREW_PROPS_MAP.put("描述",  "description");
    }
	
	/**
	 * 保存标记信息
	 * @author xuchangjian 2017年6月26日下午5:50:01
	 * @param id
	 * @param roundId
	 * @param word
	 * @param word_x
	 * @param goodsList
	 * @param roleNameList
	 * @param description
	 * @return
	 */
	public void saveMark(PlayMarkDto playMarkDto)
	{
		if (StringUtils.isBlank(playMarkDto.getRoundId()))
		{
			throw new BusinessException("场次ID不能为空");
		}
		if (StringUtils.isBlank(playMarkDto.getWord()))
		{
			throw new BusinessException("关键字不能为空");
		}
		if (playMarkDto.getWord_x() == null)
		{
			throw new BusinessException("关键字出现的次数不能为空");
		}
		if (playMarkDto.getGoodsList() == null || playMarkDto.getGoodsList().size() == 0)
		{
			throw new BusinessException("品类不能为空");
		}
		/*if (playMarkDto.getRoleNameList() == null || playMarkDto.getRoleNameList().size() == 0)
		{
			throw new BusinessException("角色不能为空");
		}*/
		
		//校验是否重复
		Map<String, Object> existMarkParam = new HashMap<String, Object>();
		existMarkParam.put("roundId", playMarkDto.getRoundId());
		existMarkParam.put("word", playMarkDto.getWord());
		existMarkParam.put("word_x", playMarkDto.getWord_x());
		PlayMarkModel existMark = this.get("selectMark", existMarkParam);
		if (existMark != null && !existMark.getId().equals(playMarkDto.getId()))
		{
			throw new BusinessException("标记重复");
		}
		

		ProjectModel project = SessionUtil.getSessionProject();
		PlayRoundModel playRound = new PlayRoundModel();
		playRound.setId(playMarkDto.getRoundId());
		
		PlayMarkModel mark = new PlayMarkModel();
		mark.setProject(project);
		mark.setPlayRound(playRound);
		mark.setWord(playMarkDto.getWord());
		mark.setWord_x(playMarkDto.getWord_x());
		mark.setDescription(playMarkDto.getDescription());
		if (StringUtils.isBlank(playMarkDto.getId()))
		{
			mark.setCreateTime(new Date());
			this.save("insertOne", mark);
		}
		else
		{
			mark.setId(playMarkDto.getId());
			this.update("upateOne", mark);
		}
		
		if (!StringUtils.isBlank(playMarkDto.getId()))
		{
			//删除该标记和品类的关联关系
			this.delete("PlayMarkGoodsMapMapper.deleteByMarkId", playMarkDto.getId());
		}
		
		
		//重新保存标记和品类的关联
		List<PlayMarkGoodsMapModel> markGoodsMapList = new ArrayList<PlayMarkGoodsMapModel>();
		for (GoodsModel goods : playMarkDto.getGoodsList())
		{
			PlayMarkGoodsMapModel map = new PlayMarkGoodsMapModel();
			map.setGoods(goods);
			map.setPlayMark(mark);
			markGoodsMapList.add(map);
		}
		this.save("PlayMarkGoodsMapMapper.insertBatch", markGoodsMapList);
		
		
		//删除该标记和角色的关联
		if (!StringUtils.isBlank(playMarkDto.getId()))
		{
			this.delete("PlayMarkRoleMapMapper.deleteByMarkId", playMarkDto.getId());
		}
		
		//重新保存该标记和角色的关联
		List<PlayMarkRoleMapModel> markRoleMapList = new ArrayList<PlayMarkRoleMapModel>();
		
		Map<String, Object> roleParam = new HashMap<String, Object>();
		roleParam.put("projectId", project.getId());
		if (playMarkDto.getRoleNameList() == null || playMarkDto.getRoleNameList().size() == 0){
		}else{
			roleParam.put("roleNameList", playMarkDto.getRoleNameList());
			List<PlayRoleModel> roleList = this.getList("PlayRoleMapper.selectPlayRoleList", roleParam);
			for (PlayRoleModel role : roleList)
			{
				PlayMarkRoleMapModel map = new PlayMarkRoleMapModel();
				map.setPlayMark(mark);
				map.setPlayRole(role);
				markRoleMapList.add(map);
			}
			this.save("PlayMarkRoleMapMapper.insertBatch", markRoleMapList);
		}
	}
	
	/**
	 * 删除标记信息
	 * @author xuchangjian 2017年6月27日下午3:40:54
	 * @param id
	 */
	public void removeMark(String id)
	{
		//删除标记和角色的关联
		this.delete("PlayMarkRoleMapMapper.deleteByMarkId", id);
		
		//删除标记和品类的关联
		this.delete("PlayMarkGoodsMapMapper.deleteByMarkId", id);
		
		//删除标记信息
		this.delete("deleteById", id);
	}
	
	/**
	 * 获取标记列表
	 * @author xuchangjian 2017年6月27日下午3:44:49
	 * @param roundId	场次ID
	 * @return
	 */
	public Map<String, Object> queryMarkList(String roundId)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (StringUtils.isBlank(roundId))
		{
			throw new BusinessException("场次ID不能为空");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roundId", roundId);
		List<PlayMarkModel> markList = this.getList("selectMark", params);
		List<Map<String, Object>> goodsMarkMapList = this.getList("GoodsMapper.selectByRoundId", params);	//带有标记ID的品类列表
		List<Map<String, Object>> roleMarkMapList = this.getList("PlayRoleMapper.selectRoleListWithMarkInfo", params);	//带有标记ID的角色列表
		
		//把品类按照标记ID分组，key为标记ID，value为品类列表
		Map<String, List<GoodsModel>> groupGoodsMap = new HashMap<String, List<GoodsModel>>();
		for (Map<String, Object> goodsMarkMap : goodsMarkMapList)
		{
			String playMarkId = (String) goodsMarkMap.get("playMarkId");
			
			GoodsModel goods = new GoodsModel();
			goods.setId((String) goodsMarkMap.get("id"));
			goods.setGoods((String) goodsMarkMap.get("goods"));
			
			if (!groupGoodsMap.containsKey(playMarkId))
			{
				List<GoodsModel> goodsList = new ArrayList<GoodsModel>();
				goodsList.add(goods);
				groupGoodsMap.put(playMarkId, goodsList);
			}
			else 
			{
				groupGoodsMap.get(playMarkId).add(goods);
			}
		}
		
		//把角色按照标记ID分组，key为标记ID，value为角色列表
		Map<String, List<String>> groupRoleMap = new HashMap<String, List<String>>();
		for (Map<String, Object> roleMarkMap : roleMarkMapList)
		{
			String playMarkId = (String) roleMarkMap.get("playMarkId");
			String roleName = (String) roleMarkMap.get("name");
			
			if (!groupRoleMap.containsKey(playMarkId))
			{
				List<String> roleList = new ArrayList<String>();
				roleList.add(roleName);
				groupRoleMap.put(playMarkId, roleList);
			}
			else
			{
				groupRoleMap.get(playMarkId).add(roleName);
			}
		}
		
		//组装最后的结果
		List<PlayMarkDto> markDtoList = new ArrayList<PlayMarkDto>();
		for (PlayMarkModel mark : markList)
		{
			String id = mark.getId();
			PlayMarkDto markDto = new PlayMarkDto();
			markDto.setId(id);
			markDto.setWord(mark.getWord());
			markDto.setWord_x(mark.getWord_x());
			markDto.setDescription(mark.getDescription());
			markDto.setGoodsList(groupGoodsMap.get(id));
			markDto.setRoleNameList(groupRoleMap.get(id));
			
			markDtoList.add(markDto);
		}
		
		resultMap.put("markList", markDtoList);
		return resultMap;
	}
	
	
	/**
	 * 获取剧本标记列表
	 * @author wangyanlong 2017年8月21日
	 * @return
	 */
	public Map<String, Object> queryScriptMarkList(Integer pageSize, Integer currentPage)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ProjectModel project = SessionUtil.getSessionProject();
		
		Page page = null;
		if (pageSize != null && currentPage != null) {
			page = new Page();
			page.setPageSize(pageSize);
			page.setCurrentPage(currentPage);
		}
		List<PlayMarkModel> markList =null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", project.getId());
		
		if(page !=null){
			markList =this.queryPageList("selectScriptMark", params, page);
		}else{
			markList = this.getList("selectScriptMark", params);
		}
		List<Map<String, Object>> goodsMarkMapList = this.getList("GoodsMapper.selectByRoundId", params);	//带有标记ID的品类列表
		List<Map<String, Object>> roleMarkMapList = this.getList("PlayRoleMapper.selectRoleListWithMarkInfo", params);	//带有标记ID的角色列表
		
		//把品类按照标记ID分组，key为标记ID，value为品类列表
		Map<String, List<GoodsModel>> groupGoodsMap = new HashMap<String, List<GoodsModel>>();
		for (Map<String, Object> goodsMarkMap : goodsMarkMapList)
		{
			String playMarkId = (String) goodsMarkMap.get("playMarkId");
			
			GoodsModel goods = new GoodsModel();
			goods.setId((String) goodsMarkMap.get("id"));
			goods.setGoods((String) goodsMarkMap.get("goods"));
			
			if (!groupGoodsMap.containsKey(playMarkId))
			{
				List<GoodsModel> goodsList = new ArrayList<GoodsModel>();
				goodsList.add(goods);
				groupGoodsMap.put(playMarkId, goodsList);
			}
			else 
			{
				groupGoodsMap.get(playMarkId).add(goods);
			}
		}
		
		//把角色按照标记ID分组，key为标记ID，value为角色列表
		Map<String, List<String>> groupRoleMap = new HashMap<String, List<String>>();
		for (Map<String, Object> roleMarkMap : roleMarkMapList)
		{
			String playMarkId = (String) roleMarkMap.get("playMarkId");
			String roleName = (String) roleMarkMap.get("name");
			
			if (!groupRoleMap.containsKey(playMarkId))
			{
				List<String> roleList = new ArrayList<String>();
				roleList.add(roleName);
				groupRoleMap.put(playMarkId, roleList);
			}
			else
			{
				groupRoleMap.get(playMarkId).add(roleName);
			}
		}
		
		//组装最后的结果
		List<PlayMarkDto> markDtoList = new ArrayList<PlayMarkDto>();
		for (PlayMarkModel mark : markList)
		{
			String id = mark.getId();
			PlayMarkDto markDto = new PlayMarkDto();
			markDto.setId(id);
			markDto.setWord(mark.getWord());
			markDto.setWord_x(mark.getWord_x());
			markDto.setPlayRound(mark.getPlayRound());
			markDto.setDescription(mark.getDescription());
			markDto.setGoodsList(groupGoodsMap.get(id));
			markDto.setRoleNameList(groupRoleMap.get(id));
			markDto.setPageCount(mark.getPageCount());
			
			String roundId =mark.getPlayRound().getId();
			List<Double> lableList = playLabelService.queryLableRoundId(roundId);
			Double sum = 0d;
			for (Double LabelScore : lableList) {
				sum += LabelScore;
			}
			//最终精彩指数
			markDto.setWonderful(BigDecimalUtil.addMultiply(sum,mark.getPageCount()));
			markDtoList.add(markDto);
		}
		
		resultMap.put("markList", markDtoList);
		if (page != null) {
			resultMap.put("totalRows", page.getTotalRows());
			resultMap.put("totalPage", page.getTotalPage());
		}
		return resultMap;
	}
	
	
	/**
	 * 导出剧本标记列表
	 * @author wangyanlong 2017年9月2日
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void queryExportMarkList(String roleNames,HttpServletResponse response) throws IllegalArgumentException, IllegalAccessException, IOException
	{
		ProjectModel project = SessionUtil.getSessionProject();
		
		List<PlayMarkModel> markList =null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", project.getId());
		markList = this.getList("selectScriptMark", params);
		List<Map<String, Object>> goodsMarkMapList = this.getList("GoodsMapper.selectByRoundId", params);	//带有标记ID的品类列表
		List<Map<String, Object>> roleMarkMapList = this.getList("PlayRoleMapper.selectRoleListWithMarkInfo", params);	//带有标记ID的角色列表
		
		//把品类按照标记ID分组，key为标记ID，value为品类列表
		List<Map<String, Object>> dealedRoundList = new ArrayList<Map<String, Object>>();
		Map<String, List<GoodsModel>> groupGoodsMap = new HashMap<String, List<GoodsModel>>();
		for (Map<String, Object> goodsMarkMap : goodsMarkMapList)
		{
			String playMarkId = (String) goodsMarkMap.get("playMarkId");
			
			GoodsModel goods = new GoodsModel();
			goods.setId((String) goodsMarkMap.get("id"));
			goods.setGoods((String) goodsMarkMap.get("goods"));
			
			if (!groupGoodsMap.containsKey(playMarkId))
			{
				List<GoodsModel> goodsList = new ArrayList<GoodsModel>();
				goodsList.add(goods);
				groupGoodsMap.put(playMarkId, goodsList);
			}
			else 
			{
				groupGoodsMap.get(playMarkId).add(goods);
			}
		}
		
		//把角色按照标记ID分组，key为标记ID，value为角色列表
		Map<String, List<String>> groupRoleMap = new HashMap<String, List<String>>();
		for (Map<String, Object> roleMarkMap : roleMarkMapList)
		{
			String playMarkId = (String) roleMarkMap.get("playMarkId");
			String roleName = (String) roleMarkMap.get("name");
			
			if (!groupRoleMap.containsKey(playMarkId))
			{
				List<String> roleList = new ArrayList<String>();
				roleList.add(roleName);
				groupRoleMap.put(playMarkId, roleList);
			}
			else
			{
				groupRoleMap.get(playMarkId).add(roleName);
			}
		}
		
		//组装最后的结果
		for (PlayMarkModel mark : markList)
		{
			Map<String, Object> roundMap = new HashMap<String, Object>();
			String id = mark.getId();
			String series_round_no =mark.getPlayRound().getSeriesNo() + "-" + mark.getPlayRound().getRoundNo();
			String word =mark.getWord();
			List<GoodsModel> goodList = groupGoodsMap.get(id);
			String goods ="";
			for (GoodsModel goodsModel : goodList) {
				if(goodList.size() <=1){
					goods = goodsModel.getGoods();
				}else{
					goods += '|' + goodsModel.getGoods();
				}
			}
			String roleNameList =String.join("|", groupRoleMap.get(id));
			String description =mark.getDescription();
			roundMap.put("description", description);
			roundMap.put("roleNameList", roleNameList);
			roundMap.put("goods", goods);
			roundMap.put("word", word);
			roundMap.put("seriesRoundNo", series_round_no);
			dealedRoundList.add(roundMap);
			
		}
		ExcelUtils.exportPropsInfoForExcel(dealedRoundList,roleNames,response,CREW_PROPS_MAP,project.getName());
	}
}
