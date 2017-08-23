package com.xiaotu.advertiser.project.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.SessionUtil;
import com.xiaotu.common.util.ThreadPool;

/**
 * 项目信息
 * @author xuchangjian 2017年6月19日下午6:53:45
 */
@Service
public class ProjectService extends BaseService {
	
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
	 */
	public ProjectModel saveProject(HttpServletRequest request, ProjectModel project)
	{
		if (StringUtils.isBlank(project.getName())) 
		{
			throw new BusinessException("项目名称不可为空");
		}
		if (project.getType() == null)
		{
			throw new BusinessException("项目类型不可为空");
		}
		
		//获取当前登录用户信息
		UserModel sessionUser = (UserModel) request.getSession().getAttribute(Constants.SESSION_USER);

		//如果id为空，则调用新增的方法，否则调用更新的方法
		if (StringUtils.isBlank(project.getId())) 
		{
			project.setUser(sessionUser);
			project.setCreateTime(new Date());
			this.save("insertProject", project);
		}
		else
		{
			this.save("updateProject", project);
		}
		
		return project;
	}
	
	/**
	 * 删除项目信息
	 * @author xuchangjian 2017年6月20日下午3:53:59
	 * @param id	项目ID
	 */
	public void removeProject(String id)
	{
		if (StringUtils.isBlank(id))
		{
			throw new BusinessException("ID不能为空");
		}
		
		// 删除分析结果
		this.delete("ImplantAnalyseResultMapper.deleteMapByProject", id);
		this.delete("ImplantAnalyseResultMapper.deleteWordByProject", id);
		this.delete("ImplantAnalyseResultMapper.deleteResultByProject", id);

		this.delete("ImplantAnalyseJobMapper.deleteByProjectId", id);
		
		this.delete("RoleAnalyseResultMapper.deleteByProjectId", id);
		
		//删除场次和角色的关联关系
		this.delete("PlayRoundRoleMapMapper.deleteByProjectId", id);
		
		//删除场次和道具的关联关系
		this.delete("PlayRoundPropMapMapper.deleteByProjectId", id);
		
		//删除场次和服装的关联关系
		this.delete("PlayRoundClothesMapMapper.deleteByProjectId", id);
		
		//删除剧本内容
		this.delete("PlayContentMapper.deleteByProjectId", id);
		
		//删除剧本标记和品类的关联
		this.delete("PlayMarkGoodsMapMapper.deleteByProjectId", id);
		
		//删除剧本标记和角色的关联
		this.delete("PlayMarkRoleMapMapper.deleteByProjectId", id);
		
		//删除剧本标记信息
		this.delete("PlayMarkMapper.deleteByProjectId", id);
		
		//删除场次临时信息
		this.delete("PlayRoundTmpMapper.deleteByProjectId", id);
		
		//删除道具
		this.delete("PropMapper.deleteByProjectId", id);
		
		//删除服装
		this.delete("ClothesMapper.deleteByProjectId", id);
		
		//删除书签
		this.delete("BookMarksMapper.deleteByProjectId", id);
		
		//删除植入广告记录和角色的关联
		this.delete("ImplantRoleMapMapper.deleteByProjectId", id);
		
		//删除植入广告记录
		this.delete("ImplantRecordMapper.deleteByProjectId", id);
		
		//删除角色
		this.delete("PlayRoleMapper.deleteByProjectId", id);
		
		//删除场次
		this.delete("PlayRoundMapper.deleteByProjectId", id);
		
		//删除剧本
		ThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					//删除服务器上的剧本文件
					List<Map<String, Object>> playList = getList("PlayMapper.selectPlay", id);
					for (Map<String, Object> playInfo : playList) {
						String storePath = (String) playInfo.get("storePath");
						File file = new File(storePath);
						if (file.exists()) {
							file.delete();
						}
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
		
		//删除剧本
		this.delete("PlayMapper.deleteByProjectId", id);
		
		//删除项目
		this.delete("deleteById", id);
	}
	
	public int getProjectCount(){
		//获取当前登录用户信息
		UserModel sessionUser = SessionUtil.getSessionUser();
		//校验是否有重名
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("createUserId", sessionUser.getId());
		return this.get("selectProjectCount", paramMap);
	} 
	
	public int getProjectSucessCount(){
		//获取当前登录用户信息
		UserModel sessionUser = SessionUtil.getSessionUser();
		//校验是否有重名
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("createUserId", sessionUser.getId());
		return this.get("selectProjectSucessCount", paramMap);
	} 
	
	
	
	@Override
	protected String getKey() 
	{
		return "ProjectMapper";
	}
}
