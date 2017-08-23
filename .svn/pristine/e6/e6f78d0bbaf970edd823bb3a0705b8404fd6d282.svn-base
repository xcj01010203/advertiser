package com.xiaotu.advertiser.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.user.model.UserBackReplyModel;
import com.xiaotu.advertiser.user.service.UserBackReplyService;
import com.xiaotu.common.db.Page;

/**
 * 反馈意见处理
 * @author 王艳龙 2017年7月5日 下午15:50
 */
@RestController
@RequestMapping(value="/userBackReply")
public class UserBackReplyController {

	@Autowired
	private UserBackReplyService userBackReplyService;
	
	/**
	 * 获取反馈意见信息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/queryUserBackReply")
	public @ResponseBody Object queryUserBackReply(Page page){
		return userBackReplyService.queryUserBackReply(page);
	}
	
	/**
	 * 添加反馈意见
	 * @param userBackReplyModel
	 */
	@RequestMapping(value="/saveUserBackReply")
	public @ResponseBody Object saveUserBackReply(UserBackReplyModel userBackReplyModel){
		userBackReplyService.saveUserBackReply(userBackReplyModel);
		return null;
	}
	
	/**
	 * 回复反馈意见
	 * @param userId
	 * @param replyContent
	 */
	@RequestMapping(value="/updateUserBackReply")
	public @ResponseBody Object updateUserBackReply(String id, String replyContent){
		userBackReplyService.updateBackReply(id, replyContent);
		return null;
	}
}
