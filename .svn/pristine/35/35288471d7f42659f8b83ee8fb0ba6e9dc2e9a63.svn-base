package com.xiaotu.common.util;

import org.apache.commons.lang.StringUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @类名 GsonUtils
 * @日期 2016年4月14日
 * @作者 高海军
 * @功能 gson帮助类
 */
public class GsonUtils
{
	private static Gson gson = new GsonBuilder().disableHtmlEscaping()
			.serializeNulls().create();
	
	/**
	 * @param src 源对象
	 * @param field 被排除的字段
	 * @return json串
	 */
	public static String getJsonSkipField(Object src, final String field)
	{
		Gson gson = new GsonBuilder()
				.setExclusionStrategies(new ExclusionStrategy()
				{
					
					@Override
					public boolean shouldSkipField(FieldAttributes f)
					{
						return f.getName().equals(field);
					}
					
					@Override
					public boolean shouldSkipClass(Class<?> clazz)
					{
						return false;
					}
				}).create();
		return gson.toJson(src);
	}
	
	public static String toJson(Object src)
	{
		if (src == null)
			return null;
		return gson.toJson(src);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T fromJson(String json, Class clazz)
	{
		if (StringUtils.isEmpty(json))
			return null;
		return (T) gson.fromJson(json, clazz);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T fromJson(String json, TypeToken typeToken)
	{
		if (StringUtils.isEmpty(json) || typeToken == null)
			return null;
		return (T) gson.fromJson(json, typeToken.getType());
	}
}
