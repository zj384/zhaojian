package com.zhxs.sys.vo;

import java.io.Serializable;

public class SchoolResult implements Serializable {
	private static final long serialVersionUID = 5192401628591586274L;
	private Integer id;
	private String schoolname;
	private String city;
	private String county;
	private String province;
	private String sctype;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getSctype() {
		return sctype;
	}
	public void setSctype(String sctype) {
		this.sctype = sctype;
	}
	@Override
	public String toString() {
		return "SchoolResult [id=" + id + ", schoolname=" + schoolname + ", city=" + city + ", county=" + county
				+ ", province=" + province + ", sctype=" + sctype + "]";
	}


}
