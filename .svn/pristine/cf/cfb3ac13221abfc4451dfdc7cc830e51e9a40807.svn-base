package com.xiaotu.advertiser.project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaotu.advertiser.play.ViewInfoDto;
import com.xiaotu.advertiser.project.controller.dto.PlayRoundDto;
import com.xiaotu.advertiser.project.model.PlayContentModel;
import com.xiaotu.advertiser.project.model.PlayRoundTmpModel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.SessionUtil;

/**
 * 需要提示替换或跳过的场次数据
 * @author xuchangjian 2017年6月21日下午6:56:16
 */
@Service
public class PlayRoundTmpService extends BaseService {
	
	@Autowired
	private PlayRoundService playRoundService;
	
	@Autowired
	private PlayService playService;

	@Override
	protected String getKey() {
		return "PlayRoundTmpMapper";
	}
	
	/**
	 * 检查是否有需要跳过或替换的数据
	 * @author xuchangjian 2017年6月28日上午10:55:22
	 * @return
	 */
	@RequestMapping("/chackHasSkipOrReplaceData")
	public Map<String, Object> chackHasSkipOrReplaceData()
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProjectModel project = SessionUtil.getSessionProject();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", project.getId());
		List<PlayRoundTmpModel> roundTmpList = this.getList("selectWithoutContent", params);
		
		boolean hasData = false;
		if (roundTmpList != null && roundTmpList.size() > 0)
		{
			hasData = true;
		}
		resultMap.put("hasData", hasData);
		return resultMap;
	}
	
	/**
	 * 获取需要跳过或替换的集场号列表
	 * @author xuchangjian 2017年6月28日上午11:00:27
	 * @return
	 */
	public Map<String, Object> queryRoundTmpList()
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		ProjectModel project = SessionUtil.getSessionProject();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", project.getId());
		List<PlayRoundTmpModel> roundTmpList = this.getList("selectWithoutContent", params);
		
		//存储集场信息的map，key表示集次，value表示场次信息列表
		Map<Integer, List<Map<String, Object>>> seriesNoMap = new HashMap<Integer, List<Map<String, Object>>>();
		for (PlayRoundTmpModel round : roundTmpList)
		{
			String id = round.getId();
			int seriesNo = round.getSeriesNo();
			String roundNo = round.getRoundNo();
			
			if (!seriesNoMap.containsKey(seriesNo))
			{
				List<Map<String, Object>> roundNoMapList = new ArrayList<Map<String, Object>>();
				Map<String, Object> roundNoMap = new HashMap<String, Object>();
				roundNoMap.put("id", id);
				roundNoMap.put("roundNo", roundNo);
				
				roundNoMapList.add(roundNoMap);
				seriesNoMap.put(seriesNo, roundNoMapList);
			}
			else 
			{
				List<Map<String, Object>> roundNoMapList = seriesNoMap.get(seriesNo);
				Map<String, Object> roundNoMap = new HashMap<String, Object>();
				roundNoMap.put("id", id);
				roundNoMap.put("roundNo", roundNo);
				
				roundNoMapList.add(roundNoMap);
			}
		}
		
		resultMap.put("seriesNoMap", seriesNoMap);
		return resultMap;
	}
	
	/**
	 * 查询单个需要跳过或替换的数据（带有原场景信息）
	 * @author xuchangjian 2017年6月28日上午11:10:13
	 * @return
	 */
	public Map<String, Object> queryRoundTmpDetail(String id)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (StringUtils.isBlank(id))
		{
			throw new BusinessException("ID不能为空");
		}
		
		//新场景信息
		Map<String, Object> roundTmpParams = new HashMap<String, Object>();
		roundTmpParams.put("id", id);
		PlayRoundTmpModel roundTmp = this.get("selectWithContent", roundTmpParams);
		
		PlayRoundDto newRoundDto = new PlayRoundDto();
		newRoundDto.setSeriesNo(roundTmp.getSeriesNo());
		newRoundDto.setRoundNo(roundTmp.getRoundNo());
		newRoundDto.setAtmosphere(roundTmp.getAtmosphere());
		newRoundDto.setSite(roundTmp.getSite());
		newRoundDto.setFirstLocation(roundTmp.getFirstLocation());
		if (!StringUtils.isBlank(roundTmp.getMajorRoleNames()))
		{
			newRoundDto.setMajorRoleNameList(Arrays.asList(roundTmp.getMajorRoleNames().split(",")));
		}
		
		Map<String, String> newPlay = new HashMap<String, String>();
		newPlay.put("title", roundTmp.getTitle());
		newPlay.put("content", roundTmp.getContent());
		
		
		//原有场景信息
		PlayRoundDto oldRoundDto = this.playRoundService.queryRoundDetail(roundTmp.getPlayRound().getId());
		
		Map<String, Object> playParams = new HashMap<String, Object>();
		playParams.put("roundId", roundTmp.getPlayRound().getId());
		PlayContentModel oldPlayContent = this.get("PlayContentMapper.selectPlayContent", playParams);
		
		Map<String, String> oldPlay = new HashMap<String, String>();
		oldPlay.put("title", oldPlayContent.getTitle());
		oldPlay.put("content", oldPlayContent.getContent());
		
		
		resultMap.put("newRoundDetail", newRoundDto);
		resultMap.put("oldRoundDetail", oldRoundDto);
		resultMap.put("newPlay", newPlay);
		resultMap.put("oldPlay", oldPlay);
		return resultMap;
	}
	
	/**
	 * 只替换剧本
	 * @author xuchangjian 2017年6月28日下午2:16:54
	 * @param idList
	 */
	public void updateContentByRoundTmp(List<String> idList)
	{
		if (idList == null || idList.size() == 0)
		{
			throw new BusinessException("请选择场次");
		}
		
		ProjectModel project = SessionUtil.getSessionProject();
		
		Map<String, Object> roundTmpParam = new HashMap<String, Object>();
		roundTmpParam.put("idList", idList);
		List<PlayRoundTmpModel> roundTmpList = this.getList("selectWithContent", roundTmpParam);
		
		
		//求出所有场次ID列表
		List<String> roundIdList = new ArrayList<String>();
		for (PlayRoundTmpModel roundTmp : roundTmpList)
		{
			String roundId = roundTmp.getPlayRound().getId();
			roundIdList.add(roundId);
		}
		
		//删掉原有剧本信息
		this.delete("PlayContentMapper.deleteBatchByRoundIds", roundIdList);
		
		//添加新数据
		List<PlayContentModel> toAddContentList = new ArrayList<PlayContentModel>();
		for (PlayRoundTmpModel roundTmp : roundTmpList)
		{
			PlayContentModel content = new PlayContentModel();
			content.setProject(project);
			content.setPlayRound(roundTmp.getPlayRound());
			content.setTitle(roundTmp.getTitle());
			content.setContent(roundTmp.getContent());
			if (!StringUtils.isBlank(roundTmp.getContent()))
			{
				content.setWordCount(roundTmp.getContent().length());
			}
			toAddContentList.add(content);
		}
		this.save("PlayContentMapper.insertBatch", toAddContentList);
		
		this.removeByIds(roundIdList);
	}
	
	/**
	 * 场次和剧本全替换
	 * @author xuchangjian 2017年6月28日下午3:48:23
	 * @param idList
	 * @return
	 */
	public void updateAllByRoundTmp(List<String> idList)
	{
		if (idList == null || idList.size() == 0)
		{
			throw new BusinessException("请选择场次");
		}
		
		ProjectModel project = SessionUtil.getSessionProject();
		
		Map<String, Object> roundTmpParam = new HashMap<String, Object>();
		roundTmpParam.put("idList", idList);
		List<PlayRoundTmpModel> roundTmpList = this.getList("selectWithContent", roundTmpParam);
		
		//求出所有场次ID列表
		List<String> roundIdList = new ArrayList<String>();
		for (PlayRoundTmpModel roundTmp : roundTmpList)
		{
			String roundId = roundTmp.getPlayRound().getId();
			roundIdList.add(roundId);
		}
		
		this.removeByIds(idList);
		
		//先删除已有的场次
		this.playRoundService.removeBatchById(roundIdList);
		
		List<ViewInfoDto> autoAddViewList = new ArrayList<ViewInfoDto>();	//自动新增的场次
		for (PlayRoundTmpModel roundTmp : roundTmpList)
		{
			ViewInfoDto viewInfoDto = new ViewInfoDto();
			viewInfoDto.setSeriesNo(roundTmp.getSeriesNo());
			viewInfoDto.setViewNo(roundTmp.getRoundNo());
			viewInfoDto.setAtmosphere(roundTmp.getAtmosphere());
			viewInfoDto.setSite(roundTmp.getSite());
			viewInfoDto.setTitle(roundTmp.getTitle());
			viewInfoDto.setContent(roundTmp.getContent());
			if (!StringUtils.isBlank(roundTmp.getMajorRoleNames()))
			{
				viewInfoDto.setMajorRoleNameList(Arrays.asList(roundTmp.getMajorRoleNames().split(",")));
			}
			viewInfoDto.setFirstLocation(roundTmp.getFirstLocation());
			autoAddViewList.add(viewInfoDto);
		}
		
		//保存需要自动保存的剧本
		this.playService.saveByViewInfoDtoList(autoAddViewList, project);
		
	}
	
	/**
	 * 根据多个ID删除
	 * @author xuchangjian 2017年6月28日下午4:00:18
	 * @param idList
	 */
	public void removeByIds(List<String> idList)
	{
		ProjectModel project = SessionUtil.getSessionProject();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idList", idList);
		params.put("projectId", project.getId());
		this.delete("deleteByRoundTmp", params);
	}
}
