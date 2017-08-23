package com.xiaotu.advertiser.dictionary.model;

/**
 * @类名 GoodsWokdMapModel
 * @日期 2017年6月16日
 * @作者 高海军
 * @功能 品类与关键词对应关系实体类
 */
public class GoodsWordMapModel
{
	private String id;
	private GoodsModel goods;
	private WordModel word;
	private double weight;
	
	public GoodsWordMapModel()
	{
		super();
	}

	/**
	 * @param goods
	 * @param word
	 * @param weight
	 */
	public GoodsWordMapModel(GoodsModel goods, WordModel word, double weight)
	{
		super();
		this.goods = goods;
		this.word = word;
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
	
	public GoodsModel getGoods()
	{
		return goods;
	}
	
	public void setGoods(GoodsModel goods)
	{
		this.goods = goods;
	}
	
	public WordModel getWord()
	{
		return word;
	}
	
	public void setWord(WordModel word)
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
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goods == null) ? 0 : goods.hashCode());
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
		GoodsWordMapModel other = (GoodsWordMapModel) obj;
		if (goods == null)
		{
			if (other.goods != null)
				return false;
		}
		else if (!goods.equals(other.goods))
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
