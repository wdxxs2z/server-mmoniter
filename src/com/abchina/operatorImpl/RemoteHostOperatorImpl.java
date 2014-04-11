package com.abchina.operatorImpl;

import com.abchina.Dao.RemoteHostDao;
import com.abchina.bean.RemoteHostBean;
import com.abchina.operator.RemoteHostOperator;

public class RemoteHostOperatorImpl implements RemoteHostOperator {

	private RemoteHostDao remoteHostDao;
	
	public RemoteHostDao getRemoteHostDao() {
		return remoteHostDao;
	}


	public void setRemoteHostDao(RemoteHostDao remoteHostDao) {
		this.remoteHostDao = remoteHostDao;
	}


	@Override
	public void saveRemoteHost(RemoteHostBean remoteHostBean, String monitId) {
		remoteHostDao.saveRemoteHost(remoteHostBean,monitId);
		
	}

}
