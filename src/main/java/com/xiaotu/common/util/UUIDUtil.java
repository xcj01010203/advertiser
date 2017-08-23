package com.xiaotu.common.util;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @类名 UUIDUtil
 * @日期 2014-12-9
 * @作者
 * @功能 UUID生成器
 */
public class UUIDUtil
{
	
	/**
	 * 获得一个UUID
	 * @return String UUID
	 */
	public static String getUUID()
	{
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获得指定数目的UUID
	 * @param number int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number)
	{
		if (number < 1)
		{
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++)
		{
			ss[i] = getUUID();
		}
		return ss;
	}
	
	public static void setUUID(Object obj, String fieldName)
	{
		if (obj == null)
			return;
		@SuppressWarnings("rawtypes")
		Class clazz = obj.getClass();
		try
		{
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			Object value = field.get(obj);
			if (value == null || "".equals(value.toString()))
				field.set(obj, getUUID());
		}
		catch (IllegalAccessException e)
		{
		}
		catch (NoSuchFieldException e)
		{
		}
		catch (SecurityException e)
		{
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
