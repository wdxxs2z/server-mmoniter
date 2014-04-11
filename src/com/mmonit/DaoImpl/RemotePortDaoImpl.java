package com.mmonit.DaoImpl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mmonit.Dao.RemotePortDao;
import com.mmonit.bean.RemotePortBean;

public class RemotePortDaoImpl implements RemotePortDao{

	private JdbcTemplate jt;
	
	
	
	public JdbcTemplate getJt() {
		return jt;
	}



	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}



	@Override
	public void saveRemotePort(RemotePortBean remotePortBean) {
		String sql = "insert into remotehostport(port,protocol,transport_type,responsetime,"
				+ "remoteHostName,remotefeed) values(?,?,?,?,?,?)";
		
		jt.update(sql, remotePortBean.getPort(),remotePortBean.getProtocol(),remotePortBean.getType(),
				remotePortBean.getResponsetime(),remotePortBean.getRemoteHostName(),remotePortBean.getRemotefeed());
	}

}
