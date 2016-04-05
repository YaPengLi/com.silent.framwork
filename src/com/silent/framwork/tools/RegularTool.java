package com.silent.framwork.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

public class RegularTool {
	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {

		// 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		// 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		// 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9

		String telRegex = "^1[3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个
		// ，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		Pattern pattern = Pattern.compile(telRegex);
		if (TextUtils.isEmpty(mobiles))
			return false;
		else {
			Matcher matcher = pattern.matcher(mobiles);
			return matcher.matches();
		}
	}
	/** 验证是否是数字*/
	public static boolean isNumeric(String str) {
		final String number = "0123456789";
		for (int i = 0; i < str.length(); i++) {
			if (number.indexOf(str.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}
	public static boolean isLength4Or6(String code){
		String telRegex = "^(\\d{6}|\\d{4})$";
		Pattern pattern = Pattern.compile(telRegex);
		if (TextUtils.isEmpty(code))
			return false;
		else {
			Matcher matcher = pattern.matcher(code);
			return matcher.matches();
		}
	}
	public static boolean isLength6(String code){
		String telRegex = "^(\\d{6})$";
		Pattern pattern = Pattern.compile(telRegex);
		if (TextUtils.isEmpty(code))
			return false;
		else {
			Matcher matcher = pattern.matcher(code);
			return matcher.matches();
		}
	}
	
}
