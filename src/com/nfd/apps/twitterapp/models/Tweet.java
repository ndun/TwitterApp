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

	// protected JSONObject jsonObject;

	private String body;
	private String id;
	private Date creationDate;

	private User user;

	public Tweet() {
		body = id = "";
		creationDate = null;
	}

	public Tweet(JSONObject jsonObj) {
		try {
			body = jsonObj.getString(TEXT_KEY);
			id = jsonObj.getString(ID_KEY);			
			user = new User(jsonObj.getJSONObject(USER_KEY));
			
			String dateStr = jsonObj.getString(CREATION_DATE_KEY);
			SimpleDateFormat sdf = new SimpleDateFormat(CREATE_DATE_FORMAT);
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			creationDate = sdf.parse(dateStr);
		} catch (ParseException pe) {
			pe.printStackTrace();
			body = id = "";
			creationDate = null;
		} catch (JSONException e) {
			e.printStackTrace();
			body = id = "";
			creationDate = null;
		}
	}

	public User getUser() {
		return user;
	}

	public String getBody() {
		// return getString(TEXT_KEY);
		return body;
	}

	public String getId() {
//		return getString(ID_KEY);
		return id;
	}

	public Date getCreationDate() {
		return creationDate;
/*
		String dateStr = getString(CREATION_DATE_KEY);
		SimpleDateFormat sdf = new SimpleDateFormat(CREATE_DATE_FORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return sdf.parse(dateStr);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return null;
		*/
	}

	/*
	 * public static Tweet fromJson(JSONObject jsonObject) { Tweet tweet = new
	 * Tweet(jsonObject); try { // tweet.jsonObject = jsonObject;
	 * tweet.getString(TEXT_KEY); tweet.user =
	 * User.fromJson(jsonObject.getJSONObject(USER_KEY)); } catch (JSONException
	 * e) { e.printStackTrace(); return null; } return tweet; }
	 */

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

			Tweet tweet = new Tweet(tweetJson);// Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}

		return tweets;
	}

	/*
	 * public String getJSONString() { return jsonObject.toString(); }
	 * 
	 * protected String getString(String name) { try { return
	 * jsonObject.getString(name); } catch (Exception e) { e.printStackTrace();
	 * return null; } }
	 * 
	 * protected long getLong(String name) { try { return
	 * jsonObject.getLong(name); } catch (Exception e) { e.printStackTrace();
	 * return 0; } }
	 * 
	 * protected int getInt(String name) { try { return jsonObject.getInt(name);
	 * } catch (Exception e) { e.printStackTrace(); return 0; } }
	 * 
	 * protected double getDouble(String name) { try { return
	 * jsonObject.getDouble(name); } catch (Exception e) { e.printStackTrace();
	 * return 0; } }
	 * 
	 * protected boolean getBoolean(String name) { try { return
	 * jsonObject.getBoolean(name); } catch (Exception e) { e.printStackTrace();
	 * return false; } }
	 */
}
