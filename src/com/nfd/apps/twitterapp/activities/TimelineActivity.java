package com.nfd.apps.twitterapp.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.adapters.TweetsAdapter;
import com.nfd.apps.twitterapp.fragments.HomeTimelineFragment;
import com.nfd.apps.twitterapp.fragments.MentionsFragment;
import com.nfd.apps.twitterapp.models.Tweet;

import eu.erikw.PullToRefreshListView;

public class TimelineActivity extends FragmentActivity implements TabListener{

	public static final int COMPOSE_TWEET_REQUEST = 123;
	public static final String TWEET_EXTRA = "tweet";
	
	private PullToRefreshListView lvTweets;
	private TweetsAdapter tweetsAdapter;
	private ArrayList<Tweet> tweets;
	private boolean shouldClearTweets = true;
//	FragmentPagerAdapter adapterViewPager;
//	ViewPager vpPager ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
//		vpPager = (ViewPager) findViewById(R.id.vpPager);
//		adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
//		vpPager.setAdapter(adapterViewPager);
		
		/*
		vpPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			// This method will be invoked when a new page becomes selected.
			@Override
			public void onPageSelected(int position) {
				Toast.makeText(TimelineActivity.this, 
		                    "Selected page position: " + position, Toast.LENGTH_SHORT).show();
			}
			
			// This method will be invoked when the current page is scrolled
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// Code goes here
			}
			
			// Called when the scroll state changes: 
			// SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
			@Override
			public void onPageScrollStateChanged(int state) {
				// Code goes here
			}
		});
		*/

/*		
		// Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // When the tab is selected, switch to the
	            // corresponding page in the ViewPager.
	            vpPager.setCurrentItem(tab.getPosition());
	        }

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}
	        
	    };
	    */
	}
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra(ProfileActivity.USER_EXTRA, TwitterApp.getUser());
		startActivity(i);
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = this.getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		Tab tabHome = actionBar.newTab().setText("Home").setTag("HomeTimeLineFragment").setTabListener(this);
		Tab mentions = actionBar.newTab().setText("Mentions").setTag("MentionsFragment").setTabListener(this);
		actionBar.addTab(tabHome);
		actionBar.addTab(mentions);
		actionBar.selectTab(tabHome);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onComposePress(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, COMPOSE_TWEET_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == COMPOSE_TWEET_REQUEST) {
			if(data != null) {
				Tweet newTweet = (Tweet) data.getSerializableExtra(ComposeActivity.TWEET_EXTRA);
				tweetsAdapter.insert(newTweet, 0);
			}
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if(tab.getTag().equals("HomeTimeLineFragment")) {
			fts.replace(R.id.frame_container, new HomeTimelineFragment());
		} else {
			fts.replace(R.id.frame_container, new MentionsFragment());
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
    public static class MyPagerAdapter extends FragmentPagerAdapter {
	private static int NUM_ITEMS = 2;
		
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        
        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
 
        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return HomeTimelineFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return MentionsFragment.newInstance();
            default:
            	return null;
            }
        }
        
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
        	return "Page " + position;
        }
        
    }

		

}
