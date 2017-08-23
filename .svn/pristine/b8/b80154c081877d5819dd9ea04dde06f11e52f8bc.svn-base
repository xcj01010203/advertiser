package com.xiaotu.advertiser.user.model;

import java.util.List;

public class MenuInfo extends MenuModel {

	private static final long serialVersionUID = 1L;

	private String text;
	private List<MenuInfo> nodes;

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public List<MenuInfo> getNodes() {
		return nodes;
	}

	public void setNodes(List<MenuInfo> nodes) {
		this.nodes = nodes;
	}
	public static MenuInfo parser(MenuModel menuModel) {
		MenuInfo menuInfo = new MenuInfo();
		menuInfo.setText(menuModel.getMenuName());
		menuInfo.setId(menuModel.getId()) ;
		menuInfo.setMenuId(menuModel.getMenuId());
		menuInfo.setMenuName(menuModel.getMenuName());
		menuInfo.setFid(menuModel.getFid()==null?"":menuModel.getFid()) ;
		menuInfo.setMenuLv(menuModel.getMenuLv());
		menuInfo.setMenuExplain(menuModel.getMenuExplain()) ;
		menuInfo.setMenuURL(menuModel.getMenuURL()==null?"":menuModel.getMenuURL());
		menuInfo.setCssName(menuModel.getCssName()==null?"":menuModel.getCssName());
		return menuModel != null ? menuInfo : null;
	}

}
