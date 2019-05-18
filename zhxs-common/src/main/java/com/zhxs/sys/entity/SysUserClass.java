package com.zhxs.sys.entity;

import java.io.Serializable;

public class SysUserClass implements	Serializable {
	private static final long serialVersionUID = 6603481964900092569L;
	private String id;
	private String clazzid;
	private String userid;
	private Integer level;
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
}
