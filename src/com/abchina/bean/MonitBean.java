package com.abchina.bean;

import java.io.Serializable;
import java.util.List;

public class MonitBean  implements Serializable {

	private static final long serialVersionUID = 5127323415181754183L;

	/**
	 * Monit info importent object
	 */
		
	/*monit id only one can read it*/
	private String monitId;
	
	/*monit version*/
	private String monitVersion;
		
	/*remote host name*/
	private String monitHostName;
	
	/*remote host IP*/
	private String monitHostIp;
	
	/*remote host status*/
	private int monitHostStatus;
	
	/*remote host port*/
	private String monitHostPort;
	
	/*system update time*/
	private String updateTime;
	
	/*platformName*/
	private String platformName;
	
	/*platformRelease*/
	private String platformRelease;
	
	/*platformVersion*/
	private String platformVersion;
	
	/*platformMachine*/
	private String platformMachine;
	
	/*platformCpu*/
	private int platformCpu;
	
	/*platformMem*/
	private int platformMem;
	
	/*platformSwap*/
	private int platformSwap;
	
	/*process object*/
	private List<ProcessBean> processBeans;
	
	/*remoteHost object*/
	private List<RemoteHostBean> remoteHostBeans;
	
	/*system object*/
	private SystemBean systemBean;
	
	/*monitEvent object*/
	private List<MonitEventBean> monitEventBeans;
	
	/*monitSerBus object*/
	private List<MonitSerBusBean> monitSerBusBeans;
	
	/*event count*/
	private int eventcount;
		
	public void setMonitVersion(String monitVersion) {
		this.monitVersion = monitVersion;
	}
	
	public String getMonitVersion() {
		return monitVersion;
	}
	

	public MonitBean() {
		super();
	}

	public String getMonitId() {
		return monitId;
	}

	public void setMonitId(String monitId) {
		this.monitId = monitId;
	}

	public String getMonitHostName() {
		return monitHostName;
	}

	public void setMonitHostName(String monitHostName) {
		this.monitHostName = monitHostName;
	}

	public String getMonitHostIp() {
		return monitHostIp;
	}

	public void setMonitHostIp(String monitHostIp) {
		this.monitHostIp = monitHostIp;
	}

	public int getMonitHostStatus() {
		return monitHostStatus;
	}

	public void setMonitHostStatus(int monitHostStatus) {
		this.monitHostStatus = monitHostStatus;
	}

	public String getMonitHostPort() {
		return monitHostPort;
	}

	public void setMonitHostPort(String monitHostPort) {
		this.monitHostPort = monitHostPort;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getPlatformRelease() {
		return platformRelease;
	}

	public void setPlatformRelease(String platformRelease) {
		this.platformRelease = platformRelease;
	}

	public String getPlatformVersion() {
		return platformVersion;
	}

	public void setPlatformVersion(String platformVersion) {
		this.platformVersion = platformVersion;
	}

	public String getPlatformMachine() {
		return platformMachine;
	}

	public void setPlatformMachine(String platformMachine) {
		this.platformMachine = platformMachine;
	}

	public int getPlatformCpu() {
		return platformCpu;
	}

	public void setPlatformCpu(int platformCpu) {
		this.platformCpu = platformCpu;
	}

	public int getPlatformMem() {
		return platformMem;
	}

	public void setPlatformMem(int platformMem) {
		this.platformMem = platformMem;
	}

	public int getPlatformSwap() {
		return platformSwap;
	}

	public void setPlatformSwap(int platformSwap) {
		this.platformSwap = platformSwap;
	}

	public List<ProcessBean> getProcessBeans() {
		return processBeans;
	}

	public void setProcessBeans(List<ProcessBean> processBeans) {
		this.processBeans = processBeans;
	}

	public List<RemoteHostBean> getRemoteHostBeans() {
		return remoteHostBeans;
	}

	public void setRemoteHostBeans(List<RemoteHostBean> remoteHostBeans) {
		this.remoteHostBeans = remoteHostBeans;
	}

	public SystemBean getSystemBean() {
		return systemBean;
	}

	public void setSystemBean(SystemBean systemBean) {
		this.systemBean = systemBean;
	}

	public List<MonitEventBean> getMonitEventBeans() {
		return monitEventBeans;
	}

	public void setMonitEventBeans(List<MonitEventBean> monitEventBeans) {
		this.monitEventBeans = monitEventBeans;
	}

	public List<MonitSerBusBean> getMonitSerBusBeans() {
		return monitSerBusBeans;
	}

	public void setMonitSerBusBean(List<MonitSerBusBean> monitSerBusBeans) {
		this.monitSerBusBeans = monitSerBusBeans;
	}

	public int getEventcount() {
		return eventcount;
	}

	public void setEventcount(int eventcount) {
		this.eventcount = eventcount;
	}	
}
