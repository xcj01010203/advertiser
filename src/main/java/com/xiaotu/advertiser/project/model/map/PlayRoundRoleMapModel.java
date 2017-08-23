package com.xiaotu.advertiser.project.model.map;

import com.xiaotu.advertiser.project.model.PlayRoleModel;
import com.xiaotu.advertiser.project.model.PlayRoundModel;
import com.xiaotu.advertiser.project.model.ProjectModel;

/**
 * 场景和角色关联关系表
 * @author xuchangjian 2017-6-19下午4:51:25
 */
public class PlayRoundRoleMapModel {

	private String id;
	private ProjectModel project;	//项目
	private PlayRoundModel playRound;	//场景
	private PlayRoleModel playRole;	//角色
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
	public PlayRoundModel getPlayRound() {
		return playRound;
	}
	public void setPlayRound(PlayRoundModel playRound) {
		this.playRound = playRound;
	}
	public PlayRoleModel getPlayRole() {
		return playRole;
	}
	public void setPlayRole(PlayRoleModel playRole) {
		this.playRole = playRole;
	}
}
