package com.xiaotu.advertiser.dictionary.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.dictionary.service.SubjectService;

/**
 * 题材
 * @author xuchangjian 2017年6月23日下午3:06:52
 */
@RestController
 @RequestMapping("subject")
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;

	/**
	 * 获取题材列表
	 * @author xuchangjian 2017年6月23日下午3:06:14
	 * @return
	 */
	@RequestMapping("/querySubjectList")
	public Object querySubjectList()
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("subjectList", this.subjectService.getList("selectSubject"));
		return resultMap;
	}
}
