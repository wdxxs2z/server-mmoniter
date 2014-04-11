package com.mmonit.operator;

import com.mmonit.bean.ProcessBean;

public interface ProcessOperator {

	void saveProcess(ProcessBean processBean, String monitId);

}
