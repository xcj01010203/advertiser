package com.xiaotu.advertiser.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.user.model.RoleModel;
import com.xiaotu.advertiser.user.service.RoleService;

@RestController
@RequestMapping(value="/role")
public class RoleController{
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 查询角色信息
	 * @param role
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryRole")
	public @ResponseBody Object queryRole(RoleModel role,HttpServletRequest request){
		return roleService.queryRole(role);
	}
	
	/**
	 * 新增角色
	 * @param roleModel
	 * @return
	 */
	@RequestMapping(value="/saveRole")
	public  @ResponseBody Object saveRole(RoleModel roleModel){
		roleService.saveRole(roleModel); 
		return null;
	}
		
	/**
	 * 修改角色
	 * @param roleModel
	 * @return
	 */
	@RequestMapping(value="/updateRole")
	public  @ResponseBody Object updateRole(RoleModel roleModel){
		roleService.updateRole(roleModel);
		return null;
	}
		
	/**
	 * 删除角色信息   以及删除角色的时候删除与之关联的   菜单和功能信息
	 * @param ids
	 * @return
	 */
    @RequestMapping(value="/removeRole")
    public @ResponseBody Object removeRole(String ids){
    	 roleService.removeRole(ids);
    	 return null;
    }
	    
    /**
     * 查询用户可用角色信息
     * @param userId
     * @return
     */
	@RequestMapping(value="/queryUserDoRole")
	public @ResponseBody Object queryUserDoRole(String userId){
		return roleService.queryUserDoRole(userId);
	}
	
	/**
	 * 查询用户已有角色信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/queryUserRole")
	public @ResponseBody Object queryUserRole(String userId){
		return roleService.queryUserRole(userId);
	}
	  
	
   /**
    * 赋予用户角色
    * @param ids
    * @param userId
    * @return
    */
    @RequestMapping(value="/saveUserRole")
    public @ResponseBody Object saveUserRole(String ids,String userId){
    	roleService.saveUserRole(userId,ids);
    	return null;
    }
    
    
    /**
     * 解除用户角色
     * @param ids
     * @param userId
     * @return
     */
    @RequestMapping(value="/removeUserRole")
    public @ResponseBody Object removeUserRole(String ids,String userId){
    	 roleService.removeUserRole(userId, ids);
    	 return null;
    }
    
    
	/**
	 * 根据角色查询用户    
	 * @param roleId
	 * @return
	 */
    @RequestMapping("/queryUserByRoleId")
    public @ResponseBody Object queryUserByRoleId(String roleId){
    	return roleService.queryUserByRoleId(roleId);
    }  
}
