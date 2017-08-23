package com.xiaotu.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class KeywordEncode
{
	
	private static Logger log = Logger.getLogger(KeywordEncode.class);
	
	/**
	 * url文字编码
	 * @param url
	 * @return
	 */
	public static final String encode(String str)
	{
		try
		{
			log.info("encode url:" + str);
			return URLEncoder.encode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			log.error("encode url error :" + e);
			return str;
		}
	}
	
	/**
	 * url文字解码
	 * @param url
	 * @return
	 */
	public static final String decode(String str)
	{
		try
		{
			log.info("decode url:" + str);
			return URLDecoder.decode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			log.error("decode url error :" + e);
			return str;
		}
	}
	
}
