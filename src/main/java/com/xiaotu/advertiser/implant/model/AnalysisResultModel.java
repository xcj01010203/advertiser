package com.xiaotu.advertiser.implant.model;

import com.xiaotu.advertiser.dictionary.model.GoodsModel;
import com.xiaotu.advertiser.project.model.PlayRoundModel;
import com.xiaotu.advertiser.project.model.ProjectModel;

/**
 * @类名 AnalysisResultModel
 * @日期 2017年7月7日
 * @作者 高海军
 * @功能 自动分析结果实体类
 */
public class AnalysisResultModel
{
	public static final int POS_TALK = 0;
	public static final int POS_PLACE = 1;
	public static final int POS_ALL = 2;
	
	private String id;
	private ProjectModel project;
	private PlayRoundModel round;
	private GoodsModel goods;
	private int position;
	private double weight;
	
	public AnalysisResultModel()
	{
		super();
	}
	
	/**
	 * @param project
	 * @param round
	 * @param goods
	 * @param position
	 * @param weight
	 */
	public AnalysisResultModel(ProjectModel project, PlayRoundModel round,
			GoodsModel goods, int position, double weight)
	{
		super();
		this.project = project;
		this.round = round;
		this.goods = goods;
		this.position = position;
		this.weight = weight;
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
	
	public PlayRoundModel getRound()
	{
		return round;
	}
	
	public void setRound(PlayRoundModel round)
	{
		this.round = round;
	}
	
	public GoodsModel getGoods()
	{
		return goods;
	}
	
	public void setGoods(GoodsModel goods)
	{
		this.goods = goods;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goods == null) ? 0 : goods.hashCode());
		result = prime * result + ((round == null) ? 0 : round.hashCode());
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
		AnalysisResultModel other = (AnalysisResultModel) obj;
		if (goods == null)
		{
			if (other.goods != null)
				return false;
		}
		else if (!goods.equals(other.goods))
			return false;
		if (round == null)
		{
			if (other.round != null)
				return false;
		}
		else if (!round.equals(other.round))
			return false;
		return true;
	}
	
}
