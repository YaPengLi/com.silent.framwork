package com.silent.framwork.interfaces;

public interface RequestCallBack {

	/**
	 * 
	 * 网络请求失败
	 * */
	public void onFailure(int status, String arg1);

	/**
	 * 
	 * 网络请求取消
	 * */
	public void onCancelled();

	/**
	 * 
	 * 网络请求开始
	 * */
	public void onStarted();

	/**
	 * 
	 * 网络请求成功
	 * */
	public void onSuccess(String arg0);

}
