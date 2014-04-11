package com.mmonit.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mmonit.bean.FileBean;
import com.mmonit.bean.MonitBean;
import com.mmonit.bean.MonitEventBean;
import com.mmonit.bean.ProcessBean;
import com.mmonit.bean.RemoteHostBean;
import com.mmonit.bean.RemotePortBean;
import com.mmonit.bean.SystemBean;
import com.mmonit.bean.MonitSerBusBean;

public class MonitService {
	
	/**
	 * 操作bean
	 * mmonit by yuanyuan 2014
	 * 主体：组合monitBean的关键类，通过接收集群中所有的monitAgent XML分析
	 * 包括 系统平台基础信息，进程信息，系统关键信息，远程端口监控信息
	 * */
	private static MonitBean monitBean = null;
	
	private static ProcessBean processBean = null;
	
	private static SystemBean systemBean = null;
	
	private static RemoteHostBean remoteHostBean = null;
	
	private static RemotePortBean remotePortBean = null;
	
	private static MonitEventBean monitEventBean = null;
	
	private static MonitSerBusBean monitSerBusBean = null;
	
	private static FileBean fileBean = null;
	
		
	public static MonitBean monitFactor(String str) {
		Document doc = null;
		monitBean = new MonitBean();
		try {
			doc = DocumentHelper.parseText(str);
			Element rootElt = doc.getRootElement();
			
			@SuppressWarnings("unchecked")
			Iterator<Attribute> attributeIterator = rootElt.attributeIterator();
			while (attributeIterator.hasNext()) {
				
				/*遍历root节点的属性*/
				Attribute a = (Attribute) attributeIterator.next();
				String name = a.getName();
				if(name.equals("id")){
					monitBean.setMonitId(a.getValue());								
				}
				if(name.equals("version")){
					monitBean.setMonitVersion(a.getValue());
				}
			}
			
			/*遍历 server 节点下的信息    platform 平台下的监控信息*/
			Iterator elementIterator = rootElt.elementIterator("server");
			while (elementIterator.hasNext()) {
				/*遍历server节点下的update等等*/
				Element serE = (Element) elementIterator.next();
				/*监控主机*/
				monitBean.setMonitHostName(serE.elementTextTrim("localhostname"));
				Iterator httpdIte = serE.elementIterator("httpd");
				while (httpdIte.hasNext()) {
					Element httpEle = (Element) httpdIte.next();
					/*监控主机的IP*/
					monitBean.setMonitHostIp(httpEle.elementTextTrim("address"));
					/*监控主机的port*/
					monitBean.setMonitHostPort(httpEle.elementTextTrim("port"));
				}
				/*monitor 用户信息*/
				Iterator credentialsIte = serE.elementIterator("credentials");
				while(credentialsIte.hasNext()){
					Element credentEle = (Element) credentialsIte.next();
					monitBean.setUsername(credentEle.elementTextTrim("username"));
					monitBean.setPassword(credentEle.elementTextTrim("password"));
				}
								
				/*监控主机的运行时间*/
				monitBean.setUpdateTime(serE.elementTextTrim("uptime"));
				/*设置主机存活状态 0为存活*/
				monitBean.setMonitHostStatus(0);
			}

			/*platform下的信息*/
			Iterator elementIterator2 = rootElt.elementIterator("platform");
			while (elementIterator2.hasNext()) {
				Element platEle = (Element) elementIterator2.next();
				
				monitBean.setPlatformName(platEle.elementTextTrim("name"));
				
				monitBean.setPlatformRelease(platEle.elementTextTrim("release"));
				
				monitBean.setPlatformVersion(platEle.elementTextTrim("version"));
				
				monitBean.setPlatformMachine(platEle.elementTextTrim("machine"));
				
				monitBean.setPlatformCpu(Integer.parseInt(platEle.elementTextTrim("cpu")));
				
				monitBean.setPlatformMem(Integer.parseInt(platEle.elementTextTrim("memory")));
				
				monitBean.setPlatformSwap(Integer.parseInt(platEle.elementTextTrim("swap")));
			}

			/*monit services 包括进程信息，系统信息，远程端口信息*/
			Iterator elementIterator3 = rootElt.elementIterator("services");
			List<ProcessBean> Processes = new ArrayList<ProcessBean>();
			List<RemoteHostBean> RemoteHostBeans = new ArrayList<RemoteHostBean>();
			List<MonitSerBusBean> monitSerBusBeans = new ArrayList<MonitSerBusBean>();
			List<FileBean> fileBeans = new ArrayList<FileBean>();
			
			while (elementIterator3.hasNext()) {
				Element serviceEle = (Element) elementIterator3.next();
				Iterator elementIterator4 = serviceEle.elementIterator();
				while (elementIterator4.hasNext()) {// service 下的所有节点
					Element servE = (Element) elementIterator4.next();
					String serviceType = servE.elementTextTrim("type");
					
					/*服务属性 type 通过类型属性进行判断 3属于系统进程 5属于系统内 2属于文件属性*/
					if (serviceType.equals("3")) {
						/*此处 processBean 然后存入list集合中去*/
						processBean = new ProcessBean();
						monitSerBusBean = new MonitSerBusBean();
						
						monitSerBusBean.setService_type(3);
						
						processBean.setProcessName(servE.attributeValue("name"));
						
						monitSerBusBean.setService_name(servE.attributeValue("name"));
						
						processBean.setCollected_sec(servE.elementTextTrim("collected_sec"));
						
						processBean.setCollected_usec(servE.elementTextTrim("collected_usec"));
						
						String status = servE.elementTextTrim("status");// 判断其是否存活 存活为0
						
						monitSerBusBean.setService_monit(Integer.parseInt(servE.elementTextTrim("monitor")));
						
						processBean.setProcessStatus(Integer.parseInt(servE.elementTextTrim("status")));
						
						if (status.equals("0")) {// 存活状态
							// 遍历存活进程的基本信息
							monitSerBusBean.setService_status(0);
							
							processBean.setProcessPid(servE.elementTextTrim("pid"));
							
							processBean.setProcessUptime(servE.elementTextTrim("uptime"));
							
							String children = servE.elementTextTrim("children");
							if(children == null){
								processBean.setProcessChildren(0);
							} else{
								processBean.setProcessChildren(Integer.parseInt(children));
							}
							
							
							// 遍历进程的内存						
							Iterator proMemIte = servE
									.elementIterator("memory");
							while (proMemIte.hasNext()) {
								Element preMemEle = (Element) proMemIte.next();
								
								processBean.setProcessMemPercenttotal(Float.parseFloat(preMemEle.elementTextTrim("percenttotal")));
								
								processBean.setProcessMemKilobytetotal(Integer.parseInt(preMemEle.elementTextTrim("kilobytetotal")));
								
							}
							// 遍历进程的CPU资源
							Iterator proCpuIte = servE.elementIterator("cpu");
							while (proCpuIte.hasNext()) {
								Element proCpuEle = (Element) proCpuIte.next();								
								processBean.setProcessCpuPercenttotal(Float.parseFloat(proCpuEle.elementTextTrim("percenttotal")));								
							}
							//设置monitId
							processBean.setMonitId(monitBean.getMonitId());
							processBean.setMonitBean(monitBean);
							
							monitSerBusBean.setMonitId(monitBean.getMonitId());
							monitSerBusBean.setMonitor_host(monitBean.getMonitHostIp());
							monitSerBusBean.setMonitBean(monitBean);
							
						} else {
							/*如果process状态不为 0 则表示有问题*/
							monitSerBusBean.setService_status(Integer.parseInt(servE.elementTextTrim("status")));
							processBean.setProcessStatusMessage(servE.elementTextTrim("status_message"));
							processBean.setProcessStatus(servE.elementTextTrim("status").charAt(0));
							processBean.setMonitId(monitBean.getMonitId());
							monitSerBusBean.setMonitId(monitBean.getMonitId());
							monitSerBusBean.setMonitor_host(monitBean.getMonitHostIp());
							processBean.setMonitBean(monitBean);
						}
						Processes.add(processBean);
						monitSerBusBeans.add(monitSerBusBean);
					}
					
					/*file 类型的监控*/
					if(serviceType.equals("2")){
						fileBean = new FileBean();
						monitSerBusBean = new MonitSerBusBean();
						fileBean.setFileName(servE.attributeValue("name"));
						fileBean.setCollected_sec(servE.elementTextTrim("collected_sec"));
						fileBean.setCollected_usec(servE.elementTextTrim("collected_usec"));
						
						int status = Integer.parseInt(servE.elementTextTrim("status"));
						fileBean.setStatus(status);
						
						/*设置服务名*/
						monitSerBusBean.setService_type(2);
						monitSerBusBean.setService_name(servE.attributeValue("name"));
						monitSerBusBean.setMonitId(monitBean.getMonitId());
						monitSerBusBean.setMonitBean(monitBean);
						monitSerBusBean.setService_monit(Integer.parseInt(servE.elementTextTrim("monitor")));
						monitSerBusBean.setMonitor_host(monitBean.getMonitHostIp());
						
						monitSerBusBean.setService_status(status);
						
						/*设置文件的具体属性*/
						if(status == 0){
							String mode = servE.elementTextTrim("mode");
							if(mode == null){
								fileBean.setFilemode(null);
							} else{
								fileBean.setFilemode(Integer.parseInt(mode));
							}

							String uid = servE.elementTextTrim("uid");
							if(uid == null){
								fileBean.setFileuid(null);
							} else{
								fileBean.setFileuid(Integer.parseInt(uid));
							}
							
							String gid = servE.elementTextTrim("gid");
							if(gid==null){
								fileBean.setFilegid(null);
							} else{
								fileBean.setFilegid(Integer.parseInt(gid));
							}
							
							fileBean.setFiletimestamp(servE.elementTextTrim("timestamp"));
							fileBean.setFilesize(servE.elementTextTrim("size"));
							fileBean.setFilechecksum(servE.elementTextTrim("checksum"));						
						}
							
						fileBean.setMonitBean(monitBean);
						fileBean.setMonitId(monitBean.getMonitId());	
							
						monitSerBusBeans.add(monitSerBusBean);
						fileBeans.add(fileBean);
					}
					
					/*system 信息 监控系统的详细：CPU MEM LOAD SWAP等*/
					if (serviceType.equals("5")) {// 系统监控
						systemBean = new SystemBean();
						monitSerBusBean = new MonitSerBusBean();
						systemBean.setSystemName(servE.attributeValue("name"));
						
						monitSerBusBean.setService_type(5);
						monitSerBusBean.setService_name(servE.attributeValue("name"));
						monitSerBusBean.setService_status(0);
						monitSerBusBean.setMonitId(monitBean.getMonitId());
						monitSerBusBean.setMonitBean(monitBean);
						monitSerBusBean.setService_monit(Integer.parseInt(servE.elementTextTrim("monitor")));
						monitSerBusBean.setMonitor_host(monitBean.getMonitHostIp());
						
						systemBean.setCollected_sec(servE.elementTextTrim("collected_sec"));
						systemBean.setCollected_usec(servE.elementTextTrim("collected_usec"));
						// 遍历system
						Iterator systemIte = servE.elementIterator("system");
						while (systemIte.hasNext()) {
							Element systemEle = (Element) systemIte.next();
							// load 负载
							Iterator loadIte = systemEle
									.elementIterator("load");
							while (loadIte.hasNext()) {
								Element loadEle = (Element) loadIte.next();
								
								systemBean.setLoadAvg01(Float.parseFloat(loadEle.elementTextTrim("avg01")));
								
								systemBean.setLoadAvg05(Float.parseFloat(loadEle.elementTextTrim("avg05")));
								
								systemBean.setLoadAvg15(Float.parseFloat(loadEle.elementTextTrim("avg15")));
								
							}
							// CPU 使用 用户 系统 等待
							Iterator systemCpuIte = systemEle
									.elementIterator("cpu");
							while (systemCpuIte.hasNext()) {
								Element systemCpuEle = (Element) systemCpuIte
										.next();
								
								systemBean.setSystemUserCpu(Float.parseFloat(systemCpuEle.elementTextTrim("user")));
								
								systemBean.setSystemSysCpu(Float.parseFloat(systemCpuEle.elementTextTrim("system")));
								
								systemBean.setSystemWaitCpu(Float.parseFloat(systemCpuEle.elementTextTrim("wait")));
								
							}
							// MEM 系统内存占用
							Iterator systemMemIte = systemEle
									.elementIterator("memory");
							while (systemMemIte.hasNext()) {
								Element systemMemEle = (Element) systemMemIte
										.next();
								
								systemBean.setSystemMemperc(Float.parseFloat(systemMemEle.elementTextTrim("percent")));
								
								systemBean.setSystemMemTot(Integer.parseInt(systemMemEle.elementTextTrim("kilobyte")));
								
							}
							// SWAP 系统交换区使用
							Iterator systemSwapIte = systemEle
									.elementIterator("swap");
							while (systemSwapIte.hasNext()) {
								Element systemSwapEle = (Element) systemSwapIte
										.next();
								
								systemBean.setSystemSwapperc(Float.parseFloat(systemSwapEle.elementTextTrim("percent")));
								
								systemBean.setSystemSwapTot(Integer.parseInt(systemSwapEle.elementTextTrim("kilobyte")));
							}
							//设置monitId
							systemBean.setMonitId(monitBean.getMonitId());
							
							systemBean.setMonitBean(monitBean);
						}
						monitSerBusBeans.add(monitSerBusBean);
					}
					
					/*remote 远程端口监控 端口的响应时间 存活状态 传输协议等*/
					List<RemotePortBean> remotePortBeans = new ArrayList<RemotePortBean>();
					if (serviceType.equals("4")) {
						// host 远程端口 和协议监控
						remoteHostBean = new RemoteHostBean();
						
						monitSerBusBean = new MonitSerBusBean();
						monitSerBusBean.setService_type(4);
						
						String hostName = servE.attributeValue("name");
						
						monitSerBusBean.setService_name(hostName);
						
						remoteHostBean.setRemoteHostName(servE.attributeValue("name"));						
						remoteHostBean.setCollected_sec(servE.elementTextTrim("collected_sec"));
						remoteHostBean.setCollected_usec(servE.elementTextTrim("collected_usec"));
						remoteHostBean.setRemoteHostStatus(Integer.parseInt(servE.elementTextTrim("status")));
						
						monitSerBusBean.setService_status(Integer.parseInt(servE.elementTextTrim("status")));
						monitSerBusBean.setService_monit(Integer.parseInt(servE.elementTextTrim("monitor")));
						
						remoteHostBean.setRemote_status_message(servE.elementTextTrim("status_message"));
						// 遍历远程应用的端口
						Iterator hostPortIte = servE.elementIterator("port");
						while (hostPortIte.hasNext()) {
							remotePortBean = new RemotePortBean();
							Element hostPortEle = (Element) hostPortIte.next();							
							
							remotePortBean.setPort(Integer.parseInt(hostPortEle.elementTextTrim("portnumber")));
							hostPortEle.elementTextTrim("request");
							
							remotePortBean.setProtocol(hostPortEle.elementTextTrim("protocol"));
							remotePortBean.setType(hostPortEle.elementTextTrim("type"));
							
							remotePortBean.setResponsetime(Float.parseFloat(hostPortEle.elementTextTrim("responsetime")));
							remotePortBean.setRemoteHostName(hostName);
							remotePortBean.setRemotefeed(remoteHostBean.getRemoteHostName()+"_"+monitBean.getMonitId());
							remotePortBean.setRemoteHostBean(remoteHostBean);
							remotePortBeans.add(remotePortBean);							
						}
						
						remoteHostBean.setMonitId(monitBean.getMonitId());
						remoteHostBean.setMonitBean(monitBean);
						remoteHostBean.setRemotePorts(remotePortBeans);
						RemoteHostBeans.add(remoteHostBean);
						
						monitSerBusBean.setMonitId(monitBean.getMonitId());
						monitSerBusBean.setMonitor_host(monitBean.getMonitHostIp());
						monitSerBusBean.setMonitBean(monitBean);
						monitSerBusBeans.add(monitSerBusBean);
					}
				}
			}
			monitBean.setProcessBeans(Processes);
			monitBean.setFileBeans(fileBeans);
			monitBean.setRemoteHostBeans(RemoteHostBeans);
			monitBean.setSystemBean(systemBean);
			monitBean.setMonitSerBusBean(monitSerBusBeans);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return monitBean;
	}
	
	/*监视monit的事件 组合事件Bean*/
	public static MonitEventBean eventMessageFactory(
			String substring) {
		Document doc = null;
		String monitId = null;
		monitEventBean = new MonitEventBean();
		try {
			doc = DocumentHelper.parseText(substring);
			
			Element rootElt = doc.getRootElement();
			Iterator<Attribute> attributeIterator = rootElt.attributeIterator();
			
			while (attributeIterator.hasNext()) {// 遍历root节点的属性
				Attribute a = (Attribute) attributeIterator.next();
				//根据monit id 进程判断 是否是新节点
				String name = a.getName();
				if(name.equals("id")){
					monitId = a.getValue();					
				}
			}
			/*将相关的message插入到数据库中*/
			Iterator elementIterator = rootElt.elementIterator("event");
			while(elementIterator.hasNext()){
				Element eventEle = (Element)elementIterator.next();
				monitEventBean.setCollected_sec(eventEle.elementTextTrim("collected_sec"));
				monitEventBean.setCollected_usec(eventEle.elementTextTrim("collected_usec"));
				monitEventBean.setService(eventEle.elementTextTrim("service"));
				monitEventBean.setType(Integer.parseInt(eventEle.elementTextTrim("type")));
				monitEventBean.setSerStatus(Integer.parseInt(eventEle.elementTextTrim("state")));
				monitEventBean.setAction(Integer.parseInt(eventEle.elementTextTrim("action")));
				monitEventBean.setMessage(eventEle.elementTextTrim("message"));
				monitEventBean.setMonitId(monitId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return monitEventBean;
		
	}
	
	/*
	 * 处理服务的监视状态
	 * */
	public static int[] getServiceStatusAndMonitor(String substring, int type, String service) {
		Document doc = null;
		int ser_status = 0;
		int ser_monit = 0;
		int[] statusAndMonitor = new int[2];
		try {
			doc = DocumentHelper.parseText(substring);
			Element rootElt = doc.getRootElement();
			Iterator elementIterator3 = rootElt.elementIterator("services");
			while (elementIterator3.hasNext()) {
				Element serviceEle = (Element) elementIterator3.next();
				Iterator elementIterator4 = serviceEle.elementIterator();
				while (elementIterator4.hasNext()) {
					// service 下的所有节点
					Element servE = (Element) elementIterator4.next();
					String serviceType = servE.elementTextTrim("type");
					if(Integer.parseInt(serviceType) == type) {
						String ser_name = servE.attributeValue("name");
						if(ser_name.equals(service)) {
							ser_status = Integer.parseInt(servE.elementTextTrim("status"));
							ser_monit = Integer.parseInt(servE.elementTextTrim("monitor"));
							statusAndMonitor[0] = ser_status;
							statusAndMonitor[1] = ser_monit;
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return statusAndMonitor;
	}

	
}
