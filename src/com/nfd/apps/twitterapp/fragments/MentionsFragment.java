package com.nfd.apps.twitterapp.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nfd.apps.twitterapp.TwitterApp;
import com.nfd.apps.twitterapp.models.Tweet;

public class MentionsFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getMentions(new RequestParams());
	}
	
	public static MentionsFragment newInstance() {
		MentionsFragment fragmentFirst = new MentionsFragment();
//		Bundle args = new Bundle();
//		args.putInt("someInt", page);
//		args.putString("someTitle", title);
//		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}
	
	public void getMentions(RequestParams parms) {
		parms.put("count", "25");
		TwitterApp.getRestClient().getMentions(
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
					}
				}, parms);
	}
	
}
