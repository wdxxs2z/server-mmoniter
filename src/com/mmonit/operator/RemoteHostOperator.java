package com.mmonit.operator;

import com.mmonit.bean.RemoteHostBean;

public interface RemoteHostOperator {

	void saveRemoteHost(RemoteHostBean remoteHostBean, String monitId);

}
