package com.xiaotu.common.util;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;

/**
 * 排序工具类
 * @author xuchangjian 2017年6月21日下午6:19:09
 */
public class SortUtils {

	/**
	 * 按照汉字排序的方法
	 * @author xuchangjian 2017年6月21日下午6:21:30
	 * @return
	 */
	public static Comparator<String> cnSort() {
		Comparator<String> com = new Comparator<String>() {
            public int compare(String o1, String o2) {
            	CollationKey key1 = Collator.getInstance().getCollationKey(o1.toLowerCase());
            	CollationKey key2 = Collator.getInstance().getCollationKey(o2.toLowerCase());
                return key1.compareTo(key2);
            }
        };
        
        return com;
	}
}
