package com.xiaotu.advertiser.project.service;

import org.springframework.stereotype.Service;

import com.xiaotu.common.mvc.BaseService;

/**
 * 场次和角色关联关系
 * @author xuchangjian 2017年6月22日下午2:01:38
 */
@Service
public class PlayRoundRoleMapService extends BaseService {

	@Override
	protected String getKey() {
		return "PlayRoundRoleMapMapper";
	}

}
