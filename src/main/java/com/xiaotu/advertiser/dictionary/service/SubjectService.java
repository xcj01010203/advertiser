package com.xiaotu.advertiser.dictionary.service;

import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.dictionary.model.SubjectModel;
import com.xiaotu.common.mvc.BaseService;

/**
 * 题材
 * @author xuchangjian 2017年6月20日上午11:54:52
 */
@Service
public class SubjectService extends BaseService {

	/**
	 * 根据ID查找题材信息
	 * @author xuchangjian 2017年6月20日上午11:59:08
	 * @param id
	 * @return
	 */
	public SubjectModel getById(String id)
	{
		return this.get("getById", id);
	}

	@Override
	protected String getKey() {
		return "SubjectMapper";
	}
}
