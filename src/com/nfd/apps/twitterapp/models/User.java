package com.nfd.apps.twitterapp.models;

import org.json.JSONObject;

public class User extends BaseModel {
	private static final String NAME_KEY = "name";
	private static final String ID_KEY = "id";
	private static final String SCREEN_NAME_KEY = "screen_name";
	private static final String PROFILE_IMG_URL_KEY = "profile_image_url";
	private static final String PROFILE_BACKGROUND_IMG_URL_KEY = "profile_background_image_url";
	private static final String NUM_TWEETS_KEY = "statuses_count";
	private static final String FOLLOWERS_COUNT_KEY = "followers_count";
	private static final String FRIENDS_COUNT_KEY = "friends_count";
	
	public String getName() {
		return getString(NAME_KEY);
	}
	
	public long getId() {
		return getLong(ID_KEY);
	}
	
	public String getProfileImageUrl() {
		return getString(PROFILE_IMG_URL_KEY);
	}
	
	public String getScreenName() {
		return getString(SCREEN_NAME_KEY);
	}
	
	public static User fromJson(JSONObject json) {
		User u = new User();
		try {
			u.jsonObject = json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
}
