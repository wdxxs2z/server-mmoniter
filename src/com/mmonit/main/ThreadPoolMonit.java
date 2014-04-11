package com.mmonit.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mmonit.bean.MonitBean;
import com.mmonit.handler.Handler;
import com.mmonit.handler.Handler2;
import com.mmonit.handler.Handler3;
import com.mmonit.utils.SpringUtil;

public class ThreadPoolMonit {
	
	/**
	 * 使用固定线程数的newFixedThreadPool管理线程池
	 * 共享资源：ServerSocket ConcurrentHashMap 
	 * */
	/*服务端监听端口*/
	private int serverPort = 8080;
	
	private ServerSocket serverSocket = null;
	private ExecutorService executorService = null;
	/*单CPU对应的线程池大小*/
	private final int POOL_SIZE = 2;
	static ConcurrentHashMap<String, String> concurrentHashMap = null;
	/*monit Bean*/
	private static MonitBean monitBean = null;
	 
	
	/**
	 * 构造 初始化 线程
	 * @throws IOException 
	 * */
	public ThreadPoolMonit() throws IOException {
		serverSocket = new ServerSocket(serverPort);
		concurrentHashMap = new ConcurrentHashMap<String, String>();
		monitBean = new MonitBean();
		/*初始化线程*/
		
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
		System.out.println("Monit Server Start.");
	}
	
	/**
	 * 使用线程接收socket 
	 * 使用handle来处理接收的数据
	 * */
	public void service(){
		/*启动容器*/
		SpringUtil.start();
		
		System.out.println(SpringUtil.getBean("jdbcTemplate"));
		while(true){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				/*将得到的socket投入线程池中 重新定义concurrentHashMap*/
				executorService.execute(new Handler(socket,concurrentHashMap));
				/*根据concurrentHashMap中的对应关系 将MonitBean对象封装 并打印出值*/
				executorService.execute(new Handler2(concurrentHashMap,monitBean));
				/*监控concurrentHashMap 发现某台agent连接不上 则从map中删除该节点*/
				executorService.execute(new Handler3(socket, concurrentHashMap));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		new ThreadPoolMonit().service();// 只负责写
		
		
	}

}
