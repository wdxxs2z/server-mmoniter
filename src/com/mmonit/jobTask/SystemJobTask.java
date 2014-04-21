package com.mmonit.jobTask;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public class SystemJobTask {

	private JdbcTemplate jt;
	
	private final static String TABLE_PORCESS = "process";
	
	private final static String TABLE_REMOTEHOST = "remotehost";
	
	private final static String TABLE_REMOTEHOSTPORT  = "remotehostport";
	
	private final static String TABLE_SYSTEM = "system";
	
	private final static String TABLE_MONITFILE = "monitfile";
	
	private final static String TABLE_STATISTIC_CPU_USAGE = "statistic_cpu_usage";
	
	private final static String TABLE_STATISTIC_MEM_USAGE = "statistic_mem_usage";
	
	private final static String TABLE_STATISTIC_LOAD_AVERAGE = "statistic_load_average";
	
	private final static String TABLE_STATISTIC_PROCESS_CPU_USAGE = "statistic_process_cpu_usage";
	
	private final static String TABLE_STATISTIC_PROCESS_MEM_USAGE = "statistic_process_mem_usage";
	
	private final static String TABLE_STATISTIC_CPU_15M = "statistic_cpu_15m";
	
	private final static String TABLE_STATISTIC_LOAD_15M = "statistic_load_15m";
	
	private final static String TABLE_STATISTIC_MEM_15M = "statistic_mem_15m";
	
	private final static String TABLE_STATISTIC_PROCESS_CPU_15M = "statistic_process_cpu_15m";
	
	private final static String TABLE_STATISTIC_PROCESS_MEM_15M = "statistic_process_mem_15m";

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
	
	/*
	 * 定期清理表
	 *
	 * */
	public void _2dayDeleteNotStaticData(){
		String deleteMFile = "delete from " + TABLE_MONITFILE;
		String deleteMSystem = "delete from " + TABLE_SYSTEM;
		String deleteMRPort = "delete from " + TABLE_REMOTEHOSTPORT;
		String deleteMRemote = "delete from " + TABLE_REMOTEHOST;
		String deleteMProcess = "delete from " + TABLE_PORCESS;
		String[] deleteBatch = {deleteMFile,deleteMSystem,deleteMRPort,deleteMRemote,deleteMProcess};
		jt.batchUpdate(deleteBatch);
		
	}
	
	public void _4dayDeleteStatic15MData(){
		String deleteCpu15Sql = "delete from " + TABLE_STATISTIC_CPU_15M;
		String deleteMem15Sql = "delete from " + TABLE_STATISTIC_MEM_15M;
		String deleteLoad15Sql = "delete from " + TABLE_STATISTIC_LOAD_15M;
		String deletePCpu15Sql = "delete from " + TABLE_STATISTIC_PROCESS_CPU_15M;
		String deletePMem15Sql = "delete from " + TABLE_STATISTIC_PROCESS_MEM_15M;
		String[] deleteBatch = {deleteCpu15Sql,deleteMem15Sql,deleteLoad15Sql,deletePCpu15Sql,deletePMem15Sql};
		jt.batchUpdate(deleteBatch);
	}
	
	public void _7dayDeleteStaticNot15MData(){
		String deleteCpuSql = "delete from " + TABLE_STATISTIC_CPU_USAGE;
		String deleteMenSql = "delete from " + TABLE_STATISTIC_MEM_USAGE;
		String deleteLoadSql = "delete from " + TABLE_STATISTIC_LOAD_AVERAGE;
		String deletePCpu = "delete from " + TABLE_STATISTIC_PROCESS_CPU_USAGE;
		String deletePMem = "delete from " + TABLE_STATISTIC_PROCESS_MEM_USAGE;
		String[] deleteBatch = {deleteCpuSql,deleteMenSql,deleteLoadSql,deletePCpu,deletePMem};
		jt.batchUpdate(deleteBatch);
	}
}
