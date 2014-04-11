package com.mmonit.DaoImpl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mmonit.Dao.FileDao;
import com.mmonit.bean.FileBean;

public class FileDaoImpl implements FileDao {

	private JdbcTemplate jt;
	
	public JdbcTemplate getJt() {
		return jt;
	}

	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}
	
	@Override
	public void saveFile(FileBean fileBean, String monitID) {
		String sql = "insert into monitfile(fileName,collected_sec,collected_usec,status,"
				+ "filemode,fileuid,filegid,filetimestamp,filesize,filechecksum,monitId) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		
		jt.update(sql, fileBean.getFileName(),fileBean.getCollected_sec(),fileBean.getCollected_usec(),
				fileBean.getStatus(),fileBean.getFilemode(),fileBean.getFileuid(),fileBean.getFilegid(),
				fileBean.getFiletimestamp(),fileBean.getFilesize(),fileBean.getFilechecksum(),monitID);	
	}

}
