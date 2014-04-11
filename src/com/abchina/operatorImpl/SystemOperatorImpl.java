package com.abchina.operatorImpl;

import com.abchina.Dao.SystemDao;
import com.abchina.bean.SystemBean;
import com.abchina.operator.SystemOperator;

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
