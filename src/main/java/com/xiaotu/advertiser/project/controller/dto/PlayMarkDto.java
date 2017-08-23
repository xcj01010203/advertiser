package com.xiaotu.advertiser.project.controller.dto;

import java.util.List;

import com.xiaotu.advertiser.dictionary.model.GoodsModel;
import com.xiaotu.advertiser.project.model.PlayRoundModel;

public class PlayMarkDto {

	private String id;
	private String roundId;	//场次ID
	private String word;	//关键字
	private Integer word_x;	//关键字在本文中是第几次出现
	private List<GoodsModel> goodsList; 	//品类列表
	private List<String> roleNameList; 	//角色名称列表
	private String description;	//描述
	private PlayRoundModel playRound;	//场次
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoundId() {
		return roundId;
	}
	public void setRoundId(String roundId) {
		this.roundId = roundId;
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
	public List<GoodsModel> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<GoodsModel> goodsList) {
		this.goodsList = goodsList;
	}
	public List<String> getRoleNameList() {
		return roleNameList;
	}
	public void setRoleNameList(List<String> roleNameList) {
		this.roleNameList = roleNameList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public PlayRoundModel getPlayRound() {
		return playRound;
	}
	public void setPlayRound(PlayRoundModel playRound) {
		this.playRound = playRound;
	}
}
