package com.example.jinglinzichan.adapter;

import java.util.List;
import java.util.Map;

import com.example.jinglinzichan.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class XiangQingAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> data;

	public XiangQingAdapter(Context context, List<Map<String, Object>> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.xiangqing_content_c, null);
			viewHolder = new ViewHolder();

			viewHolder.content_j_text = (TextView) convertView
					.findViewById(R.id.content_j_text);

			viewHolder.content_x_text = (TextView) convertView
					.findViewById(R.id.content_x_text);

			viewHolder.content_c_text = (TextView) convertView
					.findViewById(R.id.content_c_text);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.content_j_text.setText((String) data.get(position).get(
				"money"));

		viewHolder.content_x_text.setText((String) data.get(position).get(
				"user_name"));

		viewHolder.content_c_text.setText((String) data.get(position).get(
				"add_time"));

		return convertView;
	}

	class ViewHolder {
		// Ãû³Æ
		private TextView content_j_text;
		// ½ð¶î
		private TextView content_x_text;
		// ½áÓà
		private TextView content_c_text;
	}
}
