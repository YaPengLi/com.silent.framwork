package com.silent.framwork.entity;

import java.io.Serializable;

public class RegisteredEntity implements Serializable {
	private String mobile;
	private String userid;
	private int status;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
