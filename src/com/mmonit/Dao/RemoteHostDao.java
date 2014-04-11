package com.mmonit.Dao;

import com.mmonit.bean.RemoteHostBean;

public interface RemoteHostDao {

	void saveRemoteHost(RemoteHostBean remoteHostBean, String monitId);

}
