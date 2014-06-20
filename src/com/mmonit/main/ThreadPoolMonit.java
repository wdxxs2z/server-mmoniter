package com.mmonit.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.mmonit.handler.MainHandler;
import com.mmonit.handler.DetectMonitMapHandler;
import com.mmonit.handler.MQWorkerHandler;
import com.mmonit.handler.MainHandlerNOMQ;
import com.mmonit.handler.WorkHandler;
import com.mmonit.operator.MonitSerBusOperator;
import com.mmonit.utils.PropertiesUtil;
import com.mmonit.utils.SpringUtil;

public class ThreadPoolMonit {
	
	/**
	 * 使用固定线程数的newFixedThreadPool管理线程池
	 * 共享资源：ServerSocket ConcurrentHashMap 
	 * */
	private ServerSocket serverSocket = null;
	private ExecutorService executorService = null;
	ConcurrentHashMap<String, String> concurrentHashMap = null;
	/*存储队列，工作线程使用*/
	Queue<String> normalMQueue = null;
	Queue<String> eventMQueue = null;
	static Integer monitserverport = null;
	static Boolean isactivemq = false;
	static Integer threadpoolsize = null;
	
	static {
		monitserverport = Integer.parseInt(PropertiesUtil.getProValue("./monitconf.properties", "monitserverport"));
		isactivemq = Boolean.parseBoolean(PropertiesUtil.getProValue("./monitconf.properties", "isactivemq"));
		threadpoolsize = Integer.parseInt(PropertiesUtil.getProValue("./monitconf.properties", "threadpoolsize"));
	}
	
	/**
	 * 构造 初始化 线程
	 * @throws IOException 
	 * */
	public ThreadPoolMonit() throws IOException {
		/*初始化线程*/
		serverSocket = new ServerSocket(monitserverport);
		concurrentHashMap = new ConcurrentHashMap<String, String>();
		normalMQueue = new LinkedBlockingQueue<String>();//存储需要数据库操作的monit xml
		eventMQueue = new LinkedBlockingQueue<String>();//存储active处理带事件的queue
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*threadpoolsize);
		System.out.println("Monit Server Start.");
	}
	
	/**
	 * 使用线程接收socket 
	 * 使用handle来处理接收的数据
	 * */
	public void service(){
		init();//初始化 包括spring 数据库等	
		switch (isactivemq.toString()) {
		case "false":
			while(true){
				Socket socket = null;
				try {
					socket = serverSocket.accept();
					/*将得到的socket投入线程池中 重新定义concurrentHashMap*/
					executorService.execute(new MainHandlerNOMQ(socket,concurrentHashMap,normalMQueue));
					/*得到消息队列，使用工作线程对其进行数据IO操作，减轻socket的压力*/
					executorService.execute(new WorkHandler(normalMQueue));
					/*监控concurrentHashMap 发现某台agent连接不上 则从map中删除该节点*/
					executorService.execute(new DetectMonitMapHandler(socket, concurrentHashMap));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		case "true" :
			while(true){
				Socket socket = null;
				try {
					socket = serverSocket.accept();
					/*将得到的socket投入线程池中 重新定义concurrentHashMap*/
					executorService.execute(new MainHandler(socket,concurrentHashMap,normalMQueue,eventMQueue));
					/*得到消息队列，使用工作线程对其进行数据IO操作，减轻socket的压力*/
					executorService.execute(new WorkHandler(normalMQueue));
					/*activeMQ 的处理消息队列*/
					executorService.execute(new MQWorkerHandler(eventMQueue));
					/*监控concurrentHashMap 发现某台agent连接不上 则从map中删除该节点*/
					executorService.execute(new DetectMonitMapHandler(socket, concurrentHashMap));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private void init() {
		/*启动容器*/
		SpringUtil.start();
		MonitSerBusOperator monitSerBusOperator=(MonitSerBusOperator) SpringUtil.getBean(MonitSerBusOperator.class);
		monitSerBusOperator.deleteMonitSerBus();
		
	}

	public static void main(String[] args) throws IOException {
		new ThreadPoolMonit().service();// 只负责写
		
		
	}

}
