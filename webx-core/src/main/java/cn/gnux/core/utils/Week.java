/**
 * Project Name:webx-core
 * File Name:Week.java
 * Package Name:cn.gnux.core.utils
 * Date:2016-1-7下午1:55:21
 * Copyright (c) 2016, xfzhaog@isoftstone.com All Rights Reserved.
 *
 */

package cn.gnux.core.utils;

public enum Week {
	
	MONDAY("星期一", "Monday", "Mon.", 1), 
	TUESDAY("星期二", "Tuesday", "Tues.", 2), 
	WEDNESDAY("星期三", "Wednesday", "Wed.", 3), 
	THURSDAY("星期四", "Thursday","Thur.", 4), 
	FRIDAY("星期五", "Friday", "Fri.", 5), 
	SATURDAY("星期六","Saturday", "Sat.", 6), 
	SUNDAY("星期日", "Sunday", "Sun.", 7);

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