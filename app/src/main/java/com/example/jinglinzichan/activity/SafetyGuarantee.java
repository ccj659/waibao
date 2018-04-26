package com.example.jinglinzichan.activity;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

//安全保障
@SuppressLint("SetJavaScriptEnabled")
public class SafetyGuarantee extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.safetyguarantee);
		initUI();
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
		title.setText("安全保障");
	}
}
