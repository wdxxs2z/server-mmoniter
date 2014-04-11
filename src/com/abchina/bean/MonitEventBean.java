package com.abchina.bean;

import java.io.Serializable;

public class MonitEventBean implements Serializable {

	private static final long serialVersionUID = -208422763564418759L;
	
	/*Event id*/
	private int event_id;
	
	/*MonitBean 一对多关联monit*/
	private MonitBean monitBean;
	
	/*collected_sec*/
	private String collected_sec;
	
	/*collected_usec*/
	private String collected_usec;
	
	/*service event*/
	private String service;
	
	/*message*/
	private String message;
	
	/*Service Type*/
	private int type;
	
	/*server status*/
	private int serStatus;
	
	/*server action*/
	private int action;
	
	/*monit id*/
	private String monitId;

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public MonitBean getMonitBean() {
		return monitBean;
	}

	public void setMonitBean(MonitBean monitBean) {
		this.monitBean = monitBean;
	}

	public String getCollected_sec() {
		return collected_sec;
	}

	public void setCollected_sec(String collected_sec) {
		this.collected_sec = collected_sec;
	}
	
	public String getCollected_usec() {
		return collected_usec;
	}

	public void setCollected_usec(String collected_usec) {
		this.collected_usec = collected_usec;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSerStatus() {
		return serStatus;
	}

	public void setSerStatus(int serStatus) {
		this.serStatus = serStatus;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getMonitId() {
		return monitId;
	}

	public void setMonitId(String monitId) {
		this.monitId = monitId;
	}

}
