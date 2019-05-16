package com.zhxs.sys.entity;

import java.io.Serializable;

public class SysLove implements Serializable {
	private static final long serialVersionUID = 6445001992205575674L;
	private String id;
	private String userid;
	private Integer imageid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getImageid() {
		return imageid;
	}
	public void setImageid(Integer imageid) {
		this.imageid = imageid;
	}
	
}
