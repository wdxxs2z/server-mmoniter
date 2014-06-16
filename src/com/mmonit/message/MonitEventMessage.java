package com.mmonit.message;

public interface MonitEventMessage {

	public void sendMonitEventMessage(String monitxml);
	
	public void receiveMonitEventMessage(String monitxml);
	
}
