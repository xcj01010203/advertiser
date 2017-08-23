package com.xiaotu.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class IPUtil {


public final static Logger LOGGER = Logger.getLogger(IPUtil.class);
/**
* 
* 描述：获取IP地址
* @author wangyanlong 
* @date 2016年6月28日
* @param request
* @return
*/
public static String getIpAddress(HttpServletRequest request){

	 String ip=request.getHeader("tlkg-Proxy-ip");
     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip=request.getHeader("x-forwarded-for");
     }

     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip=request.getHeader("Proxy-Client-IP");
     }
     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip=request.getHeader("WL-Proxy-Client-IP");
     }
     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip=request.getRemoteAddr();
     }
     if(ip == null)
         ip="";
     	return ip.split(",")[0];
    }
}
