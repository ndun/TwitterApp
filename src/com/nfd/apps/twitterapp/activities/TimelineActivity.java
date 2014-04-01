package com.nfd.apps.twitterapp.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.adapters.TweetsAdapter;
import com.nfd.apps.twitterapp.helpers.EndlessScrollListener;
import com.nfd.apps.twitterapp.models.Tweet;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {

	public static final int COMPOSE_TWEET_REQUEST = 123;
	
	private PullToRefreshListView lvTweets;
	private TweetsAdapter tweetsAdapter;
	private ArrayList<Tweet> tweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		tweets = new ArrayList<Tweet>();
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		tweetsAdapter = new TweetsAdapter(getBaseContext(), tweets);
		lvTweets.setAdapter(tweetsAdapter);

		/*
		RequestParams parms = new RequestParams();
		parms.put("count", "25");
		
		TwitterApp.getRestClient().getMoreTweets(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						Log.d("TEST", jsonTweets.toString());
						tweets = Tweet.fromJson(jsonTweets);
						tweetsAdapter.clear();
						tweetsAdapter.addAll(tweets);
					}
				}, parms);
		*/
		fetchTimelineAsync(0);
		setListViewListeners();
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
	
	private void setListViewListeners() {
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list contents
                // Make sure you call listView.onRefreshComplete()
                // once the loading is done. This can be done from here or any
                // place such as when the network request has completed successfully.
                fetchTimelineAsync(0);
                lvTweets.onRefreshComplete();
            }
        });
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {	
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				if(tweetsAdapter.getCount() == 0) {
					return;
				}
				Tweet aTweet = tweetsAdapter.getItem(tweetsAdapter.getCount()-1);
				Log.d("TEST - last tweet", "last Tweet: " + aTweet.getBody() + " - count: " + tweetsAdapter.getCount() + " id: " + aTweet.getId());
				String lastId = aTweet.getId();
				RequestParams parms = new RequestParams();
				parms.put("max_id", lastId);
				

				TwitterApp.getRestClient().getMoreTweets(new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						Log.d("TEST", jsonTweets.toString());
						
						List<Tweet> newTweets = Tweet.fromJson(jsonTweets);
						tweetsAdapter.addAll(newTweets);

					}
				}, parms);
			}
		});
	}
	
	public void fetchTimelineAsync(int page) {
		RequestParams parms = new RequestParams();
		parms.put("count", "25");
		
		TwitterApp.getRestClient().getMoreTweets(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						Log.d("TEST", jsonTweets.toString());
						tweets = Tweet.fromJson(jsonTweets);
						tweetsAdapter.clear();
						tweetsAdapter.addAll(tweets);
					}
				}, parms);
	}

}
