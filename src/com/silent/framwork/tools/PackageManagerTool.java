package com.silent.framwork.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.os.Looper;
import android.os.RemoteException;
import android.text.format.Formatter;

public class PackageManagerTool {
	public static String getPkgVersion(Context context) throws NameNotFoundException {
		// TODO Auto-generated method stub
		PackageManager manager = context.getPackageManager();
		PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
		String version = info.versionName;
		return version;
	}
	public static int getPkgVersionCode(Context context) throws NameNotFoundException {
		// TODO Auto-generated method stub
		PackageManager manager = context.getPackageManager();
		PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
		return info.versionCode;
	}

	/**
	 * 获取Android Native App的缓存大小、数据大小、应用程序大小
	 * 
	 * @param context
	 *            Context对象
	 * @param pkgName
	 *            需要检测的Native App包名
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static void getPkgSize(final Context context,
			final GetStatsCompleted onGetStatsCompleted)
			throws NoSuchMethodException, InvocationTargetException,
			IllegalAccessException {
		// getPackageSizeInfo是PackageManager中的一个private方法，所以需要通过反射的机制来调用
		Method method = PackageManager.class.getMethod("getPackageSizeInfo",
				new Class[] { String.class, IPackageStatsObserver.class });
		// 调用 getPackageSizeInfo 方法，需要两个参数：1、需要检测的应用包名；2、回调
		method.invoke(context.getPackageManager(),
				new Object[] { context.getPackageName(),
						new IPackageStatsObserver.Stub() {
							@Override
							public void onGetStatsCompleted(
									PackageStats pStats, boolean succeeded)
									throws RemoteException {
								// 子线程中默认无法处理消息循环，自然也就不能显示Toast，所以需要手动Looper一下
								Looper.prepare();
								// 从pStats中提取各个所需数据
								onGetStatsCompleted.onGetStatsCompleted(
										Formatter.formatFileSize(context,
												pStats.cacheSize), Formatter
												.formatFileSize(context,
														pStats.dataSize),
										Formatter.formatFileSize(context,
												pStats.codeSize));
								// 遍历一次消息队列，弹出Toast
								Looper.loop();
							}
						} });
	}

	public interface GetStatsCompleted {
		void onGetStatsCompleted(String cacheSize, String dataSize,
				String codeSize);
	}
}
