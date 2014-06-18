package com.mmonit.handler;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mmonit.message.MonitEventMessage;
import com.mmonit.utils.MonitXml2O;
import com.mmonit.utils.SpringUtil;

public class MQWorkerHandler implements Runnable{

	Queue<String> eventMQueue = new LinkedBlockingQueue<String>();
	
	private MonitEventMessage monitEventMessage;
	
			
	public MQWorkerHandler(Queue<String> eventMQueue) {
		super();
		this.eventMQueue = eventMQueue;
		monitEventMessage = (MonitEventMessage) SpringUtil
				.getBean(MonitEventMessage.class);
	}

	@Override	
	public void run() {
		try {
			if(eventMQueue.isEmpty()){
				return;
			}
			String substring = eventMQueue.remove();
			String monitId = MonitXml2O.getMonitId(substring);
			/* 将monit事件添加到activeMQ中去 */
			monitEventMessage.sendMonitEventMessage(monitId,substring);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
