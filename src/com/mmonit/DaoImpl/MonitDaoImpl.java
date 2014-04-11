package com.mmonit.DaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mmonit.Dao.MonitDao;
import com.mmonit.bean.MonitBean;

public class MonitDaoImpl implements MonitDao {

	private JdbcTemplate jt;
	
	public JdbcTemplate getJt() {
		return jt;
	}

	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	@Override
	public void saveMonit(MonitBean monitBean) {
		
		String sql = "insert into monit(monitId,monitVersion,monitHostName,monitHostIp,monitHostStatus,"
				+ "monitHostPort,updateTime,platformName,platformRelease,platformVersion,platformMachine,"
				+ "platformCpu,platformMem,platformSwap) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		jt.update(sql, monitBean.getMonitId(),monitBean.getMonitVersion(),monitBean.getMonitHostName(),
				monitBean.getMonitHostIp(),monitBean.getMonitHostStatus(),monitBean.getMonitHostPort(),
				monitBean.getUpdateTime(),monitBean.getPlatformName(),monitBean.getPlatformRelease(),monitBean.getPlatformVersion(),
				monitBean.getPlatformMachine(),monitBean.getPlatformCpu(),monitBean.getPlatformMem(),monitBean.getPlatformSwap());	
	}

	@Override
	public MonitBean findOneMonitByMonitId(String monitId) {
		String sql = "select monitId,monitVersion,monitHostName,monitHostIp,monitHostStatus,"
				+ "monitHostPort,updateTime,platformName,platformRelease,platformVersion,platformMachine,"
				+ "platformCpu,platformMem,platformSwap from monit where monitId=? ";
		String[] s = {monitId};
		MonitBean monitBean = null;
		List<MonitBean> list = jt.query(sql, s, new RowMapper<MonitBean>() {
			@Override
			public MonitBean mapRow(ResultSet rs, int arg1)
					throws SQLException {
				MonitBean monitBean = new MonitBean();
				
					monitBean.setMonitId(rs.getString("monitId"));
					monitBean.setMonitVersion(rs.getString("monitVersion"));
					monitBean.setMonitHostName(rs.getString("monitHostName"));
					monitBean.setMonitHostIp(rs.getString("monitHostIp"));
					monitBean.setMonitHostStatus(rs.getString("monitHostStatus").toCharArray()[0]);
					monitBean.setMonitHostPort(rs.getString("monitHostPort"));
					monitBean.setUpdateTime(rs.getString("updateTime"));
					monitBean.setPlatformName(rs.getString("platformName"));
					monitBean.setPlatformRelease(rs.getString("platformRelease"));
					monitBean.setPlatformVersion(rs.getString("platformVersion"));
					monitBean.setPlatformMachine(rs.getString("platformMachine"));
					monitBean.setPlatformCpu(rs.getInt("platformCpu"));
					monitBean.setPlatformMem(rs.getInt("platformMem"));
					monitBean.setPlatformSwap(rs.getInt("platformSwap"));				
					return monitBean;
							
			}
		});
		if(list!=null &&list.size()>0){
			monitBean = list.get(0);
			  }else {
				  monitBean = null;
			  }
		return monitBean;
		
		
		
	}

	@Override
	public void updateMonitStatus(String monitId, int Status) {
		String sql = "update monit set monitHostStatus=? where monitId=?";
		jt.update(sql, Status,monitId);
		
	}

	@Override
	public void updateMonit(MonitBean monitBean) {
		String sql = "update monit set monitVersion=?,monitHostName=?,monitHostIp=?"
				+ ",monitHostStatus=?,monitHostPort=?,updateTime=?,platformName=?"
				+ ",platformRelease=?,platformVersion=?,platformMachine=?"
				+ ",platformCpu=?,platformMem=?,platformSwap=? where monitId=?";
		jt.update(sql,monitBean.getMonitVersion(),monitBean.getMonitHostName(),
				monitBean.getMonitHostIp(),monitBean.getMonitHostStatus(),monitBean.getMonitHostPort(),
				monitBean.getUpdateTime(),monitBean.getPlatformName(),monitBean.getPlatformRelease(),monitBean.getPlatformVersion(),
				monitBean.getPlatformMachine(),monitBean.getPlatformCpu(),monitBean.getPlatformMem(),monitBean.getPlatformSwap(),monitBean.getMonitId());
		
	}

	@Override
	public void updateMonitEventCount(int eventCount,String monitId) {
		String sql = "update monit set eventcount=? where monitId=?";
		jt.update(sql, eventCount,monitId);
	}
	
}
