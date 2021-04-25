package tolibrary;

import org.jsoup.nodes.Document;

import java.time.LocalTime;

public class PreWindow implements Runnable {
	private String r_url;
	private long startTime;
	private boolean  flag=false;

	public void run() {
		//线程运行次数最大暂定为10次结束
		int nums = 10;
		try {
			Document result = Main.toFunction(this.r_url, this.startTime);
			if (result != null) {
				String re_msg = Util.getJson(result);
				System.out.println(Thread.currentThread().getName() + "第一次结果：" + re_msg);
				System.out.println("结束时间" + LocalTime.now());
				isEnd(re_msg);
				while (!flag&&nums>0) {
					result = Main.toFunction(this.r_url, this.startTime);
					re_msg = Util.getJson(result);
					System.out.println(Thread.currentThread().getName() + "第" + (10 - nums + 2) + "结果：" + re_msg);
					System.out.println("结束时间" + LocalTime.now());
					isEnd(re_msg);
					nums--;
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void isEnd(String re_msg) {
		if("预定座位成功".equals(re_msg)) flag=true;
	}

	public String getR_url() {
		return this.r_url;
	}

	public void setR_url(String r_url) {
		this.r_url = r_url;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}