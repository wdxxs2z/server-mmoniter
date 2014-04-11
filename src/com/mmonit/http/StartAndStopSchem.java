package com.mmonit.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.ow2.util.base64.Base64;

public class StartAndStopSchem {
	
//	private final static String START = "start";
//	private final static String STOP  = "stop";
//	private final static String RESTART = "restart";
//	private final static String UNMONITOR = "unmonitor";
	
	public static void startAndStopSchem() throws Exception{
		//建立url链接
				//http://192.168.64.111
				URL url;
				StringBuffer urlhook = new StringBuffer();
				urlhook.append("http://");
				/*选择服务器*/
				System.out.println("请输入服务器：");
				BufferedReader strip = new BufferedReader(new InputStreamReader(System.in));
				String ip = strip.readLine();
				urlhook.append(ip+":2812/");
				System.out.println("请输入进程名：");
				BufferedReader strp = new BufferedReader(new InputStreamReader(System.in));
				String process = strp.readLine();
				urlhook.append(process + "?action=");
				System.out.println("请输入您的操作：start,stop,restart,unmonitor");
				BufferedReader strAction = new BufferedReader(new InputStreamReader(System.in));
				String action = strAction.readLine();
				urlhook.append(action);
				
				url = new URL(urlhook.toString());
				URLConnection connection = url.openConnection();
				//发送用户名和密码
				String encode = new String(Base64.encode(new String("admin" + ":" + "monit").getBytes()));
				System.out.println(encode);
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestProperty("Authorization", "Basic " + encode);
				
				//得到服务端响应
				String s_line = "";
				String s_totle = "";
				InputStream inputStream = connection.getInputStream();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
				while((s_line=buffer.readLine())!=null){
					s_totle += s_line + "\r\n";
				}
				System.out.println(s_totle);
	}

	public static void main(String[] args) throws Exception {
		startAndStopSchem();
	}

}
