package com.xiaotu.advertiser.project.model;

/**
 * 道具信息
 * @author xuchangjian 2017年6月22日下午3:33:59
 */
public class PropModel {

	private String id;
	private ProjectModel project;	//项目信息
	private String name;	//名称
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
}
