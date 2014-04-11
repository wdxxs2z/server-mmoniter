package com.abchina.operator;

import com.abchina.bean.MonitBean;

public interface MonitSerBusOperator {

	void saveMonitSerBus(MonitBean monitBean, String monitID);

	void updateMonitSerBus(String smonitId, String service, int serStatus,int serMonitor, int type);

	
}
