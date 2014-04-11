package com.abchina.bean;

import java.io.Serializable;
import java.util.List;

public class RemoteHostBean implements Serializable {

	/**
	 * remoteHost monit type:4
	 */
	private static final long serialVersionUID = 1L;
	
	/*remotHostid*/
	private String remote_id;

	/*remoteHost Name*/
	private String remoteHostName;
	
	/*remoteHost collected_sec*/
	private String collected_sec;
	
	/*collected_usec*/
	private String collected_usec;
	
	/*remoteHost Status message*/
	private int remoteHostStatus;
	
	/*status_message*/
	private String remote_status_message;
	
	/*remoteHost port status*/
	private List<RemotePortBean> remotePorts;
	
	/*remoteHost monitId*/
	private String monitId;

	public String getRemote_id() {
		return remote_id;
	}

	public void setRemote_id(String remote_id) {
		this.remote_id = remote_id;
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

	/*muti remoteHost to one monitbean*/
	private MonitBean monitBean;
	
	public MonitBean getMonitBean() {
		return monitBean;
	}

	public void setMonitBean(MonitBean monitBean) {
		this.monitBean = monitBean;
	}

	public RemoteHostBean() {
		super();
	}

	public String getRemoteHostName() {
		return remoteHostName;
	}

	public void setRemoteHostName(String remoteHostName) {
		this.remoteHostName = remoteHostName;
	}

	public int getRemoteHostStatus() {
		return remoteHostStatus;
	}

	public void setRemoteHostStatus(int remoteHostStatus) {
		this.remoteHostStatus = remoteHostStatus;
	}	

	public String getRemote_status_message() {
		return remote_status_message;
	}

	public void setRemote_status_message(String remote_status_message) {
		this.remote_status_message = remote_status_message;
	}

	public List<RemotePortBean> getRemotePorts() {
		return remotePorts;
	}

	public void setRemotePorts(List<RemotePortBean> remotePorts) {
		this.remotePorts = remotePorts;
	}

	public String getMonitId() {
		return monitId;
	}

	public void setMonitId(String monitId) {
		this.monitId = monitId;
	}
}
