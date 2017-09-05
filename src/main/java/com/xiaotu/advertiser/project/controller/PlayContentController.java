package com.xiaotu.advertiser.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.project.service.BookMarksService;
import com.xiaotu.advertiser.project.service.PlayContentService;
import com.xiaotu.advertiser.project.service.PlayRoundService;

/**
 * 剧本内容管理
 * @author xuchangjian 2017年6月21日上午11:08:16
 */
@RestController
@RequestMapping("/playContent")
public class PlayContentController {
	
	@Autowired
	private PlayContentService playContentService;
	
	@Autowired
	private BookMarksService bookMarksService;
	
	@Autowired
	private PlayRoundService playRoundService;

	/**
	 * 查询场次的剧本内容
	 * @author xuchangjian 2017年6月23日上午11:53:13
	 * @param roundId
	 * @return
	 */
	@RequestMapping("/queryPlayContent")
	public Object queryPlayContent(String roundId)
	{
		//保存书签信息
		this.bookMarksService.saveBookMarks(1, roundId);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roundId", roundId);
		
		return this.playContentService.get("selectPlayContent", param);
	}
	
	/**
	 * 下载剧本
	 * @author xuchangjian 2017年6月29日上午10:24:27
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/downloadPlay")
	public Object downloadPlay(HttpServletResponse response) throws IOException
	{
		this.playContentService.downloadPlay(response);
		return null;
	}
	
	/**
	 * 提取剧本中的角色
	 * @return	角色列表
	 * @throws Exception 
	 */
	@RequestMapping("/extractRoleList")
	public Object extractRoleList() throws Exception 
	{
		return this.playContentService.saveExtractedRoleList();
	}
	
	/**
	 * 查询剧本列表
	 * @return	角色列表
	 */
	@RequestMapping("/queryPlayContentMap")
	public Object queryPlayContentMap()
	{
		List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> roundResult = this.playRoundService.queryRoundListWithContent(null);
		List<Map<String, Object>> roundList = (List<Map<String, Object>>) roundResult.get("roundList");
		
		for (Map<String, Object> roundMap : roundList) {
			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("roundId", roundMap.get("id"));
			
			List<Map<String, Object>> roleList = (List<Map<String, Object>>) roundMap.get("roleList");
			List<String> roleNameList = new ArrayList<String>();
			for (Map<String, Object> roleMap : roleList) {
				roleNameList.add((String) roleMap.get("name"));
			}
			
			contentMap.put("roleList", roleNameList);
			contentMap.put("content", roundMap.get("content"));
			
			contentList.add(contentMap);
		}
		
		return contentList;
	}
	
	/**
	 * 临时接口
	 * @author xuchangjian 2017年9月4日下午4:08:39
	 * @return
	 */
	@RequestMapping("/tmp")
	public Object tmp() {
		List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
    	
		Map<String, Object> roundResult = this.playRoundService.queryRoundListWithContent(null);
		List<Map<String, Object>> roundList = (List<Map<String, Object>>) roundResult.get("roundList");
		for (Map<String, Object> roundMap : roundList) 
		{
			//只取出角色名称字段
			List<Map<String, Object>> roleList = (List<Map<String, Object>>) roundMap.get("roleList");
			List<String> roleNameList = new ArrayList<String>();
			for (Map<String, Object> roleMap : roleList) {
				roleNameList.add((String) roleMap.get("name"));
			}
			
			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("roundId", roundMap.get("id"));
			contentMap.put("roleList", roleNameList);
			contentMap.put("content", roundMap.get("content"));
			contentList.add(contentMap);
		}
		
		return contentList;
	}
}
