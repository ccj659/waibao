package com.example.jinglinzichan.activity;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//���֧��
@SuppressLint("SetJavaScriptEnabled")
public class QuickPayment extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private ImageView querenzhifu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quickpayment);
		// ���ؼ�
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// ��������
		title = (TextView) findViewById(R.id.title);
		title.setText("���֧��");
		// ȷ��֧��
		querenzhifu = (ImageView) findViewById(R.id.querenzhifu);
		querenzhifu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}
}
