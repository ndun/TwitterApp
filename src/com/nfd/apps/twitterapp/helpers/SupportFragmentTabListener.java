package com.nfd.apps.twitterapp.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
 
public class SupportFragmentTabListener<T extends Fragment> implements TabListener {
	private Fragment mFragment;
	private final FragmentActivity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	private final int mfragmentContainerId;
	private ViewPager vpPager;
 
	public SupportFragmentTabListener(FragmentActivity activity, String tag, Class<T> clz) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mfragmentContainerId = android.R.id.content;
	}
 
	public SupportFragmentTabListener(int fragmentContainerId, FragmentActivity activity, String tag, Class<T> clz, ViewPager vp) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mfragmentContainerId = fragmentContainerId;
		vpPager = vp;
	}
 
	/* The following are each of the ActionBar.TabListener callbacks */
 
	public void onTabSelected(Tab tab, FragmentTransaction sft) {
		// Check if the fragment is already initialized
		if (mFragment == null) {
			// Associate tab item to the viewpager
			vpPager.setCurrentItem(tab.getPosition());
		} else {
			// If it exists, simply attach it in order to show it
			sft.attach(mFragment);
		}
	}
 
	public void onTabUnselected(Tab tab, FragmentTransaction sft) {
		if (mFragment != null) {
			// Detach the fragment, because another one is being attached
			sft.detach(mFragment);
		}
	}
 
	public void onTabReselected(Tab tab, FragmentTransaction sft) {
		// User selected the already selected tab. Usually do nothing.
	}
}