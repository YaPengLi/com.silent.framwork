package com.silent.framwork.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.Toast;

public class ExitTool {
	private Context mContext;
	static float exitTime;

	public ExitTool(Context mContext) {
		super();
		this.mContext = mContext;
	}

	/** 双击返回 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) // System.currentTimeMillis()无论何时调用，肯定大于2000
			{
				Toast.makeText(mContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				((Activity) mContext).finish();
				System.exit(0);
			}
			return true;
		}
		return false;
	}

	/** 返回手机屏幕 */
	public void startActivityToHome() {
		Intent i = new Intent();
		i.setAction(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		mContext.startActivity(i);
	}
	
}
