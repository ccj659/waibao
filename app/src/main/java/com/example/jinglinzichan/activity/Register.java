package com.example.jinglinzichan.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//注册
public class Register extends BaseActivity {

	private LinearLayout return_key;
	private TextView title,register_yanzhengma,zhuce;

	private EditText shuruyonghu, shurumima, zaicshuru, shurushoujihao,
			shuruyanzhengma;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
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
		title.setText("账户注册");
		// 获取验证码
		register_yanzhengma = (TextView) findViewById(R.id.register_yanzhengma);
		register_yanzhengma.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				duanxin();
			}
		});
		// 请输入用户名
		shuruyonghu = (EditText) findViewById(R.id.shuruyonghu);
		// 请输入密码
		shurumima = (EditText) findViewById(R.id.shurumima);
		// 再次输入密码
		zaicshuru = (EditText) findViewById(R.id.zaicshuru);
		// 输入手机号
		shurushoujihao = (EditText) findViewById(R.id.shurushoujihao);
		// 输入验证码
		shuruyanzhengma = (EditText) findViewById(R.id.shuruyanzhengma);
		// 注册
		zhuce = (TextView) findViewById(R.id.zhuce);
		zhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isopen();
			}
		});
	}

	// 注册
	public void isopen() {
		show();
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("activation_code_in", shuruyonghu.getText()
				.toString());
		params.addBodyParameter("password", shurumima.getText().toString());
		params.addBodyParameter("password2", zaicshuru.getText().toString());
		params.addBodyParameter("mobile", shurushoujihao.getText().toString());
		params.addBodyParameter("code", shuruyanzhengma.getText().toString());
		pushData.httpClientSendWithToken(params, Constant.ONREGISTER,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							if (url.equals(Constant.ONREGISTER)) {
								Log.e("注册", jo.toString());
								showMessage(jo.getString("msg"));
								if (jo.getString("status").equals("1")) {
									finish();
								}
							}
						}
					}
				});
	}

	// 注册短信验证码
	public void duanxin() {
		show();
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobile", shurushoujihao.getText().toString());

		pushData.httpClientSendWithToken(params, Constant.GET_MOBILE_CODE,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							Log.e("注册短信验证码", jo.toString());
							showMessage(jo.getString("msg"));
						}
					}
				});
	}
}
