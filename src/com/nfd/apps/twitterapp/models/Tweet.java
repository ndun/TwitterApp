package com.nfd.apps.twitterapp.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet extends BaseModel {
	private static final String TEXT_KEY = "text";
	private static final String ID_KEY = "id";
	private static final String FAVORITE_KEY = "favorited";
	private static final String RETWEET_KEY = "retweeted";
	private static final String USER_KEY = "user";
	private static final String CREATION_DATE_KEY = "created_at";
	
	private static final String CREATE_DATE_FORMAT = "EEE MMM d HH:mm:ss z yyyy";

	private User user;

	public User getUser() {
		return user;
	}

	public String getBody() {
		return getString(TEXT_KEY);
	}
	
	public String getId() {
		return getString(ID_KEY);
	}

	public Date getCreationDate() {
		String dateStr = getString(CREATION_DATE_KEY);
		SimpleDateFormat sdf = new SimpleDateFormat(CREATE_DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return sdf.parse(dateStr);
		} catch(ParseException pe) {
			pe.printStackTrace();
		}
		return null;
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
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}

		return tweets;
	}
}
