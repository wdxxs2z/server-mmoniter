package com.mmonit.Dao;

import com.mmonit.bean.FileBean;

public interface FileDao {

	void saveFile(FileBean fileBean, String monitID);

}
