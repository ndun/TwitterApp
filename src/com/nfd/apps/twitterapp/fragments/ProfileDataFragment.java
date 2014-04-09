package com.nfd.apps.twitterapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nfd.apps.twitterapp.R;
import com.nfd.apps.twitterapp.models.User;

public class ProfileDataFragment extends Fragment {
	public static final String USER_ARG = "user";

	private User user;
	public static ProfileDataFragment newInstance(User user) {
		ProfileDataFragment fragment = new ProfileDataFragment();
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
		// TODO Auto-generated method stub
		user = (User) getArguments().getSerializable(USER_ARG);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile_data, container, false);
		TextView tvTagline = (TextView) view.findViewById(R.id.tvTagline);
		tvTagline.setText(user.getTagline());
		return view;
	}

}
