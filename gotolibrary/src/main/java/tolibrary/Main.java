package tolibrary;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    //只需修改以下三个参数即可  session 个人Session  libid场馆号   key座位号
    static String session = "4eb9f0de540a163b281fa184fe7891df";
    static String libid = "10084";
    static String key = "12,28";

    public static void main(String[] args) throws Exception {
        PreWindow preWindwo = new PreWindow();
        //init();此为自定义输入信息，如上面确认无误后可不用更改
        System.out.println("本次座位信息为  libid=" + libid + "    key=" + key);
        long startTime = System.currentTimeMillis() / 1000L;
        Document document = function(pageIndex, RequestConfig.initPageIndex(startTime, session), startTime, session);
        if (!getPersonInfo(session)) {
            return;
        }
        if (Util.isTime()) {
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

    /**
     * 自定义输入内容
     */
    public static void init(){
        Scanner sc = new Scanner(System.in);
		System.out.println("请输入Session信息");
		session = sc.nextLine();
		System.out.println("请输入座位号格式为libid+key");
		System.out.println("如(七楼中230为 11061/59,37)");
		String libkey = sc.nextLine();
		if ((libkey.split("/")).length == 2) {
			libid = libkey.split("/")[0];
			key = libkey.split("/")[1];
		} else {
			System.out.println("输入信息错误请重新输入");
			return;
		}
    }


    //首页
    static String pageIndex = "https://wechat.laixuanzuo.com/index.php/reserve/index.html?f=wechat";
    //个人中心
    static String pageCenter = "https://wechat.laixuanzuo.com/index.php/center.html";
    //明日预约
    static String pagePre = "https://wechat.laixuanzuo.com/index.php/prereserve/index.html";

    /**
     * 发请求通用方法  如：请求个人信息、请求地址参数时
     * @param url  请求地址
     * @param map  请求头
     * @param startTime2  请求时间参数
     * @param session  请求Session
     */
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
                Document document = Util.parseHtml(html);
                return document;
            }
        }
        return null;
    }

    /**
     *线程中通用的请求发送方式，也即最终的预订座位请求
     * @param pre_url  请求地址
     * @param startTime 请求时间

     */
    public static Document toFunction(String pre_url, long startTime) throws Exception {
        long secondTime = System.currentTimeMillis() / 1000L;
        return function(pre_url, RequestConfig.initBookPre(startTime + "", secondTime + "", session), secondTime,
                session);
    }
    /**
     *  获取个人信息来确认是否登入系统
     * @param session
     * @return
     */
    public static boolean getPersonInfo(String session) throws Exception {
        long startTime = System.currentTimeMillis() / 1000L;
        Document document = function(pageIndex, RequestConfig.initPageIndex(startTime, session), startTime, session);
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

    /**
     * 拼接请求地址
     * @param libid  场馆号
     * @param key   座位号
     * @return  请求地址
     */
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


    /**
     *  获取请求地址动态加密参数
     * @param session
     * @param startTime
     */
    public static String getCode(String session, long startTime) throws Exception {
        long startTime2 = System.currentTimeMillis() / 1000L;
        Document cod_document = function(pagePre, RequestConfig.initPageIndex(startTime, session), startTime2, session);
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

    /**
     * 获取验证码
     * @param startTime
     */
    public static String getYzm(long startTime) throws Exception {
        String image_url = "https://wechat.laixuanzuo.com/index.php/misc/verify";
        String img = function_header(image_url,
                RequestConfig.initGetNameVerify(session, System.currentTimeMillis() / 1000L), startTime, session);
        String[] img_codes = img.split("/");
        String img_name = img_codes[img_codes.length - 1];
        return Util.getYzm(img_name.split("\\.")[0]);
    }

    /**
     * 获取验证码图片请求 与通用的有一点不同（验证码是在一个重定向地址中）
     * @param url
     * @param map
     * @param startTime2
     * @param session
     */
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
}
