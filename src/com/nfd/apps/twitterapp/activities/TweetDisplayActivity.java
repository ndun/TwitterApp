package com.nfd.apps.twitterapp.activities;

import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.helpers.TweetHelper;
import com.nfd.apps.twitterapp.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetDisplayActivity extends Activity {

	private Tweet tweet;
	private TextView tvUserName;
	private TextView tvTwitterHandle;
	private TextView tvTweetBody;
	private TextView tvDate;
	private ImageView ivProfilePic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_display);
		setupViews();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent i = getIntent();
		tweet = (Tweet) i.getSerializableExtra(TimelineActivity.TWEET_EXTRA);
		
		loadData();
		fetchTweetStatus();
	}

	private void setupViews() {
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvTwitterHandle = (TextView) findViewById(R.id.tvTwitterHandle);
		tvTweetBody = (TextView) findViewById(R.id.tvTweetBody);
		tvDate = (TextView) findViewById(R.id.tvDate);
		ivProfilePic = (ImageView) findViewById(R.id.ivProfilePic);
		tvTweetBody.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	private void loadData() {
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), ivProfilePic);
		
		tvUserName.setText(Html.fromHtml("<b>" + tweet.getUser().getName() + "</b>"));
//		String formattedName = "<b>" + tweet.getUser().getName() + "</b> 
		tvTwitterHandle.setText(Html.fromHtml("<small><font color='#777777'>@" + tweet.getUser().getScreenName() + "</font></small>"));
		

		tvTweetBody.setText(Html.fromHtml(tweet.getBody()));
		
		
		Date createDate = tweet.getCreationDate();
		String str = DateUtils.getRelativeDateTimeString(this.getBaseContext(), createDate.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0).toString(); 
		tvDate.setText(Html.fromHtml("<small><font color='#777777'>" + str + "<font color='#777777'>")); 

	}
	
	private void fetchTweetStatus() {
		long id = tweet.getId();
		String strId = String.valueOf(id);
		RequestParams params = new RequestParams();
		params.put("id", strId);
		TwitterApp.getRestClient().getStatus(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject jsonStatus) {
				// TODO Auto-generated method stub
				Log.d("TEST - get tweet status", jsonStatus.toString());
				try {
//				tvTweetHtml.setText(Html.fromHtml(jsonStatus.getString("html")));
					String formattedBody = TweetHelper.formatTweetBody(jsonStatus);
					Log.d("TEST - formatted string" , formattedBody);
					tvTweetBody.setText(Html.fromHtml(formattedBody));
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
