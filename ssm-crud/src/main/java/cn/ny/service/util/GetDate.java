package cn.ny.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GetDate {
	
	/**
	 * 获取时间
	 * @return
	 */
	public static String getdate(){
		DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd=sdf.format(new Date());
		return sd;
	}
	
	public static void main(String[] args) {
		System.out.println("当前时间："+getdate());
	}
}
