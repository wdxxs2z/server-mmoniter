package com.mmonit.operator;

import com.mmonit.bean.FileBean;

public interface FileOperator {

	void saveFile(FileBean fileBean, String monitID);

}
