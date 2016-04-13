package com.silent.framwork.views.recycleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class AiYouRecyclerView extends RecyclerView {

	public AiYouRecyclerView(Context context) {
		super(context);
	}

	public AiYouRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AiYouRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressLint("NewApi") @Override
	public boolean canScrollVertically(int direction) {
		if (direction < 1) {
			boolean original = super.canScrollVertically(direction);
			return !original && getChildAt(0) != null
					&& getChildAt(0).getTop() < 0 || original;
		}
		return super.canScrollVertically(direction);
	}
}
