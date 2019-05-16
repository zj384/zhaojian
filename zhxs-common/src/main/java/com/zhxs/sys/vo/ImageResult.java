package com.zhxs.sys.vo;

import java.io.Serializable;
import java.util.Date;

public class ImageResult implements Serializable {
	private static final long serialVersionUID = 4465701862242947167L;
	private Integer id;
	private String clazzname;
	private String nickname;
	private String url;
	private Date createtime;
	private String ddr;
	private Integer love;
	private String islove;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClazzname() {
		return clazzname;
	}
	public void setClazzname(String clazzname) {
		this.clazzname = clazzname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getDdr() {
		return ddr;
	}
	public void setDdr(String ddr) {
		this.ddr = ddr;
	}
	public Integer getLove() {
		return love;
	}
	public void setLove(Integer love) {
		this.love = love;
	}
	public String getIslove() {
		return islove;
	}
	public void setIslove(String islove) {
		this.islove = islove;
	}
}
