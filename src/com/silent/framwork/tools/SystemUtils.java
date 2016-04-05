package com.silent.framwork.tools;

/*
 * File Name: SystemUtils.java 
 * History:
 * Created by Siyang.Miao on 2012-2-20
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class SystemUtils {

	private static final String KERNEL_PATH = "/proc/version";
	private static final String CPU_MAX_FREQ_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";

	private static StatFs sStatFs = null;

	public static void onKeyDown(Context mContext, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			((Activity) mContext).moveTaskToBack(false);
		}
	}

	/**
	 * 获取系统配置参数
	 * 
	 * @param key
	 *            参数key
	 * @return 参数值
	 */
	public static final String getSystemProperty(String key) {
		String pValue = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method m = c.getMethod("get", String.class);
			pValue = m.invoke(null, key).toString();
		} catch (ClassNotFoundException e) {
			LogUtils.e(e);
		} catch (SecurityException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return pValue;
	}

	public static String getIMEI(Context context) {
		if (null == context) {
			return null;
		}
		String imei = null;
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return imei;
	}

	/***
	 * 得到系统的时间,格式是YYYYMMDDhhmmss
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDataTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date(System.currentTimeMillis()));
	}

	private static String fDataNumer(int num) {
		String n = "" + num;
		if (n.length() < 2) {
			n = "0" + n;
		}
		return n;
	}

	/***
	 * 得到手机的型号,如lenovo k900
	 * 
	 * @return
	 */
	public static String getDeviceName() {
		return android.os.Build.MODEL;
	}

	/***
	 * 得到手机的版本数字号
	 * 
	 * @return 7|8|9|10|12|....
	 */
	public static String getSDKVersionCode() {
		return android.os.Build.VERSION.SDK;
	}

	/***
	 * 得到系统的版本号,如4.2.2
	 * 
	 * @return
	 */
	public static String getSystemCode() {
		return android.os.Build.VERSION.RELEASE;
	}


	/**
	 * 让项目中的文字不受手机设置中的字体大小的影响
	 * */
	public static void updateTextSize(Context mContext) {
		Configuration config = mContext.getResources().getConfiguration();
		DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		config.setToDefaults();
		mContext.getResources().updateConfiguration(config, metrics);
	}


	/***
	 * 隐蔽软键盘
	 * 
	 * @param context
	 * @param mView
	 */
	public static void hiddenInputMethod(Context context, View mView) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
	}

	/***
	 * 获取IP地址
	 * 
	 * @param mContext
	 * @return
	 */
	public static String getIpAddress(Context mContext) {
		WifiManager wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		/*
		 * if (!wifiManager.isWifiEnabled()) { wifiManager.setWifiEnabled(true);
		 * }
		 */
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = intToIp(ipAddress);
		return ip;
	}

	private static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	/**
	 * 取得当前sim卡的imsi
	 * 
	 * @param context
	 * @return Return null if it is unavailable.
	 */
	public static String getIMSI(Context context) {
		if (null == context) {
			return null;
		}
		String imsi = null;
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = tm.getSubscriberId();
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return imsi;
	}

	public static String getSimSN(Context context) {
		if (null == context) {
			return null;
		}
		String simSN = null;
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			simSN = tm.getSimSerialNumber();
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return simSN;
	}

	/**
	 * 获取Wifi’Mac地址，可以使用getWifiMacAddress和getEth0MacAddress来替代
	 * 
	 * @param context
	 * @return wifi'Mac
	 */
	@Deprecated
	public static String getMacAddress(Context context) {
		if (null == context) {
			return null;
		}
		String mac = null;
		try {
			WifiManager wm = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wm.getConnectionInfo();
			mac = info.getMacAddress();
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return mac;
	}

	/**
	 * 获取wifi'Mac
	 * 
	 * @param context
	 * @return wifi'Mac
	 */
	public static String getWifiMacAddress(Context context) {
		if (null == context) {
			return null;
		}
		String mac = null;
		try {
			WifiManager wm = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wm.getConnectionInfo();
			mac = info.getMacAddress();
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return mac;
	}

	/**
	 * 获取以太网卡mac
	 * 
	 * @return eth0'Mac 以太网卡Mac
	 */
	public static String getEthMacAddress() {
		String ethMac = null;
		File f = new File("/sys/class/net/eth0/address");
		if (f.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				ethMac = br.readLine();
				ethMac.trim();
				br.close();
			} catch (Exception e) {
				LogUtils.e(e);
			}
		}
		return ethMac;
	}

	/**
	 * 根据packageName获取packageInfo
	 */
	public static PackageInfo getPackageInfo(Context context, String packageName) {
		if (null == context) {
			return null;
		}
		PackageInfo info = null;
		PackageManager manager = context.getPackageManager();
		// 根据packageName获取packageInfo
		try {
			info = manager.getPackageInfo(packageName,
					PackageManager.GET_UNINSTALLED_PACKAGES);
		} catch (NameNotFoundException e) {
			LogUtils.e(e);
		}
		return info;
	}

	/**
	 * 判断是否是第三方软件
	 * 
	 * @param context
	 *            Context
	 * @param packageName
	 *            软件的包名
	 * @return 是第三方软件返回true，否则返回false
	 */
	public static boolean isThirdPartyApp(Context context, String packageName) {
		if (null == context) {
			return false;
		}
		PackageManager pm = context.getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = pm.getPackageInfo(packageName, 0);
			return isThirdPartyApp(packageInfo);
		} catch (NameNotFoundException e) {
			LogUtils.e(e);
			return false;
		}
	}

	/**
	 * 判断是否是第三方软件
	 * 
	 * @param packageInfo
	 *            软件的packageInfo
	 * @return 是第三方软件返回true，否则返回false
	 */
	public static boolean isThirdPartyApp(PackageInfo packageInfo) {
		if (null == packageInfo || null == packageInfo.applicationInfo) {
			return false;
		}

		return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
				|| ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
	}

	/**
	 * 读取指定路径下APK文件签名
	 */
	@SuppressWarnings("unchecked")
	public static String getJarSignature(String filePath) throws Exception {
		if (null == filePath) {
			return null;
		}
		String resultSign = "";
		String resultKey = "";
		List<ZipEntry> names = new ArrayList<ZipEntry>();
		ZipFile zf = new ZipFile(filePath);
		Enumeration<ZipEntry> zi = (Enumeration<ZipEntry>) zf.entries();
		while (zi.hasMoreElements()) {
			ZipEntry ze = zi.nextElement();
			String name = ze.getName();
			if (name.startsWith("META-INF/")
					&& (name.endsWith(".RSA") || name.endsWith(".DSA"))) {
				names.add(ze);
			}
		}
		Collections.sort(names, new Comparator<ZipEntry>() {
			@Override
			public int compare(ZipEntry obj1, ZipEntry obj2) {
				if (obj1 != null && obj2 != null) {
					return obj1.getName().toUpperCase()
							.compareTo(obj2.getName().toUpperCase());
				}
				return 0;
			}
		});
		for (ZipEntry ze : names) {
			// System.out.println("RSA file name " + ze.getName());
			InputStream is = zf.getInputStream(ze);
			try {
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				CertPath cp = cf.generateCertPath(is, "PKCS7");
				List<?> list = cp.getCertificates();
				for (Object obj : list) {
					if (!(obj instanceof X509Certificate))
						continue;
					X509Certificate cert = (X509Certificate) obj;
					StringBuffer sb = new StringBuffer();
					sb.setLength(0);
					byte[] key = getPKBytes(cert.getPublicKey());
					for (int i = 0; i < key.length; i++)
						sb.append(String.format("%02X", key[i]));
					resultKey += sb.toString();
					sb.setLength(0);
					byte[] signature = cert.getSignature();

					for (int i = 0; i < signature.length; i++)
						sb.append(String.format("%02X", signature[i]));
					resultSign += sb.toString();
				}
			} catch (CertificateException e) {
				LogUtils.e(e);
			}
			is.close();
		}
		if (!TextUtils.isEmpty(resultKey) && !TextUtils.isEmpty(resultSign)) {
			return hashCode(resultKey) + "," + hashCode(resultSign);
		}
		return null;
	}

	private static byte[] getPKBytes(PublicKey pk) {
		if (pk instanceof RSAPublicKey) {
			RSAPublicKey k = (RSAPublicKey) pk;
			return k.getModulus().toByteArray();
		} else if (pk instanceof DSAPublicKey) {
			DSAPublicKey k = (DSAPublicKey) pk;
			return k.getY().toByteArray();
		}
		return null;
	}

	/**
	 * 字符串对应hash code
	 * 
	 * @param str
	 * @return
	 */
	public static int hashCode(String str) {
		int hash = 0;
		if (str != null) {
			int multiplier = 1;
			int _offset = 0;
			int _count = str.length();
			char[] _value = new char[_count];
			str.getChars(_offset, _count, _value, 0);
			for (int i = _offset + _count - 1; i >= _offset; i--) {
				hash += _value[i] * multiplier;
				int shifted = multiplier << 5;
				multiplier = shifted - multiplier;
			}
		}
		return hash;
	}

	/**
	 * 通过包名读取已安装APP数字签名
	 */
	public static String getInstalledPackageSignature(Context context,
			String packageName) {
		if (null == context) {
			return null;
		}
		String signature = null;
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo appInfo = pm.getApplicationInfo(packageName,
					PackageManager.GET_SIGNATURES);
			String apkPath = appInfo.sourceDir;
			signature = getJarSignature(apkPath);
		} catch (NameNotFoundException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return signature;
	}

	public static boolean isSDCardAvailable() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	public static long getExternalSpace() {
		long availableSpace = -1l;
		try {
			String externalStoragePath = Environment
					.getExternalStorageDirectory().getPath();
			if (sStatFs == null)
				sStatFs = new StatFs(externalStoragePath);
			else
				sStatFs.restat(externalStoragePath);
			availableSpace = sStatFs.getAvailableBlocks()
					* (long) sStatFs.getBlockSize();
		} catch (Exception e) {
			LogUtils.e(e);
		}

		return availableSpace;
	}

	public static int[] getResolution(Context context) {
		WindowManager windowMgr = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int[] res = new int[2];
		res[0] = windowMgr.getDefaultDisplay().getWidth();
		res[1] = windowMgr.getDefaultDisplay().getHeight();
		return res;
	}

	// ==========================================================================
	// Inner/Nested Classes
	// ==========================================================================

	/**
	 * 获取格式化日期和时间
	 * 
	 * @param formatStr
	 *            格式化字符串，例如"yyyy-MM-dd HH:mm:ss"
	 * @return 格式化的日期时间
	 */
	public static String getFormattedDateTime(String formatStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			Date date = new Date();
			return format.format(date.getTime());
		} catch (Exception e) {
			LogUtils.e(e);
		}
		return "";
	}

	public static Resources getAPKResources(Context context, String apkPath)
			throws Exception {
		String PATH_AssetManager = "android.content.res.AssetManager";
		Class assetMagCls = Class.forName(PATH_AssetManager);
		Constructor assetMagCt = assetMagCls.getConstructor((Class[]) null);
		Object assetMag = assetMagCt.newInstance((Object[]) null);
		Class[] typeArgs = new Class[1];
		typeArgs[0] = String.class;
		Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(
				"addAssetPath", typeArgs);
		Object[] valueArgs = new Object[1];
		valueArgs[0] = apkPath;
		assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
		Resources res = context.getResources();
		typeArgs = new Class[3];
		typeArgs[0] = assetMag.getClass();
		typeArgs[1] = res.getDisplayMetrics().getClass();
		typeArgs[2] = res.getConfiguration().getClass();
		Constructor resCt = Resources.class.getConstructor(typeArgs);
		valueArgs = new Object[3];
		valueArgs[0] = assetMag;
		valueArgs[1] = res.getDisplayMetrics();
		valueArgs[2] = res.getConfiguration();
		res = (Resources) resCt.newInstance(valueArgs);
		return res;
	}

	/**
	 * Gets the number of cores available in this device, across all processors.
	 * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
	 * 
	 * @return The number of cores, or 1 if failed to get result
	 */
	public static int getNumCores() {
		// Private Class to display only CPU devices in the directory listing
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				// Check if filename is "cpu", followed by a single digit number
				if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			// Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");
			// Filter to only list the devices we care about
			File[] files = dir.listFiles(new CpuFilter());
			// Return the number of cores (virtual CPU devices)
			return files.length;
		} catch (Exception e) {
			// Default to return 1 core
			return 1;
		}
	}

	/*
	 * 获取CPU频率
	 */
	public static String getCpuFreq() {
		return readKernelInfo(CPU_MAX_FREQ_PATH);
	}

	/**
	 * 获取内核信息
	 */
	private static String readKernelInfo(String path) {
		FileReader fr = null;
		BufferedReader br = null;
		String kernelInfo = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr, 1024);
			kernelInfo = br.readLine();
			if (path.equals(KERNEL_PATH)) {
				String[] temp = kernelInfo.split("\\s+");
				if (temp.length >= 3) {
					kernelInfo = temp[2];
				}
			}
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}
		return kernelInfo;
	}

	/**
	 * 获取内存信息
	 */
	public static long getRAM() {
		long total_memory = 0;
		String temp = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader("/proc/meminfo");
			br = new BufferedReader(fr, 1024);
			temp = br.readLine();

			String[] str = temp.split("\\s+");
			total_memory = Math.round(Integer.valueOf(str[1]) / 1024f);
		} catch (Throwable e) {
			LogUtils.e(e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}

		return total_memory;
	}

}
