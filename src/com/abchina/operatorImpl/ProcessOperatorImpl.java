package com.abchina.operatorImpl;

import com.abchina.Dao.ProcessDao;
import com.abchina.bean.ProcessBean;
import com.abchina.operator.ProcessOperator;

public class ProcessOperatorImpl implements ProcessOperator {

	private ProcessDao processDao;
	

	public ProcessDao getProcessDao() {
		return processDao;
	}


	public void setProcessDao(ProcessDao processDao) {
		this.processDao = processDao;
	}


	@Override
	public void saveProcess(ProcessBean processBean, String monitId) {
		
		processDao.saveProcess(processBean,monitId);
			
	} 
	
	

}
