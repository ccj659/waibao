package com.example.jinglinzichan.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jinglinzichan.BaseFragment;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.activity.FundDetails;
import com.example.jinglinzichan.activity.Login;
import com.example.jinglinzichan.activity.ModifyLoginPassword;
import com.example.jinglinzichan.activity.MyBankCard;
import com.example.jinglinzichan.activity.MyInvitation;
import com.example.jinglinzichan.activity.Notice;
import com.example.jinglinzichan.activity.OptionBank;
import com.example.jinglinzichan.activity.Recharge;
import com.example.jinglinzichan.activity.TouBiaoJiLu;
import com.example.jinglinzichan.activity.ZhangHuXinXi1;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

//个人中心
@SuppressLint("InflateParams")
public class PersonalCenterFragment extends BaseFragment {

	private LinearLayout wodeguanzhu, toubiaojilu, zhanghuxinxi, zijinrizhi,
			mimaguanli, wodeyinhangka, chongzhi, chongzhi_, tixian,
			openanaccount, wodeyaoqing;
	private TextView zongzichan, keyongjine, tuichu, daishouyi;
	private TextView keyong;
	private TextView dongjiezijin;

	@Override
	public int getContentId() {
		return R.layout.personacenter;
	}

	@Override
	public void init(View view) {
		// 总资产
		zongzichan = (TextView) view.findViewById(R.id.zongzichan);
		// 可用余额
		keyong = (TextView) view.findViewById(R.id.keyong);
		// 代收资产
		keyongjine = (TextView) view.findViewById(R.id.keyongjine);
		// 待收益
		daishouyi = (TextView) view.findViewById(R.id.daishouyi);
		// 冻结资金
		dongjiezijin = (TextView) view.findViewById(R.id.dongjiezijin);
		// 退出
		tuichu = (TextView) view.findViewById(R.id.tuichu);
		tuichu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tuichu();
			}
		});
		// 充值
		chongzhi_ = (LinearLayout) view.findViewById(R.id.chongzhi_);
		chongzhi_.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), Recharge.class));
			}
		});
		chongzhi = (LinearLayout) view.findViewById(R.id.chongzhi);
		chongzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), Recharge.class));
			}
		});
		// 提现
		tixian = (LinearLayout) view.findViewById(R.id.tixian);
		tixian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), OptionBank.class));
			}
		});
		// 我的投标
		toubiaojilu = (LinearLayout) view.findViewById(R.id.toubiaojilu);
		toubiaojilu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), TouBiaoJiLu.class));
			}
		});
		// 我的银行卡
		wodeyinhangka = (LinearLayout) view.findViewById(R.id.wodeyinhangka);
		wodeyinhangka.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), MyBankCard.class));
			}
		});
		// 资金明细
		zijinrizhi = (LinearLayout) view.findViewById(R.id.zijinrizhi);
		zijinrizhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), FundDetails.class));
			}
		});
		// 账户信息
		zhanghuxinxi = (LinearLayout) view.findViewById(R.id.zhanghuxinxi);
		zhanghuxinxi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), ZhangHuXinXi1.class));
			}
		});
		// 平台公告
		wodeguanzhu = (LinearLayout) view.findViewById(R.id.wodeguanzhu);
		wodeguanzhu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), Notice.class));
			}
		});
		// 密码管理
		mimaguanli = (LinearLayout) view.findViewById(R.id.mimaguanli);
		mimaguanli.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),
						ModifyLoginPassword.class));
			}
		});

		// 我的邀请
		wodeyaoqing = (LinearLayout) view.findViewById(R.id.wodeyaoqing);
		wodeyaoqing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), MyInvitation.class));
			}
		});

		if (mySharedPreferences.getString("user_id", "").equals("")) {
			startActivity(new Intent(getActivity(), Login.class));
		} else {
			isopen();
		}
	}

	// 个人中心
	public void isopen() {
		show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));

		pushData.httpClientSendWithToken(params, Constant.USER_CENTER,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							hide(); // 隐藏加载中对话框
							Log.e("个人中心", jo.toString());
							zongzichan.setText(jo.getString("user_count_money"));
							keyongjine.setText(jo
									.getString("load_wait_repay_money"));
							daishouyi.setText("累计收益"
									+ jo.getString("load_earnings") + "元，待收益 "
									+ jo.getString("load_wait_earnings") + "元");
							keyong.setText(jo.getString("money_encrypt"));
							dongjiezijin.setText(jo
									.getString("user_frozen_sum"));
						}
					}
				});
	}

	public void tuichu() {
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.remove("user_id");
		editor.commit();
		startActivity(new Intent(getActivity(), Login.class));
	}
}
