package com.mmonit.Dao;

import com.mmonit.bean.MonitEventBean;

public interface MonitEventDao {

	void saveMonitEvent(MonitEventBean eventMessage);

	int countEvent(String monitId);

}
