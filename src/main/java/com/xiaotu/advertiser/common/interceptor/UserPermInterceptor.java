package com.xiaotu.advertiser.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.common.util.TextStitchUtil;
import com.xiaotu.advertiser.project.service.ProjectService;
import com.xiaotu.advertiser.user.model.MenuModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.advertiser.user.service.MenuService;
import com.xiaotu.common.util.SessionUtil;


/** 
 * 权限过滤 
 * @author wangyanlong 2017年7月17日下午15:27:43
 * @date 2017-6-30 
 */
@Component 
public class UserPermInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	MenuService menuServices;
	@Autowired
	private ProjectService projectService ;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception{
        // 请求的url   
        String url = request.getRequestURI();
        //获取session中登录用户信息
        UserModel user =SessionUtil.getSessionUser();
        HttpSession session = request.getSession();
        String ifCheck = (String)session.getAttribute(Constants.SESSION_IFCHECK);
        
        //获取当前用户项目总数 存入缓存
		SessionUtil.setSession(session, Constants.KEY_PROJECT_COUNT, projectService.getProjectCount());
		//获取当前用户已分析成功的项目 放入缓存
		SessionUtil.setSession(session, Constants.KEY_PROJECT_SUCCESS_COUNT, projectService.getProjectSucessCount());
        
    	if(user!=null){ //如果登录用户信息为空
    		if(!"NO".equals(ifCheck)){
    			List<MenuModel> menuList = menuServices.setFuncPermitList(user);
    			if(menuList.size()>0){
    				session.setAttribute(Constants.SESSION_IFCHECK, "NO");
    			}
    			
        	}
	    	//是否有访问权
	        boolean hadPerm = menuServices.checkPermControl(url);
				if(hadPerm){
				//如果有访问权，则继续
					return true;
				}else{
					//输出错误提示页
					TextStitchUtil.outPutText(request, response,Constants.RESTRICTED_ERROR);
			        return false;
				}	
				
			}else{
				//如果是管理员
				return true;
		}
	}
	/** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception{
	}
	
	/** 
     * Handler执行完成之后调用这个方法 
     */  
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception{
	}
	

}
