package com.xiaotu.advertiser.project.controller.filter;

import java.util.List;

import org.codehaus.plexus.util.StringUtils;

/**
 * 场次信息高级查询条件
 * @author xuchangjian 2017年8月28日下午4:06:47
 */
public class PlayRoundFilter {

	private Integer startSeriesNo = 1;	//开始集次，默认为1
	private String startRoundNo = "1";	//开始场次，默认为1
	private Integer endSeriesNo;	//结束集次
	private String endRoundNo;	//结束场次
	private String seriesRoundNos;	//集场编号，多个集场编号以锋号、顿号或逗号隔开，例：1-1；1-2,1-3
	private Integer seriesNo;	//集次
	private List<String> atmosphereList;	//气氛列表
	private List<String> siteList;	//内外景列表
	private List<String> firstLocationList;	//主场景列表
	private List<String> propIdList;	//道具ID列表
	private List<String> majorRoleIdList;	//主要角色ID列表
	private Integer majorRoleSearchMode = 1;	//主要角色查询模式，1-出现即可，2-不出现，3-同时出现，4-不同时出现
	private List<String> guestRoleIdList;	//特约角色ID列表
	private List<String> massRoleIdList;	//群众角色ID列表
	private String roleId;	//角色ID，不区分角色类型
	private String propId;	//道具ID
	private Integer pageSize;
	private Integer currentPage;
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPropId() {
		return propId;
	}
	public void setPropId(String propId) {
		this.propId = propId;
	}
	public Integer getStartSeriesNo() {
		return startSeriesNo;
	}
	public void setStartSeriesNo(Integer startSeriesNo) {
		if (startSeriesNo == null) {
			startSeriesNo = 1;
		}
		this.startSeriesNo = startSeriesNo;
	}
	public String getStartRoundNo() {
		return startRoundNo;
	}
	public void setStartRoundNo(String startRoundNo) {
		if (StringUtils.isBlank(startRoundNo)) {
			startRoundNo = "1";
		}
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
	public List<String> getMajorRoleIdList() {
		return majorRoleIdList;
	}
	public void setMajorRoleIdList(List<String> majorRoleIdList) {
		this.majorRoleIdList = majorRoleIdList;
	}
	public Integer getMajorRoleSearchMode() {
		return majorRoleSearchMode;
	}
	public void setMajorRoleSearchMode(Integer majorRoleSearchMode) {
		if (majorRoleSearchMode == null) {
			majorRoleSearchMode = 1;
		}
		this.majorRoleSearchMode = majorRoleSearchMode;
	}
	public List<String> getGuestRoleIdList() {
		return guestRoleIdList;
	}
	public void setGuestRoleIdList(List<String> guestRoleIdList) {
		this.guestRoleIdList = guestRoleIdList;
	}
	public List<String> getMassRoleIdList() {
		return massRoleIdList;
	}
	public void setMassRoleIdList(List<String> massRoleIdList) {
		this.massRoleIdList = massRoleIdList;
	}
}
