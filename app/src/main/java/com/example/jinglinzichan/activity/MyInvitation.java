package com.example.jinglinzichan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.adapter.AuctionPagerAdapter;

//我的邀请
@SuppressLint("SetJavaScriptEnabled")
public class MyInvitation extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private LinearLayout yaoqingma;
	private TextView yaoqingma_text;
	private View yaoqingma_view;
	private LinearLayout xiangqing;
	private TextView xiangqing_text;
	private View xiangqing_view;
	private ViewPager vp_content;
	private AuctionPagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinvitation);
		initUI();
		initData();
	}

	public void initUI() {
		// 返回键
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 标题字体
		title = (TextView) findViewById(R.id.title);
		title.setText("我的邀请");
		// 邀请码
		yaoqingma = (LinearLayout) findViewById(R.id.yaoqingma);
		yaoqingma.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				vp_content.setCurrentItem(0, true);
			}
		});
		yaoqingma_text = (TextView) findViewById(R.id.yaoqingma_text);
		yaoqingma_view = (View) findViewById(R.id.yaoqingma_view);
		// 投标详情
		xiangqing = (LinearLayout) findViewById(R.id.xiangqing);
		xiangqing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				vp_content.setCurrentItem(1, true);
			}
		});
		xiangqing_text = (TextView) findViewById(R.id.xiangqing_text);
		xiangqing_view = (View) findViewById(R.id.xiangqing_view);
		// 内容
		vp_content = (ViewPager) findViewById(R.id.vp_content);
		vp_content.setPageMargin(20);
		vp_content.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					yaoqingma_text.setTextColor(getResources().getColor(R.color.red));
					xiangqing_text.setTextColor(0xff8d8d8d);
					yaoqingma_view.setVisibility(View.VISIBLE);
					xiangqing_view.setVisibility(View.INVISIBLE);
					break;
				case 1:
					xiangqing_text.setTextColor(getResources().getColor(R.color.red));
					yaoqingma_text.setTextColor(0xff8d8d8d);
					xiangqing_view.setVisibility(View.VISIBLE);
					yaoqingma_view.setVisibility(View.INVISIBLE);
					break;
				default:
					break;
				}
				mAdapter.refresh(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void initData() {
		mAdapter = new AuctionPagerAdapter(getSupportFragmentManager());
		vp_content.setAdapter(mAdapter);
	}
}
