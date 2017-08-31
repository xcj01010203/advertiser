package com.xiaotu.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

/**
 * 实体类相关工具
 * @author xuchangjian 2017年8月28日下午6:15:47
 */
public class EntityUtils {

	/**
	 * 把实体类转换为Map
	 * @author xuchangjian 2017年8月28日下午6:21:28
	 * @param t
	 * @param clz
	 * @return
	 * @throws Exception 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static Map<String, Object> EntityToMap(Object t, Class clz) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Field[] fields = clz.getDeclaredFields();
		
		for (Field field : fields) {
			String name = field.getName();
			Method m = t.getClass().getMethod("get" + StringUtil.firstToUpper(name));
			
			Object val = m.invoke(t);
			
			resultMap.put(name, val);
		}
		
		return resultMap;
	}
	
	
}
