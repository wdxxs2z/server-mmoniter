package com.abchina.Dao;

import com.abchina.bean.MonitBean;

public interface MonitDao {
	
	public void saveMonit(MonitBean monitBean);
	
	public MonitBean findOneMonitByMonitId(String monitId);
	
	public void updateMonitStatus(String monitId,int monitstatus);

	public void updateMonit(MonitBean monitBean);

	public void updateMonitEventCount(int eventCount,String monitId);
	
	
		
}
