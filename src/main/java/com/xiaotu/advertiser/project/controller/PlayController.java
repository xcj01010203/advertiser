package com.xiaotu.advertiser.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xiaotu.advertiser.project.model.PlayFormatModel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.advertiser.project.service.PlayService;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.util.SessionUtil;

/**
 * 剧本管理
 * @author xuchangjian 2017年6月21日上午11:08:16
 */
@RestController
@RequestMapping("/play")
public class PlayController {
	
	@Autowired
	private PlayService playService;

	/**
	 * 上传剧本
	 * @author xuchangjian 2017年8月31日下午3:49:43
	 * @param file	文件
	 * @param wordCount	每行显示字数
	 * @param lineCount	每页显示行数
	 * @param pageIncludeTitle	计算页数是否包含标题
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadPlay")
	public Object uploadPlay(MultipartFile file, Integer wordCount, Integer lineCount, Boolean pageIncludeTitle) throws Exception
	{
		return this.playService.savePlay(file, wordCount, lineCount, pageIncludeTitle);
	}
	
	/**
	 * 查询剧本列表
	 * @author xuchangjian 2017年7月26日上午11:55:19
	 * @param page
	 * @return
	 */
	@RequestMapping("/queryPlayList")
	public Object queryPlayList(Page page)
	{
		return this.playService.queryPlayList(page);
	}
	
	/**
	 * 重新分析页数
	 * @author xuchangjian 2017年8月31日下午3:48:44
	 * @param wordCount	每行显示字数
	 * @param lineCount	每页显示行数
	 * @param pageIncludeTitle	计算页数是否包含标题
	 * @return
	 */
	@RequestMapping("/refreshPage")
	public Object refreshPage(Integer wordCount, Integer lineCount, Boolean pageIncludeTitle)
	{
		this.playService.savePage(wordCount, lineCount, pageIncludeTitle);
		return null;
	}
	
	/**
	 * 查询剧本格式信息
	 * @author xuchangjian 2017年8月31日下午4:52:36
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/queryPlayFormat")
	public Object queryPlayFormat() throws Exception
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProjectModel project = SessionUtil.getSessionProject();
		PlayFormatModel playFormat = this.playService.get("PlayFormatMapper.selectByProjectId", project.getId());
		
		int lineCount = 40;
		int wordCount = 35;
		boolean pageIncludeTitle = true;
		if (playFormat != null) 
		{
			lineCount = playFormat.getLineCount();
			wordCount = playFormat.getWordCount();
			pageIncludeTitle = playFormat.getPageIncludeTitle();
		}
		
		resultMap.put("lineCount", lineCount);
		resultMap.put("wordCount", wordCount);
		resultMap.put("pageIncludeTitle", pageIncludeTitle);
		return resultMap;
	}
}
