package com.abchina.handler;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.abchina.operator.MonitOperator;
import com.abchina.utils.MonitXml2O;
import com.abchina.utils.SpringUtil;

public class Handler3 implements Runnable{

	private Socket socket = null;
	private ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
	private MonitOperator monitOperator;
	private static final int monitStatus = 1;
	
	public Handler3(Socket socket,ConcurrentHashMap<String, String> concurrentHashMap) {
		this.socket = socket;
		this.concurrentHashMap = concurrentHashMap;
		monitOperator = (MonitOperator) SpringUtil.getBean(MonitOperator.class);
		
	}
		
	@Override
	public void run() {
		
		/*监听monit端的存活状态*/
		Iterator<Entry<String, String>> iterator = concurrentHashMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, String> next = iterator.next();
			String mId = next.getKey();
			String mInfo = next.getValue();
			String remoteHost = MonitXml2O.getRemoteHost(mId,mInfo);
			/*链接数据库 从map中获得的monit信息查询节点的存活 如果没有存活 则修改monit主机状态*/
			try {
				socket = new Socket(remoteHost, 2812);
			
			} catch (Exception e) {
				/*如果不能连接 则踢掉map中的mid  设置数据库 主机dead  1 标记*/
				monitOperator.updateMonitStatus(mId,monitStatus);
				concurrentHashMap.remove(mId);
				System.out.println("成功删除");
			} finally{
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
}
