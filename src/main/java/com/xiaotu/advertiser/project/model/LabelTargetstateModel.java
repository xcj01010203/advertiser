package com.xiaotu.advertiser.project.model;

/**
 * @类名 LabelTargetstateModel
 * @日期 2017年9月5日
 * @作者 王艳龙
 * @功能 自动分析标签实体类
 */
public class LabelTargetstateModel
{
	private String id;
	private ProjectModel project;
	private long startTime;
	private long endTime;
	private String targetState;
	
	public LabelTargetstateModel()
	{
		super();
	}
	
	public LabelTargetstateModel(ProjectModel project)
	{
		super();
		this.project = project;
		this.startTime = System.currentTimeMillis();
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
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

	public String getTargetState() {
		return targetState;
	}

	public void setTargetState(String targetState) {
		this.targetState = targetState;
	}
}
