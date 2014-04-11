package com.abchina.Dao;

import com.abchina.bean.RemoteHostBean;

public interface RemoteHostDao {

	void saveRemoteHost(RemoteHostBean remoteHostBean, String monitId);

}
