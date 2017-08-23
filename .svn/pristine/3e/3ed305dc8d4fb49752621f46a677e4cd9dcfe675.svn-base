package com.xiaotu.advertiser.dictionary.model;

/**
 * @类名 WordModel
 * @日期 2017年6月16日
 * @作者 高海军
 * @功能 关键词实体类
 */
public class WordModel
{
	private String id;
	private String word;
	
	public WordModel(String word)
	{
		super();
		this.word = word;
	}

	public WordModel()
	{
		super();
	}

	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public void setWord(String word)
	{
		this.word = word;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
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
		WordModel other = (WordModel) obj;
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
