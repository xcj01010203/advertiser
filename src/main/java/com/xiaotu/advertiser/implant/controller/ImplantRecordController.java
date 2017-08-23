package com.xiaotu.advertiser.implant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.implant.controller.dto.ImplantRecordDto;
import com.xiaotu.advertiser.implant.service.ImplantRecordService;

/**
 * 广告植入
 * @author xuchangjian 2017年6月27日下午4:30:06
 */
@RestController
@RequestMapping("/implantRecord")
public class ImplantRecordController {
	
	@Autowired
	private ImplantRecordService implantRecordService;

	/**
	 * 保存广告植入信息
	 * @author xuchangjian 2017年6月27日下午4:47:55
	 * @param record
	 * @return
	 */
	@RequestMapping("/saveRecord")
	public Object saveRecord(@RequestBody ImplantRecordDto recordDto)
	{
		this.implantRecordService.saveRecord(recordDto);
		return null;
	}
	
	/**
	 * 删除广告植入信息
	 * @author xuchangjian 2017年6月27日下午6:19:18
	 * @param id
	 * @return
	 */
	@RequestMapping("/removeRecord")
	public Object removeRecord(String id)
	{
		this.implantRecordService.removeRecord(id);
		return null;
	}
	
	/**
	 * 获取指定场次的广告植入列表
	 * @author xuchangjian 2017年6月27日下午6:25:36
	 * @param roundId
	 * @return
	 */
	@RequestMapping("/queryRecordList")
	public Object queryRecordList(String roundId, Integer pageSize, Integer currentPage)
	{
		return this.implantRecordService.queryRecordList(roundId, pageSize, currentPage);
	}
}
