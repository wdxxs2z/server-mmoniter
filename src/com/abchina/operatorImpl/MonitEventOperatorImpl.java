package com.abchina.operatorImpl;

import com.abchina.Dao.MonitEventDao;
import com.abchina.bean.MonitEventBean;
import com.abchina.operator.MonitEventOperator;

public class MonitEventOperatorImpl implements MonitEventOperator {

	private MonitEventDao monitEventDao;
		
	public MonitEventDao getMonitEventDao() {
		return monitEventDao;
	}

	public void setMonitEventDao(MonitEventDao monitEventDao) {
		this.monitEventDao = monitEventDao;
	}



	@Override
	public void saveMonitEvent(MonitEventBean eventMessage) {
		monitEventDao.saveMonitEvent(eventMessage);
		
	}

	@Override
	public int countEvent(String monitId) {
		return monitEventDao.countEvent(monitId);		
	}

}
