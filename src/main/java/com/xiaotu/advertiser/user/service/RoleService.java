package com.xiaotu.advertiser.user.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.user.model.RoleModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.SessionUtil;
import com.xiaotu.common.util.StringUtil;

@Service
public class RoleService extends BaseService {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * 添加角色
	 * @param roleModel
	 * @return
	 */
	public void saveRole(RoleModel roleModel) {
		if(checkRoleName(roleModel)){         //验证名称是否存在
			throw new BusinessException("添加失败，角色已存在!");
		}else{
			roleModel.setCreateTime(new Date());
			UserModel user = SessionUtil.getSessionUser();
			if(StringUtil.isEmpty(user.getUserName())){
				throw new BusinessException("用户信息已过期");
			}
			String userName = user.getUserName();
			roleModel.setCreator(userName);
			roleModel.setLastEditor(userName);
			int flag = this.save("insertRole", roleModel);
			if(flag == 0){
				throw new BusinessException("添加角色失败，请重新添加!");
	    	}
		}
}
		
		
	/**
	 * 删除角色
	 * @param ids
	 * @return
	 */
	public void removeRole(String ids) {
    	List<String> list = new ArrayList<>();
    	String[] s = ids.split(",");
    	for(String i:s){
    		list.add(i);
    	}
    	this.delete("deleteUserRole2", list);
    	int flag = this.delete("deleteRole", list);
    	if(flag == 0){
    		throw new BusinessException("删除角色信息失败!");
    	}
	}

	/**
	 * 修改角色
	 * @param roleModel
	 * @return
	 */
	public void updateRole(RoleModel roleModel) {
		UserModel user = SessionUtil.getSessionUser();
		if(StringUtil.isEmpty(user.getUserName())){
			throw new BusinessException("用户信息已过期");
		}
		String userName = user.getUserName();
		roleModel.setLastEditor(userName);
		roleModel.setLastEditTime(new Date());
		int flag = this.update("updateRole", roleModel);
		if(flag == 0){
			throw new BusinessException("修改角色信息失败!");
    	}
	}

	/**
	 * 查询角色
	 * @param role
	 * @return
	 */
	public List<RoleModel> queryRole(RoleModel role) {
		List<RoleModel> list = new ArrayList<RoleModel>();
		list = this.getList("selectRole", role);
		return list;
	}

	/**
	 * 查询用户可用角色
	 * @param userId
	 * @return
	 */
	public List<RoleModel> queryUserDoRole(String userId) {
		List<RoleModel> list = new ArrayList<RoleModel>();
		list = this.getList("selectUserDoRole", userId);
		return list;
	}

	
	/**
	 * 查询用户已有角色
	 * @param userId
	 * @return
	 */
	public List<RoleModel> queryUserRole(String userId) {
		List<RoleModel> list = new ArrayList<RoleModel>();
		list = this.getList("selectUserRole", userId);
		return list;
	}

	/**
	 * 验证名称是否存在
	 * @param roleInfo
	 * @return
	 */
	public boolean checkRoleName(RoleModel roleInfo) {
		List<RoleModel> list = this.getList("selectRole", roleInfo);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 赋予用户角色
	 * @param userId
	 * @param ids
	 * @return
	 */
	public void saveUserRole(String userId , String ids) {
		List<String> list = new ArrayList<>();
    	String[] s = ids.split(",");
    	for(String i:s){
    		list.add(i);
    	}
    	Map<String,Object> map = new HashMap<>();
    	this.removeUserRole2(userId, list);
    	map.put("userId", userId);
		map.put("list", list);
    	int flag = this.save("insertUserRole", map);
    	if(flag == 0){
    		throw new BusinessException("分配用户角色失败!");
    	}
		
	}

	/**
	 * 解除用户角色
	 * @param userId
	 * @param ids
	 * @return
	 */
	public void removeUserRole(String userId, String ids) {
		List<String> list = new ArrayList<>();
    	String[] s = ids.split(",");
    	for(String i:s){
    		list.add(i);
    	}
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("list", list);
    	this.delete("deleteUserRole", map);
	}
	
	/**
	 * 解除用户角色
	 * @param userId
	 * @param ids
	 * @return
	 */
	public void removeUserRole2(String userId, List<String> list) {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("list", list);
    	this.delete("deleteUserRole", map);
	}

	/**
	 * 根据角色查询用户
	 * @param roleId
	 * @return
	 */
	public List<String> queryUserByRoleId(String roleId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("roleId", roleId);
		return this.getList("selectUserByRoleId", map);
	}

	@Override
	protected String getKey() {
		// TODO 自动生成的方法存根
		return "RoleMapper";
	}
}
