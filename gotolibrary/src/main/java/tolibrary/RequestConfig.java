package tolibrary;

import java.util.HashMap;
import java.util.Map;

public class RequestConfig {
	public static Map<String, String> initPageIndex(long startTime, String session) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Host", "wechat.laixuanzuo.com");
		map.put("Connection", "keep-alive");
		map.put("Cache-Control", "max-age=0");
		map.put("Accept", "text/html,application/xhtml+xml,application/xml;;q=0.8");
		map.put("Cookie",
				"Hm_lpvt_7838cef374eb966ae9ff502c68d6f098=" + startTime + ";Hm_lvt_7838cef374eb966ae9ff502c68d6f098="
						+ startTime + ";FROM_TYPE=weixin; wechatSESS_ID=" + session + "");
		map.put("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/605.1.15 (KHTML, like Gecko) MicroMessenger/2.3.26(0x12031a10) MacWechat NetType/WIFI WindowsWechat");
		map.put("Accept-Language", "zh-cn");
		map.put("Accept-Encoding", "br, gzip, deflate");

		return map;
	}

	public static Map<String, String> initBookPre(String time, String solidtime, String session) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Host", "wechat.laixuanzuo.com");
		map.put("Accept", "application/json, text/javasc; q=0.01");
		map.put("Connection", "keep-alive");
		map.put("X-Requested-With", "XMLHttpRequest");
		map.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36 QBCore/4.0.1295.400 QQBrowser/9.0.2524.400 Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2875.116 Safari/537.36 NetType/WIFI MicroMessenger/7.0.5 WindowsWechat");
		map.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.5;q=0.4");
		map.put("Referer", "https://wechat.laixuanzuo.com/index.php/prereserve/index.html");
		map.put("Accept-Encoding", "gzip, deflate");
		map.put("Cookie", "wechatSESS_ID=" + session + "; FROM_TYPE=weixin; Hm_lvt_7838cef374eb966ae9ff502c68d6f098="
				+ time + "," + solidtime + "; Hm_lpvt_7838cef374eb966ae9ff502c68d6f098=" + solidtime + "");

		return map;
	}

	public static Map<String, String> initBookNow(String time, String solidtime, String session) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Host", "wechat.laixuanzuo.com");
		map.put("Accept", "application/json, text/javasc; q=0.01");
		map.put("Connection", "keep-alive");
		map.put("X-Requested-With", "XMLHttpRequest");
		map.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36 QBCore/4.0.1295.400 QQBrowser/9.0.2524.400 Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2875.116 Safari/537.36 NetType/WIFI MicroMessenger/7.0.5 WindowsWechat");
		map.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.5;q=0.4");
		map.put("Referer", "https://wechat.laixuanzuo.com/index.php/prereserve/index.html");
		map.put("Accept-Encoding", "gzip, deflate");
		map.put("Cookie", "wechatSESS_ID=" + session + "; FROM_TYPE=weixin; Hm_lvt_7838cef374eb966ae9ff502c68d6f098="
				+ time + "," + solidtime + "; Hm_lpvt_7838cef374eb966ae9ff502c68d6f098=" + solidtime + "");

		return map;
	}

	public static Map<String, String> initImage(String session, String time) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Host", "wechat.laixuanzuo.com");
		map.put("Connection", "keep-alive");
		map.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36 QBCore/4.0.1295.400 QQBrowser/9.0.2524.400 Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2875.116 Safari/537.36 NetType/WIFI MicroMessenger/7.0.5 WindowsWechat");
		map.put("Accept", "image/webp,im;q=0.8");
		map.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.5;q=0.4");
		map.put("Accept-Encoding", "gzip, deflate");
		map.put("Cookie", "wechatSESS_ID=" + session + "; FROM_TYPE=weixin;Hm_lvt_7838cef374eb966ae9ff502c68d6f098="
				+ time + "; Hm_lpvt_7838cef374eb966ae9ff502c68d6f098=" + time);

		return map;
	}

	public static Map<String, String> initGetNameVerify(String session, long l) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Host", "wechat.laixuanzuo.com");
		map.put("Connection", "keep-alive");
		map.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36 QBCore/4.0.1295.400 QQBrowser/9.0.2524.400 Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2875.116 Safari/537.36 NetType/WIFI MicroMessenger/7.0.5 WindowsWechat");
		map.put("Accept", "image/webp,im;q=0.8");
		map.put("Referer", "https://wechat.laixuanzuo.com/index.php/prereserve/index.html");
		map.put("Accept-Encoding", "gzip, deflate");
		map.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.5;q=0.4");
		map.put("Cookie", "wechatSESS_ID=" + session + "; FROM_TYPE=weixin; Hm_lvt_7838cef374eb966ae9ff502c68d6f098="
				+ l + "; Hm_lpvt_7838cef374eb966ae9ff502c68d6f098=" + l + "");

		return map;
	}
}
