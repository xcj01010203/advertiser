package com.xiaotu.advertiser.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xiaotu.advertiser.project.service.PlayService;
import com.xiaotu.common.db.Page;

/**
 * 剧本管理
 * @author xuchangjian 2017年6月21日上午11:08:16
 */
@RestController
@RequestMapping("/play")
public class PlayController {
	
	@Autowired
	private PlayService payService;

	@RequestMapping("/uploadPlay")
	public Object uploadPlay(MultipartFile file) throws Exception
	{
		return this.payService.savePlay(file);
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
		return this.payService.queryPlayList(page);
	}
}
