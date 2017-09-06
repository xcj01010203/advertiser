package com.xiaotu.advertiser.implant.model;

import java.util.Date;

import com.xiaotu.advertiser.dictionary.model.GoodsModel;
import com.xiaotu.advertiser.dictionary.model.ImplantModeModel;
import com.xiaotu.advertiser.project.model.PlayRoundModel;
import com.xiaotu.advertiser.project.model.ProjectModel;

/**
 * 广告植入记录
 * @author xuchangjian 2017-6-19下午4:55:04
 */
public class ImplantRecordModel {

	private String id;
	private ProjectModel project;	//项目
	private String name;	//广告标题
	private PlayRoundModel playRound;	//场次
	private ImplantModeModel implantMode;	//植入方式
	private GoodsModel goods;	//品类
	private String desc;	//描述
	private Date createTime;	//创建时间
	private double pageCount;  //场次页数
	private double wonderful;  //精彩指数
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
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
	public PlayRoundModel getPlayRound() {
		return playRound;
	}
	public void setPlayRound(PlayRoundModel playRound) {
		this.playRound = playRound;
	}
	public ImplantModeModel getImplantMode() {
		return implantMode;
	}
	public void setImplantMode(ImplantModeModel implantMode) {
		this.implantMode = implantMode;
	}
	public GoodsModel getGoods() {
		return goods;
	}
	public void setGoods(GoodsModel goods) {
		this.goods = goods;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public double getPageCount() {
		return pageCount;
	}
	public void setPageCount(double pageCount) {
		this.pageCount = pageCount;
	}
	public double getWonderful() {
		return wonderful;
	}
	public void setWonderful(double wonderful) {
		this.wonderful = wonderful;
	}
}
