package com.xiaotu.advertiser.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.advertiser.project.service.ProjectService;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.util.SessionUtil;

/**
 * 项目信息
 * @author xuchangjian 2017年6月19日下午6:34:47
 */
@RestController
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;

	/**
	 * 保存项目信息
	 * @author xuchangjian 2017年6月20日上午11:47:14
	 * @param id	
	 * @param name	名称
	 * @param type	类型  1-电视剧  2-电影  3-网剧  6-网大
	 * @param subject	题材
	 * @param company	制片公司
	 * @param majoractors	主演
	 * @param director	导演
	 * @param playwriter	编剧
	 * @param producer	制片人
	 * @param introduction	简介
	 * @return
	 */
	@RequestMapping("/saveProject")
	public Object saveProject(HttpServletRequest request, ProjectModel project)
	{
		return this.projectService.saveProject(request, project);
	}
	
	/**
	 * 获取项目列表
	 * @author xuchangjian 2017年6月19日下午6:48:23
	 * @param request
	 * @param page	分页信息
	 * @param type	项目类型, 1-电视剧  2-电影  3-网剧  6-网大
	 * @return
	 */
	@RequestMapping("/queryProjectList")
	public Object queryProjectList(HttpSession session, Page page, Integer type, String name)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//获取当前登录用户信息
		UserModel sessionUser = SessionUtil.getSessionUser();
		
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("createUserId", sessionUser.getId());
		parameterMap.put("type", type);
		parameterMap.put("likeName", name);
		List<ProjectModel> projectList = this.projectService.queryPageList("selectProject", parameterMap, page);
		
		//把session中项目信息置空
		SessionUtil.setSession(session, Constants.SESSION_PROJECT, null);
		
		resultMap.put("projectList", projectList);
		resultMap.put("totalPage", page.getTotalPage());
		resultMap.put("totalRows", page.getTotalRows());
		return resultMap;
	}
	
	/**
	 * 删除项目 
	 * @author xuchangjian 2017年6月19日下午6:48:52
	 * @return
	 */
	@RequestMapping("/removeProject")
	public Object removeProject(String id)
	{
		this.projectService.removeProject(id);
		return null;
	}
	
	/**
	 * 获取项目详细信息
	 * @author xuchangjian 2017年6月19日下午6:49:31
	 * @return
	 */
	@RequestMapping("/queryProjectDetail")
	public Object queryProjectDetail(HttpServletRequest request)
	{
		ProjectModel sessionProject = SessionUtil.getSessionProject();
		ProjectModel project = this.projectService.get("ProjectMapper.selectProjectDetail", sessionProject.getId());
		return project;
	}
	
	/**
	 * 进入项目
	 * @author xuchangjian 2017年6月23日上午9:31:22
	 * @param id
	 * @return
	 */
	@RequestMapping("/enterProject")
	public Object enterProject(HttpSession session, String id)
	{
		if (StringUtils.isBlank(id)) 
		{
			throw new BusinessException("ID不能为空");
		}
		ProjectModel project = this.projectService.get("selectProjectDetail", id);
		session.removeAttribute(Constants.SESSION_PROJECT);
		SessionUtil.setSession(session, Constants.SESSION_PROJECT, project);
		return null;
	}
}
