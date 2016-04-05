package com.silent.framwork.sharedPreference;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.reflect.TypeToken;
import com.silent.framwork.entity.BaseEntity;
import com.silent.framwork.entity.RegisteredEntity;
import com.silent.framwork.entity.UserInfoEntity;
import com.silent.framwork.entity.VersionEntity;
import com.silent.framwork.exception.FailureException;
import com.silent.framwork.model.Model;

@SuppressLint("CommitPrefEdits")
public class SharedPreferencesTools {
	private static Context mContext;
	private static SharedPreferencesTools mSharedPreferencesTools;
	public static final String FIRSTOPEN = "firstOpen";
	public static final String USERIFNO = "userinfo";
	public static final String VERSION = "version";
	public static final String GETREGISTERED = "registered";

	private SharedPreferencesTools() {
		super();
	}

	// 单例类 饿汉式 直接返回已创建好对象 懒汉式 获取对象时，进行判断，如果为空，进行创建
	public static synchronized SharedPreferencesTools invaliData(Context context) {
		mContext = context;
		if (null == mSharedPreferencesTools)
			mSharedPreferencesTools = new SharedPreferencesTools();
		return mSharedPreferencesTools;
	}

	/**
	 * 
	 * 通过引导页 保存标识
	 * */
	public void saveFirstOpen() {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				FIRSTOPEN, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putInt(FIRSTOPEN, 1);
		edit.commit();
	}

	/**
	 * 获取是否通过引导页标识
	 * */
	public int getFirstOpen() {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				FIRSTOPEN, Context.MODE_PRIVATE);
		return sharedPreferences.getInt(FIRSTOPEN, 0);
	}

	public void saveRegisteredEntity(RegisteredEntity mEntity) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				GETREGISTERED, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString("userid", mEntity.getUserid());
		edit.putString("mobile", mEntity.getMobile());
		edit.commit();
	}

	/**
	 * 已抛弃，无使用
	 * */
	@Deprecated
	public void saveUserInfo(UserInfoEntity mUserInfo) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				USERIFNO, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString("", "");
		edit.putString("userid", mUserInfo.getUserid());
		edit.putString("name", mUserInfo.getName());
		edit.putString("headimgurl", mUserInfo.getHeadimgurl());
		edit.putString("mobile", mUserInfo.getMobile());
		edit.putString("openid", mUserInfo.getOpenid());
		edit.putString("unionid", mUserInfo.getUnionid());
		edit.putString("sex", mUserInfo.getSex());
		edit.putString("age", mUserInfo.getAge());
		edit.putString("price", mUserInfo.getPrice());
		edit.commit();
	}

	/**
	 * 存储用户信息
	 * */
	public void saveUserInfo(String json) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				USERIFNO, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString("userinfo", json);
		edit.commit();
	}

	public void saveUserInfoForName(String name) throws JSONException {
		String userInfo = getUserInfo();
		JSONObject object = new JSONObject(userInfo);
		JSONObject optJSONObject = object.optJSONObject("data");
		optJSONObject.putOpt("name", name);
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				USERIFNO, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString("userinfo", object.toString());
		edit.commit();
	}

	public void saveUserInfoForGender(String gender) throws JSONException {
		String userInfo = getUserInfo();
		JSONObject object = new JSONObject(userInfo);
		JSONObject optJSONObject = object.optJSONObject("data");
		optJSONObject.putOpt("gender", gender);

		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				USERIFNO, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString("userinfo", object.toString());
		edit.commit();
	}

	public void saveUserInfoForHeadImg(String gender) throws JSONException {
		String userInfo = getUserInfo();
		JSONObject object = new JSONObject(userInfo);
		JSONObject optJSONObject = object.optJSONObject("data");
		optJSONObject.putOpt("headimgurl", gender);
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				USERIFNO, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString("userinfo", object.toString());
		edit.commit();
	}

	/**
	 * 获取用户信息
	 * */
	public String getUserInfo() {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				USERIFNO, Context.MODE_PRIVATE);
		String userinfo = sharedPreferences.getString("userinfo", null);
		return userinfo;
	}

	public RegisteredEntity getRegisteredEntity() {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				GETREGISTERED, Context.MODE_PRIVATE);
		RegisteredEntity mEntity = new RegisteredEntity();
		mEntity.setMobile(sharedPreferences.getString("mobile", ""));
		mEntity.setUserid(sharedPreferences.getString("userid", ""));
		return mEntity;
	}

	public void clearRegisteredEntity() {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				GETREGISTERED, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.clear();
		edit.commit();
	}

	public void clearUserInfo() {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				USERIFNO, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.clear();
		edit.commit();
	}

	@SuppressWarnings("rawtypes")
	public UserInfoEntity getUserInfoOfJSON(int id, Model mModel) {
		UserInfoEntity mUserInfo = new UserInfoEntity();
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				USERIFNO, Context.MODE_PRIVATE);
		if (null != mModel) {
			String userinfo = sharedPreferences.getString("userinfo", null);
			if (null != userinfo && !"".equals(userinfo)) {
				Type listType = new TypeToken<BaseEntity<UserInfoEntity>>() {
				}.getType();
				try {
					mUserInfo = (UserInfoEntity) mModel.analysisJSON(userinfo,
							listType);
				} catch (FailureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return mUserInfo;
	}

	public void saveVersionEntity(VersionEntity mVersionEntity) {
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				VERSION, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putInt("vercode", mVersionEntity.getVercode());
		edit.putString("msg", mVersionEntity.getMsg());
		edit.commit();
	}

	public VersionEntity getVersionEntity() {
		VersionEntity mVersionEntity = new VersionEntity();
		SharedPreferences sharedPreferences = mContext.getSharedPreferences(
				VERSION, Context.MODE_PRIVATE);
		int vercode = sharedPreferences.getInt("vercode", -1);
		String msg = sharedPreferences.getString("vercode", null);
		if (vercode != -1 && null != msg) {
			mVersionEntity.setMsg(msg);
			mVersionEntity.setVercode(vercode);
			return mVersionEntity;
		} else
			return null;
	}
}
