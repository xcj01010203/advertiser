package com.xiaotu.advertiser.project.model;

import java.util.Date;

import com.xiaotu.advertiser.user.model.UserModel;

/**
 * 剧本信息
 * @author xuchangjian 2017年6月21日上午11:13:52
 */
public class PlayModel {

	private String id;
	private ProjectModel project;	//项目信息
	private String name;	//剧本名称（带后缀）
	private String storepath;	//剧本存储路径
	private Date uploadTime;	//上传时间
	private UserModel user;	//上传人
	private boolean succeed;	//是否成功
	private String uploadDesc;	//上传结果描述
	private String scriptRule;	//匹配的解析规则
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ProjectModel getProject() {
		return project;
	}
	public void setProject(ProjectModel project) {
		this.project = project;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStorepath() {
		return storepath;
	}
	public void setStorepath(String storepath) {
		this.storepath = storepath;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public boolean getSucceed() {
		return succeed;
	}
	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
	public String getUploadDesc() {
		return uploadDesc;
	}
	public void setUploadDesc(String uploadDesc) {
		this.uploadDesc = uploadDesc;
	}
	public String getScriptRule() {
		return scriptRule;
	}
	public void setScriptRule(String scriptRule) {
		this.scriptRule = scriptRule;
	}
}
