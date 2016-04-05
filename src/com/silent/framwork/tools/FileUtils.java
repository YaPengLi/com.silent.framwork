package com.silent.framwork.tools;

/*
 * File Name: FileUtils.java 
 * History:
 * Created by Siyang.Miao on 2011-12-2
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.text.TextUtils;

public class FileUtils {
	public static String bitmaptoString(Bitmap bitmap) {
		// 将Bitmap转换成字符串
		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes);
		return string;
	}

	public static void saveBitmap(Bitmap mBitmap,String path) throws IOException {
		File f = new File(path);
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 判断文件是否存在，有返回TRUE，否则FALSE
	 * 
	 * @return
	 */
	public static boolean exists(String fullName) {
		try {
			if (TextUtils.isEmpty(fullName)) {
				return false;
			}
			return new File(fullName).exists();
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		}

	}

	public static boolean isReadable(String path) {
		try {
			if (TextUtils.isEmpty(path)) {
				return false;
			}
			File f = new File(path);
			return f.exists() && f.canRead();
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		}

	}

	public static boolean isWriteable(String path) {
		try {
			if (TextUtils.isEmpty(path)) {
				return false;
			}
			File f = new File(path);
			return f.exists() && f.canWrite();
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		}
	}

	public static boolean createDir(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdir();
		}
		return true;
	}

	public static boolean createDirs(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdirs();
		}
		return true;
	}

	public static void createFile(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static long getSize(String path) {
		if (null == path) {
			return -1l;
		}
		File f = new File(path);
		if (f.isDirectory()) {
			return -1l;
		} else {
			return f.length();
		}
	}

	public static boolean writeFile(InputStream is, String path,
			boolean recreate) {
		boolean res = false;
		File f = new File(path);
		FileOutputStream fos = null;
		try {
			if (recreate && f.exists()) {
				f.delete();
			}

			if (!f.exists() && null != is) {
				int count = -1;
				byte[] buffer = new byte[1024];
				fos = new FileOutputStream(f);
				while ((count = is.read(buffer)) != -1) {
					fos.write(buffer, 0, count);
				}
				res = true;
			}
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		} catch (Throwable e) {
			LogUtils.e(e);
		} finally {
			try {
				if (null != fos) {
					fos.close();
					fos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}

		return res;
	}

	public static boolean writeFile(String content, String path, boolean append) {
		boolean res = false;
		File f = new File(path);
		RandomAccessFile raf = null;
		try {
			if (f.exists()) {
				if (!append) {
					f.delete();
					f.createNewFile();
				}
			} else {
				f.createNewFile();
			}

			if (f.canWrite()) {
				raf = new RandomAccessFile(f, "rw");
				raf.seek(raf.length());
				raf.write(content.getBytes());
				res = true;
			}
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			try {
				if (null != raf) {
					raf.close();
					raf = null;
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}

		return res;
	}

	public static boolean writeFile(int content, String path, boolean append) {
		boolean res = false;
		File f = new File(path);
		RandomAccessFile raf = null;
		try {
			if (f.exists()) {
				if (!append) {
					f.delete();
					f.createNewFile();
				}
			} else {
				f.createNewFile();
			}

			if (f.canWrite()) {
				raf = new RandomAccessFile(f, "rw");
				raf.seek(raf.length());
				raf.writeInt(content);
				res = true;
			}
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			try {
				if (null != raf) {
					raf.close();
					raf = null;
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}

		return res;
	}

	public static void writeProperties(String filePath, String key,
			String value, String comment) {
		writeProperties(new File(filePath), key, value, comment);
	}

	public static void writeProperties(File f, String key, String value,
			String comment) {
		if (key == null || value == null) {
			return;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			if (!f.exists() || !f.isFile()) {
				f.createNewFile();
			}
			fis = new FileInputStream(f);
			Properties p = new Properties();
			p.load(fis);
			p.setProperty(key, value);
			fos = new FileOutputStream(f);
			p.store(fos, comment);
		} catch (IllegalArgumentException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} finally {
			try {
				if (null != fis) {
					fis.close();
					fis = null;
				}
				if (null != fos) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}
	}

	public static Integer readInt(String path) {
		Integer res = null;
		File f = new File(path);
		RandomAccessFile raf = null;
		try {
			if (!f.exists()) {
				return null;
			}

			if (f.canWrite()) {
				raf = new RandomAccessFile(f, "r");
				res = raf.readInt();
			}
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (IOException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			try {
				if (null != raf) {
					raf.close();
					raf = null;
				}
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}
		return res;
	}

	public static boolean copyFile(String srcPath, String destPath) {
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);
		return copyFile(srcFile, destFile);
	}

	/**
	 * 文件拷贝
	 */
	public static boolean copyFile(File srcFile, File destFile) {
		if (!srcFile.exists() || !srcFile.isFile()) {
			return false;
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = in.read(buffer)) > 0) {
				out.write(buffer, 0, i);
			}
			out.flush();
			srcFile.delete();
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LogUtils.e(e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LogUtils.e(e);
				}
			}

		}

		return true;
	}

	public static boolean deleteFile(String path) {
		return new File(path).delete();
	}

	public static boolean deleteDirs(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			return true;
		}
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File f : files) {
					f.delete();
				}
			}
			return dir.delete();
		}
		return false;
	}

	public static void chmod(String path, String mode) {
		try {
			String command = "chmod " + mode + " " + path;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		} catch (IOException e) {
			LogUtils.e(e);
		}
	}

	public static boolean zip(String srcPath, String destPath, String zipEntry) {
		return zip(new File(srcPath), new File(destPath), zipEntry);
	}

	public static boolean zip(File srcFile, File destFile, String zipEntry) {
		boolean res = false;
		if (null == srcFile || !srcFile.exists() || !srcFile.canRead()) {
			return false;
		}
		if (destFile.exists()) {
			destFile.delete();
		}
		ZipOutputStream zos = null;
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(destFile, false);
			zos = new ZipOutputStream(fos);

			ZipEntry entry = new ZipEntry(zipEntry);
			zos.putNextEntry(entry);
			fis = new FileInputStream(srcFile);
			byte[] buffer = new byte[32];
			int cnt = 0;
			while ((cnt = fis.read(buffer)) != -1) {
				zos.write(buffer, 0, cnt);
			}
			zos.flush();
			res = true;
		} catch (FileNotFoundException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (zos != null) {
					zos.closeEntry();
					zos.close();
				}
			} catch (IOException e) {
				// ignored
			}

		}
		return res;
	}

	/**
	 * 从文件中读取String字符
	 */
	public static String readString(File file) {
		if (!file.exists()) {
			return "";
		}
		FileReader reader = null;
		char[] buf = new char[1024];
		StringBuilder builder = new StringBuilder();
		try {
			reader = new FileReader(file);
			while (reader.read(buf) > 0) {
				builder.append(String.valueOf(buf));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			if (reader != null)
				reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	// ==========================================================================
	// Inner/Nested Classes
	// ==========================================================================
}
