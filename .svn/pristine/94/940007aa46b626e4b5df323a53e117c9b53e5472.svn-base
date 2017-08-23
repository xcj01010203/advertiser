package com.xiaotu.advertiser.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.advertiser.user.service.UserService;
import com.xiaotu.common.db.Page;

@RestController
@RequestMapping("user")
public class UserController{

	@Autowired
	private UserService userService;

	
	/**
	 * 查询用户信息
	 * @param request
	 * @param userName
	 * @return
	 * @throws Throwable 
	 */
	@RequestMapping(value="/queryUser")
	public  Object queryUser(HttpSession session,String userName,Page page) throws Throwable{
		return userService.queryUser(session,userName,page);
	}

	/**
	 * 新增用户信息
	 * @param userModel
	 * @return
	 */
	@RequestMapping(value="/saveUser")
	public Object saveUser(@RequestBody UserModel userModel) {
		 userService.saveUser(userModel);
		 return null;
	}
	
	
	/**
	 * 修改用户信息
	 * @param userModel
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/updateUser")
	public Object updateUser(UserModel userModel,HttpSession session){
		userService.updateUser(userModel, session); 
		return null;
	}
	
	 /**
	  * 冻结或启用 用户
	  * @param ids
	  * @return
	  */
	@RequestMapping(value="/removeUser")
	    public Object removeUser(String ids,Integer type){
	    	userService.removeUser(ids,type);
	    	return null;
    }
	
	/**
	 * 删除用户信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/deleteUser")
    public Object deleteUser(String ids){
    	userService.removeUserById(ids);
    	return null;
	}
	
    
    /**
     * 重置密码
     * @param session
     * @param newpass
     * @param oldpass
     * @param userId
     * @return
     */
    @RequestMapping(value ="/updatePassword")
    public  Object updatePassword(HttpSession session,String newpass,String oldpass){
    	return userService.updatePassword(session,newpass,oldpass);
    }
    /**
     * 根据userId重置用户名密码
     * @param session
     * @param newpass
     * @return
     */
    @RequestMapping(value ="/updatePasswordByUserId")
    public Object updatePasswordByUserId(@RequestBody UserModel userModel){
    	userService.updatePasswordByUserId(userModel);
    	return null;
    }
    
}