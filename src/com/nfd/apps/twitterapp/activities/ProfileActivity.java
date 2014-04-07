package com.nfd.apps.twitterapp.activities;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	public static final String USER_EXTRA = "user";
	private User user;
	private ImageView ivBackgroundImage ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		user = (User) getIntent().getExtras().get(USER_EXTRA);
		if(user == null) {
			user = TwitterApp.getUser();
		}
		getActionBar().setTitle("@" + user.getScreenName());
		populateProfileHeader();
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	private void populateProfileHeader() {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
//		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		ImageView ivSmallProfilePic = (ImageView) findViewById(R.id.ivSmallProfPic);
		ivBackgroundImage = (ImageView) findViewById(R.id.ivBackgroundImage);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(String.valueOf(user.getFollowersCount()) + " Followers");
		tvFollowing.setText(String.valueOf(user.getFriendsCount()) + " Following");
//		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
		ivSmallProfilePic.setBackgroundColor(Color.WHITE);
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivSmallProfilePic);
		
		if(user.getBackgroundImageUrl() != null && user.getBackgroundImageUrl() != " ") {
			new RetrieveBitMapTask().execute(user.getBackgroundImageUrl());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	private class RetrieveBitMapTask extends AsyncTask<String, Void, Bitmap> {

		// Cannot use network capabilities on main thread, so need to retrieve image in background
		protected Bitmap doInBackground(String... arg0) {
			try {
				HttpGet httpRequest = new HttpGet(URI.create(user.getBackgroundImageUrl()));
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
				Bitmap bitmap = BitmapFactory.decodeStream(bufHttpEntity.getContent());
				return bitmap;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(Bitmap bitmap) {
			BitmapDrawable backgroundDrawable = new BitmapDrawable(getBaseContext().getResources(), bitmap);
			ivBackgroundImage.setBackgroundDrawable(backgroundDrawable);
		}
	}
	
}
