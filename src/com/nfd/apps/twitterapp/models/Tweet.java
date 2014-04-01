package com.nfd.apps.twitterapp.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Serializable {

	private static final long serialVersionUID = -924872342376916190L;

	private static final String TEXT_KEY = "text";
	private static final String ID_KEY = "id";
	private static final String FAVORITE_KEY = "favorited";
	private static final String RETWEET_KEY = "retweeted";
	private static final String USER_KEY = "user";
	private static final String CREATION_DATE_KEY = "created_at";

	private static final String CREATE_DATE_FORMAT = "EEE MMM d HH:mm:ss z yyyy";

	private String body;
	private long id;
	private Date creationDate;

	private User user;

	public Tweet() {
		body = "";
		id = 0;
		creationDate = null;
	}

	public Tweet(JSONObject jsonObj) {
		try {
			body = jsonObj.getString(TEXT_KEY);
			id = jsonObj.getLong(ID_KEY);			
			user = new User(jsonObj.getJSONObject(USER_KEY));
			
			String dateStr = jsonObj.getString(CREATION_DATE_KEY);
			SimpleDateFormat sdf = new SimpleDateFormat(CREATE_DATE_FORMAT);
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			creationDate = sdf.parse(dateStr);
		} catch (ParseException pe) {
			pe.printStackTrace();
			body = "";
			id=0;
			creationDate = null;
		} catch (JSONException e) {
			e.printStackTrace();
			body = "";
			id = 0;
			creationDate = null;
		}
	}

	public User getUser() {
		return user;
	}

	public String getBody() {
		return body;
	}

	public long getId() {
		return id;
	}

	public Date getCreationDate() {
		return creationDate;
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

			Tweet tweet = new Tweet(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}

		return tweets;
	}
}
