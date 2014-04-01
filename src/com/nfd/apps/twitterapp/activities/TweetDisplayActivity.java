package com.nfd.apps.twitterapp.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.models.Tweet;

public class TweetDisplayActivity extends Activity {

	Tweet aTweet;
	TextView tvTweetHtml;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_display);
		tvTweetHtml = (TextView) findViewById(R.id.tvTweetHtml);
		Intent i = getIntent();
		aTweet = (Tweet) i.getSerializableExtra(TimelineActivity.TWEET_EXTRA);
		Toast.makeText(this, aTweet.getBody(), Toast.LENGTH_LONG).show();
		
		fetchTweetStatus();
	}

	private void fetchTweetStatus() {
		//https%3A%2F%2Ftwitter.com%2F%23!%2Ftwitter%2Fstatus%2F99530515043983360
		long id = aTweet.getId();
		String strId = String.valueOf(id);
		RequestParams params = new RequestParams();
		params.put("id", strId);
	//	params.put("url", "https%3A%2F%2Ftwitter.com%2F%23!%2Ftwitter%2Fstatus%2F" + strId);
		TwitterApp.getRestClient().getStatus(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject jsonStatus) {
				// TODO Auto-generated method stub
				Log.d("TEST - get tweet status", jsonStatus.toString());
				try {
//				tvTweetHtml.setText(Html.fromHtml(jsonStatus.getString("html")));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0, JSONObject arg1) {
				// TODO Auto-generated method stub
				arg0.printStackTrace();
				Log.d("TEST - get tweet status", arg1.toString());
			}
			
		}, params);
		

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tweet_display, menu);
		return true;
	}

}
