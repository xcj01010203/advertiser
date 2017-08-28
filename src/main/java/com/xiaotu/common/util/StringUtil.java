package com.xiaotu.common.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

/**
 * Copyright (c)2013,小土科技 All rights reserved. <core>字符串的工具类</core>
 * @author Administrator
 * @version
 */
public class StringUtil
{
	
	private static final DecimalFormat df = new DecimalFormat("0.000");
	
	private static final Gson GSON = new Gson();
	
	public static String dataFormatByP3Bit(Object data)
	{
		if (data == null)
			return "0.000";
		return df.format(data);
	}
	
	public static Map<String, Object> JsonStr2Map(String json)
	{
		if (StringUtils.isEmpty(json))
			return null;
		JSONObject jsonObj = JSONObject.fromObject(json);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (Object key : jsonObj.keySet())
			map.put(key + "", jsonObj.get(key));
		return map;
	}
	
	public static String object2Json(Object src)
	{
		if (src == null)
			return StringUtils.EMPTY;
		return GSON.toJson(src);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T json2Object(String json, Class clazz)
	{
		return (T) GSON.fromJson(json, clazz);
	}
	
	public static String removeDate4Json(String json)
	{
		if (StringUtils.isEmpty(json))
			return null;
		JSONObject jsonObj = JSONObject.fromObject(json);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (Object key : jsonObj.keySet())
			map.put(key + "", jsonObj.get(key));
		// map.remove("startDate");
		map.remove("endDate");
		return GSON.toJson(map);
	}
	
	/**
	 * 将对象转化为字符串，如果对象为null,返回""
	 * @param obj 对象
	 * @return 转化的字符串
	 */
	public static String nullToString(Object obj)
	{
		return nullToString(obj, "");
	}
	
	public static String getString(String str)
	{
		if (StringUtil.isEmpty(str))
		{
			return str;
		}
		return str.replace("{", "").replace("}", "");
	}
	
	/**
	 * 将对象转化为字符串，如果对象为null,返回tip
	 * @param obj 对象
	 * @param tip 提示信息
	 * @return 转化的字符串
	 */
	public static String nullToString(Object obj, String tip)
	{
		
		if (obj == null)
		{
			return tip;
		}
		
		String temp = obj.toString().trim();
		if (temp.equals("") || temp.equals("null"))
		{
			return tip;
		}
		else
		{
			return temp;
		}
		
	}
	
	public static boolean mapValueEmpty(Map<String, Object> map, String key)
	{
		if (map == null)
			return true;
		return isEmpty(map.get(key));
	}
	
	public static String[] jsonArray2StringArray(JSONArray jsonArray)
	{
		if (jsonArray == null)
			return null;
		return (String[]) JSONArray.toArray(jsonArray, String.class);
	}
	
	/**
	 * 判断字符串是否是空串
	 * @param str 待判断的字符串
	 * @return
	 */
	public static boolean isEmpty(Object str)
	{
		if (str == null)
		{
			return true;
		}
		else
		{
			String strObj = str.toString().toLowerCase().trim();
			if (strObj.equals("") || strObj.equals("null")
					|| strObj.equals("\"null\"") || strObj.equals("'null'"))
			{
				return true;
			}
			return false;
		}
	}
	
	/**
	 * 判断字符串不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str)
	{
		if (isEmpty(str))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串是否是数字
	 * @param str 待判断的字符串
	 * @return
	 */
	public static boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 拆分字符串：“number1, number2, number3”
	 * @param str 待拆分的字符串
	 * @return
	 */
	public static List<Long> getLongListByStr(String str)
	{
		List<Long> list = new ArrayList<Long>();
		
		if (!StringUtil.isEmpty(str))
		{
			if (str.indexOf(",") != -1)
			{
				String[] strArr = str.split(",");
				for (String s : strArr)
				{
					if (StringUtil.isNumeric(s))
					{
						list.add(Long.valueOf(s));
					}
				}
			}
			else
			{
				if (StringUtil.isNumeric(str.trim()))
				{
					list.add(Long.valueOf(str.trim()));
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 拆分字符串：“number1, number2, number3”
	 * @param str 待拆分的字符串
	 * @return
	 */
	public static List<Integer> getIntegerListByStr(String str)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		if (!StringUtil.isEmpty(str))
		{
			if (str.indexOf(",") != -1)
			{
				String[] strArr = str.split(",");
				for (String s : strArr)
				{
					if (StringUtil.isNumeric(s.trim()))
					{
						list.add(Integer.valueOf(s.trim()));
					}
				}
			}
			else
			{
				if (StringUtil.isNumeric(str.trim()))
				{
					list.add(Integer.valueOf(str.trim()));
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 将全角数值字符串转换为半角
	 * @param numberStr：
	 * @return
	 */
	public static String numberTransform(String numberStr)
	{
		char c[] = numberStr.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);
		return returnString;
	}
	
	/**
	 * 清除字符串中的空格
	 * @param str
	 * @return
	 */
	public static String removeStrSpace(String str)
	{
		if (str == null || str.equals(""))
		{
			return str;
		}
		Matcher matcher = Pattern.compile("\\s").matcher(str);
		return matcher.replaceAll("");
	}
	
	/**
	 * 解析OpenOffice名称的参数
	 * @param paraName 参数名称
	 * @param cmd 执行命令
	 * @return 参数值
	 */
	public static String parseOpenOfficeCmdParam(String paraName, String cmd)
	{
		String[] cmdArr = cmd.split(paraName + "=");
		if (cmdArr[1].indexOf(",") > -1)
			return cmdArr[1].substring(0, cmdArr[1].indexOf(","));
		else
			return cmdArr[1].substring(0, cmdArr[1].indexOf(";"));
	}
	
	public static String list2JsonArray(List<String> list)
	{
		StringBuffer dataBuffer = new StringBuffer();
		for (String value : list)
			dataBuffer.append("'" + value + "',");
		return "[" + dataBuffer.substring(0, dataBuffer.length() - ",".length())
				+ "]";
	}
	
	/**
	 * 获取元素在数组中的位置
	 * @param arr 数组
	 * @param item 元素
	 * @return 位置
	 */
	public static int getIndexFromArray(Object[] arr, Object item)
	{
		if (arr == null || arr.length < 1)
			return -1;
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i].equals(item))
				return i;
		}
		return -1;
	}
	
	public static String getOccupationWhere(String tabOccupation, String alias)
	{
		if (StringUtils.isEmpty(tabOccupation))
			return null;
		if (StringUtils.isEmpty(alias))
			alias = StringUtils.EMPTY;
		else
			alias += SepratorUtil.SEP_POINT;
		switch (tabOccupation)
		{
			case "star":
				return "'演员'=ANY(" + alias + "occupation)";
			case "screenwriter":
				return "'编剧'=ANY(" + alias + "occupation)";
			case "producer":
				return "('制片人'=ANY(" + alias + "occupation) OR '监制'=ANY("
						+ alias + "occupation))";
			case "director":
				return "'导演'=ANY(" + alias + "occupation)";
			default:
				return null;
		}
	}
	
	public static String getTabOccupation(String occupation)
	{
		if (StringUtils.isEmpty(occupation))
			return null;
		switch (occupation)
		{
			case "演员":
				return "star";
			case "编剧":
				return "screenwriter";
			case "制片人":
			case "监制":
				return "producer";
			case "导演":
				return "director";
			default:
				return null;
		}
	}
	
	public static String[] getOccupationName(String tabOccupation)
	{
		if (StringUtils.isEmpty(tabOccupation))
			return null;
		switch (tabOccupation)
		{
			case "star":
				return new String[] { "演员" };
			case "screenwriter":
				return new String[] { "编剧" };
			case "producer":
				return new String[] { "制片人", "监制" };
			case "director":
				return new String[] { "导演" };
			default:
				return null;
		}
	}
	
	public static String getTVType(String typeId)
	{
		String tvType;
		switch (typeId)
		{
			case "1":
				tvType = "电视剧";
				break;
			case "3":
				tvType = "网剧";
				break;
			case "2":
			case "4":
			case "6":
				tvType = "电影";
				break;
			default:
				tvType = "综艺";
				break;
		}
		return tvType;
	}
	
	/**
	 * @Title: isMobile
	 * @Description: 校验手机号码是否正确
	 * @param: @param str
	 * @param: @return 设定文件
	 * @return: boolean 返回类型
	 * @author: 王瑞鑫
	 * @since: Ver 1.0
	 */
	public static boolean isMobile(String str)
	{
		Pattern pattern = null;
		Matcher matcher = null;
		boolean b = false;
		pattern = Pattern.compile("^[1][3|4|5|7|8|9]\\d{9}$"); // 验证手机号
		matcher = pattern.matcher(str);
		b = matcher.matches();
		return b;
	}
	
	/**
	 * 获取排名和总数
	 * @param dataMap 所有数据
	 * @param rankObj 被排名的对象
	 * @return 排名和总数
	 */
	public static Map<String, Object> getRankAndTotal4Key(
			Map<String, Map<String, Object>> dataMap, String rankObj)
	{
		if (dataMap == null || dataMap.isEmpty())
			return null;
		if (!dataMap.containsKey(rankObj))
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rank", dataMap.get(rankObj).get("rownum"));
		map.put("total", dataMap.size());
		return map;
	}
	
	/**
	 * 把英文符号转为中文符号
	 * @param separator
	 * @return
	 */
	public static String CHToENSeparator(String separator) {
		String changedStr = separator
				.replaceAll(":", "：")
				.replaceAll(",", "，")
				.replaceAll(";", "；")
				.replaceAll("\\)", "）")
				.replaceAll("\\(", "（");
		return changedStr;
	}
}
