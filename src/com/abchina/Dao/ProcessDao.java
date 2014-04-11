package com.abchina.Dao;

import com.abchina.bean.ProcessBean;

public interface ProcessDao {

	void saveProcess(ProcessBean processBean, String monitId);

}
