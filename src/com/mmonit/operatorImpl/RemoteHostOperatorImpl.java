package com.mmonit.operatorImpl;

import com.mmonit.Dao.RemoteHostDao;
import com.mmonit.bean.RemoteHostBean;
import com.mmonit.operator.RemoteHostOperator;

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
		if(remoteHostBean.getMonitId() == null){
			
		}else{
			remoteHostDao.saveRemoteHost(remoteHostBean,monitId);
		}
		
	}

}
