package com.mmonit.operatorImpl;

import com.mmonit.Dao.SystemDao;
import com.mmonit.bean.SystemBean;
import com.mmonit.operator.SystemOperator;

public class SystemOperatorImpl implements SystemOperator {
	
	private SystemDao systemDao;
	
	
	public SystemDao getSystemDao() {
		return systemDao;
	}


	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}


	@Override
	public void saveSystem(SystemBean systemBean, String monitId) {
		systemDao.saveSystem(systemBean,monitId);
	}

}
