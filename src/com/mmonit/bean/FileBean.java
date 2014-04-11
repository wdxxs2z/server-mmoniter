package com.mmonit.bean;

import java.io.Serializable;

public class FileBean implements Serializable {

	private static final long serialVersionUID = 6998774673099716708L;
	
	/*file monit id*/
	private String file_id;
	
	/*文件名*/
	private String fileName;
	
	/*收集时间*/
	private String collected_sec;
	
	/*使用持续时间*/
	private String collected_usec;
	
	/*监控状态*/
	private Integer status;
	
	/*file的权限 777 600 等*/
	private Integer filemode;
	
	/*uid*/
	private Integer fileuid;
	
	/*gid*/
	private Integer filegid;
	
	/*时间戳*/
	private String filetimestamp;
	
	/*文件大小*/
	private String filesize;
	
	/*检测类型*/
	private String filechecksum;
	
	/*file monitId*/
	private String monitId;
	
	private MonitBean monitBean;

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCollected_sec() {
		return collected_sec;
	}

	public void setCollected_sec(String collected_sec) {
		this.collected_sec = collected_sec;
	}

	public String getCollected_usec() {
		return collected_usec;
	}

	public void setCollected_usec(String collected_usec) {
		this.collected_usec = collected_usec;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFilemode() {
		return filemode;
	}

	public void setFilemode(Integer filemode) {
		this.filemode = filemode;
	}

	public Integer getFileuid() {
		return fileuid;
	}

	public void setFileuid(Integer fileuid) {
		this.fileuid = fileuid;
	}

	public Integer getFilegid() {
		return filegid;
	}

	public void setFilegid(Integer filegid) {
		this.filegid = filegid;
	}

	public String getFiletimestamp() {
		return filetimestamp;
	}

	public void setFiletimestamp(String filetimestamp) {
		this.filetimestamp = filetimestamp;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getFilechecksum() {
		return filechecksum;
	}

	public void setFilechecksum(String filechecksum) {
		this.filechecksum = filechecksum;
	}

	public String getMonitId() {
		return monitId;
	}

	public void setMonitId(String monitId) {
		this.monitId = monitId;
	}

	public MonitBean getMonitBean() {
		return monitBean;
	}

	public void setMonitBean(MonitBean monitBean) {
		this.monitBean = monitBean;
	}
}
