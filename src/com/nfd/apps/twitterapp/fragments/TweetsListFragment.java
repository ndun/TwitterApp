package com.nfd.apps.twitterapp.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.activities.TweetDisplayActivity;
import com.nfd.apps.twitterapp.adapters.TweetsAdapter;
import com.nfd.apps.twitterapp.models.Tweet;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;


// v4 works backwards compatibility
public class TweetsListFragment extends Fragment {

	public static final String TWEET_EXTRA = "tweet";
	protected PullToRefreshListView lvTweets;
	protected TweetsAdapter tweetsAdapter;
	protected ArrayList<Tweet> tweets;
	protected boolean shouldClearTweets = true;
	
	// called when fragment is being created and activity exists
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// can call getActivity to access methods in the activity
		
		tweets = new ArrayList<Tweet>();
		lvTweets = (PullToRefreshListView) getActivity().findViewById(R.id.lvTweets);
		tweetsAdapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets.setAdapter(tweetsAdapter);
		
		setListViewListeners();
	}

	// Must be created for each fragment to inflate xml
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_tweets_list, parent, false);
	}

	
	public TweetsAdapter getAdapter() {
		return tweetsAdapter;
	}
	
	public void onProfileClick(View v) {
		Log.d("TEST - PROFILE PIC CLICK", "PROFILE PICK CLICK: " + v.getId());
	}
	
	private void setListViewListeners() {
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	shouldClearTweets = true;
//                fetchTimelineAsync(new RequestParams());
                lvTweets.onRefreshComplete();
            }
        });
		

	
		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Log.d("TEST - Item Click Listener", "on item click: " + pos);
				Intent i = new Intent(getActivity().getBaseContext(), TweetDisplayActivity.class);
				i.putExtra(TWEET_EXTRA, tweetsAdapter.getItem(pos));
//				i.setClass(getBaseContext(), TweetDisplayActivity.class);
				startActivity(i);
			}
		});
	}
	
	
}
