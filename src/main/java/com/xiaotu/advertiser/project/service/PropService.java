package com.xiaotu.advertiser.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.SessionUtil;

/**
 * 道具
 * @author xuchangjian 2017年6月23日下午2:21:18
 */
@Service
public class PropService extends BaseService {

	@Override
	protected String getKey() {
		return "PropMapper";
	}

	/**
	 * 获取道具列表
	 * @author xuchangjian 2017年6月30日上午11:14:59
	 * @return
	 */
	public Object queryPropList(Page page)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProjectModel project = SessionUtil.getSessionProject();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", project.getId());
		
		List<Map<String, Object>> propList = this.queryPageList("selectPropListWithRoundInfo", params, page);
		for (Map<String, Object> prop : propList)
		{
			String seriesRoundNos = (String) prop.get("seriesRoundNos");
			if (!StringUtils.isBlank(seriesRoundNos))
			{
				String firstRound = seriesRoundNos.split(",")[0];
				prop.put("firstRound", firstRound);
				prop.remove("seriesRoundNos");
			}
		}
		
		resultMap.put("propList", propList);
		resultMap.put("totalRows", page.getTotalRows());
		resultMap.put("totalPage", page.getTotalPage());
		return resultMap;
	}
}
