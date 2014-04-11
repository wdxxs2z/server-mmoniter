package com.mmonit.jobTask;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class SystemJobTask {

	private JdbcTemplate jt;

	public JdbcTemplate getJt() {
		return jt;
	}

	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	/*
	 * 定义方法 存储过程： delimiter $ create procedure system15mJob(IN monitid
	 * varchar(255)) begin declare cpuSql varchar(1000); declare memSql
	 * varchar(1000); declare loadSql varchar(1000);
	 * 
	 * set cpuSql = concat("insert into
	 * statistic_cpu_15m(systemCpuUsage_avg,userCpuUsage_avg
	 * ,waitCpuUsage_avg,datatype,systemname,monitId) select
	 * avg(systemSysCpu),avg
	 * (systemUserCpu),avg(systemWaitCpu),3,systemname,monitId from system where
	 * monitId='", monitid,
	 * "' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 15 minute) and now()"
	 * );
	 * 
	 * set memSql = concat("insert into
	 * statistic_mem_15m(systemMemperc_avg,datatype,systemname,monitId) select
	 * avg(systemMemperc),3,systemname,monitId from system where monitId='",
	 * monitid,
	 * "' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 15 minute) and now()"
	 * );
	 * 
	 * set loadSql = concat("insert into
	 * statistic_load_15m(loadAvg_avg,datatype,systemname,monitId) select
	 * avg(loadAvg01),3,systemname,monitId from system where monitId='",
	 * monitid,
	 * "' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 15 minute) and now()"
	 * );
	 * 
	 * 
	 * set @sql1 = cpuSql; set @sql2 = memSql; set @sql3 = loadSql;
	 * 
	 * prepare stmt_cpu from @sql1; prepare stmt_mem from @sql2; prepare
	 * stmt_load from @sql3;
	 * 
	 * execute stmt_cpu; execute stmt_mem; execute stmt_load;
	 * 
	 * end -> $
	 */
	public void _15minSystemJob() {
		String systemSql = "select monitId from monit";
		List<String> monitIds = jt.queryForList(systemSql, String.class);
		//调用存储过程
		for(String monitid : monitIds){
			jt.execute("call system15mJob('" + monitid + "')");
			//遍历进程信息
			String processSql = "select service_name from services where monitId=? and service_type=3";
			List<String> processes = jt.queryForList(processSql, String.class, monitid);
			for(String pname : processes){
				jt.execute("call process15mJob('" + monitid + "','" + pname + "')");
			}			
		}
	}
	
	/*
	 * 定义存储过程 delimiter $ create procedure system1hourJob(IN monitid
	 * varchar(255)) begin declare cpuSql varchar(1000); declare memSql
	 * varchar(1000); declare loadSql varchar(1000);
	 * 
	 * set cpuSql = concat("insert into
	 * statistic_cpu_usage(systemCpuUsage_max,systemCpuUsage_min
	 * ,systemCpuUsage_avg,userCpuUsage_max,
	 * userCpuUsage_min,userCpuUsage_avg,waitCpuUsage_max
	 * ,waitCpuUsage_min,waitCpuUsage_avg,datatype,systemname,monitId) select
	 * max
	 * (systemSysCpu),min(systemSysCpu),avg(systemSysCpu),max(systemUserCpu),min
	 * (systemUserCpu),avg(systemUserCpu),
	 * max(systemWaitCpu),min(systemWaitCpu),
	 * avg(systemWaitCpu),1,systemname,monitId from system where monitId='",
	 * monitid,
	 * "' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 hour) and now()"
	 * );
	 * 
	 * set memSql = concat("insert into
	 * statistic_mem_usage(systemMemperc_max,systemMemperc_min
	 * ,systemMemperc_avg,datatype,systemname,monitId) select
	 * max(systemMemperc),
	 * min(systemMemperc),avg(systemMemperc),1,systemname,monitId from system
	 * where monitId='", monitid,
	 * "' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 hour) and now()"
	 * );
	 * 
	 * set loadSql = concat("insert into
	 * statistic_load_average(loadAvg_max,loadAvg_min
	 * ,loadAvg_avg,datatype,systemname,monitId) select
	 * max(loadAvg01),min(loadAvg01),avg(loadAvg01),1,systemname,monitId from
	 * system where monitId='", monitid,
	 * "' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 hour) and now()"
	 * );
	 * 
	 * 
	 * set @sql1 = cpuSql; set @sql2 = memSql; set @sql3 = loadSql;
	 * 
	 * prepare stmt_cpu from @sql1; prepare stmt_mem from @sql2; prepare
	 * stmt_load from @sql3;
	 * 
	 * execute stmt_cpu; execute stmt_mem; execute stmt_load;
	 * 
	 * end -> $
	 */
	public void _1hourSystemJob() {
		String systemSql = "select monitId from monit";
		
		List<String> monitIds = jt.queryForList(systemSql, String.class);
		//调用存储过程
		for(String monitid : monitIds){
			jt.execute("call system1hourJob('" + monitid + "')");
			//遍历进程信息
			String processSql = "select service_name from services where monitId=? and service_type=3";
			List<String> processes = jt.queryForList(processSql, String.class, monitid);
			for(String pname : processes){
				jt.execute("call process1hourJob('" + monitid + "','" + pname + "')");
			}
		}
	}
	
	/*
	 * 定义存储过程 delimiter $ create procedure system1dayJob(IN monitid
	 * varchar(255)) begin declare cpuSql varchar(1000); declare memSql
	 * varchar(1000); declare loadSql varchar(1000);
	 * 
	 * set cpuSql = concat("insert into
	 * statistic_cpu_usage(systemCpuUsage_max,systemCpuUsage_min
	 * ,systemCpuUsage_avg,userCpuUsage_max,
	 * userCpuUsage_min,userCpuUsage_avg,waitCpuUsage_max
	 * ,waitCpuUsage_min,waitCpuUsage_avg,datatype,systemname,monitId) select
	 * max
	 * (systemSysCpu),min(systemSysCpu),avg(systemSysCpu),max(systemUserCpu),min
	 * (systemUserCpu),avg(systemUserCpu),
	 * max(systemWaitCpu),min(systemWaitCpu),
	 * avg(systemWaitCpu),2,systemname,monitId from system where monitId='",
	 * monitid,
	 * "' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 day) and now()"
	 * );
	 * 
	 * set memSql = concat("insert into
	 * statistic_mem_usage(systemMemperc_max,systemMemperc_min
	 * ,systemMemperc_avg,datatype,systemname,monitId) select
	 * max(systemMemperc),
	 * min(systemMemperc),avg(systemMemperc),2,systemname,monitId from system
	 * where monitId='", monitid,
	 * "' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 day) and now()"
	 * );
	 * 
	 * set loadSql = concat("insert into
	 * statistic_load_average(loadAvg_max,loadAvg_min
	 * ,loadAvg_avg,datatype,systemname,monitId) select
	 * max(loadAvg01),min(loadAvg01),avg(loadAvg01),2,systemname,monitId from
	 * system where monitId='", monitid,
	 * "' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 day) and now()"
	 * );
	 * 
	 * 
	 * set @sql1 = cpuSql; set @sql2 = memSql; set @sql3 = loadSql;
	 * 
	 * prepare stmt_cpu from @sql1; prepare stmt_mem from @sql2; prepare
	 * stmt_load from @sql3;
	 * 
	 * execute stmt_cpu; execute stmt_mem; execute stmt_load;
	 * 
	 * end $
	 */
	public void _1daySystemJob() {
		String systemSql = "select monitId from monit";
		List<String> monitIds = jt.queryForList(systemSql, String.class);
		//调用存储过程
		for(String monitid : monitIds){
			jt.execute("call system1dayJob('" + monitid + "')");
			//遍历进程信息
			String processSql = "select service_name from services where monitId=? and service_type=3";
			List<String> processes = jt.queryForList(processSql, String.class, monitid);
			for(String pname : processes){
				jt.execute("call process1dayJob('" + monitid + "','" + pname + "')");
			}
		}
	}
}
