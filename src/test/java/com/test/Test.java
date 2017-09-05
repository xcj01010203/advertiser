package com.test;

public class Test {

	public static void main(String[] args) throws Exception {
		double a = 1.11;
		System.out.println(Math.ceil(a));
	}
	
	/**
	 * 字符串首字母转大写
	 * @author xuchangjian 2017年8月28日下午6:24:23
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String getMethodName(String str) throws Exception {
		byte[] items = str.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

}
