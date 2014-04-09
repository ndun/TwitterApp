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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.fragments.ProfileDataFragment;
import com.nfd.apps.twitterapp.fragments.ProfileImageFragment;
import com.nfd.apps.twitterapp.fragments.UserTimelineFragment;
import com.nfd.apps.twitterapp.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	public static final String USER_EXTRA = "user";
	protected User user;
	private ImageView ivBackgroundImage ;
	private FragmentPagerAdapter adapterViewPager;
	private ViewPager vpPager ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		user = (User) getIntent().getExtras().get(USER_EXTRA);
		if(user == null) {
			user = TwitterApp.getUser();
		}
//		getActionBar().setTitle("@" + user.getScreenName());
		populateProfileHeader();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction fts = manager.beginTransaction();
		
		fts.replace(R.id.frUserTimeline, UserTimelineFragment.newInstance(user.getId()));
		fts.commit();
		
		vpPager = (ViewPager) findViewById(R.id.vpProfilePager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOnPageChangeListener(new OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
//                getSupportActionBar().setSelectedNavigationItem(position);
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

	}
	
	private void populateProfileHeader() {
//		TextView tvName = (TextView) findViewById(R.id.tvName);
//		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		TextView tvTotalTweets = (TextView) findViewById(R.id.tvTweets);
//		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
//		ImageView ivSmallProfilePic = (ImageView) findViewById(R.id.ivSmallProfPic);
		ivBackgroundImage = (ImageView) findViewById(R.id.ivBackgroundImage);
		
//		tvName.setText(user.getName());
//		tvTagline.setText(user.getTagline());
		tvFollowers.setText(String.valueOf(user.getFollowersCount()) + " Followers");
		tvFollowing.setText(String.valueOf(user.getFriendsCount()) + " Following");
		tvTotalTweets.setText(String.valueOf(user.getTotalTweets() + " Tweets"));
//		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
//		ivSmallProfilePic.setBackgroundColor(Color.WHITE);
//		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivSmallProfilePic);
		
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
	
	
	public class MyPagerAdapter extends FragmentPagerAdapter {
		private int NUM_ITEMS = 2;

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
	                return ProfileImageFragment.newInstance(user);
	            case 1: // Fragment # 0 - This will show FirstFragment different title
	                return ProfileDataFragment.newInstance(user);
	            default:
	                return null;
	    	}
	    }
	}
}
