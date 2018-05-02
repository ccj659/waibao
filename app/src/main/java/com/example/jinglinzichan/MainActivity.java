package com.example.jinglinzichan;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinglinzichan.fragment.HomePageFragment;
import com.example.jinglinzichan.fragment.InvestmentFragments;
import com.example.jinglinzichan.fragment.PersonalCenterFragment;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author guolin
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	MyReceiver receiver;

	/**
	 * 首页的Fragment
	 */
	private HomePageFragment homePageFragment;

	/**
	 * 借款投资的Fragment
	 */
	private InvestmentFragments loaninvestmentFragment;

	/**
	 * 个人中心的Fragment
	 */
	private PersonalCenterFragment personalcenterFragment;

	/**
	 * 首页界面布局
	 */
	private View homePageLayout;

	/**
	 * 借款投资界面布局
	 */
	private View loaninvestmentLayout;

	/**
	 * 个人中心界面布局
	 */
	private View personalcenterLayout;

	/**
	 * 在Tab布局上显示首页图标的控件
	 */
	private ImageView homePageImage;
	private TextView homePageText;

	/**
	 * 在Tab布局上显示借款投资图标的控件
	 */
	private ImageView loaninvestmentImage;
	private TextView loaninvestmentText;

	/**
	 * 在Tab布局上显示个人中心图标的控件
	 */
	private ImageView personalcenterImage;
	private TextView personalcenterText;

	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// 初始化布局元素
		initViews();
		fragmentManager = getSupportFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.zhongtoulvyou");
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		homePageLayout = findViewById(R.id.homePage_layout);
		loaninvestmentLayout = findViewById(R.id.loaninvestment_layout);
		personalcenterLayout = findViewById(R.id.personalcenter_layout);

		homePageImage = (ImageView) findViewById(R.id.homePage_image);
		homePageText = (TextView) findViewById(R.id.homePage_text);
		loaninvestmentImage = (ImageView) findViewById(R.id.loaninvestment_image);
		loaninvestmentText = (TextView) findViewById(R.id.loaninvestment_text);
		personalcenterImage = (ImageView) findViewById(R.id.personalcenter_image);
		personalcenterText = (TextView) findViewById(R.id.personalcenter_text);

		homePageLayout.setOnClickListener(this);
		loaninvestmentLayout.setOnClickListener(this);
		personalcenterLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.homePage_layout:
			// 当点击了首页tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.loaninvestment_layout:
			// 当点击了借款投资tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.personalcenter_layout:
			// 当点击了个人中心tab时，选中第4个tab
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示首页，1表示目的地，2表示游记分享，3表示年度推荐，3表示个人中心。
	 */
	public void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了首页tab时，改变控件的图片和文字颜色
			homePageImage.setImageResource(R.drawable.home_tab_down);
			homePageText.setTextColor(getResources().getColor(R.color.red));
			if (homePageFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				homePageFragment = new HomePageFragment();
				transaction.add(R.id.content, homePageFragment);
			} else {
				// 重新请求数据
				homePageFragment = new HomePageFragment();
				transaction.add(R.id.content, homePageFragment);
			}
			break;
		case 1:
			// 当点击了借款投资tab时，改变控件的图片和文字颜色
			loaninvestmentImage.setImageResource(R.drawable.home_tab_2_down);
			loaninvestmentText.setTextColor(getResources().getColor(R.color.red));
			if (loaninvestmentFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				loaninvestmentFragment = new InvestmentFragments();
				transaction.add(R.id.content, loaninvestmentFragment);
			} else {
				// 重新请求数据
				loaninvestmentFragment = new InvestmentFragments();
				transaction.add(R.id.content, loaninvestmentFragment);
			}
			break;
		case 3:
			// 当点击了个人中心tab时，改变控件的图片和文字颜色
			personalcenterImage.setImageResource(R.drawable.home_tab_3_down);
			personalcenterText.setTextColor(getResources().getColor(R.color.red));
			if (personalcenterFragment == null) {
				// 如果PersonalCenterFragment为空，则创建一个并添加到界面上
				personalcenterFragment = new PersonalCenterFragment();
				transaction.add(R.id.content, personalcenterFragment);
			} else {
				// 重新请求数据
				personalcenterFragment = new PersonalCenterFragment();
				transaction.add(R.id.content, personalcenterFragment);
			}
			break;
		default:
			break;
		}
		transaction.commitAllowingStateLoss();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	public void clearSelection() {
		homePageImage.setImageResource(R.drawable.home_tab_1_de);
		homePageText.setTextColor(0xffbcccd4);
		loaninvestmentImage.setImageResource(R.drawable.home_tab_2_d);
		loaninvestmentText.setTextColor(0xffbcccd4);
		personalcenterImage.setImageResource(R.drawable.home_tab_3_de);
		personalcenterText.setTextColor(0xffbcccd4);
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (homePageFragment != null) {
			transaction.hide(homePageFragment);
		}
		if (loaninvestmentFragment != null) {
			transaction.hide(loaninvestmentFragment);
		}
		if (personalcenterFragment != null) {
			transaction.hide(personalcenterFragment);
		}
	}

	private long exitTime = 0;
	private SharedPreferences mySharedPreferencesxx;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				mySharedPreferencesxx = this.getSharedPreferences("gonggaolan",
						Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = mySharedPreferencesxx.edit();
				editor.remove("gonggao");
				editor.commit();
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int index = intent.getIntExtra("index", 0);
			setTabSelection(index);
		}
	}
}