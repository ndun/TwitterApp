package com.nfd.apps.twitterapp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet extends BaseModel {
	private static final String TEXT_KEY = "text";
	private static final String ID_KEY = "id";
	private static final String FAVORITE_KEY = "favorited";
	private static final String RETWEET_KEY = "retweeted";
	private static final String USER_KEY = "user";
	
	private User user;
	
	public User getUser() {
		return user;
	}

	public String getBody() {
		return getString(TEXT_KEY);
	}
	
	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		try {
			tweet.jsonObject = jsonObject;
			tweet.user = User.fromJson(jsonObject.getJSONObject(USER_KEY));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}
	
	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		for(int i=0; i<jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJson(tweetJson);
			if(tweet != null) {
				tweets.add(tweet);
			}
		}
		
		return tweets;
	}
}
