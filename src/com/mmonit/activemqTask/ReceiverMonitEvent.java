package com.mmonit.activemqTask;

import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;

public class ReceiverMonitEvent {

	private JmsTemplate jmsTemplate;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public String receiveMonitEventMessage(){
		String receiveMessage = "";
		TextMessage receive = (TextMessage) jmsTemplate.receive();
		try {
			String jmsMessageID = receive.getJMSMessageID();
			System.out.println("jmsMessageID: " + jmsMessageID);
			String monitId = receive.getStringProperty("monitid");
			System.out.println("monitID: " + monitId);
			String text = receive.getText();
			System.out.println("message: " + text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return receiveMessage;
		
	}
	
	
	
}
