package com.xiaotu.advertiser.implant.controller.dto;

import java.util.List;

/**
 * 角色的广告统计信息
 * @author xuchangjian 2017年7月5日下午3:42:19
 */
public class RoleImplantDto {

	private String id;	//ID
	private String name;	//名称
	private int totalRoundCount;	//总场数
	private List<GoodsImplantDto> goodsImplantList;	//品类的广告统计信息
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalRoundCount() {
		return totalRoundCount;
	}
	public void setTotalRoundCount(int totalRoundCount) {
		this.totalRoundCount = totalRoundCount;
	}
	public List<GoodsImplantDto> getGoodsImplantList() {
		return goodsImplantList;
	}
	public void setGoodsImplantList(List<GoodsImplantDto> goodsImplantList) {
		this.goodsImplantList = goodsImplantList;
	}
	@Override
	public boolean equals(Object obj) {
		RoleImplantDto roleImplantDto = (RoleImplantDto) obj;
		return roleImplantDto.getId().equals(this.id);
	}
}
