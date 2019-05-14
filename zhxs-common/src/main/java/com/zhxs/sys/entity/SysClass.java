package com.zhxs.sys.entity;

import java.io.Serializable;

public class SysClass implements Serializable {
	private static final long serialVersionUID = 3287786163378343563L;
	private String id;
	private Integer schoolid;
	private Integer clazz;
	private Integer level;
	private Integer year;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
