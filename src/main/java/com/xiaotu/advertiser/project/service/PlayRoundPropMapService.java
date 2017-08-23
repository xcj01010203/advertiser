package com.xiaotu.advertiser.project.service;

import org.springframework.stereotype.Service;

import com.xiaotu.common.mvc.BaseService;

/**
 * 场次和道具关联关系
 * @author xuchangjian 2017年6月22日下午3:39:42
 */
@Service
public class PlayRoundPropMapService extends BaseService {

	@Override
	protected String getKey() {
		return "PlayRoundPropMapMapper";
	}

}
