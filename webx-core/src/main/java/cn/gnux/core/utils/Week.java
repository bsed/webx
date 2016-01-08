/**
 * Project Name:webx-core
 * File Name:Week.java
 * Package Name:cn.gnux.core.utils
 * Date:2016-1-7����1:55:21
 * Copyright (c) 2016, xfzhaog@isoftstone.com All Rights Reserved.
 *
 */

package cn.gnux.core.utils;

public enum Week {
	
	MONDAY("����һ", "Monday", "Mon.", 1), 
	TUESDAY("���ڶ�", "Tuesday", "Tues.", 2), 
	WEDNESDAY("������", "Wednesday", "Wed.", 3), 
	THURSDAY("������", "Thursday","Thur.", 4), 
	FRIDAY("������", "Friday", "Fri.", 5), 
	SATURDAY("������","Saturday", "Sat.", 6), 
	SUNDAY("������", "Sunday", "Sun.", 7);

	String name_cn;
	String name_en;
	String name_enShort;
	int number;

	Week(String name_cn, String name_en, String name_enShort, int number) {
		this.name_cn = name_cn;
		this.name_en = name_en;
		this.name_enShort = name_enShort;
		this.number = number;
	}

	public String getChineseName() {
		return name_cn;
	}

	public String getName() {
		return name_en;
	}

	public String getShortName() {
		return name_enShort;
	}

	public int getNumber() {
		return number;
	}
}