package com.silent.framwork.http;

import com.silent.framwork.config.Constant;
import com.silent.framwork.tools.MD5;
import com.silent.framwork.tools.NetworkUtils;
import com.silent.framwork.tools.ScreenTools;
import com.silent.framwork.tools.SystemUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author LiYapeng 网络请求包中必选项
 */
@SuppressLint("DefaultLocale")
public class RequestOptional {

	/** 客户端平台 iphone/ipad/andriod */
	public static final String PLATFORM = "os";

	/** 时间戳 */
	public static final String TIME = "time";

	/** 客户端系统版本 */
	public static final String OSVERSION = "osver";

	/** Mac地址 */
	public static final String MACID = "macid";

	/** 国际移动设备身份码 */
	public static final String IMEI = "imei";

	/** 网络连接类型 wifi 3g */
	public static final String WANTYPE = "wantype";

	/** 屏幕宽 */
	public static final String SCREENWIDTH = "screenwidth";

	/** 屏幕高 */
	public static final String SCREENHEIGHT = "screenheight";

	/** IP地址 */
	public static final String IP = "ip";

	/** 用户标识 */
	public static final String TOKEN = "token";
	public static final String CHANNEL = "channel";
	/** 验签 */
	public static final String sign = "sign";
	/** SDK版本 */
	public static final String VER = "ver";
	private static FormEncodingBuilder formEncodingBuilder;

	private RequestOptional() {

	}

	public static synchronized FormEncodingBuilder getInstance() {
		if (formEncodingBuilder == null) {
			formEncodingBuilder = new FormEncodingBuilder();
		}
		return formEncodingBuilder;
	}

	public static synchronized Request getRequest(Context mContext,
			FormEncodingBuilder formEncodingBuilder, String methodName) {
		// TODO Auto-generated method stub
		RequestBody initParam = initParam(mContext, formEncodingBuilder,
				methodName);
		Request request = new Request.Builder().url(Constant.URL_PATH).addHeader("Content-Type", "application/x-www-form-urlencoded")
				.post(initParam).build();
		return request;
	}   

	/**
	 * 初始化必选项
	 * 
	 * @param params
	 */
	private static RequestBody initParam(Context mContext,
			FormEncodingBuilder formEncodingBuilder, String methodName) {
		String timerstr = SystemUtils.getDataTimeStr();
		formEncodingBuilder.add("method", methodName);
		// params.addHeader("Content-Type",
		// "application/x-www-form-urlencoded");
		/* 客户端平台 andriod */
		formEncodingBuilder.add(PLATFORM, "android");
		/* 发起请求时间 */
		formEncodingBuilder.add(TIME, timerstr);
		/* 客户端系统版本 */
		formEncodingBuilder.add(OSVERSION, "" + SystemUtils.getSystemCode());
		formEncodingBuilder.add(VER, "1.0");
		/* Mac地址 */
		formEncodingBuilder.add(MACID, SystemUtils.getMacAddress(mContext));
		String imei2 = SystemUtils.getIMEI(mContext);
		/* 国际移动设备身份码 */
		formEncodingBuilder.add(IMEI, imei2);
		/* 网络连接类型 wifi 3g */
		String networkTypeName = NetworkUtils.getNetworkTypeName(mContext);
		formEncodingBuilder.add(WANTYPE, networkTypeName);
		/* 屏幕宽 */
		formEncodingBuilder.add(SCREENWIDTH,
				ScreenTools.getScreenWidth(mContext) + "");
		/* 屏幕高 */
		formEncodingBuilder.add(SCREENHEIGHT,
				ScreenTools.getScreenHeight(mContext) + "");
		/* IP地址 */
		formEncodingBuilder.add(IP, SystemUtils.getIpAddress(mContext));
		formEncodingBuilder.add(CHANNEL, "1");
		/* AccessToken */
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				"LGB_TOKEN", Activity.MODE_PRIVATE);
		if (sharedPreferences.getString("LGB_TOKEN", "") != null) {
			formEncodingBuilder.add(TOKEN,
					sharedPreferences.getString("LGB_TOKEN", ""));
		} else {
			formEncodingBuilder.add(TOKEN, "");
		}
		// 5F54AD25F314DBD0FB783D3FC021BA4C
		formEncodingBuilder.add(
				sign,
				MD5.MD5Encode(
						"os=android&osver=" + SystemUtils.getSystemCode()
								+ "&method=" + methodName + "&ver=1.0&time="
								+ timerstr
								+ "&appkey=d82d5fcd2b854d0091a930793a75b9df"
								+ "&channel=1").toString());
		/* 身份密钥(不参与网络传输) b87d8768a948fde31065a12341808c98(32个字符) 详见1.3.2约定的密钥 */
		return formEncodingBuilder.build();
	}
	// H5 os&time&appkey&channel&userid

}
