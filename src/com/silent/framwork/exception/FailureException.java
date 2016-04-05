package com.silent.framwork.exception;

public class FailureException extends Exception {
	private static final long serialVersionUID = -5100566806826020540L;
	private int status;
	private String msg;
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public FailureException(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public FailureException() {
		super();
	}

}
