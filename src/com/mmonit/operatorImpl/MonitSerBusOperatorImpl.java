package com.mmonit.operatorImpl;

import java.util.List;

import com.mmonit.Dao.MonitSerBusDao;
import com.mmonit.bean.MonitBean;
import com.mmonit.bean.MonitSerBusBean;
import com.mmonit.operator.MonitSerBusOperator;

public class MonitSerBusOperatorImpl implements MonitSerBusOperator {

	private MonitSerBusDao monitSerBusDao;
	
	public MonitSerBusDao getMonitSerBusDao() {
		return monitSerBusDao;
	}

	public void setMonitSerBusDao(MonitSerBusDao monitSerBusDao) {
		this.monitSerBusDao = monitSerBusDao;
	}


	@Override
	public void saveMonitSerBus(MonitBean monitBean, String monitID) {
		/*
		 * 先根据monitID 判断 server表中是否有 有-->进行下一步判断 没有则全部添加
		 * 有 则判断 sername 没有则添加没有的 有sername 更新
		 * */
		List<MonitSerBusBean> monitSerBusBeans = monitBean.getMonitSerBusBeans();
		MonitSerBusBean monitSerBusBean = monitSerBusDao.findMonitSerBusByMID(monitID);
		if(monitSerBusBean == null){
			/* 遍历monitBean 添加全部*/
			for(MonitSerBusBean busBean : monitSerBusBeans){
				monitSerBusDao.saveMonitSerBus(busBean,monitID);
			}
		}else{
			/*根据monitBean中的所有服务名查找 找到了的更新 没有的服务添加*/			
			for(MonitSerBusBean busBean : monitSerBusBeans){
				String service_name = busBean.getService_name();
				if(monitSerBusDao.findMonitSerBusByMIDAndSerName(monitID,service_name)==null){
					monitSerBusDao.saveMonitSerBus(busBean,monitID);
				}
			}
		}
		
	}

	@Override
	public void updateMonitSerBus(String smonitId, String service, int serStatus,int serMonitor,int type) {
		if(type == 3 || type == 4 || type == 5){
			if(serMonitor == 2){
				serMonitor = 1;
			}
			monitSerBusDao.updateMonitSerBus(smonitId, service, serStatus, serMonitor);
		}	
	}

	@Override
	public void deleteMonitSerBus() {
		monitSerBusDao.deleteMonitSerBus();		
	}

}
