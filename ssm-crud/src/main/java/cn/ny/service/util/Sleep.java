package cn.ny.service.util;

public class Sleep {
	//模拟网络延迟
	public static void analogSleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
