package com.example.jinglinzichan.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.jinglinzichan.fragment.InvitationCodeFragment;
import com.example.jinglinzichan.fragment.TenderDetailsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AuctionPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments = new ArrayList<Fragment>();

	public AuctionPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments.add(new InvitationCodeFragment());
		fragments.add(new TenderDetailsFragment());
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	public void refresh(int position) {
		if (position == 0) {
			((InvitationCodeFragment) fragments.get(0)).initData();
		} else {
			((TenderDetailsFragment) fragments.get(1)).initData();
		}
	}

}
