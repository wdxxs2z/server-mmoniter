package com.abchina.bean;

import java.io.Serializable;

public class RemotePortBean implements Serializable {

	/**
	 * remoteHostBean one remoteHost to one or muti port
	 */
	private static final long serialVersionUID = 1L;
	
	/*port id*/
	private int remote_port_id;

	/*port*/
	private int port;
	
	/*muti port to one remoteHostbean*/
	private RemoteHostBean remoteHostBean;
	
	/*port to protocol*/
	private String protocol;
	
	/*type*/
	private String type;
	
	/*touch the host port response time*/
	private float responsetime;
	
	/*monit remotehost hostname*/
	private String remoteHostName;
	
	/*monit  remoteprot monitid*/
	private String remotefeed;

	public int getRemote_port_id() {
		return remote_port_id;
	}

	public void setRemote_port_id(int remote_port_id) {
		this.remote_port_id = remote_port_id;
	}

	public String getRemoteHostName() {
		return remoteHostName;
	}

	public void setRemoteHostName(String remoteHostName) {
		this.remoteHostName = remoteHostName;
	}

	public RemoteHostBean getRemoteHostBean() {
		return remoteHostBean;
	}

	public void setRemoteHostBean(RemoteHostBean remoteHostBean) {
		this.remoteHostBean = remoteHostBean;
	}

	public RemotePortBean() {
		super();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getResponsetime() {
		return responsetime;
	}

	public void setResponsetime(float responsetime) {
		this.responsetime = responsetime;
	}

	public String getRemotefeed() {
		return remotefeed;
	}

	public void setRemotefeed(String remotefeed) {
		this.remotefeed = remotefeed;
	}
}
