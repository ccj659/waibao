package com.example.jinglinzichan.adapter;

import java.util.List;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.bean.NoticeBean;
import com.example.jinglinzichan.view.ViewHolder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class NoticeAdapter extends BaseAdapter {

	private List<NoticeBean> data;
	private int ss;

	public NoticeAdapter(List<NoticeBean> data, int ss) {
		this.data = data;
		this.ss = ss;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public NoticeBean getItem(int position) {
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
					R.layout.notice_content, null);
		}
		final NoticeBean item = getItem(position);
		TextView title = ViewHolder.get(convertView, R.id.title);
		title.setText(item.getTitle());
		TextView creatime = ViewHolder.get(convertView, R.id.creatime);
		creatime.setText(item.getCreatime());
		ImageView tupian = ViewHolder.get(convertView, R.id.tupian);
		if (ss == 1) {
			creatime.setVisibility(View.VISIBLE);
			tupian.setVisibility(View.VISIBLE);
		} else {
			creatime.setVisibility(View.GONE);
			tupian.setVisibility(View.GONE);
		}

		return convertView;
	}
}
