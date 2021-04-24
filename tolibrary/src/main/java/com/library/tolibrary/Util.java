package com.library.tolibrary;

import java.util.Map;

public class Util {
	public static String getPageUrl(String code, String yzm, String libid, String key) {
		return "https://wechat.laixuanzuo.com/index.php/prereserve/save/libid=" + libid + "&" + code + "=" + key
				+ "&yzm=" + yzm;
	}

	public static String getJscode(String code) {
		Map<String, String> map = Jscode.codes;
		return map.get(code);
	}

	public static String getYzm(String yzm) {
		Map<String, String> map = Jscode.yzms;
		return map.get(yzm);
	}
}