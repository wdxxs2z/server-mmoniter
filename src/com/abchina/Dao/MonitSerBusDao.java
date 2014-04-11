package com.abchina.Dao;

import com.abchina.bean.MonitSerBusBean;

public interface MonitSerBusDao {

	MonitSerBusBean findMonitSerBusByMID(String monitID);

	void saveMonitSerBus(MonitSerBusBean busBean, String monitID);

	Object findMonitSerBusByMIDAndSerName(String monitID, String service_name);

	void updateMonitSerBus(String smonitId, String service, int serStatus, int serMonitor);

}
