package com.nfd.apps.twitterapp.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.models.Tweet;
import com.nfd.apps.twitterapp.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {

	public static final String TWEET_EXTRA = "tweet";

	private EditText etTweetBody;
	private TextView tvCharCount;
	private ImageView ivProfilePic;
	private TextView tvUserName;
	private Button btnTweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		etTweetBody = (EditText) findViewById(R.id.etTweetBody);
		tvCharCount = (TextView) findViewById(R.id.tvCharCount);
		ivProfilePic = (ImageView) findViewById(R.id.ivProfilePic);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		btnTweet = (Button) findViewById(R.id.btnTweet);
		populateUserInfo();
		setTweetListener();
	}

	private void setTweetListener() {
		etTweetBody.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence str, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable str) {
				String tweetBody = etTweetBody.getText().toString();
				int remainingChars = 140 - tweetBody.length();
				tvCharCount.setText(String.valueOf(remainingChars) + " characters remaining");
				if(remainingChars < 0) {
					btnTweet.setEnabled(false);
				}
			}
		});
	}

	private void populateUserInfo() {
		
		User user = TwitterApp.getUser();
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfilePic);
		String formattedName = "<b>" + user.getName() + "</b> <small><font color='#777777'>@"
				+ user.getScreenName() + "</font></small>";
		tvUserName.setText(Html.fromHtml(formattedName));
//		TwitterApp.getRestClient().getUser(new JsonHttpResponseHandler() {
/*
			@Override
			public void onSuccess(JSONObject json) {
				Log.d("test - get user", json.toString());
				User user = new User(json);
				ImageLoader.getInstance().displayImage(
						user.getProfileImageUrl(), ivProfilePic);
				String formattedName = "<b>" + user.getName()
						+ "</b> <small><font color='#777777'>@"
						+ user.getScreenName() + "</font></small>";
				tvUserName.setText(Html.fromHtml(formattedName));

			}

			@Override
			public void onFailure(Throwable arg0, JSONObject arg1) {
				arg0.printStackTrace();
				Log.d("test - fail user", arg1.toString());
			}

		});
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	public void cancelTweet(View view) {
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
				// newTweet = Tweet.fromJson(jsonObj);
				Intent data = new Intent();
				data.putExtra(TWEET_EXTRA, newTweet);
				setResult(RESULT_OK, data);
				finish();
			}

		}, params);
	}

}
