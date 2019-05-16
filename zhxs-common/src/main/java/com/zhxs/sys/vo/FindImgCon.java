package com.zhxs.sys.vo;

import java.io.Serializable;

public class FindImgCon implements Serializable {
	private static final long serialVersionUID = 3528786985920009251L;
	private String userid;
	private String clazzid;
	private Integer startIndex;
	private Integer limit;
	private Integer islove;
	private Integer isuser;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getClazzid() {
		return clazzid;
	}
	public void setClazzid(String clazzid) {
		this.clazzid = clazzid;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getIslove() {
		return islove;
	}
	public void setIslove(Integer islove) {
		this.islove = islove;
	}
	public Integer getIsuser() {
		return isuser;
	}
	public void setIsuser(Integer isuser) {
		this.isuser = isuser;
	}
}
