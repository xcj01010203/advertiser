package com.xiaotu.common.util;

import java.util.List;

/**
 * 列表工具类
 * @author xuchangjian 2017年9月4日上午11:42:57
 */
public class ListUtils {

	/**
	 * 处理以下列表为空的情况
	 * 如果列表等于null或者列表size为空，都返回null
	 * @author xuchangjian 2017年9月4日上午11:39:35
	 * @param objList
	 */
	public static Object filterListNull(List<?> objList) {
		if (objList == null) {
			return null;
		}
		if (objList.size() == 0) {
			return null;
		}
		return objList;
	}
}
