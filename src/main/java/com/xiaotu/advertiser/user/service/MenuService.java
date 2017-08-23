package com.xiaotu.advertiser.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.user.model.MenuModel;
import com.xiaotu.advertiser.user.model.MenuTree;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.SessionUtil;

import net.sf.json.JSONObject;


@Service
public class MenuService extends BaseService{
	
	private static final Logger log = LoggerFactory.getLogger(MenuService.class);
	//当前用户的功能权限List，保存当前用户的所有功能权限信息 id 
	private static List<MenuModel> permList;
	
	/**
	 * 添加菜单
	 * @param request
	 * @param menu
	 * @return
	 */
	public void saveMenu(HttpServletRequest request, MenuModel menu){
    	int flag = this.save("insertMenu", menu);
    	if(flag > 0){
			UserModel user = SessionUtil.getSessionUser();
    		HttpSession session = request.getSession();
    		this.setMenuList(user.getId(),session);
    	}else{
    		throw new BusinessException("添加菜单失败!");
    	}
	}
	
	/**
	 * 删除菜单
	 * @param request
	 * @param menuids
	 * @return
	 */
	public void removeMenu(HttpServletRequest request,String menuids){
    	List<String> list = new ArrayList<>();
    	String[] s = menuids.split(",");
    	for(String i:s){
    		list.add(i);
    	}
    	this.delete("deleteMenu", list);
		HttpSession session = request.getSession();
		UserModel user =SessionUtil.getSessionUser();
		this.setMenuList(user.getId(),session);
	}
	
	/**
	 * 修改菜单
	 * @param request
	 * @param menu
	 * @return
	 */
	public void updateMenu(HttpServletRequest request,MenuModel menu){
		Map<String,Object> map = new HashMap<>();
		map.put("menuName", menu.getMenuName());
    	int flag = this.update("updateMenu", menu);
    	if(flag>0){
			HttpSession session = request.getSession();
			UserModel user = SessionUtil.getSessionUser();
    		this.setMenuList(user.getId(),session);
    	}else{
    		throw new BusinessException("修改菜单操作失败!");
    	}
	}
	
	/**
	 * 根据父菜单ID查询父菜单名称
	 * @param menuFid
	 * @return
	 */
	public List<MenuModel> queryMenuFidByName(String fid){
		Map<String,Object>  map = new HashMap<>();
		map.put("fid", fid);
		List<MenuModel> menuNameList =this.getList("selectMenuByName", map);
		return  menuNameList;
	}
	
	/**
	 * 查询所有菜单功能表
	 * @return
	 */
	public JSONObject queryMenu() {
	    // 原始的数据
	    List<MenuModel> rootMenu = this.getList("selectMenu");
	    String jsonStr = MenuTree.getParentMenu(rootMenu);
	    JSONObject jsStr = JSONObject.fromObject(jsonStr); 
	    return jsStr;
	}
	
	/**
	 * 查询一个菜单信息
	 * @param menuInfo
	 * @return
	 */
	public MenuModel queryOneMenu(MenuModel menuInfo){
		return this.get("selectMenu", menuInfo);
	}
	
	/**
	 * 分配菜单时查询该角色的菜单并默认选中
	 * @param roleId
	 * @return
	 */
	public JSONObject queryHadMenu(String roleId){
    	List<MenuModel> menuHadList = new ArrayList<MenuModel>();
    	// 原始的数据
	    menuHadList = this.getList("selectHadMenu",roleId);
	    String jsonStr = MenuTree.getParentMenu(menuHadList);
	    JSONObject jsStr = JSONObject.fromObject(jsonStr); 
	    return jsStr;
    	
	}
	
	
	/**
	 * 查看角色所拥有菜单
	 * @param roleId
	 * @return
	 */
	public JSONObject queryRoleMenu(String roleId){
		
		List<MenuModel> menuHadList = new ArrayList<MenuModel>();
    	// 原始的数据
	    menuHadList = this.getList("selectHadMenu",roleId);
	    String jsonStr = MenuTree.getParentMenu(menuHadList);
	    JSONObject jsStr = JSONObject.fromObject(jsonStr); 
	    return jsStr;
	    
	}
	
	
	/**
	 * 为角色分配菜单
	 * @param request
	 * @param roleId
	 * @param ids
	 * @return
	 */
	public void saveRoleMenu(HttpServletRequest request,String roleId,String ids){
		List<String> list = new ArrayList<>();
    	String[] s = ids.split(",");
    	for(String i:s){
    		list.add(i);
    	}
    	
    	Map<String,Object>  map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("list",list);
		
		this.delete("deleteRoleMenu", roleId);
    	int flag = this.save("insertRoleMenu", map);
    	if(flag>0){
			HttpSession session = request.getSession();
			UserModel user =SessionUtil.getSessionUser();
    		this.setMenuList(user.getId(),session);
    	}else{
    		throw new BusinessException("为角色添加菜单失败!");
    	}
	}
	
	
	/**
	 * 通过用户查询该用户的菜单
	 * @param userId
	 * @return
	 */
	public List<MenuModel> queryUserMenu(String userId){
		return this.getList("selectUserMenu", userId);
	
	}
	
	/**
	 * 查询最大菜单Id
	 * @param request
	 * @param menu
	 * @return
	 */
	public Map<String,Object> queryMaxMenuId(MenuModel menu){
		Map<String,Object> map = new HashMap<>();
		if(menu.getMenuLv() !=null){
			int menuLv = menu.getMenuLv();
			menuLv ++;
			menu.setMenuLv(menuLv);
		}else{
			menu.setMenuLv(0);
		}
    	List<MenuModel> menuInfoList = this.getList("selectMaxMenuId", menu);
    	MenuModel menuInfo = menuInfoList.get(0);
    	
		String maxId="";
		if(menuInfo==null){
			char startId = menu.getMenuId().charAt(0);
			char startMaxId =(char)((int)startId+1);
			int endMaxId = 100001;
			maxId = String.valueOf(startMaxId) + String.valueOf(endMaxId);
			
		}else{
			char startId = menuInfo.getMenuId().charAt(0);
			char startMaxId =startId;
			String endId = menuInfo.getMenuId().substring(1);
			int endMaxId = Integer.parseInt(endId)+1;
			maxId = String.valueOf(startMaxId) + String.valueOf(endMaxId);
		}
		map.put("menuId", maxId);
		return map;
	}
	
	/**
	 * 缓存菜单,通过用户查询该用户的菜单
	 * @param userId
	 * @param session
	 */
	public void setMenuList(String userId,HttpSession session){
		session.setAttribute(Constants.SESSION_MENULIST, queryUserMenu(userId));
	}
	
	
	/**
	 * 通过menuid查询该menu已经配置的角色
	 * @param list
	 * @return
	 */
	public List<Map<String,Object>> queryMenuByMenuId(List<String> list) {
		return this.getList("queryMenuByMenuId", list);
	}
	
	/**
	 * 根据URL获取菜单编号
	 * @param menuUrl
	 * @return
	 */
	public String queryMenuUrlByMenuId(String menuurl){
		Map<String,Object> map = new HashMap<String,Object>();
		String menuid="";
		map.put("menuurl", menuurl);
		List<MenuModel>  list = this.getList("selectMenuUrlByMenuId", map);
		if(list.size() == 0){
			log.error("未查找到菜单编号!");
		}else{
			menuid = list.get(0).getMenuId();
		}
			return menuid;
	}
	
	
		/**
		 * 根据用户名称，将其权限信息放入缓存中，并将所有已注册的权限信息放入缓存
		 * @param user 用户实体。
		 */
		public List<MenuModel>  setFuncPermitList(UserModel user) {		
			return permList = this.getList("selectPermList", user.getId());
		}
		
		/**
		 * 根据请求的url，判断当前用户是否对其有访问权
		 * @param requestUrl 请求的URL。
		 * @return 如果有访问权限返回真，否则返回假。
		 **/
		public boolean checkPermControl (String url) {
			boolean flag = false; 
			for(MenuModel menu:permList){
				if(menu!=null){
						if(menu.getId()!=null && menu.getMenuURL()!=null){
							if(menu.getMenuURL().equals(url)){
								flag = true;
							}
						}
						if(menu.getMenuURL()!=null){
							if(menu.getMenuURL().equals(url)){
								flag = true;
							}
						}
				}
			}
			return flag;
		}
		
		public static boolean isNull(String str){
			if(str!=null && !"".equals(str)){
				return false;
			}else{
				return true;
			}
		}
	
	@Override
	protected String getKey() {
		// TODO 自动生成的方法存根
		return "MenuMapper";
	}
}
