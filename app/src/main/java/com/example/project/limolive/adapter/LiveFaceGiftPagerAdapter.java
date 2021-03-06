package com.example.project.limolive.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 表情与礼物ViewPagerADapter共用
 * @author wsc
 * 
 * @email jacen@wscnydx.com
 * @date 2014-4-2 上午11:07:21
 */
public class LiveFaceGiftPagerAdapter extends PagerAdapter {

	private List<View> views;

	public LiveFaceGiftPagerAdapter(List<View> views) {

		this.views = views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// super.destroyItem(container, position, object);
		if (position < views.size()) {
			((ViewPager) container).removeView(views.get(position));
		}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(views.get(position));
		return views.get(position);
	}
}
