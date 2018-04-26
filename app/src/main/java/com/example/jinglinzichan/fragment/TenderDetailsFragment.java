package com.example.jinglinzichan.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseFragment;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.activity.TheYear;
import com.example.jinglinzichan.adapter.TenderDetailsAdapter;
import com.example.jinglinzichan.bean.TenderDetailsBean;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

//投标详情
@SuppressLint("InflateParams")
public class TenderDetailsFragment extends BaseFragment {

	private ListView details_list;
	private List<TenderDetailsBean> tenderdetails;
	private TenderDetailsAdapter adapter;
	private TextView leijiyaoqing;
	private TextView nianhua;

	@Override
	public int getContentId() {
		return R.layout.tenderdetails;
	}

	@Override
	public void init(View view) {
		// 累计邀请
		leijiyaoqing = (TextView) view.findViewById(R.id.leijiyaoqing);
		// 本月年化总额
		nianhua = (TextView) view.findViewById(R.id.nianhua);
		// 列表
		details_list = (ListView) view.findViewById(R.id.details_list);
		// 选项监听
		details_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterx, View v,
					int position, long arg3) {
				// 跳转
				Intent intent = new Intent(getActivity(), TheYear.class);
				intent.putExtra("id", tenderdetails.get(position).getId());
				startActivity(intent);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		initData();
	}

	// 投标详情
	public void initData() {
		show();
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));

		pushData.httpClientSendWithToken(params, Constant.WEL,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							Log.e("投标详情", jo.toString());
							leijiyaoqing.setText(jo.getJSONObject("data")
									.getString("count"));
							nianhua.setText(jo.getJSONObject("data").getString(
									"num_nh_all"));
							tenderdetails = new Gson()
									.fromJson(
											jo.getJSONObject("data").getString(
													"list"),
											new TypeToken<ArrayList<TenderDetailsBean>>() {
											}.getType());
							adapter = new TenderDetailsAdapter(tenderdetails);
							details_list.setAdapter(adapter);
						}
					}
				});
	}
}
