package com.example.jinglinzichan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

//ȷ��Ͷ��
public class ConfirmTender extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private TextView woyaochongzhi, querentoubiao;
	private String money_encrypt, need_money;
	private TextView queren_mingcheng, queren_jiekuan, queren_jindu,
			queren_nianlilv, queren_qixian, queren_fangshi, queren_ketou,
			queren_keyong, fuwuxieyi;
	private EditText queren_shurujine;
	private TextView quantou;
	private String id;
	private EditText qingshuruyaoqingma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.querentoubiao);
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
		title.setText("ȷ��Ͷ��");
		// ����
		queren_mingcheng = (TextView) findViewById(R.id.queren_mingcheng);
		// �����
		queren_jiekuan = (TextView) findViewById(R.id.queren_jiekuan);
		// ��ɽ���
		queren_jindu = (TextView) findViewById(R.id.queren_jindu);
		// �� �� ��
		queren_nianlilv = (TextView) findViewById(R.id.queren_nianlilv);
		// ����
		queren_qixian = (TextView) findViewById(R.id.queren_qixian);
		// ���ʽ
		queren_fangshi = (TextView) findViewById(R.id.queren_fangshi);
		// ��Ͷ���
		queren_ketou = (TextView) findViewById(R.id.queren_ketou);
		// �������
		queren_keyong = (TextView) findViewById(R.id.queren_keyong);
		// Ͷ����
		queren_shurujine = (EditText) findViewById(R.id.queren_shurujine);
		// ��Ҫ��ֵ
		woyaochongzhi = (TextView) findViewById(R.id.woyaochongzhi);
		woyaochongzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ConfirmTender.this, Recharge.class));
			}
		});
		// ȫͶ
		quantou = (TextView) findViewById(R.id.quantou);
		quantou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				queren_shurujine.setText(need_money);
			}
		});
		// ȷ��Ͷ��
		querentoubiao = (TextView) findViewById(R.id.querentoubiao);
		querentoubiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ת
				Intent intent = new Intent(ConfirmTender.this,
						ConfirmTenderWeb.class);
				intent.putExtra("id", id);
				intent.putExtra("bid_money", queren_shurujine.getText()
						.toString());
				intent.putExtra("activation_code_in", qingshuruyaoqingma
						.getText().toString());
				startActivity(intent);
			}
		});
		// ������
		qingshuruyaoqingma = (EditText) findViewById(R.id.qingshuruyaoqingma);
		// ����Э��
		fuwuxieyi = (TextView) findViewById(R.id.fuwuxieyi);
		fuwuxieyi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ConfirmTender.this,
						ServiceAgreement.class));
			}
		});

		qutoubiao();
	}

	// ȥͶ��
	public void qutoubiao() {
		show(); // ��ʾ�����жԻ���
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));
		params.addBodyParameter("id", getIntent().getStringExtra("id"));
		pushData.httpClientSendWithToken(params, Constant.DEAL_ONARTICLE,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						if (jo == null) {
							showMessage("��������ʧ�ܣ���������");
						} else {
							hide(); // ���ؼ����жԻ���
							Log.e("ȥͶ��", jo.toString());
							id = jo.getString("id");
							need_money = jo.getString("need_money");
							money_encrypt = jo.getJSONObject("user_info")
									.getString("money_encrypt");
							qingshuruyaoqingma.setText(jo.getJSONObject(
									"user_info")
									.getString("activation_code_in"));

							if (!jo.getJSONObject("user_info")
									.getString("activation_code_in").equals("")) {
								qingshuruyaoqingma.setKeyListener(null);
							}

							queren_mingcheng.setText(jo.getString("name"));
							queren_jiekuan.setText(jo
									.getString("borrow_amount"));
							queren_jindu.setText(jo.getString("progress_point")
									+ "%");
							queren_nianlilv.setText(jo.getString("rate") + "%");
							queren_qixian.setText(jo.getString("repay_time")
									+ "����");
							queren_fangshi.setText(jo
									.getString("loantype_format"));
							queren_ketou.setText(jo.getString("need_money"));
							queren_keyong.setText(money_encrypt);
						}
					}
				});
	}
}
