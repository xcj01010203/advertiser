package com.xiaotu.advertiser.user.model;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @类名 UserModel
 * @日期 2017年6月19日
 * @作者   王艳龙
 * @功能   用户管理实体类
 */
public class UserModel  implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键标识
	private String userName;//用户名
	private String passWord;//密码
	private String realName;//真实姓名
	private Integer sex;//性别    0男   1女
	private String birthday;
	private String company; //公司属性
	private String email;//邮箱
	private String tel;//电话 
	private String status;//状态  0:可用，1：已删除

	@Autowired
	private RoleModel role;//角色信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	

}
