package com.silent.framwork.views.recycleview;

import com.silent.framwork.adapter.BasesAdapter;
import com.silent.framwork.interfaces.RefreshAndLoadingData;
import com.silent.framwork.model.Model;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

/**
 * 
 * recyclerviewHelper：
 * RecyclerView的配置类，配置了adapter、layoutmanager、item动画和分页加载，以及刷新
 * 
 * */
public class RecyclerViewHelper {
	private RecyclerView mRecyclerView;
	private LinearLayoutManager linearLayoutManager;
	private Model mModel;
	public BasesAdapter mAdapter;
	private int lastVisibleItem;
	private Context mContext;
	private RefreshAndLoadingData mData;

	public RecyclerViewHelper(Context mContext, RecyclerView mRecyclerView,
			BasesAdapter mAdapter, Model mModel, RefreshAndLoadingData mData) {
		super();
		this.mContext = mContext;
		this.mRecyclerView = mRecyclerView;
		this.mAdapter = mAdapter;
		this.mModel = mModel;
		this.mData = mData;
		inivalidata();
	}

	public void inivalidata() {
		mRecyclerView.setOnScrollListener(mOnScrollListener);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setAdapter(mAdapter);
		linearLayoutManager = new LinearLayoutManager(mContext);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,
				DividerItemDecoration.VERTICAL_LIST));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
	}

	public OnScrollListener mOnScrollListener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			super.onScrollStateChanged(recyclerView, newState);
			if (newState == RecyclerView.SCROLL_STATE_IDLE
					&& lastVisibleItem + 1 == mAdapter.getItemCount()) {
				if (newState == RecyclerView.SCROLL_STATE_IDLE
						&& lastVisibleItem + 1 == mAdapter.getItemCount()) {
					if (mModel.page < mModel.pagecount - 1) {
						if (mAdapter instanceof BasesAdapter)
							mData.addMoreData(recyclerView);
							((BasesAdapter) mAdapter).msgType = 0;
					} else {
						if (mAdapter instanceof BasesAdapter)
							((BasesAdapter) mAdapter).msgType = 1;
					}
				}
				mAdapter.notifyItemRangeChanged(mAdapter.getItemCount() - 1,
						mAdapter.getItemCount());
			}
		}

		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
		}
	};

	/**
	 * 刷新最后mModel.count+1条item
	 * */
	public void notifyItemRangeChanged() {
		int size = mAdapter.getItemCount();
		mAdapter.notifyItemRemoved(size - mModel.pagecount - 1);
		// mAdapter.notifyItemRangeChanged(size > 0 ? size - mModel.count - 1 :
		// 0,
		// mAdapter.getItemCount());
		int nowSize = size - mModel.pagecount;
		mAdapter.notifyItemRangeInserted(nowSize < 0 ? 0 : nowSize,
				mAdapter.getItemCount());
	}
}
