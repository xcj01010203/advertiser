package com.xiaotu.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author xuchangjian
 */
public class RegexUtils
{
	
	public static final String REGEX_E_NOTATION = "^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$";// 科学计数法正则
	
	public static final String REGEX_NUMBER = "-?\\d+(\\.\\d+)?";// 数字正则
	
	public static final String REGEX_INTEGER = "[0-9]+";// 整数正则
	
	public static final String REGEX_URL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";// url正则
	
	public static final String REGEX_TAB = "\\s*|\t|\r|\n";// 空格、回车、换行符、制表符正则
	
	public static final String REGEX_SUFFIX = "$";// 结尾
	
	/**
	 * 判断字符串和指定的正则表达式是否匹配
	 * @param reg 正则表达式
	 * @param str 带匹配的字符串
	 * @return
	 */
	public static boolean regexFind(String reg, String str)
	{
		if (str == null)
			return false;
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	
	/**
	 * 判断字符串和指定的正则表达式是否匹配
	 * @param reg 正则表达式
	 * @param str 带匹配的字符串
	 * @return
	 */
	public static String regexSearch(String reg, String str)
	{
		if (str == null)
			return null;
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		if (m.find())
			return m.group();
		return null;
	}
	
	/**
	 * 通过正则表达式拆分字符串
	 * @param reg
	 * @param str
	 * @return
	 */
	public static String[] regexSplitStr(String reg, String str)
	{
		if (str == null)
		{
			str = "";
		}
		return str.split(reg);
	}
	
	/**
	 * 替换
	 */
	public static String replaceStr(String str, String rex, String replaceStr)
	{
		if (str == null)
			return null;
		Matcher matcher = Pattern.compile(rex).matcher(str);
		return matcher.replaceAll(replaceStr);
	}
	
	/**
	 * 是否匹配
	 */
	public static boolean regexMatch(String rex, String str)
	{
		if (str == null)
			return false;
		return Pattern.compile(rex).matcher(str).matches();
	}
	
	public static List<String> regexSearchAll(String reg, String str)
	{
		if (str == null)
			return null;
		Pattern p = Pattern.compile(reg);// 查找规则公式中大括号以内的字符
		Matcher m = p.matcher(str);
		List<String> list = new ArrayList<String>();
		while (m.find())
			list.add(m.group());
		if (list.isEmpty())
			return null;
		return list;
	}
	
	public static void main(String[] args)
	{
		System.out.println(regexSearch("[0-9]+", "http://weibo.com/u/1071535747?refer_flag=1001030103_"));
	}
}
