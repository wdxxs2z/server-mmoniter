package com.abchina.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.abchina.bean.MonitBean;
import com.abchina.bean.MonitEventBean;
import com.abchina.bean.ProcessBean;
import com.abchina.bean.RemoteHostBean;
import com.abchina.bean.RemotePortBean;
import com.abchina.bean.SystemBean;
import com.abchina.bean.MonitSerBusBean;
import com.abchina.operator.MonitEventOperator;
import com.abchina.operator.MonitOperator;
import com.abchina.operator.MonitSerBusOperator;
import com.abchina.operator.ProcessOperator;
import com.abchina.operator.RemoteHostOperator;
import com.abchina.operator.RemotePortOperator;
import com.abchina.operator.SystemOperator;
import com.abchina.service.MonitService;
import com.abchina.utils.MonitXml2O;
import com.abchina.utils.SpringUtil;

public class Handler implements Runnable {

	private Socket socket = null;
	private ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
	MonitBean monitBean = new MonitBean();
	MonitSerBusBean monitSerBusBean = new MonitSerBusBean();
	private MonitOperator monitOperator;
	private ProcessOperator processOperator;
	private SystemOperator systemOperator;
	private RemoteHostOperator remoteHostOperator;
	private RemotePortOperator remotePortOperator;
	private MonitEventOperator monitEventOperator;
	private MonitSerBusOperator monitSerBusOperator;

	/**
	 * 构造初始化 socket
	 * */
	public Handler(Socket socket,
			ConcurrentHashMap<String, String> concurrentHashMap) {
		this.socket = socket;
		this.concurrentHashMap = concurrentHashMap;

		this.monitEventOperator = (MonitEventOperator) SpringUtil
				.getBean("monitEventOperator");
		monitOperator = (MonitOperator) SpringUtil.getBean(MonitOperator.class);
		processOperator = (ProcessOperator) SpringUtil
				.getBean(ProcessOperator.class);
		systemOperator = (SystemOperator) SpringUtil
				.getBean(SystemOperator.class);
		remoteHostOperator = (RemoteHostOperator) SpringUtil
				.getBean(RemoteHostOperator.class);
		remotePortOperator = (RemotePortOperator) SpringUtil
				.getBean(RemotePortOperator.class);
		monitSerBusOperator = (MonitSerBusOperator) SpringUtil
				.getBean(MonitSerBusOperator.class);
	}

	@Override
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			String s_line = "";
			String s_totle = "";
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					inputStream));
			while ((s_line = buffer.readLine()) != null) {
				s_totle += s_line + "\r\n";
			}
			int lastIndexOf = s_totle.lastIndexOf("<?xml");
			String substring = s_totle.substring(lastIndexOf);
			int judge = substring.lastIndexOf("event");
			if (judge < 0) {
				ConcurrentHashMap<String, String> monitIdInfo = MonitXml2O
						.getMonitId(substring, concurrentHashMap);
				// 遍历monitIdInfo keys 重新存储
				Enumeration<String> monitIds = monitIdInfo.keys();
				String monitId = monitIds.nextElement();
				concurrentHashMap.put(monitId, substring);
				/* monitBean */
				monitBean = MonitService.monitFactor(substring);
				
				/*存储monitBean*/
				monitOperator.saveMonit(monitBean);
				
				String monitID = monitBean.getMonitId();
				
				/*存储monitSerBusBean 判断monitSerBus 是否有重复 无重复添加 有重复更新
				 * monitId --> serverName
				 * */
				monitSerBusOperator.saveMonitSerBus(monitBean,monitID);
				
				/* 批量插入processBean 到数据库 */
				saveProcess(monitBean,monitID);
				
				/* 插入systemBean 到数据库 */
				saveSystem(monitBean,monitID);
				
				/* 批量插入remoteHostBean 到数据库 */
				saveRemote(monitBean,monitID);			

			} else {
				/* 处理时间信息 */
				System.out.println("接入事件：");
				System.out.println(substring);
				MonitEventBean eventMessage = MonitService
						.eventMessageFactory(substring);
				
				int type = eventMessage.getType();
				
				/*根据 type类型将服务更新*/
				String smonitId = eventMessage.getMonitId();
				String service = eventMessage.getService();
				
				/*匹配事件更新*/
				int[] serStatusAndMonitor = MonitService.getServiceStatusAndMonitor(substring,type,service);
				
				monitSerBusOperator.updateMonitSerBus(smonitId,service,serStatusAndMonitor[0],serStatusAndMonitor[1],type);				
				
				monitEventOperator.saveMonitEvent(eventMessage);
				
				/*计算事件次数*/
				String emonitId = eventMessage.getMonitId();
				int eventCount = monitEventOperator.countEvent(emonitId);
				monitOperator.updateMonitEventCount(eventCount,emonitId);
			}
			System.out.println("------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null)
					socket.close(); // 断开连接
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void saveRemote(MonitBean rMonitBean, String monitID) {
		List<RemoteHostBean> remoteHostBeans = rMonitBean
				.getRemoteHostBeans();
		for (RemoteHostBean remoteHostBean : remoteHostBeans) {
			remoteHostOperator.saveRemoteHost(remoteHostBean, monitID);
			/* 批量插入remotePortBean */
			List<RemotePortBean> remotePorts = remoteHostBean
					.getRemotePorts();
			for (RemotePortBean remotePortBean : remotePorts) {
				remotePortOperator.saveRemotePort(remotePortBean);
			}
		}
		
	}

	private void saveSystem(MonitBean sMonitBean, String monitID) {
		SystemBean systemBean = sMonitBean.getSystemBean();
		systemOperator.saveSystem(systemBean, monitID);
		
	}

	private void saveProcess(MonitBean pMonitBean, String monitID) {		
		List<ProcessBean> processBeans = pMonitBean.getProcessBeans();
		for (ProcessBean processBean : processBeans) {
			processOperator.saveProcess(processBean, monitID);
			
		}
		
	}

}
