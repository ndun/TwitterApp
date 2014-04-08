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

import com.loopj.android.http.RequestParams;
import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.activities.TweetDisplayActivity;
import com.nfd.apps.twitterapp.adapters.TweetsAdapter;
import com.nfd.apps.twitterapp.helpers.EndlessScrollListener;
import com.nfd.apps.twitterapp.models.Tweet;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;


// v4 works backwards compatibility
public abstract class TweetsListFragment extends Fragment {

	public static final String TWEET_EXTRA = "tweet";
	protected PullToRefreshListView lvTweets;
	protected TweetsAdapter tweetsAdapter;
	protected ArrayList<Tweet> tweets;
	protected boolean shouldClearTweets = true;
	
	public abstract void fetchTweets(RequestParams parms);
	public abstract RequestParams getRequestParams();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		tweetsAdapter = new TweetsAdapter(getActivity(), tweets);
	}
	// called when fragment is being created and activity exists
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		// can call getActivity to access methods in the activity
	}

	// Must be created for each fragment to inflate xml
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
		lvTweets = (PullToRefreshListView) view.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetsAdapter);
		setListViewListeners();
		return view;
	}
	
	public TweetsAdapter getAdapter() {
		return tweetsAdapter;
	}
	
	private void setListViewListeners() {
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            	shouldClearTweets = true;
            	fetchTweets(getRequestParams());
                lvTweets.onRefreshComplete();
            }
        });

		lvTweets.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Log.d("TEST - Item Click Listener", "on item click: " + pos);
				Intent i = new Intent(getActivity().getBaseContext(), TweetDisplayActivity.class);
				i.putExtra(TWEET_EXTRA, tweetsAdapter.getItem(pos));
				startActivity(i);
			}
		});
		
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
				
				RequestParams parms = getRequestParams();
				parms.put("max_id", String.valueOf(lastId));
				fetchTweets(parms);
			}
		});
	}
}
