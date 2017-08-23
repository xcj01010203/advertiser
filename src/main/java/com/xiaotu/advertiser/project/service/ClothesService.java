package com.xiaotu.advertiser.project.service;

import org.springframework.stereotype.Service;

import com.xiaotu.common.mvc.BaseService;

/**
 * 服装信息
 * @author xuchangjian 2017年6月23日下午2:00:13
 */
@Service
public class ClothesService extends BaseService{

	@Override
	protected String getKey() {
		return "ClothesMapper";
	}

}
