package com.example.jinglinzichan.adapter;

import java.util.List;
import java.util.Map;

import com.example.jinglinzichan.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class XuanZeYinHangAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> data;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	public XuanZeYinHangAdapter(Context context, List<Map<String, Object>> data) {
		this.context = context;
		this.data = data;
		initImageLoader();
	}

	private void initImageLoader() {
		@SuppressWarnings("deprecation")
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache());

		ImageLoaderConfiguration config = builder.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
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
					R.layout.xuanzeyinahang_content, null);
			viewHolder = new ViewHolder();
			viewHolder.yinhangmingcheng = (TextView) convertView
					.findViewById(R.id.yinhangmingcheng);

			viewHolder.yinhangkahao = (TextView) convertView
					.findViewById(R.id.yinhangkahao);

			viewHolder.zhenshixingming = (TextView) convertView
					.findViewById(R.id.zhenshixingming);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.yinhangmingcheng.setText((String) data.get(position).get(
				"bank_name"));

		viewHolder.yinhangkahao.setText((String) data.get(position).get(
				"bankcard"));

		viewHolder.zhenshixingming.setText((String) data.get(position).get(
				"real_name"));

		return convertView;
	}

	class ViewHolder {
		// 银行名称
		private TextView yinhangmingcheng;
		// 银行卡号
		private TextView yinhangkahao;
		// 真实姓名
		private TextView zhenshixingming;
	}
}
