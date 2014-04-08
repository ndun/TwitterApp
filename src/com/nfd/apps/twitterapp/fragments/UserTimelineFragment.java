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

public class UserTimelineFragment extends TweetsListFragment {

	public static String USER_ID_ARG_KEY = "userId";
	
	public static UserTimelineFragment newInstance(long userId) {
		UserTimelineFragment fragment = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putLong(USER_ID_ARG_KEY, userId);		
		fragment.setArguments(args);
		return fragment;
	}
	
	public long getUserId() {
		return getArguments().getLong(USER_ID_ARG_KEY);
	}
	
	public RequestParams getRequestParams() {
		RequestParams parms = new RequestParams();
		parms.put("user_id", String.valueOf(getUserId()));
		return parms;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		RequestParams parms = new RequestParams();
		
		parms.put("user_id", String.valueOf(getUserId()));
		shouldClearTweets = true;
		fetchTweets(parms);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
/*		
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
				parms.put("user_id", String.valueOf(getUserId()));
				parms.put("max_id", String.valueOf(lastId));
				fetchTimelineAsync(parms);
			}
		});
		*/
	}


	public void fetchTweets(RequestParams parms) {
		parms.put("count", "25");		
		TwitterApp.getRestClient().getUserTimeline(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						Log.d("TEST", jsonTweets.toString());
						tweets = Tweet.fromJson(jsonTweets);
						if(shouldClearTweets) {
							tweetsAdapter.clear();
						}
						tweetsAdapter.addAll(tweets);
					}

					@Override
					public void onFailure(Throwable arg0, JSONObject arg1) {
						// TODO Auto-generated method stub
						Log.d("test - failure", arg0.toString());
//						super.onFailure(arg0, arg1);
					}
					
					
				}, parms);
	}




}
