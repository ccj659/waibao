package com.example.jinglinzichan.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseFragment;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.activity.AboutUs;
import com.example.jinglinzichan.activity.HelpCenter;
import com.example.jinglinzichan.activity.SafetyGuarantee;
import com.example.jinglinzichan.activity.XiangQing;
import com.example.jinglinzichan.adapter.HomePageAdapter;
import com.example.jinglinzichan.bean.HomepageBean;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.example.jinglinzichan.view.DynamicListView;
import com.example.jinglinzichan.view.SlideShowView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

//首页
@SuppressLint("InflateParams")
public class HomePageFragment extends BaseFragment {

	private SlideShowView homepage_slideshow;
	private DynamicListView homepage_list;
	private String qubie = "0";
	private List<HomepageBean> homepage;
	private HomePageAdapter adapter;
	private LinearLayout guanyuwomen, anquanbaozhang, bangzhu;

	@Override
	public int getContentId() {
		return R.layout.homepage;
	}

	@Override
	public void init(View view) {
		// 首页轮播图
		homepage_slideshow = (SlideShowView) view
				.findViewById(R.id.homepage_slideshow);
		// 关于我们
		guanyuwomen = (LinearLayout) view.findViewById(R.id.guanyuwomen);
		guanyuwomen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AboutUs.class));
			}
		});
		// 999安全保障
		anquanbaozhang = (LinearLayout) view.findViewById(R.id.anquanbaozhang);
		anquanbaozhang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), SafetyGuarantee.class));
			}
		});
		// 帮助
		bangzhu = (LinearLayout) view.findViewById(R.id.bangzhu);
		bangzhu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), HelpCenter.class));
			}
		});
		// 列表
		homepage_list = (DynamicListView) view.findViewById(R.id.homepage_list);
		// 选项监听
		homepage_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterx, View v,
					int position, long arg3) {
				// 跳转
				Intent intent = new Intent(getActivity(), XiangQing.class);
				intent.putExtra("id", homepage.get(position).getId());
				intent.putExtra("load_id", "");
				startActivity(intent);
			}
		});

		index_deal();
	}

	// 首页的列表
	public void index_deal() {
		show();
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		pushData.httpClientSendWithToken(params, Constant.INDEX_DEAL,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						hide();
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							Log.e("首页的列表", jo.toString());
							homepage = new Gson().fromJson(
									jo.getString("data"),
									new TypeToken<ArrayList<HomepageBean>>() {
									}.getType());
							adapter = new HomePageAdapter(homepage, qubie);
							homepage_list.setAdapter(adapter);
						}
					}
				});
	}

	@Override
	public void onResume() {
		// 开启轮播
		homepage_slideshow.startPlay();
		super.onResume();
	}

	@Override
	public void onPause() {
		// 暂停轮播
		homepage_slideshow.stopPlay();
		super.onPause();
	}
}
