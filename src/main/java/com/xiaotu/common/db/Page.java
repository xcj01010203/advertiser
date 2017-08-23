package com.xiaotu.common.db;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

@SuppressWarnings("serial")
public class Page extends RowBounds implements Serializable
{
	
	public static final int DEFAULT_PAGESIZE = 20;
	private int pageSize = DEFAULT_PAGESIZE;
	private int currentPage = 1;
	private int totalRows = 0;
	private int totalPage = 0;
	private List<?> result;
	
	public Page()
	{
	}
	
	public Page(int pageSize, int currentPage)
	{
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}
	
	public Page(List<?> result, int pageSize, int currentPage, int totalRows)
	{
		this.result = result;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.setTotalRows(totalRows);
	}
	
	public int getOffset(int currentPage, int pageSize)
	{
		if (currentPage <= 0)
		{
			currentPage = 1;
		}
		if (pageSize <= 0)
		{
			pageSize = this.pageSize;
		}
		return (currentPage - 1) * pageSize;
	}
	
	public List<?> getResult()
	{
		return result;
	}
	
	public void setResult(List<?> result)
	{
		this.result = result;
	}
	
	public int getPageSize()
	{
		return pageSize;
	}
	
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
	
	public int getCurrentPage()
	{
		int totalPage = getTotalPage();
		if (this.currentPage > totalPage)
		{
			// this.setCurrentPage(totalPage);
		}
		else if (this.currentPage < 1)
		{
			this.setCurrentPage(1);
		}
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}
	
	public int getTotalRows()
	{
		return totalRows;
	}
	
	public void setTotalRows(int totalRows)
	{
		this.totalRows = totalRows;
		if (this.getTotalRows() > 0)
		{
			if (this.getTotalRows() % this.getPageSize() == 0)
			{
				this.totalPage = this.getTotalRows() / this.getPageSize();
			}
			else
			{
				this.totalPage = this.getTotalRows() / this.getPageSize() + 1;
			}
		}
	}
	
	public int getTotalPage()
	{
		
		return this.totalPage;
	}
	
}
