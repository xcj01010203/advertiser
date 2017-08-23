package com.xiaotu.advertiser.user.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.MD5;
import com.xiaotu.common.util.SessionUtil;
import com.xiaotu.common.util.StringUtil;
import com.xiaotu.common.util.UUIDUtil;


@Service
public class UserService extends BaseService {
	
	@Autowired
	RoleService roleService;
	
	private static MD5 md5 = new MD5();
	
	/**
	 * 添加用户
	 * @param userModel
	 * @return
	 */
	public void saveUser(UserModel userModel){
		userModel.setPassWord(md5.getMD5(userModel.getPassWord()));
		if (checkUserName(userModel.getUserName())) { // 验证权重组名称是否存在
			throw new BusinessException("操作失败，用户名已存在!");
		} else {
			String uuid = UUIDUtil.getUUID();
			userModel.setId(uuid);
			this.save("insertUser", userModel);
		}
		
	}
	
	/**
	 * 冻结或或启用 用户
	 * @param ids
	 * @param type
	 * @return
	 */
	public void removeUser(String ids,Integer type){
		List<String> list = new ArrayList<String>();
    	String[] s = ids.split(",");
    	for(String i:s){
    		list.add(i);
    	}
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("list",list);
		map.put("s",type);
    	int flag = this.update("updateUserStatus", map);
    	if(flag == 0){
    		throw new BusinessException("冻结或启用用户失败!!!");
    	}
	}
	
	/**
	 * 删除用户
	 * @param ids
	 * @return
	 */
	public void removeUserById(String ids){
		List<String> list = new ArrayList<String>();
    	String[] s = ids.split(",");
    	for(String i:s){
    		list.add(i);
    	}
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("list",list);
    	this.update("deleteUserById", map);
    	//删除用户与消息对应关系
    	this.delete("deleteUserMessage", map);
	}
	
	/**
	 * 修改用户
	 * @param userModel
	 * @param session
	 * @return
	 */
	public void updateUser(UserModel userModel,HttpSession session){
		if(StringUtil.isEmpty(userModel.getId())){
			UserModel users = SessionUtil.getSessionUser();
			userModel.setId(users.getId());
			int flag = this.update("updateUser", userModel);
			if(flag == 0){
				throw new BusinessException("修改操作失败");
			}
		}else{
			int flag = this.update("updateUser", userModel);
			if(flag == 0){
				throw new BusinessException("修改操作失败");
			}
		}
			session.setAttribute("userInfo", userModel);
	}
	
	/**
	 * 查询用户
	 * @param userName
	 * @return
	 */
	public Map<String, Object> queryUser(HttpSession session,String userName,Page page){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName",userName);
		List<Map<String, Object>> userList =this.queryPageList("selectAllUser", map,page);
		resultMap.put("userList", userList);
		resultMap.put("totalPage", page.getTotalPage());
		resultMap.put("totalRows", page.getTotalRows());
		return resultMap;
		
	}
	
	/**
	 * 验证名称是否存在
	 * @param userName
	 * @return
	 */
	public boolean checkUserName(String userName){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName",userName);
		List<UserModel> list = this.getList("selectUserByName", map);
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 重置密码
	 * @param session
	 * @param newpass
	 * @param oldpass
	 * @param userId
	 * @return
	 */
	public String updatePassword(HttpSession session,String newpass,String oldpass) {
		String errorPass="";
		UserModel user = new UserModel();
    	user.setPassWord(md5.getMD5(newpass));
    	oldpass = (md5.getMD5(oldpass));
    	UserModel userModel = (UserModel) session.getAttribute("user");
    	user.setId(userModel.getId());
    	if(oldpass.equals(userModel.getPassWord())){
    	int num = this.update("updatePassword", user);
        	if(num == 0){
        		throw new BusinessException("密码重置失败!!!");
        	}
    	}else{
    		errorPass ="原始密码不正确,请重新输入!";
    	}
    	
    	return errorPass;
		
	}
	
	/**
	 * 根据userId重置用户名密码
	 * @param session
	 * @param newpass
	 */
	public void updatePasswordByUserId(UserModel userModel) {
		userModel.setPassWord(md5.getMD5(userModel.getPassWord()));
		this.update("updatePassword", userModel);
	}

	
	@Override
	protected String getKey() {
		// TODO 自动生成的方法存根
		return "UserMapper";
	}
}
