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
import android.widget.LinearLayout;
import android.widget.TextView;

//ʵ����֤
public class RealNameAuthentication extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private EditText zhenshixingming, shenfenzhenghao;
	private LinearLayout queren_renzheng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shimingrenzheng);
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
		title.setText("ʵ����֤");
		// ��ʵ����
		zhenshixingming = (EditText) findViewById(R.id.zhenshixingming);
		// ���֤��
		shenfenzhenghao = (EditText) findViewById(R.id.shenfenzhenghao);
		// ȷ������
		queren_renzheng = (LinearLayout) findViewById(R.id.queren_renzheng);
		queren_renzheng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isopen();
			}
		});

	}

	// ʵ����֤
	public void isopen() {
		show(); // ��ʾ�����жԻ���
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));
		params.addBodyParameter("idno", shenfenzhenghao.getText().toString());
		params.addBodyParameter("user_name_true", zhenshixingming.getText()
				.toString());

		pushData.httpClientSendWithToken(params, Constant.ISHE_IDNO,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						if (jo == null) {
							showMessage("��������ʧ�ܣ���������");
						} else {
							hide();
							Log.e("ʵ����֤", jo.toString());
							showMessage(jo.getString("msg"));
							if (jo.getString("status").equals("1")) {
								finish();
							}
						}
					}
				});
	}
}
