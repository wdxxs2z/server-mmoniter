package com.mmonit.operatorImpl;

import com.mmonit.Dao.FileDao;
import com.mmonit.bean.FileBean;
import com.mmonit.operator.FileOperator;

public class FileOperatorImpl implements FileOperator {

	private FileDao fileDao;
	
	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	@Override
	public void saveFile(FileBean fileBean, String monitID) {
		fileDao.saveFile(fileBean,monitID);
		
	}

}
