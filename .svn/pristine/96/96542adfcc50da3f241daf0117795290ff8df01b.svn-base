package com.xiaotu.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;


public class HttpJsonReaderUtils {

	public static String GetFromHtml(String htmladdr,String params) throws IOException {

		HttpURLConnection url = null;
		String s="";

		try {
			//create web site url
			URL url1 = new URL(htmladdr);
			
			//open site connection
			url = (HttpURLConnection) url1.openConnection();

			//set request properties
			url.setRequestProperty("Accept", "application/x-javascript,text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			url.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
			url.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			url.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			url.setRequestProperty("Cache-Control", "max-age=0");
			url.setRequestProperty("Host", "service.channel.mtime.com");
			url.setRequestProperty("Upgrade-Insecure-Requests", "1");
			url.setRequestMethod("GET");
			url.setDoInput(true); 
			
			if(StringUtil.isNotEmpty(params)){
			//out put the request params
			url.setDoOutput(true); 			
			OutputStreamWriter osw = new OutputStreamWriter(url.getOutputStream()); 
			osw.write(params); 
			osw.flush(); 
			osw.close(); 
			}
			
			//connect
			url.connect();

			//get content
			s=getContentFromIn(url,"UTF-8");
			//System.out.println(s);

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		
		return s;
	}

	public static String getContentFromIn(HttpURLConnection urlConn,
			String charset) {
		BufferedReader br = null;
		StringBuilder content = new StringBuilder(200);
		InputStream in = null;
		try {
			if (null == urlConn) {
				return "";
			}
			if (StringUtil.isNotEmpty(urlConn.getContentEncoding())) {
				String encode = urlConn.getContentEncoding().toLowerCase();
				if (encode.indexOf("gzip") >= 0) {
					in = new GZIPInputStream(urlConn.getInputStream());
				}
			}

			if (null == in) {
				in = urlConn.getInputStream();
			}
			if (null != in) {
				br = new BufferedReader(new InputStreamReader(in, charset));
				String line = "";
				while ((line = br.readLine()) != null) {
					content.append(line);
				}
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(urlConn.getContentEncoding());
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
		}
		return content.toString();
	}
}
