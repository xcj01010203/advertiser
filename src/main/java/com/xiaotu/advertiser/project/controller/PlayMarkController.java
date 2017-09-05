package com.xiaotu.advertiser.project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.project.controller.dto.PlayMarkDto;
import com.xiaotu.advertiser.project.service.PlayMarkService;

/**
 * 剧本管理
 * @author xuchangjian 2017年6月21日上午11:08:16
 */
@RestController
@RequestMapping("/playMark")
public class PlayMarkController {

	@Autowired
	private PlayMarkService playMarkService;

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
	@RequestMapping("saveMark")
	public Object saveMark(@RequestBody PlayMarkDto playMarkDto)
	{
		this.playMarkService.saveMark(playMarkDto);
		return null;
	}
	
	/**
	 * 删除标记信息
	 * @author xuchangjian 2017年6月27日下午3:40:10
	 * @param id 标记ID
	 * @return
	 */
	@RequestMapping("removeMark")
	public Object removeMark(String id)
	{
		this.playMarkService.removeMark(id);
		return null;
	}
	
	/**
	 * 获取标记列表
	 * @author xuchangjian 2017年6月27日下午3:44:49
	 * @param roundId	场次ID
	 * @return
	 */
	@RequestMapping("queryMarkList")
	public Object queryMarkList(String roundId)
	{
		return this.playMarkService.queryMarkList(roundId);
	}
	
	/**
	 * 获取剧本标记列表
	 * @author wangyanlong 2017年8月21日
	 * @return
	 */
	@RequestMapping("queryScriptMarkList")
	public Object queryScriptMarkList(Integer pageSize, Integer currentPage)
	{
		return this.playMarkService.queryScriptMarkList(pageSize,currentPage);
	}
	
	/**
	 * 导出剧本标记列表
	 * @author wangyanlong 2017年9月2日
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@RequestMapping("queryExportMarkList")
	public Object queryExportMarkList(String roleNames,HttpServletResponse response) throws IllegalArgumentException, IllegalAccessException, IOException
	{
		 this.playMarkService.queryExportMarkList(roleNames,response);
		 return null;
	}
}
