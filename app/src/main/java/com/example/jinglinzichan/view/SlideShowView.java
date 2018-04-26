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
 * ViewPagerʵ�ֵ��ֲ�ͼ����Զ�����ͼ���義����ҳ�Ĺ���ֲ�ͼЧ���� ��֧���Զ��ֲ�ҳ��Ҳ֧�����ƻ����л�ҳ��
 */
@SuppressLint("HandlerLeak")
public class SlideShowView extends FrameLayout implements BusinessResponse {

	private ImageLoader imageLoader = ImageLoader.getInstance();
	// �ֲ�ͼͼƬ����
	private final static int IMAGE_COUNT = 5;
	// �Զ��ֲ����ÿ���
	private final static boolean isAutoPlay = false;
	// �Զ����ֲ�ͼ����Դ
	private String[] imageUrls = null;
	// ���ֲ�ͼƬ��ImageView ��list
	private List<ImageView> imageViewsList;
	// ��Բ���View��list
	private List<View> dotViewsList;
	private ViewPager viewPager;
	// ��ǰ�ֲ�ҳ
	private int currentItem = 0;
	// ��ʱ����
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
	 * ��ʼ�ֲ�ͼ�л�
	 */
	public void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * ֹͣ�ֲ�ͼ�л�
	 */
	public void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	/**
	 * ��ʼ�����Data
	 */
	private void initData() {
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();
		// һ�������ȡͼƬ
		new GetListTask().execute("");
	}

	/**
	 * ���ViewPager��ҳ��������
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
							// ��ת
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
							// ��ת
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
							// ��ת
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
							// ��ת
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
							// ��ת
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
	 * ViewPager�ļ����� ��ViewPager��ҳ���״̬�����ı�ʱ����
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case 1:// ���ƻ�����������
				isAutoPlay = false;
				break;
			case 2:// �����л���
				isAutoPlay = true;
				break;
			case 0:// �������������л���ϻ��߼������
					// ��ǰΪ���һ�ţ���ʱ�������󻬣����л�����һ��
				if (viewPager.getCurrentItem() == viewPager.getAdapter()
						.getCount() - 1 && !isAutoPlay) {
					viewPager.setCurrentItem(0);
				}
				// ��ǰΪ��һ�ţ���ʱ�������һ������л������һ��
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
	 * ִ���ֲ�ͼ�л�����
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
	 * ����ImageView��Դ�������ڴ�
	 * 
	 */
	@SuppressWarnings("unused")
	private void destoryBitmaps() {

		for (int i = 0; i < IMAGE_COUNT; i++) {
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null) {
				// ���drawable��view������
				drawable.setCallback(null);
			}
		}
	}

	/**
	 * �첽����,��ȡ����
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
	 * ImageLoader ͼƬ�����ʼ��
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

	// ��ҳ���ֲ�ͼ
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
			Toast.makeText(context, "��������ʧ�ܣ���������", Toast.LENGTH_SHORT).show();
		} else {
			if (url.equals(Constant.TRUN_LIST)) {
				Log.e("��ҳ���ֲ�ͼ", jo.toString());
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
					// �ȵ������ͼƬ�������
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