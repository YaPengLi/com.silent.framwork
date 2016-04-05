package com.silent.framwork.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.silent.framwork.config.Constant;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.Handler;
import android.os.Build.VERSION;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.widget.ImageView;

public class ImageHelper {
	
//	private DisplayImageOptions options; // 显示图片的设置
//	protected ImageLoader imageLoader;
//	ImageLoaderConfiguration configuration;
	
	private static ImageHelper imageHelper;

	private ImageHelper() {

	}


	public static synchronized ImageHelper getInstance() {
		if (imageHelper == null) {
			imageHelper = new ImageHelper();
		}
		return imageHelper;
	}
	
	
	
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
	
	/**
	 * 把Bitmap存入文件
	 * 
	 * @param bitmap
	 * @param name
	 */
	public static void savaBitmap(Bitmap bitmap, String name) {
		
		final String filename=Environment.getExternalStorageDirectory()+Constant.IMG_PATH;
		  File file_record = new File(filename);
		  if (!file_record.exists()) {
				file_record.mkdirs();
		  }
		File f = new File(filename+getUrlSuffixStr(name));
		
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(bitmap==null || fOut==null ){
			return;
		}
		
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);// 把Bitmap对象解析成流
		
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/** 压缩成指定大小的正方形图片  */
	public static Bitmap cutButMap(Bitmap bitmap,int width){
		return Bitmap.createScaledBitmap(bitmap, width, width, true);
	}
	
	/***
	 * 裁切为目标大小的图片
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap,int width,int height) {
		
		float tarwid = width;
		float tarhei = height;
		
		float wid = bitmap.getWidth();
		float hei = bitmap.getHeight();
		
		float standardRate = tarwid / tarhei;
		
		float currentRate = wid / hei;

		Bitmap tBitmap = null;

		if (currentRate < standardRate) {// 高
			float th = tarwid / wid * hei;
			tBitmap = Bitmap.createScaledBitmap(bitmap, (int) tarwid, (int) th,
					true);
			tBitmap = Bitmap.createBitmap(tBitmap, 0, (int) (th - tarhei) / 2,
					(int) tarwid, (int) tarhei);

//			return tBitmap;

		} else if (currentRate > standardRate) {// 低

			float tw = tarhei / hei * wid;
			tBitmap = Bitmap.createScaledBitmap(bitmap, (int) tw, (int) tarhei,
					true);
			tBitmap = Bitmap.createBitmap(tBitmap, (int) (tw - tarwid) / 2, 0,
					(int) tarwid, (int) tarhei);

//			return tBitmap;
		} else {// 相同

			tBitmap = Bitmap.createScaledBitmap(bitmap, (int) tarwid,
					(int) tarhei, true);
//			return tBitmap;
		}
		
		/*if(bitmap!=null){
			bitmap.recycle();
		}*/
		
		return tBitmap;
		
		
	}
	
	
	
	
	/***
	 * 查看图片的旋转角度
	 * 
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	
	
	/**
	 * 旋转一个Bitmap
	 * @param b
	 * @param degrees range:0~360
	 * @return
	 */
	public static Bitmap rotate(Bitmap b, int degrees) {
		
		if(degrees==0){
			return b;
		}
		
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() /*/ 2*/,
					(float) b.getHeight() /*/ 2*/);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle(); // Android开发网再次提示Bitmap操作完应该显示的释放
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				// Android123建议大家如何出现了内存不足异常，最好return 原始的bitmap对象。.
			}
		}
		return b;
	}
	
	/**
	 * @param jpegPath 图片路径
	 * @param degree 旋转角度
	 */
	public static boolean roateBitmap(String jpegPath,int degree){
		File file=new File(jpegPath);
		if(!file.exists()|| degree==0){
			return false;
		}
		Bitmap bitmap=BitmapFactory.decodeFile(jpegPath);
		bitmap=rotate(bitmap, degree);
		savaBitmap(bitmap, jpegPath);
		System.gc();
		return true;
	}
	
	/**
	 * @param jpegPath 图片路径
	 * @param degree 旋转角度
	 * @param handler 用于告诉Activity图片已经处理完毕
	 * 
	 */
	public static boolean roateBitmapInThread(final String jpegPath,final int degree, final Handler handler){
		File file=new File(jpegPath);
		if(!file.exists()){
			return false;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Bitmap bitmap=BitmapFactory.decodeFile(jpegPath);
					bitmap=rotate(bitmap, degree);
					savaBitmap(bitmap, jpegPath);
					System.gc();
//					handler.sendEmptyMessage(BeautifyTheShopActivity.SIGN_AFTER_ROTATE_BITMAP);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
		return true;
	}
	
	
	/**
	 * 把Drawable 转化成Bitmap并且可以被编辑
	 * */
	public static final Bitmap copyDrawable2Bitmap(Drawable drawable){
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap().copy(Bitmap.Config.ARGB_4444, true);
	}
	
	
	
	/**
	 * 读取assert中的图片并且设置到ImageView中
	 * 
	 ***/
	public static void  setAssertImage(ImageView mIView ,String fileName,Context context){
	      Bitmap image = null;  
	      AssetManager am = context.getResources().getAssets();  
	      try  
	      {  
	          InputStream is = am.open(fileName);  
	          image = BitmapFactory.decodeStream(is);  
	          is.close();  
	      }  
	      catch (IOException e)  
	      {  
	          e.printStackTrace();  
	      }  
	     mIView.setImageBitmap(image);
	}
	
	
	/** 从Assert中读取图片 */
	public static Bitmap  getAssertImage(String fileName,Context context){
	      Bitmap image = null;  
	      AssetManager am = context.getResources().getAssets();  
	      try  
	      {  
	          InputStream is = am.open(fileName);  
	          image = BitmapFactory.decodeStream(is);  
	          is.close();  
	      }  
	      catch (IOException e)  
	      {  
	          e.printStackTrace();  
	      }  
	     return image;
	}
	
	public static Bitmap getPopImage(Bitmap bitmap,String text,int textsizeUpx,int paddingPx,Paint paint,Context context){
		int count=text.length();
		int width=count * textsizeUpx + paddingPx;
		if(width > bitmap.getWidth()){
			bitmap=Bitmap.createScaledBitmap(bitmap, width, bitmap.getHeight(), true);
		}
		
		Canvas canvas=new Canvas(bitmap);
		canvas.drawText(text, paddingPx, ScreenTools.dip2px(context, 15), paint);
		
		return bitmap;
	}
	
	
	/** 水平方向模糊度 */
	private static float hRadius = 5;
	/** 竖直方向模糊度 */
	private static float vRadius = 5;
	/** 模糊迭代度 */
	private static int iterations = 7;
	
	/*** 高斯模糊  */
	public static Drawable BoxBlurFilter(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < iterations; i++) {
			blur(inPixels, outPixels, width, height, hRadius);
			blur(outPixels, inPixels, height, width, vRadius);
		}
		blurFractional(inPixels, outPixels, width, height, hRadius);
		blurFractional(outPixels, inPixels, height, width, vRadius);
		bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}
	
	public static Bitmap getBlurBitmap(Context mContext,Bitmap bitmap,int yFrom){
		Bitmap bitmap2 =fastblur(mContext, Bitmap.createBitmap(bitmap, 0, yFrom, bitmap.getWidth(), bitmap.getHeight()-yFrom), 8);
		Canvas canvas=new Canvas(bitmap);
		canvas.drawBitmap(bitmap2, 01, yFrom, new Paint());
		return bitmap;
	}
	
	public static Bitmap BoxBlurFilterReturnBitmap(Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < iterations; i++) {
			blur(inPixels, outPixels, width, height, hRadius);
			blur(outPixels, inPixels, height, width, vRadius);
		}
		blurFractional(inPixels, outPixels, width, height, hRadius);
		blurFractional(outPixels, inPixels, height, width, vRadius);
		bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
	
	public static void blur(int[] in, int[] out, int width, int height,
			float radius) {
		int widthMinus1 = width - 1;
		int r = (int) radius;
		int tableSize = 2 * r + 1;
		int divide[] = new int[256 * tableSize];

		for (int i = 0; i < 256 * tableSize; i++)
			divide[i] = i / tableSize;

		int inIndex = 0;

		for (int y = 0; y < height; y++) {
			int outIndex = y;
			int ta = 0, tr = 0, tg = 0, tb = 0;

			for (int i = -r; i <= r; i++) {
				int rgb = in[inIndex + clamp(i, 0, width - 1)];
				ta += (rgb >> 24) & 0xff;
				tr += (rgb >> 16) & 0xff;
				tg += (rgb >> 8) & 0xff;
				tb += rgb & 0xff;
			}

			for (int x = 0; x < width; x++) {
				out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16)
						| (divide[tg] << 8) | divide[tb];

				int i1 = x + r + 1;
				if (i1 > widthMinus1)
					i1 = widthMinus1;
				int i2 = x - r;
				if (i2 < 0)
					i2 = 0;
				int rgb1 = in[inIndex + i1];
				int rgb2 = in[inIndex + i2];

				ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
				tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
				tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
				tb += (rgb1 & 0xff) - (rgb2 & 0xff);
				outIndex += height;
			}
			inIndex += width;
		}
	}

	public static void blurFractional(int[] in, int[] out, int width,
			int height, float radius) {
		radius -= (int) radius;
		float f = 1.0f / (1 + 2 * radius);
		int inIndex = 0;

		for (int y = 0; y < height; y++) {
			int outIndex = y;

			out[outIndex] = in[0];
			outIndex += height;
			for (int x = 1; x < width - 1; x++) {
				int i = inIndex + x;
				int rgb1 = in[i - 1];
				int rgb2 = in[i];
				int rgb3 = in[i + 1];

				int a1 = (rgb1 >> 24) & 0xff;
				int r1 = (rgb1 >> 16) & 0xff;
				int g1 = (rgb1 >> 8) & 0xff;
				int b1 = rgb1 & 0xff;
				int a2 = (rgb2 >> 24) & 0xff;
				int r2 = (rgb2 >> 16) & 0xff;
				int g2 = (rgb2 >> 8) & 0xff;
				int b2 = rgb2 & 0xff;
				int a3 = (rgb3 >> 24) & 0xff;
				int r3 = (rgb3 >> 16) & 0xff;
				int g3 = (rgb3 >> 8) & 0xff;
				int b3 = rgb3 & 0xff;
				a1 = a2 + (int) ((a1 + a3) * radius);
				r1 = r2 + (int) ((r1 + r3) * radius);
				g1 = g2 + (int) ((g1 + g3) * radius);
				b1 = b2 + (int) ((b1 + b3) * radius);
				a1 *= f;
				r1 *= f;
				g1 *= f;
				b1 *= f;
				out[outIndex] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
				outIndex += height;
			}
			out[outIndex] = in[width - 1];
			inIndex += width;
		}
	}

	public static int clamp(int x, int a, int b) {
		return (x < a) ? a : (x > b) ? b : x;
	}
	
	


	private static final String TAG = "Blur";

	@SuppressLint("NewApi")
	public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

		if (VERSION.SDK_INT > 16) {
			Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

			final RenderScript rs = RenderScript.create(context);
			final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
					Allocation.USAGE_SCRIPT);
			final Allocation output = Allocation.createTyped(rs, input.getType());
			final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
			script.setRadius(radius /* e.g. 3.f */);
			script.setInput(input);
			script.forEach(output);
			output.copyTo(bitmap);
			return bitmap;
		}

		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		Log.e("pix", w + " " + h + " " + pix.length);
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				// Preserve alpha channel: ( 0xff000000 & pix[yi] )
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}

		Log.e("pix", w + " " + h + " " + pix.length);
		bitmap.setPixels(pix, 0, w, 0, 0, w, h);
		return (bitmap);
	}


	
	
	/** 得到url中的最后一部分,我们这里用来处理图片名称 */
	public static String getUrlSuffixStr(String url){
		String strs[]=url.split("/");
		return strs[strs.length-1];
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
