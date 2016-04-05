package com.silent.framwork.activitys;


import com.silent.framwork.R;
import com.silent.framwork.tools.ScreenTools;
import com.silent.framwork.tools.SystemUtils;
import com.silent.framwork.views.lib.SwipeBackLayout;
import com.silent.framwork.views.lib.app.SwipeBackActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

@SuppressLint("ResourceAsColor")
public abstract class UIBaseActivity extends SwipeBackActivity {
	public LinearLayout mBaseLayout;
	public LayoutInflater mLayoutInflater;
	public LinearLayout.LayoutParams mParams;
	private SwipeBackLayout mSwipeBackLayout;
	public View status_bar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uibase);
		status_bar = findViewById(R.id.status_bar);
		int statusBarHeight = ScreenTools.getStatusBarHeight(this);
		Log.e("", statusBarHeight + "");
		status_bar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				statusBarHeight));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			status_bar.setVisibility(View.VISIBLE);
		} else {
			status_bar.setVisibility(View.GONE);
		}
		initialization();
		initView();
		initData();
		SystemUtils.updateTextSize(this);
	}

	/**
	 * 是否可用右滑返回
	 * */
	public void setCanTranslation(boolean isCanTranslation) {
		setSwipeBackEnable(isCanTranslation);
	}

	private void initialization() {
		mSwipeBackLayout = getSwipeBackLayout();
		// 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
		mBaseLayout = (LinearLayout) findViewById(R.id.mBaseLayout);
		mLayoutInflater = LayoutInflater.from(this);
		mParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	public void setContentViewOfBase(int layoutResID) {
		View inflate = mLayoutInflater.inflate(layoutResID, null);
		mBaseLayout.addView(inflate, mParams);
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.base_slide_right_in,
				R.anim.base_slide_remain);
	}

	// Press the back button in mobile phone
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

	public abstract void initView();

	public abstract void initData();
}
