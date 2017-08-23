package com.xiaotu.advertiser.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.user.model.MenuModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.advertiser.user.service.MenuService;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.DateUtils;
import com.xiaotu.common.util.MD5;
import com.xiaotu.common.util.SessionUtil;

@Controller
@RequestMapping("/login")
public class LoginController{
	
	private static MD5 md5 = new MD5();
	
	public static List<MenuModel> userMenuList ;
	
	@Autowired
	@Qualifier(BaseService.BEAN_NAME)
	private BaseService service;
	@Autowired
	private MenuService menuService ;
	
	/**
	 * 用户登录页面
	 * @param userReq
	 * @param mv
	 * @param session
	 * @return
	 */
	@RequestMapping("/toLogin")
	public Object toLogin(UserModel userReq, ModelAndView mv,HttpSession session,HttpServletRequest request)throws Exception
	{
		String userId = userReq.getTel();
		String password = userReq.getPassWord();
		SessionUtil.setSession(session, Constants.SESSION_USERN_ID, userId);
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(password))
		{
			mv.addObject("erroeMsg", "");
			mv.setViewName("user/login");
			return mv;
		}
		userReq.setPassWord(md5.getMD5(password));
		List<UserModel> users = service.getList("selectUser", userReq);
		
		if (users != null&&users.size()>0)
		{
			UserModel user = users.get(0);
			SessionUtil.setSession(session, Constants.SESSION_USER, user);
			//获取最后登录时间  缓存到session
			SessionUtil.setSession(session, Constants.SESSION_USER_LASTTIME,DateUtils.stampToDate(session.getLastAccessedTime()));
			menuService.setMenuList(user.getId(),session);

			mv.setViewName("project/projectList");
		}
		else
		{
			mv.addObject("erroeMsg", "用户名和密码不匹配，请重新登录");
			mv.setViewName("user/login");
		}
		
		return mv;
	}
	/**
	 * 退出登录页面
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("/exit")
	public ModelAndView exit(ModelAndView mv,HttpServletRequest request)
	{
	
		HttpSession session = request.getSession();
		session.invalidate();
		mv.setViewName("user/login");
		return mv;
		
	}
	
}
