package com.mmonit.messageImpl;

import com.mmonit.activemqTask.ReceiverMonitEvent;
import com.mmonit.activemqTask.SenderMonitEvent;
import com.mmonit.message.MonitEventMessage;

public class MonitEventMessageImpl implements MonitEventMessage {

	private SenderMonitEvent sender;
	private ReceiverMonitEvent receiver;

	public SenderMonitEvent getSender() {
		return sender;
	}

	public void setSender(SenderMonitEvent sender) {
		this.sender = sender;
	}

	public ReceiverMonitEvent getReceiver() {
		return receiver;
	}

	public void setReceiver(ReceiverMonitEvent receiver) {
		this.receiver = receiver;
	}

	@Override
	public void sendMonitEventMessage(String monitId,String monitxml) {
		sender.sendMonitEventMessage(monitId,monitxml);
	}

	@Override
	public void receiveMonitEventMessage(String monitxml) {
		

	}

}
