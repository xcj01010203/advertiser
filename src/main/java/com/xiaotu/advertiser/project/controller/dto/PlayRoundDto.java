package com.xiaotu.advertiser.project.controller.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 场次信息
 * @author xuchangjian 2017年6月27日下午5:23:33
 */
public class PlayRoundDto {

	private String id;
	private Integer seriesNo;	//集次
	private String roundNo;	//场次
	private String atmosphere;	//气氛
	private String site;	//内外景
	private String firstLocation;	//一级场景
	private List<String> majorRoleNameList = new ArrayList<String>();	//主要角色列表
	private List<String> guestRoleNameList = new ArrayList<String>();	//特约角色列表
	private List<String> massRoleNameList = new ArrayList<String>();	//群众角色列表
	private List<String> clothesNameList = new ArrayList<String>();	//服装列表
	private List<String> propNameList = new ArrayList<String>();	//道具列表
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getSeriesNo() {
		return seriesNo;
	}
	public void setSeriesNo(Integer seriesNo) {
		this.seriesNo = seriesNo;
	}
	public String getRoundNo() {
		return roundNo;
	}
	public void setRoundNo(String roundNo) {
		this.roundNo = roundNo;
	}
	public String getAtmosphere() {
		return atmosphere;
	}
	public void setAtmosphere(String atmosphere) {
		this.atmosphere = atmosphere;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getFirstLocation() {
		return firstLocation;
	}
	public void setFirstLocation(String firstLocation) {
		this.firstLocation = firstLocation;
	}
	public List<String> getMajorRoleNameList() {
		return majorRoleNameList;
	}
	public void setMajorRoleNameList(List<String> majorRoleNameList) {
		this.majorRoleNameList = majorRoleNameList;
	}
	public List<String> getGuestRoleNameList() {
		return guestRoleNameList;
	}
	public void setGuestRoleNameList(List<String> guestRoleNameList) {
		this.guestRoleNameList = guestRoleNameList;
	}
	public List<String> getMassRoleNameList() {
		return massRoleNameList;
	}
	public void setMassRoleNameList(List<String> massRoleNameList) {
		this.massRoleNameList = massRoleNameList;
	}
	public List<String> getClothesNameList() {
		return clothesNameList;
	}
	public void setClothesNameList(List<String> clothesNameList) {
		this.clothesNameList = clothesNameList;
	}
	public List<String> getPropNameList() {
		return propNameList;
	}
	public void setPropNameList(List<String> propNameList) {
		this.propNameList = propNameList;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
