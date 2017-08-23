package com.xiaotu.advertiser.implant.model;

/**
 * @类名 AnalysisWordModel
 * @日期 2017年7月7日
 * @作者 高海军
 * @功能 自动分析结果-关联词实体类
 */
public class AnalysisWordModel
{
	private String id;
	private AnalysisResultModel result;
	private String word;
	private int position;
	private double weight;
	
	public AnalysisWordModel()
	{
		super();
	}
	
	/**
	 * @param id
	 * @param result
	 * @param word
	 * @param weight
	 */
	public AnalysisWordModel(AnalysisResultModel result, String word,
			int position, double weight)
	{
		super();
		this.result = result;
		this.word = word;
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
	
	public AnalysisResultModel getResult()
	{
		return result;
	}
	
	public void setResult(AnalysisResultModel result)
	{
		this.result = result;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public void setWord(String word)
	{
		this.word = word;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + position;
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
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
		AnalysisWordModel other = (AnalysisWordModel) obj;
		if (position != other.position)
			return false;
		if (result == null)
		{
			if (other.result != null)
				return false;
		}
		else if (!result.equals(other.result))
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
