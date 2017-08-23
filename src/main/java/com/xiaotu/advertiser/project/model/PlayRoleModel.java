package com.xiaotu.advertiser.project.model;

import java.util.Date;

/**
 * 场景角色信息
 * @author xuchangjian 2017-6-19下午4:47:31
 */
public class PlayRoleModel
{
	/*
	 * 角色类型常量
	 */
	public static final int MIAN_ROLE_TYPE = 1;	//主要角色
	public static final int GUEST_ROLE_TYPE = 2;	//特约角色
	public static final int MASS_ROLE_TYPE = 3;	//群众角色
	
	private String id;
	private ProjectModel project; // 项目
	private String name; // 角色名称
	private Integer type; // 场景角色类型。1：主要角色；2：特约角色；3：群众角色
	private Integer order; // 用于排序的序号
	private Date createTime; // 创建时间
	private Date lastUpdateTime; // 最后修改时间
	
	public PlayRoleModel()
	{
		super();
	}

	public PlayRoleModel(String id, ProjectModel project, String name)
	{
		super();
		this.id = id;
		this.project = project;
		this.name = name;
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
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Integer getType()
	{
		return type;
	}
	
	public void setType(Integer type)
	{
		this.type = type;
	}
	
	public Integer getOrder()
	{
		return order;
	}
	
	public void setOrder(Integer order)
	{
		this.order = order;
	}
	
	public Date getCreateTime()
	{
		return createTime;
	}
	
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	
	public Date getLastUpdateTime()
	{
		return lastUpdateTime;
	}
	
	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayRoleModel other = (PlayRoleModel) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (project == null)
		{
			if (other.project != null)
				return false;
		}
		else if (!project.equals(other.project))
			return false;
		return true;
	}
	
}
