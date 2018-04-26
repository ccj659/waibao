package com.example.jinglinzichan.adapter;

import java.util.List;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.bean.TheYearBean;
import com.example.jinglinzichan.view.ViewHolder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class TheYearAdapter extends BaseAdapter {

	private List<TheYearBean> data;

	public TheYearAdapter(List<TheYearBean> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public TheYearBean getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.theyear_content, null);
		}
		final TheYearBean item = getItem(position);
		TextView add_time = ViewHolder.get(convertView, R.id.add_time);
		add_time.setText(item.getAdd_time());
		TextView repay_time = ViewHolder.get(convertView, R.id.repay_time);
		repay_time.setText(item.getRepay_time() + "¸öÔÂ");
		TextView num_nh = ViewHolder.get(convertView, R.id.num_nh);
		num_nh.setText(item.getNum_nh());

		return convertView;
	}
}
