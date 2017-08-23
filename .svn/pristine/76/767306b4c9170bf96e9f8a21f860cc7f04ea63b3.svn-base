package com.xiaotu.advertiser.project.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.project.model.BookMarksModel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.SessionUtil;

/**
 * 书签信息
 * @author xuchangjian 2017年6月23日上午10:45:05
 */
@Service
public class BookMarksService extends BaseService {

	@Override
	protected String getKey() {
		return "BookMarksMapper";
	}
	
	
	/**
	 * 保存书签信息
	 * @author xuchangjian 2017年7月31日上午10:50:44
	 * @param type	书签类型，1-剧本
	 * @param value	书签值
	 */
	public void saveBookMarks(int type, String value)
	{
		ProjectModel project = SessionUtil.getSessionProject();
		UserModel user = SessionUtil.getSessionUser();
		
		//先删除已有书签
		Map<String, Object> parmas = new HashMap<String, Object>();
		parmas.put("projectId", project.getId());
		parmas.put("type", type);
		parmas.put("userId", user.getId());
		this.delete("deleteBookMark", parmas);
		
		BookMarksModel mark = new BookMarksModel();
		mark.setProject(project);
		mark.setUser(user);
		mark.setType(type);
		mark.setValue(value);
		this.save("insertOne", mark);
	}
}
