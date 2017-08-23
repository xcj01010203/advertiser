package com.xiaotu.common.db.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UUIDUtils
{
	
	private static int count = 10000;
	private static int step = 0;
	
	/**
	 * 生成18位长整形数字
	 * @return
	 */
	public static synchronized long nextLongId()
	{
		if (count > 99999)
		{
			step++;
			count = 10000;
		}
		return Long.parseLong(
				"" + (System.currentTimeMillis() + step) + (count++));
	}
	
	/**
	 * 生成24位字符串
	 * @return
	 */
	public static synchronized String getStringUUID()
	{
		count++;
		long time = System.currentTimeMillis();
		String uuid = "X" + Long.toHexString(time) + Integer.toHexString(count)
				+ Long.toHexString(Double.doubleToLongBits(Math.random()));
		uuid = uuid.substring(0, 24).toUpperCase();
		return uuid;
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
				field.set(obj, getStringUUID());
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
	
	public static void main(String[] args)
	{
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(date));
		
	}
}
