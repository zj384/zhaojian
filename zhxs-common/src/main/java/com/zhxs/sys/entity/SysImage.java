package com.zhxs.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class SysImage implements Serializable {
	private static final long serialVersionUID = -2392587911618583995L;
	private Integer id;
	private String clazzid;
	private String userid;
	private String url;
	private String ddr;
	private Date createtime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClazzid() {
		return clazzid;
	}
	public void setClazzid(String clazzid) {
		this.clazzid = clazzid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDdr() {
		return ddr;
	}
	public void setDdr(String ddr) {
		this.ddr = ddr;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
