package com.mmonit.DaoImpl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mmonit.Dao.RemoteHostDao;
import com.mmonit.bean.RemoteHostBean;

public class RemoteHostDaoImpl implements RemoteHostDao {
	
	private JdbcTemplate jt;
	
	public JdbcTemplate getJt() {
		return jt;
	}


	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}


	@Override
	public void saveRemoteHost(RemoteHostBean remoteHostBean, String monitId) {
		String sql ="insert into remotehost(remoteHostName,collected_sec,collected_usec,"
				+ "remoteHostStatus,monitId) values(?,?,?,?,?)";
		
		jt.update(sql, remoteHostBean.getRemoteHostName(),remoteHostBean.getCollected_sec(),remoteHostBean.getCollected_usec(),
				remoteHostBean.getRemoteHostStatus(),monitId);
		
	}

}
