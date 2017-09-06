package com.xiaotu.advertiser.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.user.model.MessageModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.advertiser.user.service.MessageService;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.SessionUtil;

/**
 * 消息信息
 * @author 王艳龙  2017年7月5日 下午16:05
 */
@RestController
@RequestMapping(value="/message")
public class MessageController {

	@Autowired 
	private MessageService messageService;
	@Autowired
	@Qualifier(BaseService.BEAN_NAME)
	private BaseService service;
	
	/**
	 * 获取管理员消息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/queryAdminMessage")
	public @ResponseBody Object queryAdminMessage(String messagetype, Page page){
		return messageService.queryHistoryMessage(messagetype,page);
	}
	
	/**
	 * 获取个人用户消息列表
	 * @return
	 */
	@RequestMapping(value="/queryUserMessage")
	public @ResponseBody Object queryUserMessage(Page page){
		return messageService.queryUserMessage(page);
	}
	
	
	/**
	 * 添加推送消息
	 * @param messageModel
	 * @return
	 */
	@RequestMapping(value="/saveMessage")
	public @ResponseBody Object saveMessage(MessageModel messageModel){
		 messageService.saveMessage(messageModel);
		 return null;
	}
	
	/**
	 * 查询个人信息用户列表
	 * @return
	 */
	@RequestMapping(value="/queryMessageUser")
	public @ResponseBody Object queryMessageUser(){
		 UserModel user =SessionUtil.getSessionUser();
		 return service.getList("selectUser", user);
	}
}
