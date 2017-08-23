package com.xiaotu.advertiser.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.user.model.MessageModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.SessionUtil;
import com.xiaotu.common.util.StringUtil;

/**
 * 消息信息
 * @author 王艳龙  2017年7月5日 下午16:05
 */
@Service
public class MessageService extends BaseService{

	/**
	 * 获取管理员消息列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> queryHistoryMessage(String messagetype, Page page){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("messagetype", messagetype);
		List<Map<String,Object>> messageList = this.queryPageList("selectHistoryMessage", map,page);
		resultMap.put("messageList", messageList);
		resultMap.put("totalPage", page.getTotalPage());
		resultMap.put("totalRows", page.getTotalRows());
		
		return resultMap;
	}
	
	/**
	 * 获取个人用户的消息列表
	 * @param page
	 * @return
	 */
	public Map<String, Object> queryUserMessage(Page page){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		UserModel users = SessionUtil.getSessionUser();
		if(users !=null){
			map.put("userid", users.getId());
		}else{
			throw new BusinessException("用户session已过期!");
		}
		List<Map<String,Object>> userMessageList = this.queryPageList("selectUserMessage", map,page);
		resultMap.put("userMessageList", userMessageList);
		resultMap.put("totalPage", page.getTotalPage());
		resultMap.put("totalRows", page.getTotalRows());
		
		return resultMap;
	}
	
	/**
	 * 添加消息
	 * @param messageModel
	 */
	public void saveMessage(MessageModel messageModel){
		List<String> list = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		String userids =messageModel.getUserid();
		if(StringUtil.isEmpty(userids)){
		//选中设置  给所有用户添加消息
		List<UserModel> listUserModel = this.getList("selectUserList");
		for (UserModel userModel : listUserModel) {
			list.add(userModel.getId());
		}
		//给选中用户添加消息
		}else{
	    	String[] s = userids.split(",");
	    	for(String i:s){
	    		list.add(i);
	    	}
		}
		UserModel user = SessionUtil.getSessionUser();
		messageModel.setUserid(user.getId());
		int flag = this.save("insertMessage", messageModel);
		if(flag == 0){
			throw new BusinessException("添加消息失败!");
		}
		/*map.put("userid", user.getId());
		MessageModel messageId =this.get("selectMessageUserId", map);*/
		map.put("messageId", messageModel.getId());
		map.put("list", list);
		this.save("insertUserMessage", map);
	}
	
	@Override
	protected String getKey() {
		// TODO 自动生成的方法存根
		return "MessageMapper";
	}
}
