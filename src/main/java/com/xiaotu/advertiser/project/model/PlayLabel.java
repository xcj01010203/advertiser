/**  
 * Copyright © 2017公司名字. All rights reserved.
 *
 * @Title: PlayLabel.java
 * @Prject: Advertiser
 * @Package: com.xiaotu.advertiser.project.model
 * @Description: TODO
 * @author: Administrator  
 * @date: 2017年9月4日 上午11:16:35
 * @version: V1.0  
 */
package com.xiaotu.advertiser.project.model;

import java.io.Serializable;

/**
 * @ClassName: PlayLabel
 * @Description: 标签实体类
 * @author: Administrator
 * @date: 2017年9月4日 上午11:16:35
 */
public class PlayLabel implements Serializable{

	private static final long serialVersionUID = 6472242958508336112L;
	private Integer id;
	private String label;
	private Integer parentId;
	private Double score;
	private String ids;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public PlayLabel(Integer id, String label, Integer parentId, Double score, String ids) {
		super();
		this.id = id;
		this.label = label;
		this.parentId = parentId;
		this.score = score;
		this.ids = ids;
	}
	public PlayLabel() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "PlayLabel [id=" + id + ", label=" + label + ", parentId=" + parentId + ", score=" + score + ", ids="
				+ ids + "]";
	}
	
}
