package com.xiaotu.advertiser.user.model;

/**
 * 反馈意见处理信息
 * @author 王艳龙 2017年7月5日 下午14:00
 */
public class UserBackReplyModel{
	
	private String id;	//反馈意见id
	private String userid;	//用户标识id
	private String content;	//反馈意见
	private String replystatus;	//'回复状态  0：未回复，1：已回复',
	private String replycontent;//反馈回复内容
	private String createtime;	//反馈回复时间
	
	private String userName;
	private String tel;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReplystatus() {
		return replystatus;
	}
	public void setReplystatus(String replystatus) {
		this.replystatus = replystatus;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getReplycontent() {
		return replycontent;
	}
	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}
	public static UserBackReplyModel parser(UserModel userModel) {
		UserBackReplyModel replyModel = new UserBackReplyModel();
		replyModel.setUserName(userModel.getUserName());
		replyModel.setTel(userModel.getTel()) ;
		return userModel != null ? replyModel : null;
	}
}
