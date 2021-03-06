package com.mmonit.handler;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mmonit.bean.FileBean;
import com.mmonit.bean.MonitBean;
import com.mmonit.bean.MonitEventBean;
import com.mmonit.bean.MonitSerBusBean;
import com.mmonit.bean.ProcessBean;
import com.mmonit.bean.RemoteHostBean;
import com.mmonit.bean.RemotePortBean;
import com.mmonit.bean.SystemBean;
import com.mmonit.operator.FileOperator;
import com.mmonit.operator.MonitEventOperator;
import com.mmonit.operator.MonitOperator;
import com.mmonit.operator.MonitSerBusOperator;
import com.mmonit.operator.ProcessOperator;
import com.mmonit.operator.RemoteHostOperator;
import com.mmonit.operator.RemotePortOperator;
import com.mmonit.operator.SystemOperator;
import com.mmonit.service.MonitService;
import com.mmonit.utils.SpringUtil;

public class WorkHandler implements Runnable {

	Queue<String> sQueue = new LinkedBlockingQueue<String>();
	MonitBean monitBean = new MonitBean();
	MonitSerBusBean monitSerBusBean = new MonitSerBusBean();
	private MonitOperator monitOperator;
	private ProcessOperator processOperator;
	private SystemOperator systemOperator;
	private RemoteHostOperator remoteHostOperator;
	private RemotePortOperator remotePortOperator;
	private MonitEventOperator monitEventOperator;
	private MonitSerBusOperator monitSerBusOperator;
	private FileOperator fileOperator;

	public WorkHandler(Queue<String> sQueue) {
		super();
		this.sQueue = sQueue;
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
		fileOperator = (FileOperator) SpringUtil.getBean(FileOperator.class);
	}

	@Override
	public void run() {
		/**
		 * 从队列中处理服务端发来的xml，在此进行组装bean 并完成逻辑层的IO
		 * */
		try {
			
			String substring = sQueue.remove();
			int judge = substring.lastIndexOf("event");
			if (judge < 0) {
				/* monitBean */
				monitBean = MonitService.monitFactor(substring);
				/* 存储monitBean */
				monitOperator.saveMonit(monitBean);

				String monitID = monitBean.getMonitId();

				/*
				 * 存储monitSerBusBean 判断monitSerBus 是否有重复 无重复添加 有重复更新 monitId -->
				 * serverName
				 */
				monitSerBusOperator.saveMonitSerBus(monitBean, monitID);

				/* 批量插入processBean 到数据库 */
				saveProcess(monitBean, monitID);

				/* 批量插入fileBean 到数据库 */
				saveFile(monitBean, monitID);

				/* 插入systemBean 到数据库 */
				saveSystem(monitBean, monitID);

				/* 批量插入remoteHostBean 到数据库 */
				saveRemote(monitBean, monitID);

			} else {
				/* 处理时间信息 */
				System.out.println("接入事件：");
				System.out.println(substring);
				MonitEventBean eventMessage = MonitService
						.eventMessageFactory(substring);
				
				int type = eventMessage.getType();

				/* 根据 type类型将服务更新 */
				String smonitId = eventMessage.getMonitId();
				String service = eventMessage.getService();

				/* 匹配事件更新 */
				int[] serStatusAndMonitor = MonitService
						.getServiceStatusAndMonitor(substring, type, service);

				monitSerBusOperator.updateMonitSerBus(smonitId, service,
						serStatusAndMonitor[0], serStatusAndMonitor[1], type);

				monitEventOperator.saveMonitEvent(eventMessage);

				/* 计算事件次数 */
				String emonitId = eventMessage.getMonitId();
				int eventCount = monitEventOperator.countEvent(emonitId);
				monitOperator.updateMonitEventCount(eventCount, emonitId);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	private void saveFile(MonitBean fMonitBean, String monitID) {
		List<FileBean> fileBeans = fMonitBean.getFileBeans();
		for(FileBean fileBean : fileBeans){
			fileOperator.saveFile(fileBean,monitID);
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
