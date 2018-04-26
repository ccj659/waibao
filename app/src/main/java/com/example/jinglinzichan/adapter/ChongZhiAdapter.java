package com.example.jinglinzichan.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jinglinzichan.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

@SuppressLint({ "UseSparseArrays", "InflateParams" })
public class ChongZhiAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> data;
	private ImageLoader imageLoader;
	/** 分类选中 */
	private HashMap<Integer, Integer> mapx = new HashMap<Integer, Integer>();

	public ChongZhiAdapter(Context context, List<Map<String, Object>> data,
			HashMap<Integer, Integer> mapx) {
		this.context = context;
		this.data = data;
		this.mapx = mapx;
		initImageLoader();
	}

	@SuppressWarnings("deprecation")
	private void initImageLoader() {
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
					R.layout.zhifu_item, null);
			viewHolder = new ViewHolder();

			viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic);

			viewHolder.radio = (RadioButton) convertView
					.findViewById(R.id.radio);

			viewHolder.keyongyue = (TextView) convertView
					.findViewById(R.id.keyongyue);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		imageLoader.displayImage(data.get(position).get("pic").toString(),
				viewHolder.pic);

		viewHolder.keyongyue.setText((String) data.get(position).get("name"));

		viewHolder.radio.setChecked(mapx.get(position) == null ? false : true);
		return convertView;
	}

	class ViewHolder {
		// 图片
		private ImageView pic;
		private RadioButton radio;
		private TextView keyongyue;
	}

}
