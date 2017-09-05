package com.xiaotu.advertiser.user.model;

import java.io.Serializable;

/**
 * @类名 LogManageModel
 * @日期 2017年6月19日
 * @作者 	王艳龙
 * @功能 	日志记录实体类
 */
public class LogManageModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;     //日志ID
	private String userId; //登录账号
	private String menuId; //执行的模块ID
	private String ip;     //执行IP
	private String createTime;    //执行时间
	private String commite; //执行描述
	private String status;  //执行状态
	private UserModel userModel; //用户
	private MenuModel menuModel; //菜单
	private String projectName; //项目名称
    
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCommite() {
		return commite;
	}
	public void setCommite(String commite) {
		this.commite = commite;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	public MenuModel getMenuModel() {
		return menuModel;
	}
	public void setMenuModel(MenuModel menuModel) {
		this.menuModel = menuModel;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}
