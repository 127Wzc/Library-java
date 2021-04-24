package com.library.tolibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TeseMain {
	static String pageIndex = "https://wechat.laixuanzuo.com/index.php/reserve/index.html?f=wechat";

	static String pagePre = "https://wechat.laixuanzuo.com/index.php/prereserve/index.html";

	/**
	 * Session用户标识
	 * 8楼西电子 11075 21,12
	 */
	static String session = "bad1ee53b7a82d0e1b9d75e1a5fa8610";

	static String pageCenter = "https://wechat.laixuanzuo.com/index.php/center.html";

	public static void main(String[] args) throws Exception {
		
		//场馆号
		String libid = "11075";
		//座位号
		String key = "21,12";

		PreWindwo preWindwo = new PreWindwo();

//		Scanner sc = new Scanner(System.in);
//		System.out.println("请输入Session信息");
//		session = sc.nextLine();
//
//		System.out.println("请输入座位号格式为libid+key");
//		System.out.println("如(七楼中230为 11061/59,37)");

//		String libkey = sc.nextLine();
//		if ((libkey.split("/")).length == 2) {
//			libid = libkey.split("/")[0];
//			key = libkey.split("/")[1];
//		} else {
//			System.out.println("输入信息错误请重新输入");
//
//			return;
//		}
		
		System.out.println("本次座位信息为  libid=" + libid + "    key=" + key);
		long startTime = System.currentTimeMillis() / 1000L;
		Document document = function(pageIndex, RrowerConfig.initPageIndex(startTime, session), startTime, session);
		if (!getPersonInfo(session)) {
			return;
		}
		if (isTime()) {
			System.out.println("开始时间" + LocalTime.now());
			String r_url = null;
			while (r_url == null) {
				r_url = getRequsetUrl(libid, key);
			}

			preWindwo.setR_url(r_url);
			preWindwo.setStartTime(startTime);

			Thread t1 = new Thread(preWindwo);

			Thread t2 = new Thread(preWindwo);
			Thread t3 = new Thread(preWindwo);

			t1.start();
			t2.start();
			t3.start();
		}
	}

	public static Document toFunction(String pre_url, long startTime) throws Exception {
		long secondTime = System.currentTimeMillis() / 1000L;
		return function(pre_url, RrowerConfig.initBookPre(startTime + "", secondTime + "", session), secondTime,
				session);
	}

	public static boolean isTime() throws ParseException, InterruptedException {
		LocalDate data = LocalDate.now();
		String goalTime = data + " 19:50:00";
		System.out.println("目标时间：" + goalTime);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long goal_time = dateformat.parse(goalTime).getTime();
		System.out.println(goal_time);
		while (System.currentTimeMillis() < goal_time) {
			System.out.println("预计等待：" + ((goal_time - System.currentTimeMillis()) / 1000L) + "秒");
			Thread.sleep(1000L);
		}
		return true;
	}

	public static String getRequsetUrl(String libid, String key) throws Exception {
		long startTime = System.currentTimeMillis() / 1000L;

		String code = getCode(session, startTime);
		System.out.println("解密后:" + code);
		if (code.equals("未开放预约页面")) {
			System.out.println("获取动态Code失败，已经抢到或未开放");
			return null;
		}

		String yzm = getYzm(startTime);
		if (yzm == null) {
			System.out.println("验证码渠道已经改变，请明天再来");

			return null;
		}
		System.out.println("验证码为：" + yzm);

		String pre_url = Util.getPageUrl(code, yzm, libid, key);
		System.out.println("请求地址：" + pre_url);
		return pre_url;
	}

	public static String getJson(Document result) throws JSONException {
		String result_json = result.getElementsByTag("body").text();
		System.out.println(result_json);

		JSONObject jsonObject = new JSONObject(result_json);
		String msg = jsonObject.getString("msg");
		return msg;
	}

	public static String getYzm(long startTime) throws Exception {
		String image_url = "https://wechat.laixuanzuo.com/index.php/misc/verify";
		String img = function_header(image_url,
				RrowerConfig.initGetNameVerify(session, System.currentTimeMillis() / 1000L), startTime, session);
		String[] img_codes = img.split("/");
		String img_name = img_codes[img_codes.length - 1];
		return Util.getYzm(img_name.split("\\.")[0]);
	}

	public static String getCode(String session, long startTime) throws Exception {
		long startTime2 = System.currentTimeMillis() / 1000L;
		Document cod_document = function(pagePre, RrowerConfig.initPageIndex(startTime, session), startTime2, session);
		String regex = "<script src=\"(.*?)\"";
		Pattern pa = Pattern.compile(regex);
		Matcher ma = pa.matcher(cod_document.toString());
		String jscode_url = null;
		int flag = 0;
		while (ma.find()) {
			jscode_url = ma.group();
			System.out.println(jscode_url);
			flag++;
		}
		System.out.println("js脚本匹配数量：" + flag);
		String[] jscode_urls = null;
		if (flag == 2) {
			jscode_urls = jscode_url.split("/");
		} else {
			return "未开放预约页面";
		}
		String now_code = jscode_urls[jscode_urls.length - 1];
		System.out.println(now_code);
		String[] codes = now_code.split("\\.");
		System.out.println("加密js头：" + codes[0]);
		String code = Util.getJscode(codes[0]);
		return code;
	}

	public static Document function(String url, Map<String, String> map, long startTime2, String session)
			throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		long startTime = System.currentTimeMillis() / 1000L;

		Set<Map.Entry<String, String>> entrySet = map.entrySet();

		Iterator<Map.Entry<String, String>> it = entrySet.iterator();

		while (it.hasNext()) {

			Map.Entry<String, String> entry = it.next();
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}

		CloseableHttpResponse response = httpClient.execute((HttpUriRequest) httpGet);

		if (response.getStatusLine().getStatusCode() == 200) {
			String html = "";

			if (response.getEntity() != null) {
				html = EntityUtils.toString(response.getEntity(), "UTF-8");
				Document document = parseHtml(html);
				return document;
			}
		}

		return null;
	}

	public static String function_header(String url, Map<String, String> map, long startTime2, String session)
			throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		BasicHttpContext basicHttpContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(url);
		long startTime = System.currentTimeMillis() / 1000L;

		Set<Map.Entry<String, String>> entrySet = map.entrySet();

		Iterator<Map.Entry<String, String>> it = entrySet.iterator();

		while (it.hasNext()) {

			Map.Entry<String, String> entry = it.next();
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}

		CloseableHttpResponse response = httpClient.execute((HttpUriRequest) httpGet, (HttpContext) basicHttpContext);

		HttpUriRequest realRequest = (HttpUriRequest) basicHttpContext.getAttribute("http.request");

		String real_url = realRequest.getURI().toString();
		return real_url;
	}

	private static Document parseHtml(String html) throws Exception {
		Document document = Jsoup.parse(html);
		return document;
	}

	private static boolean getPersonInfo(String session) throws Exception {
		long startTime = System.currentTimeMillis() / 1000L;
		Document document = function(pageIndex, RrowerConfig.initPageIndex(startTime, session), startTime, session);
		Elements user_info = null;
		try {
			user_info = document.getElementsByClass("user-title");
			System.out.println("您的系统昵称为：" + user_info.text());
		} catch (Exception e) {

			System.out.println("Session错误或过期");
			return false;
		}
		return true;
	}
}
