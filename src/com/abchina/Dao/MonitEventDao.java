package com.abchina.Dao;

import com.abchina.bean.MonitEventBean;

public interface MonitEventDao {

	void saveMonitEvent(MonitEventBean eventMessage);

	int countEvent(String monitId);

}
