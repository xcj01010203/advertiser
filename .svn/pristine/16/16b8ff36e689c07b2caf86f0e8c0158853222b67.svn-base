package com.xiaotu.advertiser.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.user.model.MenuModel;
import com.xiaotu.advertiser.user.service.MenuService;

@RestController
@RequestMapping(value="/menu")
public class MenuController{
	
	@Autowired
	MenuService menuServices;
	/**
	 * 显示树形表格   查询菜单
	 * @return
	 */
    @RequestMapping(value="/queryMenu")
    public @ResponseBody Object queryMenu(){
    	return menuServices.queryMenu();
    }
    
    /**
     * 添加菜单信息
     * @param request
     * @param menu
     * @return
     */
    @RequestMapping(value="/saveMenu")
    public @ResponseBody Object saveMenu(HttpServletRequest request,@RequestBody MenuModel menuModel){
    	 menuServices.saveMenu(request,menuModel);
    	 return null;
    }
    
    /**
     * 为角色分配菜单信息
     * @param request
     * @param roleId
     * @param ids
     * @return
     */
    @RequestMapping(value="/saveRoleMenu")
    public @ResponseBody Object saveRoleMenu(HttpServletRequest request,String roleId,String ids){
    	 menuServices.saveRoleMenu(request,roleId,ids);
    	 return null;
    }
    
    
    /**
     * 编辑菜单信息
     * @param request
     * @param menu
     * @return
     */
    @RequestMapping(value="/updateMenu")
    public @ResponseBody Object updateMenu(HttpServletRequest request,MenuModel menu){
    	 menuServices.updateMenu(request,menu);
    	 return null;
    }
    
    /**
     * 根据父菜单ID查询父菜单名称
     * @param menuFid
     * @return
     */
    @RequestMapping(value="/queryMenuName")
    public @ResponseBody Object queryMenuName(String fid){
    	return menuServices.queryMenuFidByName(fid);
    }
    
    
    /**
     * 查询最大菜单Id
     * @param request
     * @param menu
     * @return
     */
    @RequestMapping(value="/queryMaxMenuId")
    public @ResponseBody Object queryMaxMenuId(@RequestBody MenuModel menu){
    	return menuServices.queryMaxMenuId(menu);
    	
    }
    
    
    /**
     * 删除菜单
     * @param request
     * @param menuids
     * @return
     */
    @RequestMapping(value="/removeMenu")
    public @ResponseBody Object removeMenu(HttpServletRequest request,String menuids){
    	menuServices.removeMenu(request,menuids);
    	return null;
    }
    
    
    /**
     * 分配菜单时查询该角色的菜单并默认选中
     * @param roleId
     * @return
     */
    @RequestMapping(value="/queryHadMenu")
    public @ResponseBody Object queryHadMenu(String roleId){
    	return menuServices.queryHadMenu(roleId);
    }
    
    
    /**
     * 查看角色所拥有菜单
     * @param roleId
     * @return
     */
    @RequestMapping(value="/queryRoleMenu")
    public @ResponseBody Object queryRoleMenu(String roleId){
    	return menuServices.queryRoleMenu(roleId);
    }
    
    
    /**
     * 查询一个菜单信息
     * @param menuInfo
     * @return
     */
    @RequestMapping("/queryMenuInfo")
    public @ResponseBody Object findMenuInfo(MenuModel menuInfo){
    	return menuServices.queryOneMenu(menuInfo);
    }
    
}
