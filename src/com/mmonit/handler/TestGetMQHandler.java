package com.mmonit.handler;

import com.mmonit.message.MonitEventMessage;
import com.mmonit.utils.SpringUtil;

public class TestGetMQHandler implements Runnable {

	private MonitEventMessage monitEventMessage;
	
	public TestGetMQHandler() {
		super();
		monitEventMessage = (MonitEventMessage) SpringUtil
				.getBean(MonitEventMessage.class);
	}

	@Override
	public void run() {
		monitEventMessage.receiveMonitEventMessage();
	}

}
