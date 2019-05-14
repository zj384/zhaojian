package com.zhxs.sys.vo;

import java.io.Serializable;

public class FileResult implements Serializable {
	private static final long serialVersionUID = -7498342004428021941L;
	private Integer code = 0;
	private String message;
	private String src;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	
	public FileResult() {
		super();
	}
	public FileResult(Integer code, String message, String src) {
		super();
		this.code = code;
		this.message = message;
		this.src = src;
	}
	
}
