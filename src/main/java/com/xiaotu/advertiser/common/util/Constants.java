package com.xiaotu.advertiser.common.util;

import com.xiaotu.common.util.PropertiesUtil;

/**
 * @类名 Constants
 * @日期 2017年6月19日
 * @作者 高海军
 * @功能 全剧配置帮助类
 */
public class Constants
{
	private static final String FILE_ENCODE = "file.encode";// 文件编码
	
	public static final String LOGIN_URL = "/login/toLogin"; // 登录跳转地址
	
	public static final String SESSION_USER = "user"; // 缓存的USER对象
	
	public static final String SESSION_IFCHECK = "ifCheck"; // 缓存选中状态
	
	public static final String SESSION_MENULIST = "menuList"; // 缓存菜单数据
	
	public static final String SESSION_USERN_ID = "USER_ID"; // 缓存用户登录账号
	
	public static final String SESSION_PROJECT = "PROJECT"; // 缓存当前操作的项目ID
	
	public static final String SESSION_USERLIST = "userList"; // 缓存用户数据
	
	public static final String SESSION_USER_LASTTIME = "lastTime"; // 缓存用户最后登录时间
	
	public static final String AUTO_MODEL_IP = "autoModel.ip"; // 自动分析模型ip
	
	public static final String AUTO_MODEL_PORT = "autoModel.port"; // 自动分析模型port
	
	public static final String CHARSET_UTF8 = "UTF-8";// 编码格式
	
	public static final String RESTRICTED_ERROR = "/base/forward/common/restricted";
	
	public static final String KEY_LAST_DAY = "NEW_DATE_CHACE_1";// 日数据最新日期
	
	public static final String KEY_LAST_WEEK = "NEW_DATE_CHACE_2";// 周数据最新日期
	
	public static final String KEY_PROJECT_COUNT = "projectCount";// 用户项目总数
	
	public static final String KEY_PROJECT_SUCCESS_COUNT = "projectSuccessCount";// 分析成功项目总数
	
	public static final String ROLE_ANALYSE_IP = "roleAnalyse.ip";	//角色分析远程服务ip
	
	public static final String ROLE_ANALYSE_PROT = "roleAnalyse.port";	//角色分析远程服务端口
	
	public static final String AUTOMATIC_TAG_SCRIPT = "AUTOMATIC_TAG_SCRIPT";	//提取标签远程服务脚本路径
	
	public static String getFileEncode()
	{
		return PropertiesUtil.getProperty(FILE_ENCODE);
	}
}
