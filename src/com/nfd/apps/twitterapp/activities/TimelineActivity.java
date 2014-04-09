package com.nfd.apps.twitterapp.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.fragments.HomeTimelineFragment;
import com.nfd.apps.twitterapp.fragments.MentionsFragment;
import com.nfd.apps.twitterapp.helpers.SupportFragmentTabListener;
import com.nfd.apps.twitterapp.models.Tweet;

public class TimelineActivity extends ActionBarActivity {

	public static final int COMPOSE_TWEET_REQUEST = 123;
	public static final String TWEET_EXTRA = "tweet";

	private Tweet newTweet;
	private FragmentPagerAdapter adapterViewPager;
	private ViewPager vpPager ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOnPageChangeListener(new OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            	Log.d("TEST", "onPageScrolled");
            }

            // Called when the scroll state changes: 
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            	Log.d("TEST", "state changed");
            }
        });
		setupTabs();
	}
	
	private void setupTabs() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
		    .newTab()
		    .setText("Home")
		    .setTag("HomeTimelineFragment")
		    .setTabListener(new SupportFragmentTabListener<HomeTimelineFragment>(R.id.frame_container, this,
                        "Home", HomeTimelineFragment.class, vpPager));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
		    .newTab()
		    .setText("Mentions")
		    .setTag("MentionsTimelineFragment")
		    .setTabListener(new SupportFragmentTabListener<MentionsFragment>(R.id.frame_container, this,
                        "Mentions", MentionsFragment.class, vpPager));
		actionBar.addTab(tab2);
	}

	public boolean onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra(ProfileActivity.USER_EXTRA, TwitterApp.getUser());
		startActivity(i);
		return true;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.timeline, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.miCompose:
	        	onComposePress(item);
	            return true;
	        case R.id.miProfile:
	            onProfileView(item);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	public boolean onComposePress(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, COMPOSE_TWEET_REQUEST);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == COMPOSE_TWEET_REQUEST) {
			if (data != null) {
				newTweet = (Tweet) data
						.getSerializableExtra(ComposeActivity.TWEET_EXTRA);
			}
		}

	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (newTweet != null) {
			FragmentManager manager = getSupportFragmentManager();
			FragmentTransaction fts = manager.beginTransaction();

			if (manager.findFragmentById(R.id.frame_container) instanceof HomeTimelineFragment) {
				HomeTimelineFragment fragment = (HomeTimelineFragment) manager.findFragmentById(R.id.frame_container);
				fragment.getAdapter().insert(newTweet, 0);				
			}

			fts.commit();
			newTweet = null;
		}
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
	}
}
