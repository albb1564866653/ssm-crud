package cn.ny.service.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Encode {

	//md5加密
	public static String md5Encode(byte[] input){//byte[] ("abc")-->String("123134fjdf")
		String md5Hex = DigestUtils.md5Hex(input);
		
		StringBuffer sb=new StringBuffer(md5Hex);
		sb.insert(0, "upwdencode");
        return sb.toString();
    }
	
	public static void main(String[] args) {
		String str="15918969438";
		str=md5Encode(str.getBytes());
		System.out.println(str);
	}

}
