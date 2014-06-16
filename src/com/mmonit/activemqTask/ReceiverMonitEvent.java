package com.mmonit.activemqTask;

import org.springframework.jms.core.JmsTemplate;

public class ReceiverMonitEvent {

	private JmsTemplate jmsTemplate;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	
	
}
