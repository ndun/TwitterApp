package com.nfd.apps.twitterapp.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.models.Tweet;

public class ComposeActivity extends Activity {

	public static final String TWEET_EXTRA = "tweet";
	
	private EditText etTweetBody;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		etTweetBody = (EditText) findViewById(R.id.etTweetBody);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	public void cancelTweet(View view) {
//		Intent i = new Intent(this, TimelineActivity.class);
//		startActivity(i);
		setResult(RESULT_OK);
		finish();
	}
	
	
	
	public void createTweet(View view) {
		RequestParams params = new RequestParams();
		params.put("status", etTweetBody.getText().toString());

		TwitterApp.getRestClient().postTweet(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject jsonObj) {
				Log.d("TEST - Posted Tweet", jsonObj.toString());
				Tweet newTweet = new Tweet(jsonObj);
//				newTweet = Tweet.fromJson(jsonObj);
				Intent data = new Intent();
				data.putExtra(TWEET_EXTRA, newTweet);
				setResult(RESULT_OK, data);
				finish();
			}
			
		}
		, params);
//		Intent i = new Intent(this, TimelineActivity.class);		
//		startActivity(i);
	}
	
}
