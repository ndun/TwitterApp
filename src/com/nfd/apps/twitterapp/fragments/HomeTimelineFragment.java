package com.nfd.apps.twitterapp.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.activities.ComposeActivity;
import com.nfd.apps.twitterapp.activities.TimelineActivity;
import com.nfd.apps.twitterapp.models.Tweet;

public class HomeTimelineFragment extends TweetsListFragment {
	public static final String TWEET_ARG_KEY = "newTweet";
	
	// newInstance constructor for creating fragment with arguments
	public static HomeTimelineFragment newInstance(Tweet newTweet) {
		HomeTimelineFragment fragment = new HomeTimelineFragment();
		Bundle args = new Bundle();
		args.putSerializable(TWEET_ARG_KEY, newTweet);		
		fragment.setArguments(args);
		return fragment;
	}
	
	public static HomeTimelineFragment newInstance() {
		HomeTimelineFragment fragment = new HomeTimelineFragment();
		return fragment;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, parent, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shouldClearTweets = true;
		fetchTweets(new RequestParams());
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TimelineActivity.COMPOSE_TWEET_REQUEST) {
			if (data != null) {
				Tweet newTweet = (Tweet) data
						.getSerializableExtra(ComposeActivity.TWEET_EXTRA);
				tweetsAdapter.insert(newTweet, 0);
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle args = getArguments();
		if(args != null) {
			Tweet newTweet = (Tweet)args.getSerializable(TWEET_ARG_KEY);
			if(newTweet != null) {
				Log.d("TEST - new Tweet", newTweet.getBody());
			}
		}
	}

	public void fetchTweets(RequestParams parms) {
		parms.put("count", "25");
		
		TwitterApp.getRestClient().getHomeTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
//						Log.d("TEST", jsonTweets.toString());
						tweets = Tweet.fromJson(jsonTweets);
						if(shouldClearTweets) {
							tweetsAdapter.clear();
						}
						tweetsAdapter.addAll(tweets);
					}

					@Override
					public void onFailure(Throwable arg0, JSONObject arg1) {
						Log.d("test - failure", arg0.toString());
//						super.onFailure(arg0, arg1);
					}
				}, parms);
	}


	@Override
	public RequestParams getRequestParams() {
		return new RequestParams();
	}
}
