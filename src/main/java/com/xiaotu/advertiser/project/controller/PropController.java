package com.xiaotu.advertiser.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.project.service.PropService;
import com.xiaotu.common.db.Page;

/**
 * 场次中道具
 * @author xuchangjian 2017年6月29日下午6:29:36
 */
@RestController
@RequestMapping("/prop")
public class PropController {

	@Autowired
	private PropService propService;
	
	/**
	 * 获取道具列表
	 * @author xuchangjian 2017年6月30日上午11:14:59
	 * @return
	 */
	@RequestMapping("/queryPropList")
	public Object queryPropList(Page page)
	{
		return this.propService.queryPropList(page);
	}
}
