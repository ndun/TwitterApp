package com.nfd.apps.twitterapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileImageFragment extends Fragment {

	public static final String USER_ARG = "user";
	private User user;

	public static ProfileImageFragment newInstance(User user) {
		ProfileImageFragment fragment = new ProfileImageFragment();
		Bundle args = new Bundle();
		args.putSerializable(USER_ARG, user);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		user = (User) getArguments().getSerializable(USER_ARG);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile_image, container, false);

		TextView tvName = (TextView) view.findViewById(R.id.tvName);
		tvName.setText(user.getName());
		
		TextView tvTwitterHandle = (TextView) view.findViewById(R.id.tvTwitterHandle);
		tvTwitterHandle.setText("@" + user.getScreenName());
		
		ImageView ivSmallProfilePic = (ImageView) view.findViewById(R.id.ivSmallProfPic);
		ivSmallProfilePic.setBackgroundColor(Color.WHITE);
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivSmallProfilePic);

		return view;
	}
}
