package com.mmonit.DaoImpl;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mmonit.Dao.SystemDao;
import com.mmonit.bean.SystemBean;

public class SystemDaoImpl implements SystemDao {

	private JdbcTemplate jt;
	
	public JdbcTemplate getJt() {
		return jt;
	}



	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	@Override
	public void saveSystem(SystemBean systemBean, String monitId) {
		String sql = "insert into system(systemName,collected_sec,collected_usec,loadAvg01,loadAvg05,"
				+ "loadAvg15,systemUserCpu,systemSysCpu,systemWaitCpu,systemMemperc,"
				+ "systemMemTot,systemSwapperc,systemSwapTot,monitId) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		jt.update(sql, systemBean.getSystemName(),systemBean.getCollected_sec(),systemBean.getCollected_usec(),
				systemBean.getLoadAvg01(),systemBean.getLoadAvg05(),systemBean.getLoadAvg15(),systemBean.getSystemUserCpu(),
				systemBean.getSystemSysCpu(),systemBean.getSystemWaitCpu(),systemBean.getSystemMemperc(),
				systemBean.getSystemMemTot(),systemBean.getSystemSwapperc(),
				systemBean.getSystemSwapTot(),systemBean.getMonitId());
	}

}
