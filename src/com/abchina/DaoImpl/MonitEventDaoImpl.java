package com.abchina.DaoImpl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.abchina.Dao.MonitEventDao;
import com.abchina.bean.MonitEventBean;

public class MonitEventDaoImpl implements MonitEventDao {

	private JdbcTemplate jt;
	
	public JdbcTemplate getJt() {
		return jt;
	}


	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	@Override
	public void saveMonitEvent(MonitEventBean eventMessage) {
		String sql = "insert into monitevent(service,server_type,serStatus,action,collected_sec,collected_usec,"
				+ "message,monitId) values(?,?,?,?,?,?,?,?)";
		jt.update(sql, eventMessage.getService(),eventMessage.getType(),eventMessage.getSerStatus()
				,eventMessage.getAction(),eventMessage.getCollected_sec(),
				eventMessage.getCollected_usec(),eventMessage.getMessage(),eventMessage.getMonitId());
	}


	@Override
	public int countEvent(String monitId) {
		String sql = "select count(*) from monitevent where monitId=?";
		return jt.queryForInt(sql, monitId);
	}

}
