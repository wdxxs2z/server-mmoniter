/*Drop database*/
drop database ibank_mmonit;
/*Create database mmonit*/
create database ibank_mmonit;
use ibank_mmonit;
/*Create table monit critical*/
create table monit(
monitId varchar(255) not null primary key,   /*Monit ID monit的唯一标示*/
monitVersion varchar(16),                    /*monit 的版本*/
monitHostName varchar(32),                   /*monit 安装端的主机名*/
monitHostIp varchar(42),                     /*monit 安装端的IP*/
monitHostStatus int,                         /*monit 安装端的主机状态 0：存活 1：未连接*/
monitHostPort varchar(6),                    /*monit 安装端的接收端口*/
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
