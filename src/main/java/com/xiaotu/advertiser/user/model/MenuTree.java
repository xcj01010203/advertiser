package com.xiaotu.advertiser.user.model; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiaotu.common.util.StringUtil;  

/**
 * 递归生成菜单树
 * @author wyl
 *
 */
public class MenuTree { 
	
	private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

	public static String getParentMenu(List<MenuModel> rootMenu) {
		// 最后的结果
		List<MenuInfo> menuList = new ArrayList<MenuInfo>();
		// 先找到所有的一级菜单
		for (int i = 0; i < rootMenu.size(); i++) {
			// 一级菜单没有fid
			if (StringUtil.isEmpty((rootMenu.get(i).getFid()))) {
				menuList.add(MenuInfo.parser(rootMenu.get(i)));
			}
		}
		// 为一级菜单设置子菜单，getChild是递归调用的
		for (MenuInfo menu : menuList) {
			menu.setNodes(getChild(menu.getId(), rootMenu));
		}
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("menu", menuList);
		//System.out.println(gson.toJson(jsonMap));

		return gson.toJson(jsonMap);
	}

	/**
	 * 递归查找子菜单
	 * 
	 * @param id
	 *            当前菜单id
	 * @param rootMenu
	 *            要查找的列表
	 * @return
	 */
	private static List<MenuInfo> getChild(String id, List<MenuModel> rootMenu) {
		// 子菜单
		List<MenuInfo> childList = new ArrayList<>();
		for (MenuModel menu : rootMenu) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (StringUtil.isNotEmpty(menu.getFid())) {
				if (menu.getFid().equals(id)) {
					childList.add(MenuInfo.parser(menu));
				}
			}
		}
		// 把子菜单的子菜单再循环一遍
		for (MenuInfo menu : childList) {// 没有url子菜单还有子菜单
			if (StringUtil.isNotEmpty(menu.getMenuURL())) {
				// 递归
				menu.setNodes(getChild(menu.getId(), rootMenu));
			}
		} // 递归退出条件
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}
}  
