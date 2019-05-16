package com.zhxs.sys.vo;

import java.io.Serializable;

public class ImageCondition implements Serializable {
	private static final long serialVersionUID = 8343127701256296436L;
	private Integer level;
	private Integer isUser;
	private Integer page;
	private Integer limit;
	private Integer islove;
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getIsUser() {
		return isUser;
	}
	public void setIsUser(Integer isUser) {
		this.isUser = isUser;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
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
}
