package com.xiaotu.advertiser.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.user.model.MessageModel;
import com.xiaotu.advertiser.user.model.UserBackReplyModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.SessionUtil;

/**
 * 反馈意见处理
 * @author 王艳龙 2017年7月5日 下午15:50
 */
@Service
public class UserBackReplyService extends BaseService{
	
	/**
	 * 获取反馈意见信息列表
	 * @param page
	 * @return
	 */
	public Map<String,Object> queryUserBackReply(Page page){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		UserModel users = SessionUtil.getSessionUser();
		if(users !=null){
			UserBackReplyModel.parser(users);
			List<Map<String,Object>> backReplyList =this.queryPageList("selectUserBackReply", map, page);
			resultMap.put("backReplyList", backReplyList);
			resultMap.put("totalPage", page.getTotalPage());
			resultMap.put("totalRows", page.getTotalRows());
		}else{
			throw new BusinessException("用户session已失效!");
		}
		
		return resultMap;
		
	}
	
	/**
	 * 添加反馈意见
	 * @param backReply
	 */
	public void saveUserBackReply(UserBackReplyModel backReply){
		UserModel users = SessionUtil.getSessionUser();
		if(users !=null){
			backReply.setUserid(users.getId());
			backReply.setReplystatus("0");
			int flag =this.save("insertUserBackReply", backReply);
			if(flag == 0){
				throw new BusinessException("添加反馈意见失败!");
			}
		}else{
			throw new BusinessException("用户session信息已过期!");
		}
	}
	
	/**
	 * 回复反馈意见
	 * @param userId
	 * @param replyContent
	 */
	public void updateBackReply(String id, String replyContent){
		MessageModel messageModel = new MessageModel();
		Map<String,Object> mapUpdate = new HashMap<String,Object>();
		Map<String,Object> mapInsert = new HashMap<String,Object>();
		List<String> list = new ArrayList<>();
		mapUpdate.put("id", id);
		mapUpdate.put("replycontent", replyContent);
		mapUpdate.put("replystatus", "1");
		int flag = this.update("updateReplyContent", mapUpdate);
		if(flag == 0){
			throw new BusinessException("回复反馈意见失败!");
		}
		UserModel users = SessionUtil.getSessionUser();
		if(users !=null){
			messageModel.setUserid(users.getId());
			messageModel.setMessageinfo(replyContent);
			messageModel.setMessagetype("3");
			messageModel.setReplycontent(replyContent);
			int flag2 = this.save("insertMessage", messageModel);
			if(flag2 == 0){
				throw new BusinessException("添加消息失败!");
			}
			
			List<UserBackReplyModel> userBackReplyId =this.getList("selectBackReplyUserId",id);
			if(userBackReplyId.size()>0){
				String userids = userBackReplyId.get(0).getUserid();
				list.add(userids);
			}
			mapInsert.put("messageId", messageModel.getId());
			mapInsert.put("list", list);
			this.save("insertUserMessage", mapInsert);
		}
	}
	
	@Override
	protected String getKey() {
		// TODO 自动生成的方法存根
		return "UserBackReplyMapper";
	}
}
