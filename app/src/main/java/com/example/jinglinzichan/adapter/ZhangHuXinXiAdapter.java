package com.example.jinglinzichan.adapter;

import java.util.List;
import java.util.Map;

import com.example.jinglinzichan.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ZhangHuXinXiAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> data;

	public ZhangHuXinXiAdapter(Context context, List<Map<String, Object>> data) {
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
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.package_content, null);
			viewHolder = new ViewHolder();
			viewHolder.package_mingcheng = (TextView) convertView
					.findViewById(R.id.package_mingcheng);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.package_mingcheng.setText((String) data.get(position).get(
				"name"));
		return convertView;
	}

	class ViewHolder {
		// ¹ú¼ÒÃû
		private TextView package_mingcheng;
	}

}
