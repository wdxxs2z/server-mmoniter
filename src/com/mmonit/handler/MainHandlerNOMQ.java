package com.mmonit.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.mmonit.utils.MonitXml2O;

public class MainHandlerNOMQ implements Runnable {

	private Socket socket = null;
	private ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
	private Queue<String> normalMQueue = new LinkedBlockingQueue<String>();
	
	public MainHandlerNOMQ(Socket socket,
			ConcurrentHashMap<String, String> concurrentHashMap,
			Queue<String> normalMQueue) {
		this.socket = socket;
		this.concurrentHashMap = concurrentHashMap;
		this.normalMQueue = normalMQueue;
	}

	@Override
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			String s_line = "";
			String s_totle = "";
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					inputStream));
			while ((s_line = buffer.readLine()) != null) {
				s_totle += s_line + "\r\n";
			}
			int lastIndexOf = s_totle.lastIndexOf("<?xml");
			String substring = s_totle.substring(lastIndexOf);
			normalMQueue.add(substring);
			int judge = substring.lastIndexOf("event");
			if (judge < 0) {
				ConcurrentHashMap<String, String> monitIdInfo = MonitXml2O
						.getMonitId(substring, concurrentHashMap);
				// 遍历monitIdInfo keys 重新存储
				Enumeration<String> monitIds = monitIdInfo.keys();
				String monitId = monitIds.nextElement();
				concurrentHashMap.put(monitId, substring);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null)
					socket.close(); // 断开连接
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
