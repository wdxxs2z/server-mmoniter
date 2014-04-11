package com.mmonit.operator;

import com.mmonit.bean.MonitEventBean;

public interface MonitEventOperator {

	void saveMonitEvent(MonitEventBean eventMessage);

	int countEvent(String monitId);

}
