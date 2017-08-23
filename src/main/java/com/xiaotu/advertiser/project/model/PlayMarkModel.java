package com.xiaotu.advertiser.project.model;

import java.util.Date;

/**
 * 剧本标记信息
 * @author xuchangjian 2017-6-19下午4:59:59
 */
public class PlayMarkModel {

	private String id;
	private ProjectModel project;	//项目
	private PlayRoundModel playRound;	//场次
	private String word;		//关键字
	private Integer word_x;	//标识关键词在当前场次第几次出现
	private Integer word_order;	//标记序号
	private String description;	//描述信息
	private Date createTime;	//创建时间
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
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Integer getWord_x() {
		return word_x;
	}
	public void setWord_x(Integer word_x) {
		this.word_x = word_x;
	}
	public Integer getWord_order() {
		return word_order;
	}
	public void setWord_order(Integer word_order) {
		this.word_order = word_order;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
