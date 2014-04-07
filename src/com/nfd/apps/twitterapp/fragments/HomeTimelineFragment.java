package com.nfd.apps.twitterapp.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.helpers.EndlessScrollListener;
import com.nfd.apps.twitterapp.models.Tweet;

import android.os.Bundle;
import android.util.Log;

public class HomeTimelineFragment extends TweetsListFragment {

	// newInstance constructor for creating fragment with arguments
		public static HomeTimelineFragment newInstance() {
			HomeTimelineFragment fragmentFirst = new HomeTimelineFragment();
//			Bundle args = new Bundle();
//			args.putInt("someInt", page);
//			args.putString("someTitle", title);
//			fragmentFirst.setArguments(args);
			return fragmentFirst;
		}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fetchTimelineAsync(new RequestParams());
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {	
			@Override
			public void onLoadMore(int page, int totalItemsCount) {

				if(tweetsAdapter.getCount() == 0) {
					return;
				}
				Tweet aTweet = tweetsAdapter.getItem(tweetsAdapter.getCount()-1);
				Log.d("TEST - last tweet", "last Tweet: " + aTweet.getBody() + " - count: " + tweetsAdapter.getCount() + " id: " + aTweet.getId());
				long lastId = aTweet.getId();
				lastId = lastId - 1;
				shouldClearTweets = false;
				
				RequestParams parms = new RequestParams();
				parms.put("max_id", String.valueOf(lastId));
				fetchTimelineAsync(parms);
			}
		});
	}

	public void fetchTimelineAsync(RequestParams parms) {
//		RequestParams parms = new RequestParams();
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
}
