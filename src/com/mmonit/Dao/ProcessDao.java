package com.mmonit.Dao;

import com.mmonit.bean.ProcessBean;

public interface ProcessDao {

	void saveProcess(ProcessBean processBean, String monitId);

}
