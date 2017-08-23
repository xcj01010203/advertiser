package com.xiaotu.advertiser.implant.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.implant.controller.dto.ImplantRecordDto;
import com.xiaotu.advertiser.implant.model.ImplantRecordModel;
import com.xiaotu.advertiser.implant.model.map.ImplantRoleMapModel;
import com.xiaotu.advertiser.project.model.PlayRoleModel;
import com.xiaotu.advertiser.project.model.PlayRoundModel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.SessionUtil;

/**
 * 广告植入
 * @author xuchangjian 2017年6月27日下午4:31:05
 */
@Service
public class ImplantRecordService extends BaseService {

	@Override
	protected String getKey() {
		return "ImplantRecordMapper";
	}
	
	/**
	 * 保存广告植入信息
	 * @author xuchangjian 2017年6月27日下午4:47:55
	 * @param record
	 * @return
	 */
	public void saveRecord(ImplantRecordDto recordDto)
	{
		if (recordDto.getPlayRound() == null || StringUtils.isBlank(recordDto.getPlayRound().getId()))
		{
			throw new BusinessException("场次ID不能为空");
		}
		if (StringUtils.isBlank(recordDto.getName()))
		{
			throw new BusinessException("名称不能为空");
		}
		if (recordDto.getRoleNameList() == null || recordDto.getRoleNameList().size() == 0)
		{
			throw new BusinessException("角色名称不能为空");
		}
		if (recordDto.getImplantMode() == null || StringUtils.isBlank(recordDto.getImplantMode().getId()))
		{
			throw new BusinessException("植入方式不能为空");
		}
		if (recordDto.getGoods() == null || StringUtils.isBlank(recordDto.getGoods().getId()))
		{
			throw new BusinessException("品类不能为空");
		}
		
		//校验重复
		Map<String, Object> existRecordParam = new HashMap<String, Object>();
		existRecordParam.put("name", recordDto.getName());
		existRecordParam.put("roundId", recordDto.getPlayRound().getId());
		existRecordParam.put("modeId", recordDto.getImplantMode().getId());
		existRecordParam.put("goodsId", recordDto.getGoods().getId());
		ImplantRecordModel existRecord = this.get("selectRecord", existRecordParam);
		if (existRecord != null && !existRecord.getId().equals(recordDto.getId()))
		{
			throw new BusinessException("广告重复");
		}
		
		
		ProjectModel project = SessionUtil.getSessionProject();
		
		ImplantRecordModel record = new ImplantRecordModel();
		record.setProject(project);
		record.setName(recordDto.getName());
		record.setPlayRound(recordDto.getPlayRound());
		record.setImplantMode(recordDto.getImplantMode());
		record.setGoods(recordDto.getGoods());
		record.setDesc(recordDto.getDesc());
		
		if (!StringUtils.isBlank(recordDto.getId()))
		{
			record.setId(recordDto.getId());
			this.update("updateOne", record);
		}
		else 
		{
			record.setCreateTime(new Date());
			this.save("insertOne", record);
		}
		
		
		//删除原有的和角色的关联
		if (!StringUtils.isBlank(recordDto.getId()))
		{
			this.delete("ImplantRoleMapMapper.deleteByRecordId", recordDto.getId());
		}
		
		
		//重新添加和角色的关联
		Map<String, Object> roleParam = new HashMap<String, Object>();
		roleParam.put("roleNameList", recordDto.getRoleNameList());
		roleParam.put("projectId", project.getId());
		List<PlayRoleModel> roleList = this.getList("PlayRoleMapper.selectPlayRoleList", roleParam);
		
		List<ImplantRoleMapModel> implantRecordMapList = new ArrayList<ImplantRoleMapModel>();
		for (PlayRoleModel role : roleList)
		{
			ImplantRoleMapModel map = new ImplantRoleMapModel();
			map.setImplantRecord(record);
			map.setPlayRole(role);
			implantRecordMapList.add(map);
		}
		this.save("ImplantRoleMapMapper.insertBatch", implantRecordMapList);
	}
	
	/**
	 * 删除广告植入信息
	 * @author xuchangjian 2017年6月27日下午6:19:18
	 * @param id
	 * @return
	 */
	public void removeRecord(String id)
	{
		if (StringUtils.isBlank(id))
		{
			throw new BusinessException("广告ID不能为空");
		}
		
		//删除广告植入和角色的关联
		this.delete("ImplantRoleMapMapper.deleteByRecordId", id);
		
		//删除广告植入信息
		this.delete("deleteOne", id);
	}
	
	/**
	 * 获取指定场次的广告植入列表
	 * @author xuchangjian 2017年6月27日下午6:25:36
	 * @param roundId
	 * @return
	 */
	public Map<String, Object> queryRecordList(String roundId, Integer pageSize, Integer currentPage)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProjectModel project = SessionUtil.getSessionProject();
		
		Page page = null;
		if (pageSize != null && currentPage != null) {
			page = new Page();
			page.setPageSize(pageSize);
			page.setCurrentPage(currentPage);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roundId", roundId);
		params.put("projectId", project.getId());
		
		List<ImplantRecordModel> recordList = null;
		if (page != null) {
			recordList = this.queryPageList("selectRecord", params, page);
		} else {
			recordList = this.getList("selectRecord", params);
		}
		
		List<Map<String, Object>> recordRoleMapList = this.getList("PlayRoleMapper.selectRoleListWithImplantInfo", params);
		
		//把角色按照广告信息分组，key为广告ID， value为角色列表
		Map<String, List<String>> groupRecordMap = new HashMap<String, List<String>>();
		for (Map<String, Object> recordMap : recordRoleMapList)
		{
			String implantId = (String) recordMap.get("implantId");
			String roleName = (String) recordMap.get("name");
			
			if (!groupRecordMap.containsKey(implantId))
			{
				List<String> roleNameList = new ArrayList<String>();
				roleNameList.add(roleName);
				groupRecordMap.put(implantId, roleNameList);
			}
			else
			{
				groupRecordMap.get(implantId).add(roleName);
			}
		}
		
		//处理最后的结果
		List<ImplantRecordDto> recordDtoList = new ArrayList<ImplantRecordDto>();
		for (ImplantRecordModel record : recordList)
		{
			ImplantRecordDto recordDto = new ImplantRecordDto();
			recordDto.setId(record.getId());
			recordDto.setName(record.getName());
			recordDto.setPlayRound(record.getPlayRound());
			recordDto.setRoleNameList(groupRecordMap.get(record.getId()));
			recordDto.setImplantMode(record.getImplantMode());
			recordDto.setGoods(record.getGoods());
			recordDto.setDesc(record.getDesc());
			recordDtoList.add(recordDto);
		}
		
		resultMap.put("recordList", recordDtoList);
		if (page != null) {
			resultMap.put("totalRows", page.getTotalRows());
			resultMap.put("totalPage", page.getTotalPage());
		}
		return resultMap;
	}

}
