package com.mmonit.Dao;

import com.mmonit.bean.SystemBean;

public interface SystemDao {

	void saveSystem(SystemBean systemBean, String monitId);

}
