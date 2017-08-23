package com.xiaotu.advertiser.project.model;

/**
 * 角色分析结果
 * @author xuchangjian 2017年8月17日下午12:08:15
 */
public class RoleAnalyseResultModel {

	private String id;
	private ProjectModel project;	//项目
	private PlayRoundModel round;	//场次
	private String roleName;	//角色名
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
	public PlayRoundModel getRound() {
		return round;
	}
	public void setRound(PlayRoundModel round) {
		this.round = round;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((project.getId() == null) ? 0 : project.getId().hashCode());
		result = prime * result + ((round.getId() == null) ? 0 : round.getId().hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		RoleAnalyseResultModel other = (RoleAnalyseResultModel) obj;
		if (other.getRoleName().equals(this.roleName) 
				&& other.getRound().getId().equals(this.round.getId()) 
				&& other.getProject().getId().equals(this.project.getId())) {
			return true;
		}
		return false;
	}
}
