package com.abchina.operatorImpl;

import com.abchina.Dao.RemotePortDao;
import com.abchina.bean.RemotePortBean;
import com.abchina.operator.RemotePortOperator;

public class RemotePortOperatorImpl implements RemotePortOperator {

	private RemotePortDao remotePortDao;
	
	
	public RemotePortDao getRemotePortDao() {
		return remotePortDao;
	}


	public void setRemotePortDao(RemotePortDao remotePortDao) {
		this.remotePortDao = remotePortDao;
	}


	@Override
	public void saveRemotePort(RemotePortBean remotePortBean) {
		remotePortDao.saveRemotePort(remotePortBean);
		
	}

}
