package com.example.jinglinzichan.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.activity.InformationDetails;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * ViewPager实现的轮播图广告自定义视图，如京东首页的广告轮播图效果； 既支持自动轮播页面也支持手势滑动切换页面
 */
@SuppressLint("HandlerLeak")
public class SlideShowView extends FrameLayout implements BusinessResponse {

	private ImageLoader imageLoader = ImageLoader.getInstance();
	// 轮播图图片数量
	private final static int IMAGE_COUNT = 5;
	// 自动轮播启用开关
	private final static boolean isAutoPlay = false;
	// 自定义轮播图的资源
	private String[] imageUrls = null;
	// 放轮播图片的ImageView 的list
	private List<ImageView> imageViewsList;
	// 放圆点的View的list
	private List<View> dotViewsList;
	private ViewPager viewPager;
	// 当前轮播页
	private int currentItem = 0;
	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;
	private Context context;
	private MyPagerAdapter myPagerAdapter;
	List<String> list = new ArrayList<String>();
	List<String> list1 = new ArrayList<String>();

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public SlideShowView(Context context) {
		this(context, null);
	}

	public SlideShowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		isopen();
		initImageLoader(context);
		initData();
		if (isAutoPlay) {
			startPlay();
		}
	}

	/**
	 * 开始轮播图切换
	 */
	public void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * 停止轮播图切换
	 */
	public void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	/**
	 * 初始化相关Data
	 */
	private void initData() {
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();
		// 一步任务获取图片
		new GetListTask().execute("");
	}

	/**
	 * 填充ViewPager的页面适配器
	 * 
	 */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, final int position) {
			ImageView imageView = imageViewsList.get(position);
			imageLoader.displayImage(imageView.getTag() + "", imageView);
			((ViewPager) container).addView(imageViewsList.get(position));
			if (position == 0) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String app_url = list.get(position);
						Log.e("app_url", app_url);
						String id = list1.get(position);
						Log.e("id", id);
						if (!app_url.equals("0")) {
							// 跳转
							Intent intent = new Intent(context,
									InformationDetails.class);
							intent.putExtra("id", id);
							context.startActivity(intent);
						}
					}
				});
			} else if (position == 1) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String app_url = list.get(position);
						Log.e("app_url", app_url);
						String id = list1.get(position);
						Log.e("id", id);
						if (!app_url.equals("0")) {
							// 跳转
							Intent intent = new Intent(context,
									InformationDetails.class);
							intent.putExtra("id", id);
							context.startActivity(intent);
						}
					}
				});
			} else if (position == 2) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String app_url = list.get(position);
						Log.e("app_url", app_url);
						String id = list1.get(position);
						Log.e("id", id);
						if (!app_url.equals("0")) {
							// 跳转
							Intent intent = new Intent(context,
									InformationDetails.class);
							intent.putExtra("id", id);
							context.startActivity(intent);
						}
					}
				});
			} else if (position == 3) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String app_url = list.get(position);
						Log.e("app_url", app_url);
						String id = list1.get(position);
						Log.e("id", id);
						if (!app_url.equals("0")) {
							// 跳转
							Intent intent = new Intent(context,
									InformationDetails.class);
							intent.putExtra("id", id);
							context.startActivity(intent);
						}
					}
				});
			} else if (position == 4) {
				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String app_url = list.get(position);
						Log.e("app_url", app_url);
						String id = list1.get(position);
						Log.e("id", id);
						if (!app_url.equals("0")) {
							// 跳转
							Intent intent = new Intent(context,
									InformationDetails.class);
							intent.putExtra("id", id);
							context.startActivity(intent);
						}
					}
				});
			}
			return imageViewsList.get(position);
		}

		@Override
		public int getCount() {
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

	/**
	 * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case 1:// 手势滑动，空闲中
				isAutoPlay = false;
				break;
			case 2:// 界面切换中
				isAutoPlay = true;
				break;
			case 0:// 滑动结束，即切换完毕或者加载完毕
					// 当前为最后一张，此时从右向左滑，则切换到第一张
				if (viewPager.getCurrentItem() == viewPager.getAdapter()
						.getCount() - 1 && !isAutoPlay) {
					viewPager.setCurrentItem(0);
				}
				// 当前为第一张，此时从左向右滑，则切换到最后一张
				else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
					viewPager
							.setCurrentItem(viewPager.getAdapter().getCount() - 1);
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int pos) {
			currentItem = pos;
			for (int i = 0; i < dotViewsList.size(); i++) {
				if (i == pos) {
					((View) dotViewsList.get(pos))
							.setBackgroundResource(R.drawable.page_indicator_focused);
				} else {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.page_indicator_unfocused);
				}
			}
		}
	}

	/**
	 * 执行轮播图切换任务
	 * 
	 */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}
	}

	/**
	 * 销毁ImageView资源，回收内存
	 * 
	 */
	@SuppressWarnings("unused")
	private void destoryBitmaps() {

		for (int i = 0; i < IMAGE_COUNT; i++) {
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null) {
				// 解除drawable对view的引用
				drawable.setCallback(null);
			}
		}
	}

	/**
	 * 异步任务,获取数据
	 * 
	 */
	class GetListTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * ImageLoader 图片组件初始化
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
	}

	private int array_count = 0;
	private LinearLayout dotLayout;

	// 首页的轮播图
	public void isopen() {
		LayoutInflater.from(context).inflate(R.layout.slideshow, this, true);
		dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
		dotLayout.removeAllViews();

		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();

		pushData.httpClientSendWithToken(params, Constant.TRUN_LIST, this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo)
			throws JSONException {
		if (jo == null) {
			Toast.makeText(context, "连接网络失败，请检查网络", Toast.LENGTH_SHORT).show();
		} else {
			if (url.equals(Constant.TRUN_LIST)) {
				Log.e("首页的轮播图", jo.toString());
				String status = jo.getString("status");
				if (status.equals("1")) {
					JSONArray jsonArray = jo.getJSONArray("data");
					JSONArray jArray = new JSONArray();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String urlString = jsonObject.getString("pic");
						Log.e("urlString", urlString);
						String id = jsonObject.getString("id");
						String app_url = jsonObject.getString("app_url");
						jArray.put(urlString);
						array_count = i + 1;

						list.add(app_url);
						list1.add(id);
					}
					imageUrls = new String[array_count];
					for (int j = 0; j < array_count; j++) {
						imageUrls[j] = jArray.getString(j);
					}
					// 热点个数与图片特殊相等
					for (int i = 0; i < imageUrls.length; i++) {
						ImageView view = new ImageView(context);
						view.setTag(imageUrls[i]);
						view.setScaleType(ScaleType.FIT_XY);
						imageViewsList.add(view);

						ImageView dotView = new ImageView(context);
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
						params.leftMargin = 4;
						params.rightMargin = 4;
						dotLayout.addView(dotView, params);
						dotViewsList.add(dotView);
					}
					viewPager = (ViewPager) findViewById(R.id.viewPager);
					viewPager.setFocusable(true);
					myPagerAdapter = new MyPagerAdapter();
					viewPager.setAdapter(myPagerAdapter);
					viewPager
							.setOnPageChangeListener(new MyPageChangeListener());
				} else {

				}
			}
		}
	}
}