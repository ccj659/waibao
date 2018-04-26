package com.example.jinglinzichan.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.adapter.HomePageAdapter;
import com.example.jinglinzichan.bean.HomepageBean;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.example.jinglinzichan.view.HkDialogLoading;
import com.example.jinglinzichan.view.PullDownView;
import com.example.jinglinzichan.view.PullDownView.OnPullDownListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

//�ҵĴ���
public class YiFaBuXinXi extends BaseActivity implements OnClickListener,
		BusinessResponse {

	private LinearLayout return_key;
	private TextView title;
	private PullDownView toubiaojilu_list;
	private HomePageAdapter adapter;
	private String qubie = "3";
	private LinearLayout huankuan_yihuanqing;
	private HkDialogLoading dialogLoading;
	private int pageNumber = 1;
	private int yy = 0;
	private List<HomepageBean> homepage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.toubiaojilu);
		dialogLoading = new HkDialogLoading(this);
		// ���ؼ�
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(this);
		// ��������
		title = (TextView) findViewById(R.id.title);
		title.setText("�ҵĴ���");
		huankuan_yihuanqing = (LinearLayout) findViewById(R.id.huankuan_yihuanqing);
		huankuan_yihuanqing.setVisibility(View.GONE);
		// �б�
		toubiaojilu_list = (PullDownView) findViewById(R.id.toubiaojilu_list);
		// ���ݼ���
		toubiaojilu_list.setOnPullDownListener(new OnPullDownListener() {

			@Override
			public void onRefresh() {
				// ˢ�·���
				pageNumber = 1;
				yy = 0;
				adapter = new HomePageAdapter(homepage, qubie);
				toubiaojilu_list.getListView().setAdapter(adapter);
				isopen();
			}

			@Override
			public void onMore() {
				pageNumber++;
				yy++;
				isopen();
			}
		});

		// ѡ�����
		toubiaojilu_list.getListView().setOnItemClickListener(
				new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View v,
							int position, long arg3) {
						// ��ת
						Intent intent = new Intent(YiFaBuXinXi.this,
								XiangQing.class);
						intent.putExtra("id", homepage.get(position).getId());
						intent.putExtra("load_id", "");
						startActivity(intent);
					}
				});

		isopen();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_key:
			finish();
			break;
		default:
			break;
		}
	}

	// �ҵĴ���
	public void isopen() {
		dialogLoading.show(); // ��ʾ�����жԻ���
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", mySharedPreferences.getString("user_id", ""));
		params.addBodyParameter("p", pageNumber + "");

		pushData.httpClientSendWithToken(params, Constant.BORROWED, this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo)
			throws JSONException {
		dialogLoading.dismiss(); // ���ؼ����жԻ���
		if (jo == null) {
			Toast.makeText(this, "��������ʧ�ܣ���������", Toast.LENGTH_SHORT).show();
		} else {
			if (url.equals(Constant.BORROWED)) {
				Log.e("��������", jo.toString());
				homepage = new Gson().fromJson(
						jo.getString("data"),
						new TypeToken<ArrayList<HomepageBean>>() {
						}.getType());
				adapter = new HomePageAdapter(homepage, qubie);
				toubiaojilu_list.getListView().setAdapter(adapter);
				toubiaojilu_list.getListView().setSelection(yy * 10);
				toubiaojilu_list.RefreshComplete();
				toubiaojilu_list.notifyDidMore();
				toubiaojilu_list.setShowFooter();
			}
		}
	}
}
