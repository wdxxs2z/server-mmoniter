package com.mmonit.DaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mmonit.Dao.MonitSerBusDao;
import com.mmonit.bean.MonitSerBusBean;

public class MonitSerBusDaoImpl implements MonitSerBusDao{
	
	private JdbcTemplate jt;
	
	public JdbcTemplate getJt() {
		return jt;
	}

	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}
	
	@Override
	public MonitSerBusBean findMonitSerBusByMID(String monitID) {
		String sql = "select service_name,service_type,service_status,service_monit,monitor_host,monitId from services "
				+ "where monitId=?";
		String[] s = {monitID};
		MonitSerBusBean monitSerBusBean = null;
		List<MonitSerBusBean> list = jt.query(sql, s, new RowMapper<MonitSerBusBean>(){

			@Override
			public MonitSerBusBean mapRow(ResultSet rs, int arg1) 
					throws SQLException {
				MonitSerBusBean monitSerBusBean = new MonitSerBusBean();
				monitSerBusBean.setService_name(rs.getString("service_name"));
				monitSerBusBean.setService_type(rs.getInt("service_type"));
				monitSerBusBean.setService_status(rs.getInt("service_status"));
				monitSerBusBean.setService_monit(rs.getInt("service_monit"));
				monitSerBusBean.setMonitor_host(rs.getString("monitor_host"));
				monitSerBusBean.setMonitId(rs.getString("monitId"));
				return monitSerBusBean;
			}
			
			
		});
		if(list!=null &&list.size()>0){
			monitSerBusBean = list.get(0);
			  }else {
				  monitSerBusBean = null;
			  }
		return monitSerBusBean;
	}

	@Override
	public void saveMonitSerBus(MonitSerBusBean busBean,String monitId) {
		
		String sql= "insert into services(service_name,service_type,"
				+ "service_status,service_monit,monitor_host,monitId) values(?,?,?,?,?,?)";
		jt.update(sql,busBean.getService_name(),busBean.getService_type(),
				busBean.getService_status(),busBean.getService_monit(),busBean.getMonitor_host(),monitId);
	}

	@Override
	public Object findMonitSerBusByMIDAndSerName(String monitID,
			String service_name) {
		String sql = "select service_name,service_type,service_status,service_monit,monitor_host,monitId from services "
				+ "where monitId=? and service_name=?";
		String[] s = {monitID,service_name};
		MonitSerBusBean monitSerBusBean = null;
		List<MonitSerBusBean> list = jt.query(sql, s, new RowMapper<MonitSerBusBean>(){

			@Override
			public MonitSerBusBean mapRow(ResultSet rs, int arg1) 
					throws SQLException {
				MonitSerBusBean monitSerBusBean = new MonitSerBusBean();
				monitSerBusBean.setService_name(rs.getString("service_name"));
				monitSerBusBean.setService_type(rs.getInt("service_type"));
				monitSerBusBean.setService_status(rs.getInt("service_status"));
				monitSerBusBean.setService_monit(rs.getInt("service_monit"));
				monitSerBusBean.setMonitor_host(rs.getString("monitor_host"));
				monitSerBusBean.setMonitId(rs.getString("monitId"));
				return monitSerBusBean;
			}
			
			
		});
		if(list!=null &&list.size()>0){
			monitSerBusBean = list.get(0);
			  }else {
				  monitSerBusBean = null;
			  }
		return monitSerBusBean;
	}

	@Override
	public void updateMonitSerBus(String smonitId, String service, int serStatus, int serMonitor) {
		String sql = "update services set service_status=?, service_monit =? where monitId=? and service_name=?";
		jt.update(sql, serStatus,serMonitor,smonitId,service);
	}

	@Override
	public void deleteMonitSerBus() {
		String deleteServiceTable = "delete from services";
		jt.update(deleteServiceTable);		
	}


}
