package msh.productdiscountsystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private  static SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	private static void print(String log){
		System.out.println(sdf.format(new Date())+":"+log);
	}
	public static void debug(String log){
		print("DEBUG:"+log);
	}
	public static void error(String log){
		print("ERROR:"+log);
	}
	public static void info(String log){
		print("INFO:"+log);
	}
	public static void print(Object obj){
		System.out.println(obj);
	}
}
