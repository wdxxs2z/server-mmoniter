/*Drop database*/
drop database mmonit;
/*Create database mmonit*/
create database mmonit;
use mmonit;
/*Create table monit critical*/
create table monit(
monitId varchar(255) not null primary key,   /*Monit ID monit的唯一标示*/
monitVersion varchar(16),                    /*monit 的版本*/
monitHostName varchar(32),                   /*monit 安装端的主机名*/
monitHostIp varchar(42),                     /*monit 安装端的IP*/
monitHostStatus int,                         /*monit 安装端的主机状态 0：存活 1：未连接*/
monitHostPort varchar(6),                    /*monit 安装端的接收端口*/
username varchar(32),                        /*monit 用户名*/
password varchar(64),                        /*monit 用户密码*/
updateTime varchar(16),                      /*系统运行时间*/
platformName varchar(20),                    /*平台名*/
platformRelease varchar(255),                /*平台内核版本*/
platformVersion varchar(255),                /*平台内核编译时间*/
platformMachine varchar(255),                /*平台系统架构*/
platformCpu int,                             /*平台CPU个数*/
platformMem int,                             /*平台内存使用*/
platformSwap int,                            /*平台交换区使用*/
eventcount int                               /*事件个数*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Create table services*/
create table services(
id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
service_name varchar(32),                   /*服务名 对应事件的service*/
service_type int,                           /*服务类型 3 4 5*/
service_status int,                         /*服务状态*/
service_monit int,                          /*服务中 monit的状态 0为不监视 1为监视*/
monitor_host varchar(128),                  /*服务中 monit对应的monit IP OR HostName*/
monitId varchar(255) not null               /*monitId 唯一标识*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Create table processes*/
create table process (
process_id INT(5) AUTO_INCREMENT NOT NULL primary key,
processName varchar(16),                     /*进程名*/
processStatus int,                           /*进程状态*/
status_message varchar(64),                  /*进程状态信息*/
collected_sec varchar(16),                   /*收集进程数据开始时间*/
collected_usec varchar(16),                  /*收集进程数据持续时间*/
processPid varchar(8),                       /*进程的PID*/
processUptime varchar(16),                   /*进程运行时间*/
processChildren int,                         /*进程的子进程数*/
processMemPercenttotal numeric(4,1),         /*进程内存占用百分比*/
processMemKilobytetotal int,                 /*进程占用内存数 KB*/
processCpuPercenttotal numeric(4,1),         /*进程占用CPU百分比*/
monitId varchar(255)                         /*关联monit*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Create table system*/
create table system (
system_id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
systemName varchar(24),                      /*监控系统名*/
collected_sec varchar(16),                   /*收集系统数据开始时间*/
collected_usec varchar(16),                  /*收集系统数据持续时间*/
loadAvg01 numeric(5,2),                      /*load 负载 01*/
loadAvg05 numeric(5,2),                      /*load 负载 05*/
loadAvg15 numeric(5,2),                      /*load 负载 15*/
systemUserCpu numeric(4,1),                  /*用户态CPU占用百分比*/
systemSysCpu numeric(4,1),                   /*系统态CPU占用百分比*/
systemWaitCpu numeric(4,1),                  /*系统空闲CPU占用百分比*/
systemMemperc numeric(4,1),                  /*系统内存占用百分比*/
systemMemTot int,                            /*系统内存总占用数KB*/
systemSwapperc numeric(4,1),                 /*系统交换区占用百分比*/
systemSwapTot int,                           /*系统交换区占用数 KB*/
monitId varchar(255) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Create table remotehost*/
create table remotehost (
remote_id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
remoteHostName varchar(24),                  /*远程监控端 计算机名*/
collected_sec varchar(16),                   /*远程监控端 数据采集开始时间*/
collected_usec varchar(16),                  /*远程监控端 数据采集持续时间*/
remoteHostStatus int,                        /*远程监控端 计算节点的存活状态*/
remote_status_message varchar(64),           /*远程监控端 发生故障时 会报此信息 默认为null*/
monitId varchar(255) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Create table remotehostport*/
create table remotehostport (
remote_port_id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
port int,                                    /*远程监控端 监控端口*/
protocol varchar(12),                        /*远程监控端 监控服务协议*/
transport_type varchar(12),                  /*远程监控端 传输协议类型*/
responsetime numeric(6,3),                   /*远程监控端 端口相应时间*/
remoteHostName varchar(24),                  /*远程监控端 对应的IP*/
remotefeed varchar(255) not null             /*远程监控端 与remoteHost关联 格式：remoteHostName_monitId*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Create table monitevent*/
create table monitevent (
event_id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
service varchar(24),                         /*事件服务名*/
server_type int,                             /*事件服务的类型 3：进程，5：系统，4：远程*/
serStatus int,                               /*接收事件中服务的状态*/
action int,                                  /*接收事件中的动作*/
collected_sec varchar(16),                   /*事件 数据采集开始时间*/
collected_usec varchar(16),                  /*事件 数据采集持续时间*/
message varchar(255),                        /*事件具体信息*/
monitId varchar(255) not null                /*事件关联monit*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table monitfile(
file_id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
fileName varchar(24),
collected_sec varchar(24),
collected_usec varchar(24),
status int,
filemode int,
fileuid int,
filegid int,
filetimestamp varchar(64),
filesize varchar(128),
filechecksum varchar(255),
monitId varchar(255) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Create table static_processes*/
create table statistic_processes (
statistic_pro_id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
processName varchar(24),
avg_pro_mempercent numeric(4,1),
avg_pro_memKilobyte numeric(4,1),
avg_pro_cpupercent numeric(4,1),
statistic_type varchar(16),                 /*1m 15m 1hour 12hour 1day 7day*/
monitId varchar(255) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table statistic_systems (
statistic_sys_id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
systemName varchar(24),
avg_sys_load numeric(5,2),
avg_sys_syscpu numeric(4,1),
avg_sys_mempercent numeric(4,1),
avg_sys_memTol numeric(5,0),
avg_sys_swapTol numeric(5,0),
statistic_type varchar(16),                 /*15m 1hour 12hour 1day 7day*/
monitId varchar(255)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*datatype:  1:min 2:15min 3:1hout 4:1day*/
/*    CPU Usage    */
create table statistic_cpu_usage (
statistic_cpu_id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
inserttime timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
serverCpuUsage_max numeric(5,1),
serverCpuUsage_min numeric(5,1),
serverCpuUsage_avg numeric(5,1),
datatype int,
servertype int,
servermname varchar(64),
monitId varchar(255) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*    MEM Usage    */
create table statistic_mem_usage (
statistic_mem_id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
inserttime timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
serverMemUsage_max numeric(5,1),
serverMemUsage_min numeric(5,1),
serverMemUsage_avg numeric(5,1),
datatype int,
servertype int,
servername varchar(64),
monitId varchar(255) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*    LOAD Average */
create table statistic_load_average (
statistic_load_id INT(5) UNSIGNED AUTO_INCREMENT NOT NULL primary key,
inserttime timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
systemLoadAverage_max numeric(5,1),
systemLoadAverage_min numeric(5,1),
systemLoadAverage_avg numeric(5,1),
datatype int,
systemname varchar(64),
monitId varchar(255)  not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for `process15mJob`
-- ----------------------------
DROP PROCEDURE IF EXISTS `process15mJob`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `process15mJob`(IN monitid varchar(255),IN pname varchar(64))
begin

declare pcpuSql varchar(1000);
declare pmemSql varchar(1000);

set pcpuSql = concat("insert into statistic_process_cpu_15m(processCpuPercenttotal_avg,datatype,processname,monitId) 
select avg(processCpuPercenttotal),3,processName,monitId 
from process where monitId='" ,monitid, "' and processName='" ,pname, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 15 minute) and now()");

set pmemSql = concat("insert into statistic_process_mem_15m(processMemPercenttotal_avg,datatype,processname,monitId) 
select avg(processMemPercenttotal),3,processName,monitId 
from process where monitId='" ,monitid, "' and processName='" ,pname, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 15 minute) and now()");


set @sql1 = pcpuSql;
set @sql2 = pmemSql;

prepare stmt_pcpu from @sql1;
prepare stmt_pmem from @sql2;

execute stmt_pcpu;
execute stmt_pmem;
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `process1dayJob`
-- ----------------------------
DROP PROCEDURE IF EXISTS `process1dayJob`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `process1dayJob`(IN `monitid` varchar(255),IN `pname` varchar(64))
BEGIN

declare pcpuSql varchar(1000);
declare pmemSql varchar(1000);

set pcpuSql = concat("insert into statistic_process_cpu_usage(processCpuPercenttotal_max,processCpuPercenttotal_min,processCpuPercenttotal_avg,datatype,processname,monitId) 
select max(processCpuPercenttotal),min(processCpuPercenttotal),avg(processCpuPercenttotal),2,processName,monitId 
from process where monitId='" ,monitid, "' and processName='" ,pname, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 day) and now()");

set pmemSql = concat("insert into statistic_process_mem_usage(processMemPercenttotal_max,processMemPercenttotal_min,processMemPercenttotal_avg,datatype,processname,monitId) 
select max(processMemPercenttotal),min(processMemPercenttotal),avg(processMemPercenttotal),2,processName,monitId 
from process where monitId='" ,monitid, "' and processName='" ,pname, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 day) and now()");


set @sql1 = pcpuSql;
set @sql2 = pmemSql;

prepare stmt_pcpu from @sql1;
prepare stmt_pmem from @sql2;

execute stmt_pcpu;
execute stmt_pmem;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `process1hourJob`
-- ----------------------------
DROP PROCEDURE IF EXISTS `process1hourJob`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `process1hourJob`(IN `monitid` varchar(255),IN `pname` varchar(64))
BEGIN

declare pcpuSql varchar(1000);
declare pmemSql varchar(1000);

set pcpuSql = concat("insert into statistic_process_cpu_usage(processCpuPercenttotal_max,processCpuPercenttotal_min,processCpuPercenttotal_avg,datatype,processname,monitId) 
select max(processCpuPercenttotal),min(processCpuPercenttotal),avg(processCpuPercenttotal),1,processName,monitId 
from process where monitId='" ,monitid, "' and processName='" ,pname, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 hour) and now()");

set pmemSql = concat("insert into statistic_process_mem_usage(processMemPercenttotal_max,processMemPercenttotal_min,processMemPercenttotal_avg,datatype,processname,monitId) 
select max(processMemPercenttotal),min(processMemPercenttotal),avg(processMemPercenttotal),1,processName,monitId 
from process where monitId='" ,monitid, "' and processName='" ,pname, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 hour) and now()");


set @sql1 = pcpuSql;
set @sql2 = pmemSql;

prepare stmt_pcpu from @sql1;
prepare stmt_pmem from @sql2;

execute stmt_pcpu;
execute stmt_pmem;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `system15mJob`
-- ----------------------------
DROP PROCEDURE IF EXISTS `system15mJob`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `system15mJob`(IN monitid varchar(255))
begin 
declare cpuSql varchar(1000);
declare memSql varchar(1000);
declare loadSql varchar(1000);

set cpuSql = concat("insert into statistic_cpu_15m(systemCpuUsage_avg,userCpuUsage_avg,waitCpuUsage_avg,datatype,systemname,monitId) 
select avg(systemSysCpu),avg(systemUserCpu),avg(systemWaitCpu),3,systemname,monitId from system where monitId='", monitid, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 15 minute) and now()");

set memSql = concat("insert into statistic_mem_15m(systemMemperc_avg,datatype,systemname,monitId) 
select avg(systemMemperc),3,systemname,monitId from system where monitId='", monitid, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 15 minute) and now()");

set loadSql = concat("insert into statistic_load_15m(loadAvg_avg,datatype,systemname,monitId) 
select avg(loadAvg01),3,systemname,monitId from system where monitId='", monitid, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 15 minute) and now()");


set @sql1 = cpuSql;
set @sql2 = memSql;
set @sql3 = loadSql;

prepare stmt_cpu from @sql1;
prepare stmt_mem from @sql2;
prepare stmt_load from @sql3;

execute stmt_cpu;
execute stmt_mem;
execute stmt_load;
 
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `system1dayJob`
-- ----------------------------
DROP PROCEDURE IF EXISTS `system1dayJob`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `system1dayJob`(IN `monitid` varchar(255))
begin 
declare cpuSql varchar(1000);
declare memSql varchar(1000);
declare loadSql varchar(1000);

set cpuSql = concat("insert into statistic_cpu_usage(systemCpuUsage_max,systemCpuUsage_min,systemCpuUsage_avg,userCpuUsage_max,
userCpuUsage_min,userCpuUsage_avg,waitCpuUsage_max,waitCpuUsage_min,waitCpuUsage_avg,datatype,systemname,monitId) 
select max(systemSysCpu),min(systemSysCpu),avg(systemSysCpu),max(systemUserCpu),min(systemUserCpu),avg(systemUserCpu),
max(systemWaitCpu),min(systemWaitCpu),avg(systemWaitCpu),2,systemname,monitId from system where monitId='", monitid, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 day) and now()");

set memSql = concat("insert into statistic_mem_usage(systemMemperc_max,systemMemperc_min,systemMemperc_avg,datatype,systemname,monitId) 
select max(systemMemperc),min(systemMemperc),avg(systemMemperc),2,systemname,monitId from system where monitId='", monitid, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 day) and now()");

set loadSql = concat("insert into statistic_load_average(loadAvg_max,loadAvg_min,loadAvg_avg,datatype,systemname,monitId)  
select max(loadAvg01),min(loadAvg01),avg(loadAvg01),2,systemname,monitId from system where monitId='", monitid, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 day) and now()");


set @sql1 = cpuSql;
set @sql2 = memSql;
set @sql3 = loadSql;

prepare stmt_cpu from @sql1;
prepare stmt_mem from @sql2;
prepare stmt_load from @sql3;

execute stmt_cpu;
execute stmt_mem;
execute stmt_load;

end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `system1hourJob`
-- ----------------------------
DROP PROCEDURE IF EXISTS `system1hourJob`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `system1hourJob`(IN `monitid` varchar(255))
begin 
declare cpuSql varchar(1000);
declare memSql varchar(1000);
declare loadSql varchar(1000);

set cpuSql = concat("insert into statistic_cpu_usage(systemCpuUsage_max,systemCpuUsage_min,systemCpuUsage_avg,userCpuUsage_max,
userCpuUsage_min,userCpuUsage_avg,waitCpuUsage_max,waitCpuUsage_min,waitCpuUsage_avg,datatype,systemname,monitId) 
select max(systemSysCpu),min(systemSysCpu),avg(systemSysCpu),max(systemUserCpu),min(systemUserCpu),avg(systemUserCpu),
max(systemWaitCpu),min(systemWaitCpu),avg(systemWaitCpu),1,systemname,monitId from system where monitId='", monitid, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 hour) and now()");

set memSql = concat("insert into statistic_mem_usage(systemMemperc_max,systemMemperc_min,systemMemperc_avg,datatype,systemname,monitId) 
select max(systemMemperc),min(systemMemperc),avg(systemMemperc),1,systemname,monitId from system where monitId='", monitid, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 hour) and now()");

set loadSql = concat("insert into statistic_load_average(loadAvg_max,loadAvg_min,loadAvg_avg,datatype,systemname,monitId)  
select max(loadAvg01),min(loadAvg01),avg(loadAvg01),1,systemname,monitId from system where monitId='", monitid, 
"' and FROM_UNIXTIME(collected_sec) between subdate(now(), interval 1 hour) and now()");


set @sql1 = cpuSql;
set @sql2 = memSql;
set @sql3 = loadSql;

prepare stmt_cpu from @sql1;
prepare stmt_mem from @sql2;
prepare stmt_load from @sql3;

execute stmt_cpu;
execute stmt_mem;
execute stmt_load;

end
;;
DELIMITER ;
