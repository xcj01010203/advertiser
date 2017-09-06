/**  
 * Copyright © 2017公司名字. All rights reserved.
 *
 * @Title: PlayLabelService.java
 * @Prject: Advertiser
 * @Package: com.xiaotu.advertiser.project.service
 * @Description: TODO
 * @author: Administrator  
 * @date: 2017年9月4日 上午11:11:59
 * @version: V1.0  
 */
package com.xiaotu.advertiser.project.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.project.model.PlayLabel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.PropertiesUtil;
import com.xiaotu.common.util.SessionUtil;



/**
 * @ClassName: PlayLabelService
 * @Description: 标签service层
 * @author: 张晓
 * @date: 2017年9月4日 上午11:11:59
 */
@Service
public class PlayLabelService extends BaseService{

	 private static final Logger LOGGER = LoggerFactory
	            .getLogger(PlayLabelService.class);
	/**
	 * @Title: queryPlayLabel
	 * @Description: 查询所有标签数据
	 * @return
	 * @author: 张晓
	 * @return: Object
	 */
	public Object queryPlayLabel() {
		List<PlayLabel> playLabelList=this.getList("queryPlayLabelList");
		//获得根节点的所有子节点
		List<Map> mapList=getMenuListByPid(0,playLabelList);
			for (Map map : mapList) {
				List<Map> nodesList=getChildsByPid(map,playLabelList);
				map.put("nodes",nodesList);
			}
		return mapList;
	}
	
	
	/**
	 * @Title: getMenuListByPid
	 * @Description: 根据ID查询它所拥有子节点集合
	 * @author: 张晓
	 * @param id
	 * @param childList
	 * @return
	 * @return: List<Map>
	 */
	public List<Map> getMenuListByPid(int id,List<PlayLabel> childList){
		//用于存放根节点所拥有的子节点集合
		List<Map> chList=new ArrayList();
		//找出根节点根据ID与集合相匹配
		for (int i = 0; i < childList.size(); i++) {
			PlayLabel label = childList.get(i);
			if(id==label.getParentId()){
				Map map=new HashMap();
				map.put("id",label.getId());
				map.put("fid",label.getParentId());
				map.put("label",label.getLabel());
				map.put("score",label.getScore());
				chList.add(map);
			}
		}
		return chList;
	}
	/**
	 * 
	 * @Title: getChildsByPid
	 * @Description: 根据父节点ID获得所对应的子节点
	 * @param menuMap
	 * @param childList
	 * @author: 张晓
	 * @return
	 * @return: List<Map>
	 */
	public List<Map> getChildsByPid(Map menuMap,List<PlayLabel> childList){
		List<Map> nodesList=new ArrayList();
		//获得传递每个父节点对象的ID
		int pid =Integer.parseInt(menuMap.get("id").toString());
		for (int i = 0; i < childList.size(); i++) {
			PlayLabel label = childList.get(i);
			if(pid==label.getParentId()){
				Map map=new HashMap();
				map.put("id",label.getId());
				map.put("fid",label.getParentId());
				map.put("label",label.getLabel());
				map.put("score",label.getScore());
				nodesList.add(map);
				List<Map> noMapList=getChildsByPid(map,childList);
				map.put("nodes",noMapList);
			}
		}
		return nodesList;
	} 
	@Override
	protected String getKey() {
		// TODO 自动生成的方法存根
		return "PlayLabelMapper";
	}


	/**
	 * @param playLabel 
	 * @Title: queryMaxLabelId
	 * @Description: 回显最大id 与 当前增加弹框回显上级名称
	 * @return
	 * @return: Map<String,Object>
	 */
	public Map<String, Object> saveMaxLabelId(PlayLabel playLabel) {
		Map<String,Object> map=new HashMap();
		if(playLabel.getId()==null){
			playLabel.setLabel(""+new Date());
			playLabel.setScore(0.0);
			playLabel.setParentId(0);
			playLabel.setId(0);
			Integer id=this.save("returnOneLevelMaxId", playLabel);
			PlayLabel maxLabel=this.get("queryMaxLabel", null);
			Integer maxId=maxLabel.getId();
			map.put("maxId", maxId);
			map.put("label", "");
			
		}else{
			//1.查寻上级名称
			PlayLabel label=this.get("queryLabelName", playLabel);
			String labelName=label.getLabel();
			//2.回显最大id
			playLabel.setLabel(""+new Date());
			playLabel.setScore(0.0);
			Integer id=this.save("returnMaxId", playLabel);
			PlayLabel maxLabel=this.get("queryMaxLabel", null);
			Integer maxId=maxLabel.getId();
			
			map.put("label", labelName);
			map.put("maxId", maxId);
			
		}
		return map;
	}


	/**
	 * @Title: saveMaxLabel
	 * @Description: 增加标签
	 * @param playLabel
	 * @author: 张晓
	 * @return: void
	 */
	public void updateMaxLabel(PlayLabel playLabel) {
		//修改
		int id=this.update("savePlayLabel", playLabel);
		if(id!=1){
			throw new BusinessException("增加标签操作失败!");
		}
	}


	/**
	 * @Title: removePlayLabel
	 * @Description: 删除一级标签及一下的二级标签标签
	 * @param playLabel
	 * @author: 张晓
	 * @return: void
	 */
	public void removePlayLabel(PlayLabel playLabel) {
		String [] idsArr=playLabel.getIds().split(",");
		List list=new ArrayList();
		for (int i = 0; i < idsArr.length; i++) {
			list.add(Integer.parseInt(idsArr[i]));
		}
		
		this.delete("deleteAllLabel", list);
	}


	/**
	 * @Title: beforeUpdateQueryById
	 * @Description: 修改前的查询返回label实体
	 * @param playLabel  包含主键id
	 * @author: 张晓
	 * @return
	 * @return: Object
	 */
	public PlayLabel beforeUpdateQueryById(PlayLabel playLabel) {
		PlayLabel label=this.get("beforeUpdateQueryById", playLabel);
		return label;
	}


	/**
	 * @Title: updatePlayLabelById
	 * @Description: 修改标签根据id
	 * @param playLabel 
	 * @author: 张晓
	 * @return: void
	 */
	public void updatePlayLabelById(PlayLabel playLabel) {
		int id=this.update("updatePlayLabel", playLabel);
		if(id!=1){
			throw new BusinessException("修改标签操作失败!");
		}
	}
	
	
	/**
	 * 根据场次ID获取标签评分
	 * @param roundId
	 * @return
	 */
	public List<Double> queryLableRoundId(String roundId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roundId",roundId);
		return this.getList("queryLableRoundId", map);
	}
	
	
	 /**
     * 调用远程服务shell脚本,自动分析标签
     * @return
     * @throws Exception
     */
    public void processPlayEpisode() throws Exception {
    	try{
    		ProjectModel project = SessionUtil.getSessionProject();
        	ProcessBuilder processBuilder = new ProcessBuilder();
    		List<String> commands = new ArrayList<String>();
    		String automaticTagScript = PropertiesUtil.getProperty(Constants.AUTOMATIC_TAG_SCRIPT);
    		commands.add(automaticTagScript);
    		commands.add(project.getId());
    		processBuilder.command(commands);
    		processBuilder.redirectErrorStream(true);
    		Process process = null;
    		process = processBuilder.start();
    			process.waitFor();
    			String stdout = null, line = null;
    			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
    			while ((line = in.readLine()) != null) stdout = stdout == null ? line : (stdout = stdout + System.lineSeparator() + line);
    			in.close();
        }catch (Exception e){
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
    }
    
    /**
	 * @Title: removePlayLabelById
	 * @Description: 取消按钮删除删除点击确定时生成的标签记录根据id
	 * @param playLabel
	 * @return: void
	 */
	public void removePlayLabelById(PlayLabel playLabel) {
		int value=this.delete("removePlayLabelById", playLabel);
		if(value!=1){
			throw new BusinessException("取消按钮操作失败!");
		}
	}
}
