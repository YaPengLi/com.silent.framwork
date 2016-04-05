package com.silent.framwork.tools;

import java.lang.reflect.Method;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenTools {
	/**
	 * dip转为 px
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 *  px 转为 dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 得到屏幕的宽度
	 * 
	 * @param mContext
	 * @return
	 */
	public static int getScreenWidth(Context mContext) {
		Display display = ((WindowManager) mContext
				.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getWidth();
	}

	//获取屏幕原始尺寸高度，包括虚拟功能键高度
	public static int getDpi(Context context){
	    int dpi = 0;
	    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    Display display = windowManager.getDefaultDisplay();
	    DisplayMetrics displayMetrics = new DisplayMetrics();
	    @SuppressWarnings("rawtypes")
	    Class c;
	    try {
	        c = Class.forName("android.view.Display");
	        @SuppressWarnings("unchecked")
	        Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
	        method.invoke(display, displayMetrics);
	        dpi=displayMetrics.heightPixels;
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	    return dpi;
	}

	/**
	 * 获取 虚拟按键的高度
	 * @param context
	 * @return
	 */
	public static  int getBottomStatusHeight(Context context){
	    int totalHeight = getDpi(context);
	    
	    int contentHeight = getScreenHeight(context);
	    
	    return totalHeight  - contentHeight;
	}
	/**
	 * 得到屏幕的高度
	 * 
	 * @param mContext
	 * @return
	 */
	public static int getScreenHeight(Context mContext) {
		Display display = ((WindowManager) mContext
				.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getHeight();
	}

	public static int getStatusBarHeight(Context mContext) {
		int result = 0;
		int resourceId = mContext.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = mContext.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
}
