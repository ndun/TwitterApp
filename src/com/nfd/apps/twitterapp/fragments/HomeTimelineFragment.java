package com.nfd.apps.twitterapp.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.models.Tweet;

public class HomeTimelineFragment extends TweetsListFragment {

	// newInstance constructor for creating fragment with arguments
	public static HomeTimelineFragment newInstance() {
		HomeTimelineFragment fragmentFirst = new HomeTimelineFragment();
		return fragmentFirst;
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shouldClearTweets = true;
		fetchTweets(new RequestParams());
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
