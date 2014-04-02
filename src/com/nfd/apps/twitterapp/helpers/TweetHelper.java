package com.nfd.apps.twitterapp.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nfd.apps.twitterapp.models.Tweet;

public class TweetHelper {

	/*
	"entities":{
		"urls":[
			{"display_url":"yahoo.com",
			"expanded_url":"http:\/\/www.yahoo.com",
			"indices":[14,36],
			"url":"http:\/\/t.co\/1xXbTETFkI"
			}
			],
		"hashtags":[],
		"user_mentions":[],
		"symbols":[]
	}*/
	
	public static String formatTweetBody(JSONObject status) {
		Tweet tweet = new Tweet(status);		
		String body = tweet.getBody();

		try {
			JSONObject entities = status.getJSONObject("entities");
			JSONArray urls = entities.getJSONArray("urls");
			for(int i = 0; i < urls.length(); i++) {
				JSONObject aLink = urls.getJSONObject(i);
				String expandedUrl = aLink.getString("expanded_url");
				String displayUrl = aLink.getString("display_url");
				String htmlLink = "<a href=\"" + expandedUrl + "\">" + displayUrl + "</a>";
				String url = aLink.getString("url");
				body = body.replace(url, htmlLink); 
			}
			JSONArray mentions = entities.getJSONArray("user_mentions");
			for(int i = 0; i < mentions.length(); i++) {
				JSONObject aMention = mentions.getJSONObject(i);
				String screenName = aMention.getString("screen_name");
				String linkToUser = "<a href=\"http://www.twitter.com/" + screenName + "\">@" + screenName + "</a>"; 
				body = body.replace("@"+screenName, linkToUser);
			}
			
			JSONArray hashTags = entities.getJSONArray("hashtags");
			for(int i = 0; i < hashTags.length(); i++) {
				JSONObject tag = hashTags.getJSONObject(i);
				String value = tag.getString("text");
				String searchLink = "<a href=\"https://twitter.com/search?q=%23" + value + "\">#" + value + "</a>"; 
//				https://twitter.com/search?q=%23test
				body = body.replace("#" + value, searchLink);
			}
			
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return body;//status.toString();
	}
/*	
	"user_mentions":[{"id":293934083,
		"indices":[3,12],
		"screen_name":"DIYGirls",
		"id_str":"293934083",
		"name":"DIY Girls"}

	public static String findMentions() {
		@(\w+)
	}
*/
}
