package com.xiaotu.advertiser.common.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 输出错误提示页面
 * @author wangyanlong 2017年7月17日下午15:27:43
 *
 */
public class TextStitchUtil {
	private static final Logger logs = LoggerFactory.getLogger(TextStitchUtil.class);
	
	private static String buliderStr = "<script charset=\"utf-8\" language=\"javascript\" "
			+ "type=\"text/javascript\">window.location='%s';</script>";
	
	public static void outPutText(HttpServletRequest request,HttpServletResponse response,String url){
		 try {
			 
			HttpSession session = request.getSession();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			String path = request.getContextPath();
			String loginPage = path + url;
			StringBuilder builder = new StringBuilder();
			String str = String.format(buliderStr, loginPage);
			builder.append(str);
			session.setAttribute("flag", "nohadPerm");
			out.print(builder.toString());
			
		} catch (Exception e) {
			
			logs.error(ExceptionUtils.getStackTrace(e));
		}
	}
}
