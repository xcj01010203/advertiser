package com.xiaotu.advertiser.dictionary.model;

/**
 * 题材信息
 * @author xuchangjian 2017-6-19下午4:35:52
 */
public class SubjectModel {
	
	public static final int OWNER_TYPE_TV = 1;	//电视剧/网剧
	public static final int OWNER_TYPE_MOVIE = 2;	//电影/网大

	private String id;
	private String name;	//名称
	private int ownerType;	//所属剧类型，1-电视剧/网剧   2-电影/网大
	public int getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
