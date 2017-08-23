package com.xiaotu.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class KeyWordFormat {

	private static String arr_rp[] = { "TV", "tv", "未删减", "未剪辑", "剪辑", "卫视", "国语", "精华", "dvd", "DVD", "定制", "高清",
			"央视", "内地", "韩语", "全明星", "粤语", "完整", "网络", "清晰", "片花版", "吐槽", "海外", "泰语", "台湾", "中国", "精简", "脱水", "标准",
			"精编", "新版", "电视", "大陆", "香港", "新春特别", "电影", "免费版", "无字幕", "无声", "原声", "重制", "抢鲜", "英文", "特别版", "加长", "弹幕" };

	public static String getFormatKeyword(String keyWord) {
		String s = keyWord;
		
		//清除串内所有特殊字符
		s = stringFilter(s);
		
		//System.out.println(s);
		if(s.indexOf("版")>-1){
			for (String item : arr_rp) {
				if (s.indexOf(item)>-1) {
					//替换空格后，如果末尾是【版】，则截取数组元素起始之前，如【花千骨未删减版】
					//否则，截取元素之后的串，如【电视剧版西游记】
					s=s.replace(" ", "");
					
					if(s.endsWith("版")){
						s = s.substring(0, s.indexOf(item));
					}else{
						s = s.substring(s.indexOf("版")+1);
					}
					
					//找到匹配元素后跳出循环
					break;
				}
			}
			
			//如果仍然存在类似【08版武松】的不识别类型，截取
			if(s.indexOf("版")>-1&&s.indexOf("版")<s.length()-1){
				s = s.substring(s.indexOf("版")+1);
			}
			s=s.replace(" ", "");
		}
		return s;
	}

	// 过滤特殊字符
	public static String stringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字 ，{},"// String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|':;,{}\\-'\\[\\].·<>/?~！@#￥%……&*（）——+|【】‘；：”“’，。、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	 public static void main(String[] args) {
	 System.out.println(getFormatKeyword("花千· 骨（特‘别-*版)"));
	 System.out.println(getFormatKeyword("花千·骨（ 高清网络特‘别-*版)"));
	 System.out.println(getFormatKeyword("08版@# &*，；花千·骨"));
	 }
}
