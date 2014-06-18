package com.mmonit.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.mmonit.handler.Handler;
import com.mmonit.handler.Handler3;
import com.mmonit.handler.MQWorkerHandler;
import com.mmonit.handler.WorkHandler;
import com.mmonit.operator.MonitSerBusOperator;
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
	private final int POOL_SIZE = 4;
	ConcurrentHashMap<String, String> concurrentHashMap = null;
	/*存储队列，工作线程使用*/
	Queue<String> normalMQueue = null;
	Queue<String> eventMQueue = null;
	 
	
	/**
	 * 构造 初始化 线程
	 * @throws IOException 
	 * */
	public ThreadPoolMonit() throws IOException {
		/*初始化线程*/
		serverSocket = new ServerSocket(serverPort);
		concurrentHashMap = new ConcurrentHashMap<String, String>();
		normalMQueue = new LinkedBlockingQueue<String>();//存储需要数据库操作的monit xml
		eventMQueue = new LinkedBlockingQueue<String>();//存储active处理带事件的queue
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
		MonitSerBusOperator monitSerBusOperator=(MonitSerBusOperator) SpringUtil.getBean(MonitSerBusOperator.class);
		monitSerBusOperator.deleteMonitSerBus();		
		while(true){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				/*将得到的socket投入线程池中 重新定义concurrentHashMap*/
				executorService.execute(new Handler(socket,concurrentHashMap,normalMQueue,eventMQueue));
				/*得到消息队列，使用工作线程对其进行数据IO操作，减轻socket的压力*/
				executorService.execute(new WorkHandler(normalMQueue));
				/*activeMQ 的处理消息队列*/
				executorService.execute(new MQWorkerHandler(eventMQueue));
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
