package com.zhxs.sys.vo;

import java.io.Serializable;

public class ClassResult implements Serializable {
	private static final long serialVersionUID = 1186166265652879159L;
	private String id;
	private String schoolname;
	private Integer clazz;
	private Integer level;
	private Integer year;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSchoolname() {
		return schoolname;
	}
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}
	public Integer getClazz() {
		return clazz;
	}
	public void setClazz(Integer clazz) {
		this.clazz = clazz;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
}
