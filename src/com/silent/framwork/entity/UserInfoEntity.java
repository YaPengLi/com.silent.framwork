package com.silent.framwork.entity;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.silent.framwork.sharedPreference.SharedPreferencesTools;

public class UserInfoEntity implements Serializable {
	private String userid;
	private String name;
	private String headimgurl;
	private String mobile;
	private String openid;
	private String unionid;
	private String gender;
	private String sex;
	private String age;
	private String price;
	private String province;
	private String city;
	private String country;
	private List<ChannelItem> channellist;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<ChannelItem> getChannellist() {
		return channellist;
	}

	public void setChannellist(List<ChannelItem> channellist) {
		this.channellist = channellist;
	}

	public static UserInfoEntity saveUserInfoEntity(HashMap<String, Object> res) {
		UserInfoEntity userInfoEntity = new UserInfoEntity();
		userInfoEntity.setUnionid((String) res.get("unionid"));
		userInfoEntity.setCountry((String) res.get("country"));
		userInfoEntity.setName((String) res.get("nickname"));
		userInfoEntity.setCity((String) res.get("city"));
		userInfoEntity.setProvince((String) res.get("province"));
		userInfoEntity.setHeadimgurl((String) res.get("headimgurl"));
		userInfoEntity.setGender((Integer) res.get("sex") == 1 ? "M" : "F");
		userInfoEntity.setOpenid((String) res.get("openid"));
		return userInfoEntity;
	}
	
	public static String getUserInfo(Context mContext) {
		SharedPreferencesTools invaliData = SharedPreferencesTools
				.invaliData(mContext);
		String userInfo = invaliData.getUserInfo();
		return userInfo;
	}
}
