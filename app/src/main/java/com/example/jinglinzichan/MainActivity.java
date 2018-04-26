package com.example.jinglinzichan;

import com.example.jinglinzichan.fragment.HomePageFragment;
import com.example.jinglinzichan.fragment.InvestmentFragments;
import com.example.jinglinzichan.fragment.PersonalCenterFragment;

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

/**
 * ��Ŀ����Activity�����е�Fragment��Ƕ�������
 * 
 * @author guolin
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	MyReceiver receiver;

	/**
	 * ��ҳ��Fragment
	 */
	private HomePageFragment homePageFragment;

	/**
	 * ���Ͷ�ʵ�Fragment
	 */
	private InvestmentFragments loaninvestmentFragment;

	/**
	 * �������ĵ�Fragment
	 */
	private PersonalCenterFragment personalcenterFragment;

	/**
	 * ��ҳ���沼��
	 */
	private View homePageLayout;

	/**
	 * ���Ͷ�ʽ��沼��
	 */
	private View loaninvestmentLayout;

	/**
	 * �������Ľ��沼��
	 */
	private View personalcenterLayout;

	/**
	 * ��Tab��������ʾ��ҳͼ��Ŀؼ�
	 */
	private ImageView homePageImage;
	private TextView homePageText;

	/**
	 * ��Tab��������ʾ���Ͷ��ͼ��Ŀؼ�
	 */
	private ImageView loaninvestmentImage;
	private TextView loaninvestmentText;

	/**
	 * ��Tab��������ʾ��������ͼ��Ŀؼ�
	 */
	private ImageView personalcenterImage;
	private TextView personalcenterText;

	/**
	 * ���ڶ�Fragment���й���
	 */
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// ��ʼ������Ԫ��
		initViews();
		fragmentManager = getSupportFragmentManager();
		// ��һ������ʱѡ�е�0��tab
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
	 * �������ȡ��ÿ����Ҫ�õ��Ŀؼ���ʵ���������������úñ�Ҫ�ĵ���¼���
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
			// ���������ҳtabʱ��ѡ�е�1��tab
			setTabSelection(0);
			break;
		case R.id.loaninvestment_layout:
			// ������˽��Ͷ��tabʱ��ѡ�е�2��tab
			setTabSelection(1);
			break;
		case R.id.personalcenter_layout:
			// ������˸�������tabʱ��ѡ�е�4��tab
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	/**
	 * ���ݴ����index����������ѡ�е�tabҳ��
	 * 
	 * @param index
	 *            ÿ��tabҳ��Ӧ���±ꡣ0��ʾ��ҳ��1��ʾĿ�ĵأ�2��ʾ�μǷ���3��ʾ����Ƽ���3��ʾ�������ġ�
	 */
	public void setTabSelection(int index) {
		// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
		clearSelection();
		// ����һ��Fragment����
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction);
		switch (index) {
		case 0:
			// ���������ҳtabʱ���ı�ؼ���ͼƬ��������ɫ
			homePageImage.setImageResource(R.drawable.home_tab_icon1_pre);
			homePageText.setTextColor(0xff3faaf5);
			if (homePageFragment == null) {
				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				homePageFragment = new HomePageFragment();
				transaction.add(R.id.content, homePageFragment);
			} else {
				// ������������
				homePageFragment = new HomePageFragment();
				transaction.add(R.id.content, homePageFragment);
			}
			break;
		case 1:
			// ������˽��Ͷ��tabʱ���ı�ؼ���ͼƬ��������ɫ
			loaninvestmentImage.setImageResource(R.drawable.home_tab_icon2_pre);
			loaninvestmentText.setTextColor(0xff3faaf5);
			if (loaninvestmentFragment == null) {
				// ���ContactsFragmentΪ�գ��򴴽�һ������ӵ�������
				loaninvestmentFragment = new InvestmentFragments();
				transaction.add(R.id.content, loaninvestmentFragment);
			} else {
				// ������������
				loaninvestmentFragment = new InvestmentFragments();
				transaction.add(R.id.content, loaninvestmentFragment);
			}
			break;
		case 3:
			// ������˸�������tabʱ���ı�ؼ���ͼƬ��������ɫ
			personalcenterImage.setImageResource(R.drawable.home_tab_icon3_pre);
			personalcenterText.setTextColor(0xff3faaf5);
			if (personalcenterFragment == null) {
				// ���PersonalCenterFragmentΪ�գ��򴴽�һ������ӵ�������
				personalcenterFragment = new PersonalCenterFragment();
				transaction.add(R.id.content, personalcenterFragment);
			} else {
				// ������������
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
	 * ��������е�ѡ��״̬��
	 */
	public void clearSelection() {
		homePageImage.setImageResource(R.drawable.home_tab_icon1);
		homePageText.setTextColor(0xffbcccd4);
		loaninvestmentImage.setImageResource(R.drawable.home_tab_icon2);
		loaninvestmentText.setTextColor(0xffbcccd4);
		personalcenterImage.setImageResource(R.drawable.home_tab_icon3);
		personalcenterText.setTextColor(0xffbcccd4);
	}

	/**
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            ���ڶ�Fragmentִ�в���������
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
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
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