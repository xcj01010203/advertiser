package com.xiaotu.advertiser.project.model;

import java.util.Date;

/**
 * 场次信息
 * @author xuchangjian 2017-6-19下午4:38:31
 */
public class PlayRoundModel
{
	
	private String id;
	private ProjectModel project; // 项目
	private Integer seriesNo; // 集次
	private String roundNo; // 场次
	private String atmosphere; // 气氛
	private String site; // 内外景
	private String firstLocation; // 主场景
	private String remark; // 备注
	private Date createTime; // 创建时间
	private Date lastUpdateTime; // 最后修改时间
	private boolean isManualSaved; // 是否已手动保存
	private double pageCount;	//页数
	
	public PlayRoundModel()
	{
		super();
	}
	
	/**
	 * @param project
	 * @param seriesNo
	 * @param roundNo
	 */
	public PlayRoundModel(String id, ProjectModel project, Integer seriesNo,
			String roundNo)
	{
		super();
		this.id = id;
		this.project = project;
		this.seriesNo = seriesNo;
		this.roundNo = roundNo;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((roundNo == null) ? 0 : roundNo.hashCode());
		result = prime * result
				+ ((seriesNo == null) ? 0 : seriesNo.hashCode());
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
		PlayRoundModel other = (PlayRoundModel) obj;
		if (project == null)
		{
			if (other.project != null)
				return false;
		}
		else if (!project.equals(other.project))
			return false;
		if (roundNo == null)
		{
			if (other.roundNo != null)
				return false;
		}
		else if (!roundNo.equals(other.roundNo))
			return false;
		if (seriesNo == null)
		{
			if (other.seriesNo != null)
				return false;
		}
		else if (!seriesNo.equals(other.seriesNo))
			return false;
		return true;
	}
	
	public double getPageCount() {
		return pageCount;
	}

	public void setPageCount(double pageCount) {
		this.pageCount = pageCount;
	}

	public boolean getIsManualSaved()
	{
		return isManualSaved;
	}
	
	public void setIsManualSaved(boolean isManualSaved)
	{
		this.isManualSaved = isManualSaved;
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
	
	public Integer getSeriesNo()
	{
		return seriesNo;
	}
	
	public void setSeriesNo(Integer seriesNo)
	{
		this.seriesNo = seriesNo;
	}
	
	public String getRoundNo()
	{
		return roundNo;
	}
	
	public void setRoundNo(String roundNo)
	{
		this.roundNo = roundNo;
	}
	
	public String getAtmosphere()
	{
		return atmosphere;
	}
	
	public void setAtmosphere(String atmosphere)
	{
		this.atmosphere = atmosphere;
	}
	
	public String getSite()
	{
		return site;
	}
	
	public void setSite(String site)
	{
		this.site = site;
	}
	
	public String getFirstLocation()
	{
		return firstLocation;
	}
	
	public void setFirstLocation(String firstLocation)
	{
		this.firstLocation = firstLocation;
	}
	
	public String getRemark()
	{
		return remark;
	}
	
	public void setRemark(String remark)
	{
		this.remark = remark;
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
}
