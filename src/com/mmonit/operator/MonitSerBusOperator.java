package com.mmonit.operator;

import com.mmonit.bean.MonitBean;

public interface MonitSerBusOperator {

	void saveMonitSerBus(MonitBean monitBean, String monitID);

	void updateMonitSerBus(String smonitId, String service, int serStatus,int serMonitor, int type);

	
}
