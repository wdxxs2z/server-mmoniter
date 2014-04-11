package com.mmonit.operator;

import com.mmonit.bean.MonitBean;

public interface MonitOperator {
	
	public void saveMonit(MonitBean monitBean);

	public void updateMonitStatus(String mId, int monitstatus);

	public void updateMonitEventCount(int eventCount,String monitId);
	
	

}
