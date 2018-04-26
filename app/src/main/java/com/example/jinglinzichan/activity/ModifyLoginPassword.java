package com.example.jinglinzichan.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//ÐÞ¸ÄµÇÂ¼ÃÜÂë
public class ModifyLoginPassword extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private EditText xiugai_jiumima, xiugai_xinmima;
	private TextView xiugai_querenxiugai,tv_forgot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiugaidenglumima);
		initUI();
	}

	public void initUI() {
		// ·µ»Ø¼ü
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// ±êÌâ×ÖÌå
		title = (TextView) findViewById(R.id.title);
		title.setText("ÐÞ¸ÄµÇÂ¼ÃÜÂë");
		// ¾ÉÃÜÂë
		xiugai_jiumima = (EditText) findViewById(R.id.xiugai_jiumima);
		xiugai_jiumima.setKeyListener(DialerKeyListener.getInstance());
		// ÐÂÃÜÂë
		xiugai_xinmima = (EditText) findViewById(R.id.xiugai_xinmima);
		xiugai_xinmima.setKeyListener(DialerKeyListener.getInstance());
		// Ìá½»
		xiugai_querenxiugai = (TextView) findViewById(R.id.xiugai_querenxiugai);
		xiugai_querenxiugai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isopen();
			}
		});
		tv_forgot= (TextView) findViewById(R.id.tv_forgot);
		tv_forgot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ModifyLoginPassword.this,Forgot.class));
			}
		});
	}

	// ÐÞ¸ÄµÇÂ½ÃÜÂë
	public void isopen() {
		show();
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));
		params.addBodyParameter("old_password", xiugai_jiumima.getText()
				.toString());
		params.addBodyParameter("password", xiugai_xinmima.getText().toString());
		pushData.httpClientSendWithToken(params, Constant.MODIFY_PASSWORD,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("Á¬½ÓÍøÂçÊ§°Ü£¬Çë¼ì²éÍøÂç");
						} else {
							if (url.equals(Constant.MODIFY_PASSWORD)) {
								Log.e("ÐÞ¸ÄµÇÂ½ÃÜÂë", jo.toString());
								showMessage(jo.getString("msg"));
								if (jo.getString("status").equals("1")) {
									tuichu();
								}
							}
						}
					}
				});
	}

	public void tuichu() {
		finish();
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.remove("user_id");
		editor.commit();
		startActivity(new Intent(this, Login.class));
	}
}
