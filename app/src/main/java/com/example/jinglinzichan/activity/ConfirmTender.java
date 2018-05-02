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

//确认投标
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
		title.setText("确认投标");
		// 标题
		queren_mingcheng = (TextView) findViewById(R.id.queren_mingcheng);
		// 借款金额
		queren_jiekuan = (TextView) findViewById(R.id.queren_jiekuan);
		// 完成进度
		queren_jindu = (TextView) findViewById(R.id.queren_jindu);
		// 年 利 率
		queren_nianlilv = (TextView) findViewById(R.id.queren_nianlilv);
		// 期限
		queren_qixian = (TextView) findViewById(R.id.queren_qixian);
		// 还款方式
		queren_fangshi = (TextView) findViewById(R.id.queren_fangshi);
		// 可投金额
		queren_ketou = (TextView) findViewById(R.id.queren_ketou);
		// 可用余额
		queren_keyong = (TextView) findViewById(R.id.queren_keyong);
		// 投标金额
		queren_shurujine = (EditText) findViewById(R.id.queren_shurujine);
		// 我要充值
		woyaochongzhi = (TextView) findViewById(R.id.woyaochongzhi);
		woyaochongzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ConfirmTender.this, Recharge.class));
			}
		});
		// 全投
		quantou = (TextView) findViewById(R.id.quantou);
		quantou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				queren_shurujine.setText(need_money);
			}
		});
		// 确认投标
		querentoubiao = (TextView) findViewById(R.id.querentoubiao);
		querentoubiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转
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
		// 邀请码
		qingshuruyaoqingma = (EditText) findViewById(R.id.qingshuruyaoqingma);
		// 服务协议
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

	// 去投标
	public void qutoubiao() {
		show(); // 显示加载中对话框
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
							showMessage("连接网络失败，请检查网络");
						} else {
							hide(); // 隐藏加载中对话框
							Log.e("去投标", jo.toString());
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
									+ "个月");
							queren_fangshi.setText(jo
									.getString("loantype_format"));
							queren_ketou.setText(jo.getString("need_money"));
							queren_keyong.setText(money_encrypt);
						}
					}
				});
	}
}
