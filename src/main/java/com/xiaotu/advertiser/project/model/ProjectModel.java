package com.xiaotu.advertiser.project.model;

import java.util.Date;

import com.xiaotu.advertiser.dictionary.model.SubjectModel;
import com.xiaotu.advertiser.user.model.UserModel;

/**
 * 项目信息
 * @author xuchangjian 2017-6-19下午4:16:19
 */
public class ProjectModel
{
	
	private String id;
	private String name; // 名称
	private Integer type; // 类型， 1-电视剧 2-电影 3-网剧 6-网大
	private SubjectModel subject; // 题材
	private String company; // 制片公司
	private String majorActors; // 主演
	private String director; // 导演
	private String playWriter; // 编剧
	private String producer; // 制片
	private String introduction; // 简介
	private UserModel user; // 创建人
	private Date createTime; // 创建时间
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
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
	
	public SubjectModel getSubject()
	{
		return subject;
	}
	
	public void setSubject(SubjectModel subject)
	{
		this.subject = subject;
	}
	
	public String getCompany()
	{
		return company;
	}
	
	public void setCompany(String company)
	{
		this.company = company;
	}
	
	public String getMajorActors()
	{
		return majorActors;
	}
	
	public void setMajorActors(String majorActors)
	{
		this.majorActors = majorActors;
	}
	
	public String getDirector()
	{
		return director;
	}
	
	public void setDirector(String director)
	{
		this.director = director;
	}
	
	public String getPlayWriter()
	{
		return playWriter;
	}
	
	public void setPlayWriter(String playWriter)
	{
		this.playWriter = playWriter;
	}
	
	public String getProducer()
	{
		return producer;
	}
	
	public void setProducer(String producer)
	{
		this.producer = producer;
	}
	
	public String getIntroduction()
	{
		return introduction;
	}
	
	public void setIntroduction(String introduction)
	{
		this.introduction = introduction;
	}
	
	public UserModel getUser()
	{
		return user;
	}
	
	public void setUser(UserModel user)
	{
		this.user = user;
	}
	
	public Date getCreateTime()
	{
		return createTime;
	}
	
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProjectModel other = (ProjectModel) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
}
