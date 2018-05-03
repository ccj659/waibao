package com.example.jinglinzichan.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinglinzichan.R;

//账户信息1
@SuppressLint("InflateParams")
public class ZhangHuXinXi1 extends FragmentActivity implements OnClickListener{
	private LinearLayout return_key;
	private TextView title;
	private LinearLayout huankuan_yihuanqing;

	private TextView t1;
	private TextView t2;

	private Fragment tab1;
	private Fragment tab2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhanghuxinxi1);


		// 返回键
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(this);
		// 标题字体
		title = (TextView) findViewById(R.id.title);
		title.setText("账户信息");
		//huankuan_yihuanqing = (LinearLayout) findViewById(R.id.huankuan_yihuanqing);
		//huankuan_yihuanqing.setVisibility(View.GONE);



		initView();
		initEvent();
		setSelect(0);
	}


	/*
     * 初始化点击
     */
	private void initEvent() {
		// TODO Auto-generated method stub
		t1.setOnClickListener(this);
		t2.setOnClickListener(this);
	}
	//初始化
	private void initView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);

	}

	/**
	 * 将tab 的text 初始化
	 */
	public void reset() {
		t1.setText("我的账户");
		t2.setText("开户");
	}

	//设置Fragment内容区域
	private void setSelect(int i) {
		FragmentManager fm=getSupportFragmentManager();
		FragmentTransaction trs=fm.beginTransaction();
		//隐藏Fragment
		hideFragment(trs);
		t2.setTextColor(getResources().getColor(R.color.black));
		t1.setTextColor(getResources().getColor(R.color.black));
		// 把text 切换为选中
		switch (i) {
			case 0:
				if(tab1==null)
				{
					tab1=new ZhangHuXinXiFrag1();
					trs.add(R.id.id_content, tab1);
				}else
				{
					trs.show(tab1);
				}
				t1.setTextColor(getResources().getColor(R.color.red));
				break;
			case 1:
				if(tab2==null)
				{
					tab2=new ZhangHuXinXiFrag2();
					trs.add(R.id.id_content, tab2);
				}else
				{
					trs.show(tab2);
				}
				t2.setTextColor(getResources().getColor(R.color.red));

				break;



			default:
				break;
		}
		trs.commit();
	}
	/*
     * 隐藏所有的Fragment
     */
	private void hideFragment(FragmentTransaction trs) {

		if(tab1!=null)
		{
			trs.hide(tab1);
		}
		if(tab2!=null)
		{
			trs.hide(tab2);
		}


	}

	@Override
	public void onClick(View v) {
		reset();
		switch (v.getId()) {
			case R.id.text1:
				setSelect(0);
				break;
			case R.id.text2:
				setSelect(1);

				break;
			case R.id.return_key:
					finish();
				break;
			default:
				break;
		}
	}


}
