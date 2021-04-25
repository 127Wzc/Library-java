package tolibrary;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;

public class Util {

    //拼接地址参数
	public static String getPageUrl(String code, String yzm, String libid, String key) {
		return "https://wechat.laixuanzuo.com/index.php/prereserve/save/libid=" + libid + "&" + code + "=" + key
				+ "&yzm=" + yzm;
	}

	//获取请求地址后的参数
	public static String getJscode(String code) {
		Map<String, String> map = Jscode.codes;
		return map.get(code);
	}

	//获取验证码
	public static String getYzm(String yzm) {
		Map<String, String> map = Jscode.yzms;
		return map.get(yzm);
	}

	/**
	 * 解析json数据
	 * @param result
	 * @return
	 */
	public static String getJson(Document result) throws JSONException {
		String result_json = result.getElementsByTag("body").text();
		System.out.println(result_json);
		JSONObject jsonObject = new JSONObject(result_json);
		String msg = jsonObject.getString("msg");
		return msg;
	}

	/**
	 * 解析html页面
	 * @param html
	 * @return
	 */
	public static Document parseHtml(String html) throws Exception {
		Document document = Jsoup.parse(html);
		return document;
	}

	/**
	 * 定时来判断是否到达19：50
	 * @return
	 */
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


}