package com.nfd.apps.twitterapp.activities;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.adapters.TweetsAdapter;
import com.nfd.apps.twitterapp.models.Tweet;

public class TimelineActivity extends Activity {

	private ListView lvTweets;
	private TweetsAdapter tweetsAdapter;
	private ArrayList<Tweet> tweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		


		TwitterApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						Log.d("TEST", jsonTweets.toString());
						tweets = Tweet.fromJson(jsonTweets);
						lvTweets = (ListView) findViewById(R.id.lvTweets);
						tweetsAdapter = new TweetsAdapter(getBaseContext(), tweets);
						lvTweets.setAdapter(tweetsAdapter);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
