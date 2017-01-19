package com.oraro.sunlon.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.oraro.sunlon.Interface.ViewPagerTouchCallback;

public class CycleViewPager extends ViewPager {

	private InnerPagerAdapter mAdapter;
	private  ViewPagerTouchCallback callback;
	private float y1;
	private float y2;
	private float x1;
	private float x2;

	public CycleViewPager(Context context) {
		super(context);
		setOnPageChangeListener(null);
	}

	public CycleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnPageChangeListener(null);
	}
	public void setCallback(ViewPagerTouchCallback callback) {
		this.callback = callback;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()){

			case MotionEvent.ACTION_DOWN:

				callback.postTouchState(true);
				Log.e("wy","y1 = " + ev.getY());
				y1 = ev.getY();
				x1=ev.getX();
				break;

			case MotionEvent.ACTION_UP:
				Log.e("wy","y2 = " + ev.getY());
				y2 = ev.getY();
				x2=ev.getX();
				float distance = x2 - x1;
				if (Math.abs(y2-y1) >150 && Math.abs(distance) < 80) {
					callback.postTouchDirection(1);
				}
				break;
		}

		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void setAdapter(PagerAdapter arg0) {
		mAdapter = new InnerPagerAdapter(arg0);
		super.setAdapter(mAdapter);
		setCurrentItem(1);
	}

	@Override
	public void addOnPageChangeListener(OnPageChangeListener listener) {
		super.addOnPageChangeListener(new InnerOnPageChangeListener(listener));
	}

	private class InnerOnPageChangeListener implements OnPageChangeListener {

		private OnPageChangeListener listener;
		private int position;

		public InnerOnPageChangeListener(OnPageChangeListener listener) {
			this.listener = listener;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			if (null != listener) {
				listener.onPageScrollStateChanged(arg0);
			}
			if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
				if (position == mAdapter.getCount() - 1) {
					setCurrentItem(1, false);
				} else if (position == 0) {
					setCurrentItem(mAdapter.getCount() - 2, false);
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			if (null != listener) {
				listener.onPageScrolled(arg0, arg1, arg2);
			}
		}

		@Override
		public void onPageSelected(int arg0) {
			position = arg0;
			if (null != listener) {
				listener.onPageSelected(arg0);
			}
		}
	}

	private class InnerPagerAdapter extends PagerAdapter {

		private PagerAdapter adapter;

		public InnerPagerAdapter(PagerAdapter adapter) {
			this.adapter = adapter;
			adapter.registerDataSetObserver(new DataSetObserver() {

				@Override
				public void onChanged() {
					notifyDataSetChanged();
				}

				@Override
				public void onInvalidated() {
					notifyDataSetChanged();
				}

			});
		}

		@Override
		public int getCount() {
			return adapter.getCount() + 2;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return adapter.isViewFromObject(arg0, arg1);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (position == 0) {
				position = adapter.getCount() - 1;
			} else if (position == adapter.getCount() + 1) {
				position = 0;
			} else {
				position -= 1;
			}
			return adapter.instantiateItem(container, position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			adapter.destroyItem(container, position, object);
		}
	}
}