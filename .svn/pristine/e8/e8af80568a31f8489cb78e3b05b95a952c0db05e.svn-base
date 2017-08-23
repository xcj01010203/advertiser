package com.xiaotu.advertiser.implant.model.map;

import com.xiaotu.advertiser.implant.model.AnalysisWordModel;
import com.xiaotu.advertiser.project.model.PlayRoleModel;

/**
 * @类名 AnalyseWordRoleMap
 * @日期 2017年7月7日
 * @作者 高海军
 * @功能 自动分析结果-关联词与角色映射实体类
 */
public class AnalysisWordRoleMapModel
{
	private String id;
	private PlayRoleModel role;
	private AnalysisWordModel word;
	
	public AnalysisWordRoleMapModel()
	{
		super();
	}

	public AnalysisWordRoleMapModel(PlayRoleModel role, AnalysisWordModel word)
	{
		super();
		this.role = role;
		this.word = word;
	}

	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public PlayRoleModel getRole()
	{
		return role;
	}
	
	public void setRole(PlayRoleModel role)
	{
		this.role = role;
	}
	
	public AnalysisWordModel getWord()
	{
		return word;
	}
	
	public void setWord(AnalysisWordModel word)
	{
		this.word = word;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
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
		AnalysisWordRoleMapModel other = (AnalysisWordRoleMapModel) obj;
		if (role == null)
		{
			if (other.role != null)
				return false;
		}
		else if (!role.equals(other.role))
			return false;
		if (word == null)
		{
			if (other.word != null)
				return false;
		}
		else if (!word.equals(other.word))
			return false;
		return true;
	}
	
	
}
