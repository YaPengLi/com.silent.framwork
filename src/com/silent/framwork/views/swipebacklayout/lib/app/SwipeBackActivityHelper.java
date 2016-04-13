package com.silent.framwork.views.swipebacklayout.lib.app;

import com.silent.framwork.R;
import com.silent.framwork.views.swipebacklayout.lib.SwipeBackLayout;
import com.silent.framwork.views.swipebacklayout.lib.Utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;


/**
 * @author Yrom
 */
public class SwipeBackActivityHelper {
	private Activity mActivity;

	private SwipeBackLayout mSwipeBackLayout;

	public SwipeBackActivityHelper(Activity activity) {
		mActivity = activity;
	}

	@SuppressWarnings("deprecation")
	public void onActivityCreate() {
		mActivity.getWindow().setBackgroundDrawable(
				new ColorDrawable(R.color.app_color));
		mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
		mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(mActivity)
				.inflate(R.layout.swipeback_layout, null);
		mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
			@Override
			public void onScrollStateChange(int state, float scrollPercent) {
			}

			@Override
			public void onEdgeTouch(int edgeFlag) {
				Utils.convertActivityToTranslucent(mActivity);
			}

			@Override
			public void onScrollOverThreshold() {

			}
		});
	}

	public void onPostCreate() {
		mSwipeBackLayout.attachToActivity(mActivity);
	}

	public View findViewById(int id) {
		if (mSwipeBackLayout != null) {
			return mSwipeBackLayout.findViewById(id);
		}
		return null;
	}

	public SwipeBackLayout getSwipeBackLayout() {
		return mSwipeBackLayout;
	}
}
