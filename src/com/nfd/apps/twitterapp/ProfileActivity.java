package com.nfd.apps.twitterapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfd.apps.twitterapp.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		user = TwitterApp.getUser();
		getActionBar().setTitle("@" + user.getScreenName());
		populateProfileHeader();
	}
	
	private void populateProfileHeader() {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		ImageView ivSmallProfilePic = (ImageView) findViewById(R.id.ivSmallProfPic);
		ImageView ivBackgroundImage = (ImageView) findViewById(R.id.ivBackgroundImage);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(String.valueOf(user.getFollowersCount()) + " Followers");
		tvFollowing.setText(String.valueOf(user.getFriendsCount()) + " Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
		ivSmallProfilePic.setBackgroundColor(Color.WHITE);
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivSmallProfilePic);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
