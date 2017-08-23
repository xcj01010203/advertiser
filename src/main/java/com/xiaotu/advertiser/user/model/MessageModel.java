package com.xiaotu.advertiser.user.model;

import java.sql.Timestamp;

/**
 * 消息信息
 * @author 王艳龙  2017年7月5日 下午13:51
 */
public class MessageModel {

	private String id;			//消息编号
	private String userid;      //用户ID
	private String messageinfo;	//消息内容
	private String messagetype;	//消息类型：1，系统消息(全部用户) 2，个人消息(单个用户) 3，反馈消息
	private String replycontent;//反馈回复内容
	private Timestamp createtime = new Timestamp(System.currentTimeMillis());	//推送消息时间
	
	private String userName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessageinfo() {
		return messageinfo;
	}
	public void setMessageinfo(String messageinfo) {
		this.messageinfo = messageinfo;
	}
	public String getMessagetype() {
		return messagetype;
	}
	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getReplycontent() {
		return replycontent;
	}
	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
