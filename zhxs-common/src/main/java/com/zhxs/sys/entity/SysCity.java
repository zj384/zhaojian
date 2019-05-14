package com.zhxs.sys.entity;

import java.io.Serializable;

public class SysCity implements Serializable {
	private static final long serialVersionUID = 3355912352379113534L;
	private Integer id;
	private String name;
	private Integer parentid;
	private Integer level;
	private String first;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	
	
}
