package com.zhxs.sys.entity;

import java.io.Serializable;

public class SysSchool implements Serializable {
	private static final long serialVersionUID = 4884101370006186349L;
	private Integer id;
	private String schoolname;
	private Integer cityid;
	private Integer countyid;
	private Integer provinceid;
	private Integer sctypeid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Integer getCountyid() {
		return countyid;
	}

	public void setCountyid(Integer countyid) {
		this.countyid = countyid;
	}

	public Integer getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public Integer getSctypeid() {
		return sctypeid;
	}

	public void setSctypeid(Integer sctypeid) {
		this.sctypeid = sctypeid;
	}

	@Override
	public String toString() {
		return "SysSchool [id=" + id + ", schoolname=" + schoolname + ", cityid=" + cityid + ", countyid=" + countyid
				+ ", provinceid=" + provinceid + ", sctypeid=" + sctypeid + "]";
	}

	

	

}
