package com.mmonit.activemqTask;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class SenderMonitEvent {
	
	private JmsTemplate jmsTemplate;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void sendMonitEventMessage(final String msgxml){
		final String uuid = UUID.randomUUID().toString();
		jmsTemplate.send(new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage msg = session.createTextMessage(msgxml);
				msg.setJMSMessageID("monit_event");
				msg.setJMSCorrelationID(uuid);
				return msg;
			}
		});
		
	}
	

}
