package com.xiaotu.advertiser.project.controller.filter;

import java.util.List;

/**
 * 场次信息高级查询条件
 * @author xuchangjian 2017年8月28日下午4:06:47
 */
public class PlayRoundFilter {

	private Integer startSeriesNo;	//开始集次
	private String startRoundNo;	//开始场次
	private Integer endSeriesNo;	//结束集次
	private String endRoundNo;	//结束场次
	private String seriesRoundNos;	//集场编号，多个集场编号以锋号、顿号或逗号隔开，例：1-1；1-2,1-3
	private Integer seriesNo;	//集次
	private List<String> atmosphereList;	//气氛列表
	private List<String> siteList;	//内外景列表
	private List<String> firstLocationList;	//主场景列表
	private List<String> propIdList;	//道具ID列表
	private List<String> roleIdList;	//主要角色ID列表
	public Integer getStartSeriesNo() {
		return startSeriesNo;
	}
	public void setStartSeriesNo(Integer startSeriesNo) {
		this.startSeriesNo = startSeriesNo;
	}
	public String getStartRoundNo() {
		return startRoundNo;
	}
	public void setStartRoundNo(String startRoundNo) {
		this.startRoundNo = startRoundNo;
	}
	public Integer getEndSeriesNo() {
		return endSeriesNo;
	}
	public void setEndSeriesNo(Integer endSeriesNo) {
		this.endSeriesNo = endSeriesNo;
	}
	public String getEndRoundNo() {
		return endRoundNo;
	}
	public void setEndRoundNo(String endRoundNo) {
		this.endRoundNo = endRoundNo;
	}
	public String getSeriesRoundNos() {
		return seriesRoundNos;
	}
	public void setSeriesRoundNos(String seriesRoundNos) {
		this.seriesRoundNos = seriesRoundNos;
	}
	public Integer getSeriesNo() {
		return seriesNo;
	}
	public void setSeriesNo(Integer seriesNo) {
		this.seriesNo = seriesNo;
	}
	public List<String> getAtmosphereList() {
		return atmosphereList;
	}
	public void setAtmosphereList(List<String> atmosphereList) {
		this.atmosphereList = atmosphereList;
	}
	public List<String> getSiteList() {
		return siteList;
	}
	public void setSiteList(List<String> siteList) {
		this.siteList = siteList;
	}
	public List<String> getFirstLocationList() {
		return firstLocationList;
	}
	public void setFirstLocationList(List<String> firstLocationList) {
		this.firstLocationList = firstLocationList;
	}
	public List<String> getPropIdList() {
		return propIdList;
	}
	public void setPropIdList(List<String> propIdList) {
		this.propIdList = propIdList;
	}
	public List<String> getRoleIdList() {
		return roleIdList;
	}
	public void setRoleIdList(List<String> roleIdList) {
		this.roleIdList = roleIdList;
	}
}
