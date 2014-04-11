package com.mmonit.operatorImpl;

import com.mmonit.Dao.MonitEventDao;
import com.mmonit.bean.MonitEventBean;
import com.mmonit.operator.MonitEventOperator;

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
