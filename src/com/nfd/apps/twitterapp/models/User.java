package com.nfd.apps.twitterapp.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable { 

	private static final long serialVersionUID = 3811867177527781150L;

	private static final String NAME_KEY = "name";
	private static final String ID_KEY = "id";
	private static final String SCREEN_NAME_KEY = "screen_name";
	private static final String PROFILE_IMG_URL_KEY = "profile_image_url";
	private static final String PROFILE_BACKGROUND_IMG_URL_KEY = "profile_background_image_url";
	private static final String NUM_TWEETS_KEY = "statuses_count";
	private static final String FOLLOWERS_COUNT_KEY = "followers_count";
	private static final String FRIENDS_COUNT_KEY = "friends_count";
	
	private String name;
	private String profileImageUrl;
	private String screenName;
	private long id;
	
	public User() {
		name = profileImageUrl = screenName = "";
		id = 0;
	}
	
	public User(JSONObject json) {
		try {
			name = json.getString(NAME_KEY);
			id = json.getLong(ID_KEY);
			profileImageUrl = json.getString(PROFILE_IMG_URL_KEY);
			screenName = json.getString(SCREEN_NAME_KEY);
		} catch(JSONException e) {
			e.printStackTrace();
			name = profileImageUrl = screenName = "";
			id = 0;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public long getId() {
		return id;
	}
	
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public String getScreenName() {
		return screenName;
	}
}
