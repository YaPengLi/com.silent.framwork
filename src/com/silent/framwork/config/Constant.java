package com.silent.framwork.config;

import android.os.Environment;

public interface Constant {
	/** 测试地址 */
	public static final String URL_IP = "http://aiyou.yintai.com";
	/**
	 * 接口访问URL
	 * */
	public static final String URL_PATH = URL_IP
			+ "/Module/mobile/aiyou.string";
	
	public static final String PATH_BASE = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/zhaoshang";
	public static final String PATH_IMAGE = PATH_BASE + "/image";
	public static final String PATH_APK = PATH_BASE + "/apk";
	/** 截屏路径 */
	public static final String IMG_PATH = PATH_BASE + "/screenshot/";
	/** 上传图片临时文件名 */
	public static final String TEMP_IMG = PATH_IMAGE + "/tmp.png";
	/*---------------------------------接口状态定义------------------------------------------------*/
	/** 网络访问失败 */
	public static final int FAILURE_NET_STATUS = -1;
	/** 返回数据为空 */
	public static final int FAILURE_NULL_STATUS = -2;
	/** JSON数据异常 */
	public static final int FAILURE_JSON_STATUS = -3;
	/** 微信登录失败 */
	public static final int FAILURE_WEICHAT_STATUS = -4;
	/** */
	public static final int FAILURE_MOBILE_STATUS = -5;
	/** 手机号为空 */
	public static final int FAILURE_MOBILE_STATUS_NULL = -6;
	/** 手机号长度不足 */
	public static final int FAILURE_MOBILE_STATUS_LENTHER = -7;
	/** 手机号不符合规则 */
	public static final int FAILURE_MOBILE_STATUS_REGULAR = -8;
	/** 已验证后手机号发生改变 */
	public static final int FAILURE_MOBILE_STATUS_CHANGE = -9;
	/** 手机已被绑定 */
	public static final int FAILURE_CODE_STATUS_EXISTENCE = -10;
	/** 非法验证码 */
	public static final int FAILURE_CODE_STATUS_ILLEGAL = -11;
	/** 验证码为空 */
	public static final int FAILURE_CODE_STATUS_NULL = -12;
	/** 验证码长度不足4-6位 */
	public static final int FAILURE_CODE_STATUS_LENGTH = -13;
	/** 描述为空 */
	public static final int FAILURE_DES_NULL = -14;
	/** 系统返回数据异常 */
	public static final int FAILURE_ERROR = -15;
	/** 密码为空*/
	public static final int FAILURE_PASSWORD_STATUS_NULL = -16;
}
