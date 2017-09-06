package com.xiaotu.advertiser.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.project.model.PlayLabel;
import com.xiaotu.advertiser.project.service.PlayLabelService;
import com.xiaotu.common.util.SessionUtil;

/**
 * 
 * @ClassName: PlayLabelController
 * @Description: 标签管理
 * @author: 张晓
 * @date: 2017年9月4日 上午11:07:14
 */
@RestController
@RequestMapping("/playLabel")
public class PlayLabelController {
	@Autowired
	private PlayLabelService playLabelService;
	
	/**
	 * 
	 * @Title: queryPlayLabel
	 * @Description: 查询标签树
	 * @return 张晓
	 * @return: Object
	 */
	@RequestMapping("/queryPlayLabel")
	public Object queryPlayLabel(){
		return playLabelService.queryPlayLabel();
	}
	
	/**
	 * 
	 * @Title: queryMaxLabelId
	 * @Description: 回显最大id 与 当前增加弹框回显上级名称
	 * @return 张晓
	 * @return: Object
	 */
	@RequestMapping("/queryMaxLabelId")
	public Object queryMaxLabelId(@RequestBody PlayLabel playLabel){
		Map<String,Object> map= playLabelService.saveMaxLabelId(playLabel);
		return map;
	}
	
	/**
	 * 
	 * @Title: queryMaxLabelId
	 * @Description: 增加标签
	 * @param playLabel
	 * @return 张晓
	 * @return: Object
	 */
	@RequestMapping("/savePlayLabel")
	public Object savePlayLabel(@RequestBody PlayLabel playLabel){
		playLabelService.updateMaxLabel(playLabel);
		return null;
	}
	
	/**
	 * 调用shell脚本,自动分析标签
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/savePlayEpisode")
	public Object savePlayEpisode() throws Exception
	{
		playLabelService.processPlayEpisode();
		return null;
	}
	
	/***
	 * 
	 * @Title: removePlayLabel
	 * @Description: 删除一级标签及一下的二级标签标签
	 * @param playLabel
	 * @return 张晓
	 * @return {ids} id字符串
	 * @return: Object
	 */
	@RequestMapping("/removePlayLabel")
	public Object removePlayLabel(@RequestBody PlayLabel playLabel){
		playLabelService.removePlayLabel(playLabel);
		return null;
	}
	/**
	 * 
	 * @Title: beforeUpdateQueryById
	 * @Description: 修改前的查询返回label实体
	 * @param playLabel 包含主键id
	 * @return   张晓 
	 * @return: Object
	 */
	@RequestMapping("/beforeUpdateQueryById")
	public Object beforeUpdateQueryById(@RequestBody PlayLabel playLabel){
		PlayLabel label=playLabelService.beforeUpdateQueryById(playLabel);
		return label;
	}
	/***
	 * 
	 * @Title: updatePlayLabelById
	 * @Description: 修改标签根据id
	 * @param playLabel
	 * @author 张晓 
	 * @return: Object
	 */
	@RequestMapping("/updatePlayLabelById")
	public Object updatePlayLabelById(@RequestBody PlayLabel playLabel){
		playLabelService.updatePlayLabelById(playLabel);
		return null;
	}
	
	
	/***
	 * 
	 * @Title: removePlayLabelById
	 * @Description: 取消按钮删除删除点击确定时生成的标签记录根据id
	 * @param playLabel
	 * @author 张晓 
	 * @return: Object
	 */
	@RequestMapping("/removePlayLabelById")
	public Object removePlayLabelById(@RequestBody PlayLabel playLabel){
		playLabelService.removePlayLabelById(playLabel);
		return null;
	}
	
	/**
	 * 查询自动分析任务
	 * @return 查询结果
	 */
	@RequestMapping("/queryLableJob")
	public Object queryLableJob()
	{
		return playLabelService.get("LabelTargetstateMapper.selectJob",
				SessionUtil.getSessionProject());
	}
}
