package com.abchina.operator;

import com.abchina.bean.MonitBean;

public interface MonitOperator {
	
	public void saveMonit(MonitBean monitBean);

	public void updateMonitStatus(String mId, int monitstatus);

	public void updateMonitEventCount(int eventCount,String monitId);
	
	

}
