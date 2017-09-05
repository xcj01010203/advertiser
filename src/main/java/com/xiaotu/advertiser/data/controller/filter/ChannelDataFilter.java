package com.xiaotu.advertiser.data.controller.filter;

import java.util.List;

/**
 * 频道数据查询条件
 * @author xuchangjian 2017年9月1日下午4:01:56
 */
public class ChannelDataFilter {

	private Integer areaId;	//区域ID
	private Integer channelId;	//频道ID
	private String startDate;	//开始日期
	private String endDate;	//结束日期
	private String startTime;	//开始时间
	private String endTime;	//结束时间
	private List<Integer> subjectIdList;	//题材ID列表
	private Integer ageType;	//年龄类型，0：4+人群，1：4-14人群，2：15-24人群，3：25-34人群，4：35-44人群，5：45-54人群，6：55-64人群，7：65+人群
	private Integer sexType;	//性别类型，1：男，2：女
	private Integer eduType;	//教育程度类型，1：未受过正规教育，2：小学，3：初中，4：高中，5：大学及以上
	private Integer earnType;	//收入类型，1：0-2000，2：2000-4000，3：4000-6000，4：6000+
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<Integer> getSubjectIdList() {
		return subjectIdList;
	}
	public void setSubjectIdList(List<Integer> subjectIdList) {
		this.subjectIdList = subjectIdList;
	}
	public Integer getAgeType() {
		return ageType;
	}
	public void setAgeType(Integer ageType) {
		this.ageType = ageType;
	}
	public Integer getSexType() {
		return sexType;
	}
	public void setSexType(Integer sexType) {
		this.sexType = sexType;
	}
	public Integer getEduType() {
		return eduType;
	}
	public void setEduType(Integer eduType) {
		this.eduType = eduType;
	}
	public Integer getEarnType() {
		return earnType;
	}
	public void setEarnType(Integer earnType) {
		this.earnType = earnType;
	}
}
