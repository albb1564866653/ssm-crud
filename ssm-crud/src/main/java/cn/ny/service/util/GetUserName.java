package cn.ny.service.util;

import java.util.HashMap;
import java.util.Random;

public class GetUserName {
	
	
	public synchronized static String getUserName() {
		Random  r=new Random();
		StringBuffer sb=new StringBuffer();
		
		int index=r.nextInt(3);
		//随机生成10位、9位、8位、7位数字
		if(index==0) {//长度10
			int count=10;

			for(int i=0;i<count;i++) {
				int nextInt = r.nextInt(10)+1;
				
				sb.append(nextInt);
				
			}
			  
			 return sb.toString();
		}else if(index==1) {//9
			int count=9;
			for(int i=0;i<count;i++) {
				int nextInt = r.nextInt(10)+1;
				sb.append(nextInt);
			}
			 
			return sb.toString();
		}else if(index==2) {//8
			int count=8;
			for(int i=0;i<count;i++) {
				int nextInt = r.nextInt(10)+1;
				sb.append(nextInt);
			}
			 
			return sb.toString();
		}else {//7
			int count=7;
			for(int i=0;i<count;i++) {
				int nextInt = r.nextInt(10)+1;
				sb.append(nextInt);
			}
			 
			return sb.toString();
		} 
		
		 
	}

	
	
	public static void main(String[] args) {
		
		
		HashMap<String,String>map=new HashMap<String, String>();
		for(int i=0;i<1000000;i++) {
			//System.out.println(name1());
			map.put(getUserName(), i+"");
		}
		
		System.out.println("11:"+map.size());
		//name1();
		
		
		System.out.println(getUserName());
	}

}
