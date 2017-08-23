package com.test;

import java.awt.List;

import com.xiaotu.common.util.GsonUtils;

public class Test {

	public static void main(String[] args) {
		String data = "[{roleList=[], roundId=X15E07D770C527433FE221D2}, {roleList=[常宝童, 常天慧], roundId=X15E07D770C627443FD4A165]";
		
		List list = GsonUtils.fromJson(data, List.class);
		
		System.out.println(list);
	}

}
