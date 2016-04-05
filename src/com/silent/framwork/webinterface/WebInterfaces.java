package com.silent.framwork.webinterface;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;

import com.silent.framwork.config.Constant;
import com.silent.framwork.http.OkHttpUtil;
import com.silent.framwork.http.RequestOptional;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;

/**
 * 接口访问
 * 
 * */
public class WebInterfaces {
	/** 请求登录 */
	void requestSignin(Context mContext, String account, String password,
			Callback mCallBack) {
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("mobile", account);
		builder.add("password", password);
		Request request = RequestOptional.getRequest(mContext, builder,
				Constant.SIGN_IN);
		OkHttpUtil.enqueue(request);
	}

	/** 请求退出 */
	public void requestSignout(Context mContext, String userId,
			Callback mCallBack) {
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("userid", userId + "");
		Request request = RequestOptional.getRequest(mContext, builder,
				Constant.SIGN_OUT);
		OkHttpUtil.enqueue(request);
	}

	/** 收藏内容 */
	public void requestCollect(Context mContext, String userId,
			Callback mCallBack) {
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("userid", userId + "");
		builder.add("targetid", userId + "");
		Request request = RequestOptional.getRequest(mContext, builder,
				Constant.COLLECT);
		OkHttpUtil.enqueue(request);
	}

	/** 获取用户信息 */
	public void requestUserInfo(Context mContext, String userId,
			Callback mCallBack) {
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("userid", userId + "");
		Request request = RequestOptional.getRequest(mContext, builder,
				Constant.GET_USERINFO);
		OkHttpUtil.enqueue(request);
	}

	/** 修改姓名 */
	public void requestUpdateName(Context mContext, String userId, String name,
			Callback mCallBack) {
		try {
			name = URLEncoder.encode(name, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("userid", userId + "");
		builder.add("name", name + "");
		Request request = RequestOptional.getRequest(mContext, builder,
				Constant.UPDATE_NAME);
		OkHttpUtil.enqueue(request);
	}

	/** 修改性别 */
	public void requestUpdateGender(Context mContext, String userId,
			String gender, Callback mCallBack) {
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("userid", userId + "");
		builder.add("gender", gender);
		Request request = RequestOptional.getRequest(mContext, builder,
				Constant.UPDATE_GENDER);
		OkHttpUtil.enqueue(request);
	}

	/** 修改头像 */
	public void requestUpdateHeadImg(Context mContext, String userId,
			String base64, Callback mCallBack) {
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("userid", userId + "");
		builder.add("headimg", base64);
		Request request = RequestOptional.getRequest(mContext, builder,
				Constant.UPDATE_HEADIMG);
		OkHttpUtil.enqueue(request);
	}

	/** 注册 */
	public void requestToRegistered(Context mContext, String userId,
			String mobile, String password, String code, Callback mCallBack) {
		userId = "".equals(userId) ? "-1" : userId;
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("userid", userId + "");
		builder.add("mobile", mobile);
		builder.add("code", code);
		builder.add("password", password);
		Request request = RequestOptional.getRequest(mContext, builder,
				Constant.SUBMIT_CODE);
		OkHttpUtil.enqueue(request);
	}

	/** 请求验证码 */
	public void requestToCode(Context mContext, String userId, String mobile,
			Callback mCallBack) {
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("userid", userId + "");
		builder.add("mobile", mobile);
		Request request = RequestOptional.getRequest(mContext, builder,
				Constant.GET_CODE);
		OkHttpUtil.enqueue(request);
	}
}
