package com.xiaotu.advertiser.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.project.service.PlayRoundTmpService;

/**
 * 需要提示替换或跳过的场次数据
 * @author xuchangjian 2017年6月28日上午10:53:32
 */
@RestController
@RequestMapping("/playRoundTmp")
public class PlayRoundTmpController {

	@Autowired
	private PlayRoundTmpService playRoundTmpService;
	
	/**
	 * 检查是否有需要跳过或替换的数据
	 * @author xuchangjian 2017年6月28日上午10:55:22
	 * @return
	 */
	@RequestMapping("/chackHasSkipOrReplaceData")
	public Object chackHasSkipOrReplaceData()
	{
		return this.playRoundTmpService.chackHasSkipOrReplaceData();
	}
	
	/**
	 * 获取需要跳过或替换的集场号列表
	 * @author xuchangjian 2017年6月28日上午11:00:27
	 * @return
	 */
	@RequestMapping("/queryRoundTmpList")
	public Object queryRoundTmpList()
	{
		return this.playRoundTmpService.queryRoundTmpList();
	}
	
	/**
	 * 查询单个需要跳过或替换的数据（带有原场景信息）
	 * @author xuchangjian 2017年6月28日上午11:10:13
	 * @return
	 */
	@RequestMapping("/queryRoundTmpDetail")
	public Object queryRoundTmpDetail(String id)
	{
		return this.playRoundTmpService.queryRoundTmpDetail(id);
	}
	
	/**
	 * 只替换剧本
	 * @author xuchangjian 2017年6月28日下午2:15:39
	 * @param idList
	 * @return
	 */
	@RequestMapping("/replaceContent")
	public Object replaceContent(@RequestParam(required=false, value="idList[]") List<String> idList)
	{
		this.playRoundTmpService.updateContentByRoundTmp(idList);
		return null;
	}
	
	/**
	 * 场次和剧本全替换
	 * @author xuchangjian 2017年6月28日下午3:48:23
	 * @param idList
	 * @return
	 */
	@RequestMapping("/replaceAll")
	public Object replaceAll(@RequestParam(required=false, value="idList[]") List<String> idList)
	{
		this.playRoundTmpService.updateAllByRoundTmp(idList);
		return null;
	}
	
	/**
	 * 跳过
	 * @author xuchangjian 2017年6月28日下午4:00:34
	 * @param idList
	 * @return
	 */
	@RequestMapping("/skip")
	public Object skip(@RequestParam(required=false, value="idList[]") List<String> idList)
	{
		this.playRoundTmpService.removeByIds(idList);
		return null;
	}
	
	/**
	 * 全部跳过
	 * @author xuchangjian 2017年6月28日下午4:01:21
	 * @param idList
	 * @return
	 */
	@RequestMapping("/skipAll")
	public Object skipAll()
	{
		this.playRoundTmpService.removeByIds(null);
		return null;
	}
}
