package com.nfd.apps.twitterapp.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.nfd.apps.twitterapp.ProfileActivity;
import com.nfd.apps.twitterapp.R;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
//		TweetsListFragment fragmentTweets = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);



//		fetchTimelineAsync(new RequestParams());
//		setListViewListeners();
		
	}
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
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
		

}
