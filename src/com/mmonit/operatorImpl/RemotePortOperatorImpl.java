package com.mmonit.operatorImpl;

import com.mmonit.Dao.RemotePortDao;
import com.mmonit.bean.RemotePortBean;
import com.mmonit.operator.RemotePortOperator;

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
