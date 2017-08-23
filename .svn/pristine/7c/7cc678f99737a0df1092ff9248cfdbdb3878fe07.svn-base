package com.xiaotu.advertiser.implant.model;

import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.advertiser.user.model.UserModel;

/**
 * @类名 AnalysisJobModel
 * @日期 2017年7月7日
 * @作者 高海军
 * @功能 自动分析任务实体类
 */
public class AnalysisJobModel
{
	public static final int JOB_SUCCESS = 0;
	public static final int JOB_FAIL = 1;
	public static final int JOB_RUNNING = 2;
	
	private String id;
	private UserModel user;
	private ProjectModel project;
	private long startTime;
	private long endTime;
	private Integer status;
	private String message;
	
	public AnalysisJobModel()
	{
		super();
	}
	
	public AnalysisJobModel(UserModel user, ProjectModel project)
	{
		super();
		this.user = user;
		this.project = project;
		this.startTime = System.currentTimeMillis();
		this.status = JOB_RUNNING;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public UserModel getUser()
	{
		return user;
	}
	
	public void setUser(UserModel user)
	{
		this.user = user;
	}
	
	public ProjectModel getProject()
	{
		return project;
	}
	
	public void setProject(ProjectModel project)
	{
		this.project = project;
	}
	
	public long getStartTime()
	{
		return startTime;
	}
	
	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}
	
	public long getEndTime()
	{
		return endTime;
	}
	
	public void setEndTime(long endTime)
	{
		this.endTime = endTime;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
}
