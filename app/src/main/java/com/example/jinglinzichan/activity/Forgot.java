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

//�һ�����
public class Forgot extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private TextView forgot_huoquyanzhengma;
	private EditText forgot_shoujihao, forgot_yanzhengma, forgot_mima;
	private TextView zhaohuimima;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot);
		initUI();
	}

	public void initUI() {
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
		title.setText("�һص�¼����");
		// ��ȡ��֤��
		forgot_huoquyanzhengma = (TextView) findViewById(R.id.forgot_huoquyanzhengma);
		forgot_huoquyanzhengma.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				duanxin();
			}
		});
		// �������ֻ���
		forgot_shoujihao = (EditText) findViewById(R.id.forgot_shoujihao);
		// ��������֤��
		forgot_yanzhengma = (EditText) findViewById(R.id.forgot_yanzhengma);
		// ����������
		forgot_mima = (EditText) findViewById(R.id.forgot_mima);
		// �ύ
		zhaohuimima = (TextView) findViewById(R.id.zhaohuimima);
		zhaohuimima.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isopen();
			}
		});
	}

	// �һ�����
	public void isopen() {
		show();
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobile", forgot_shoujihao.getText().toString());
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));
		params.addBodyParameter("password_mob", forgot_mima.getText()
				.toString());
		params.addBodyParameter("code", forgot_yanzhengma.getText().toString());
		pushData.httpClientSendWithToken(params, Constant.ONGETPASSWORD,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("��������ʧ�ܣ���������");
						} else {
							Log.e("�һ�����", jo.toString());
							showMessage(jo.getString("msg"));
							if (jo.getString("status").equals("1")) {
								finish();
							}
						}
					}
				});
	}

	// �һ��������
	public void duanxin() {
		show();
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobile", forgot_shoujihao.getText().toString());
		pushData.httpClientSendWithToken(params, Constant.GET_MOBLILE_CODE_ZH,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("��������ʧ�ܣ���������");
						} else {
							Log.e("�һ��������", jo.toString());
							showMessage(jo.getString("msg"));
						}
					}
				});
	}
}
