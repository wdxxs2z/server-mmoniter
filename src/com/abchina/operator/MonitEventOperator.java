package com.abchina.operator;

import com.abchina.bean.MonitEventBean;

public interface MonitEventOperator {

	void saveMonitEvent(MonitEventBean eventMessage);

	int countEvent(String monitId);

}
