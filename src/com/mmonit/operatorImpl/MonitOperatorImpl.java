package com.mmonit.operatorImpl;

import com.mmonit.Dao.MonitDao;
import com.mmonit.bean.MonitBean;
import com.mmonit.operator.MonitOperator;

public class MonitOperatorImpl implements MonitOperator {
	
	private MonitDao monitDao;
	
	
	public MonitDao getMonitDao() {
		return monitDao;
	}

	public void setMonitDao(MonitDao monitDao) {
		this.monitDao = monitDao;
	}

	@Override
	public void saveMonit(MonitBean monitBean) {
		String monitId = monitBean.getMonitId();
		MonitBean monit = monitDao.findOneMonitByMonitId(monitId);
		/*不为空则更新 为空则添加*/
		if(monit == null){
			monitDao.saveMonit(monitBean);
		} else{
			monitDao.updateMonit(monitBean);
		}
	}

	@Override
	public void updateMonitStatus(String mId, int monitstatus) {
		monitDao.updateMonitStatus(mId, monitstatus);
	}

	@Override
	public void updateMonitEventCount(int eventCount,String monitId) {
		monitDao.updateMonitEventCount(eventCount,monitId);		
	}

	
}
