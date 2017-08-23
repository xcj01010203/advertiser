package com.xiaotu.advertiser.dictionary.model;

/**
 * @类名 GoodsModel
 * @日期 2017年6月16日
 * @作者 高海军
 * @功能 品类实体类
 */
public class GoodsModel
{
	private String id;
	private String goods;
	
	public GoodsModel()
	{
		super();
	}
	
	/**
	 * @param id
	 * @param goods
	 */
	public GoodsModel(String id, String goods)
	{
		super();
		this.id = id;
		this.goods = goods;
	}


	public GoodsModel(String goods)
	{
		super();
		this.goods = goods;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getGoods()
	{
		return goods;
	}
	
	public void setGoods(String goods)
	{
		this.goods = goods;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goods == null) ? 0 : goods.hashCode());
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
		GoodsModel other = (GoodsModel) obj;
		if (goods == null)
		{
			if (other.goods != null)
				return false;
		}
		else if (!goods.equals(other.goods))
			return false;
		return true;
	}
}
