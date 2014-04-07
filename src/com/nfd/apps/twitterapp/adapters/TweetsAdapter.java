package com.nfd.apps.twitterapp.adapters;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.activities.ProfileActivity;
import com.nfd.apps.twitterapp.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	private Tweet tweet;
	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}
		
		tweet = getItem(position);
		
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		imageView.setOnClickListener(new ImageViewClickListener(position));
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
		
		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "</b> <small><font color='#777777'>@" + tweet.getUser().getScreenName() + "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));
		
		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		
		TextView createDateView = (TextView) view.findViewById(R.id.tvCreateDate);
		Date createDate = tweet.getCreationDate();
		String str = DateUtils.getRelativeDateTimeString(this.getContext(), createDate.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0).toString(); 
		createDateView.setText(Html.fromHtml("<small><font color='#777777'>" + str + "<font color='#777777'>")); 
		
		return view;
	}
	
	class ImageViewClickListener implements OnClickListener {
		int position;
		public ImageViewClickListener(int pos) {
			this.position = pos;
		}

		public void onClick(View v) {
			Intent i = new Intent(v.getContext(), ProfileActivity.class);
			
			i.putExtra(ProfileActivity.USER_EXTRA, getItem(position).getUser());
			v.getContext().startActivity(i);
		}
	}
}
