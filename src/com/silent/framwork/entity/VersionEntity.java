package com.silent.framwork.entity;

import java.io.Serializable;

public class VersionEntity implements Serializable{
	private int vercode;
	private String msg;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getVercode() {
		return vercode;
	}
	public void setVercode(int vercode) {
		this.vercode = vercode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
