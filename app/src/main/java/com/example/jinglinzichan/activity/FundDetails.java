package com.example.jinglinzichan.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.adapter.FundDetailsAdapter;
import com.example.jinglinzichan.bean.FundDetailsBean;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.example.jinglinzichan.view.PullDownView;
import com.example.jinglinzichan.view.PullDownView.OnPullDownListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

//�ʽ���ϸ
public class FundDetails extends BaseActivity {

	private LinearLayout return_key;
	private TextView title;
	private PullDownView funddetails_list;
	private List<FundDetailsBean> fundDetails;
	private FundDetailsAdapter fundDetailsadapter;
	private int pageNumber = 1;
	private int yy = 0;
	private String data, data1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.funddetails);
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
		title.setText("�ʽ���ϸ");
		// �б�
		funddetails_list = (PullDownView) findViewById(R.id.funddetails_list);
		// ���ݼ���
		funddetails_list.setOnPullDownListener(new OnPullDownListener() {

			@Override
			public void onRefresh() {
				// ˢ�·���
				pageNumber = 1;
				yy = 0;
				isopen();
			}

			@Override
			public void onMore() {
				pageNumber++;
				yy++;
				isopen();
			}
		});

		isopen();
	}

	// �ʽ���ϸ
	public void isopen() {
		show(); // ��ʾ�����жԻ���
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));
		params.addBodyParameter("p", pageNumber + "");

		pushData.httpClientSendWithToken(params, Constant.MONEY_LOG,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("��������ʧ�ܣ���������");
						} else {
							Log.e("�ʽ���־", jo.toString());
							if (jo.getString("status").equals("1")) {
								data = jo.getString("data");
								if (pageNumber == 1) {
										data1 = data;
										fundDetails = new Gson()
												.fromJson(
														data1,
														new TypeToken<ArrayList<FundDetailsBean>>() {
														}.getType());
										fundDetailsadapter = new FundDetailsAdapter(
												fundDetails);
										funddetails_list.getListView()
												.setAdapter(fundDetailsadapter);
										funddetails_list.RefreshComplete();
										funddetails_list.notifyDidMore();
										funddetails_list.setShowFooter();
								} else {
										data = data.substring(1, data.length());
										data1 = data1.substring(0,
												data1.length() - 1);
										data1 = data1 + "," + data;
										fundDetails = new Gson()
												.fromJson(
														data1,
														new TypeToken<ArrayList<FundDetailsBean>>() {
														}.getType());
										fundDetailsadapter = new FundDetailsAdapter(
												fundDetails);
										funddetails_list.getListView()
												.setAdapter(fundDetailsadapter);
										funddetails_list.getListView()
												.setSelection(yy * 10);
										funddetails_list.RefreshComplete();
										funddetails_list.notifyDidMore();
										funddetails_list.setShowFooter();
								}
							} else {
								funddetails_list.RefreshComplete();
								funddetails_list.notifyDidMore();
								funddetails_list.setShowFooter();
							}
						}
					}
				});
	}
}
