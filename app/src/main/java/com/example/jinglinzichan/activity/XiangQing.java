package com.example.jinglinzichan.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.adapter.XiangQingAdapter;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.example.jinglinzichan.view.DynamicListView;
import com.example.jinglinzichan.view.HkDialogLoading;
import com.example.jinglinzichan.view.RoundProgressBar;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//详情
@SuppressLint("HandlerLeak")
public class XiangQing extends BaseActivity implements OnClickListener,
		BusinessResponse {

	private LinearLayout fanhuijain_xiangqing;
	private RoundProgressBar sink;
	private ImageView mashangtoubiao_xiangqing;
	private String id, load_id;
	private SharedPreferences mySharedPreferences;
	private String user_id;
	private TextView biaoti, xiangqing_qixian, xiangqing_jine, xiangqing_zuidi;
	private LinearLayout jibenxinxi, fengxiankongzhi, toubiaojilu,
			jiekuanxieyi, daikuanxinxi_xiangqing;
	private TextView xiangqing_mingcheng, xiangqing_bianhao, xiangqing_kuan,
			xiangqing_lilv, xiangqing_qi, xiangqing_toubiao, xiangqing_fangshi,
			xiangqing_yongtu, zhuangtai;
	private WebView xiangqing_web;
	private WebView xiangqing_web_kongzhi;
	private WebView xiangqing_web_xieyi;
	private DynamicListView xiangqing_list;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private Map<String, Object> map;
	private XiangQingAdapter adapter;
	private String deal_status, remain_time;
	private HkDialogLoading dialogLoading;
	private String flag = "cn.tianqiziben.p2p.dibu_PersonalCenter";
	private View jibenxinxi_layout;
	private View fengxiankongzhi_layout;
	private View toubiaojilu_layout;
	private View jiekuanxieyi_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiangqing);
		dialogLoading = new HkDialogLoading(this);
		// 注册刷新列表成功广播接收器
		IntentFilter filter = new IntentFilter(flag);
		XiangQing.this.registerReceiver(successReceiver, filter);
		// 返回键
		fanhuijain_xiangqing = (LinearLayout) findViewById(R.id.fanhuijain_xiangqing);
		fanhuijain_xiangqing.setOnClickListener(this);
		// 后面图片
		sink = (RoundProgressBar) findViewById(R.id.sink);
		// 马上投标
		mashangtoubiao_xiangqing = (ImageView) findViewById(R.id.mashangtoubiao_xiangqing);
		mashangtoubiao_xiangqing.setOnClickListener(this);
		// 标题
		biaoti = (TextView) findViewById(R.id.biaoti);
		// 还款期限
		xiangqing_qixian = (TextView) findViewById(R.id.xiangqing_qixian);
		// 贷款金额
		xiangqing_jine = (TextView) findViewById(R.id.xiangqing_jine);
		// 最低金额
		xiangqing_zuidi = (TextView) findViewById(R.id.xiangqing_zuidi);
		// 基本信息
		jibenxinxi = (LinearLayout) findViewById(R.id.jibenxinxi);
		jibenxinxi.setOnClickListener(this);
		jibenxinxi_layout = (View) findViewById(R.id.jibenxinxi_layout);
		// 风险控制
		fengxiankongzhi = (LinearLayout) findViewById(R.id.fengxiankongzhi);
		fengxiankongzhi.setOnClickListener(this);
		fengxiankongzhi_layout = (View) findViewById(R.id.fengxiankongzhi_layout);
		// 投标记录
		toubiaojilu = (LinearLayout) findViewById(R.id.toubiaojilu);
		toubiaojilu.setOnClickListener(this);
		toubiaojilu_layout = (View) findViewById(R.id.toubiaojilu_layout);
		// 借款记录
		jiekuanxieyi = (LinearLayout) findViewById(R.id.jiekuanxieyi);
		jiekuanxieyi.setOnClickListener(this);
		jiekuanxieyi_layout = (View) findViewById(R.id.jiekuanxieyi_layout);
		// 贷款信息
		daikuanxinxi_xiangqing = (LinearLayout) findViewById(R.id.daikuanxinxi_xiangqing);
		// 名称
		xiangqing_mingcheng = (TextView) findViewById(R.id.xiangqing_mingcheng);
		// 借款编号
		xiangqing_bianhao = (TextView) findViewById(R.id.xiangqing_bianhao);
		// 借款金额
		xiangqing_kuan = (TextView) findViewById(R.id.xiangqing_kuan);
		// 借款年利率
		xiangqing_lilv = (TextView) findViewById(R.id.xiangqing_lilv);
		// 借款期限
		xiangqing_qi = (TextView) findViewById(R.id.xiangqing_qi);
		// 最低投标金额
		xiangqing_toubiao = (TextView) findViewById(R.id.xiangqing_toubiao);
		// 还款方式
		xiangqing_fangshi = (TextView) findViewById(R.id.xiangqing_fangshi);
		// 借款用途
		xiangqing_yongtu = (TextView) findViewById(R.id.xiangqing_yongtu);
		// 状态
		zhuangtai = (TextView) findViewById(R.id.zhuangtai);
		// 内容
		xiangqing_web = (WebView) findViewById(R.id.xiangqing_web);
		// 对webview进行设置
		WebSettings settingswebxiaotieshi = xiangqing_web.getSettings();
		// 允许浏览器缩放
		settingswebxiaotieshi.setBuiltInZoomControls(true);
		// 设置网页默认文字编码
		settingswebxiaotieshi.setDefaultTextEncodingName("UTF-8");
		// 是否可以获取焦点
		xiangqing_web.requestFocus();
		// 配置自定义WebViewClient,实现拦截过滤
		xiangqing_web.setWebViewClient(new MyWebViewClient());
		// 内容
		xiangqing_web_kongzhi = (WebView) findViewById(R.id.xiangqing_web_kongzhi);
		// 对webview进行设置
		WebSettings settingswebxiaotieshix = xiangqing_web_kongzhi
				.getSettings();
		// 允许浏览器缩放
		settingswebxiaotieshix.setBuiltInZoomControls(true);
		// 设置网页默认文字编码
		settingswebxiaotieshix.setDefaultTextEncodingName("UTF-8");
		// 是否可以获取焦点
		xiangqing_web_kongzhi.requestFocus();
		// 配置自定义WebViewClient,实现拦截过滤
		xiangqing_web_kongzhi.setWebViewClient(new MyWebViewClient());
		// 内容
		xiangqing_web_xieyi = (WebView) findViewById(R.id.xiangqing_web_xieyi);
		// 对webview进行设置
		WebSettings settingswebxiaotieshiy = xiangqing_web_xieyi.getSettings();
		// 允许浏览器缩放
		settingswebxiaotieshiy.setBuiltInZoomControls(true);
		// 设置网页默认文字编码
		settingswebxiaotieshiy.setDefaultTextEncodingName("UTF-8");
		// 是否可以获取焦点
		xiangqing_web_xieyi.requestFocus();
		// 配置自定义WebViewClient,实现拦截过滤
		xiangqing_web_xieyi.setWebViewClient(new MyWebViewClient());
		// 列表
		xiangqing_list = (DynamicListView) findViewById(R.id.xiangqing_list);

		// 显示
		daikuanxinxi_xiangqing.setVisibility(View.VISIBLE);
		xiangqing_web.setVisibility(View.VISIBLE);
		jibenxinxi_layout.setVisibility(View.VISIBLE);
		// 隐藏
		xiangqing_web_kongzhi.setVisibility(View.GONE);
		xiangqing_list.setVisibility(View.GONE);
		fengxiankongzhi_layout.setVisibility(View.GONE);
		toubiaojilu_layout.setVisibility(View.GONE);
		jiekuanxieyi_layout.setVisibility(View.GONE);
		xiangqing_web_xieyi.setVisibility(View.GONE);

		// 提取用户名、密码
		mySharedPreferences = this.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		user_id = mySharedPreferences.getString("user_id", "");

		// 接收数据
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		load_id = intent.getStringExtra("load_id");

		isopen();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fanhuijain_xiangqing:
			finish();
			break;
		case R.id.mashangtoubiao_xiangqing:
			if (deal_status.equals("0")) {
				mashangtoubiao_xiangqing
						.setImageResource(R.drawable.home_invest_btn_04);
			} else if (deal_status.equals("5")) {
				mashangtoubiao_xiangqing
						.setImageResource(R.drawable.home_invest_btn_04);
			} else if (deal_status.equals("4")) {
				mashangtoubiao_xiangqing
						.setImageResource(R.drawable.home_invest_btn_04);
			} else if (deal_status.equals("1")
					&& Integer.valueOf(remain_time).intValue() > 0) {
				// 跳转
				Intent intent = new Intent(this, ConfirmTender.class);
				intent.putExtra("id", id);
				startActivity(intent);

				mashangtoubiao_xiangqing
						.setImageResource(R.drawable.home_invest_btn_04);
			} else if (deal_status.equals("2")) {
				mashangtoubiao_xiangqing
						.setImageResource(R.drawable.home_invest_btn_04);
			} else if (deal_status.equals("3")) {
				mashangtoubiao_xiangqing
						.setImageResource(R.drawable.home_invest_btn_04);
			} else if (deal_status.equals("1")
					&& Integer.valueOf(remain_time).intValue() <= 0) {
				mashangtoubiao_xiangqing
						.setImageResource(R.drawable.home_invest_btn_04);
			}
			break;
		case R.id.jibenxinxi:
			// 显示
			daikuanxinxi_xiangqing.setVisibility(View.VISIBLE);
			xiangqing_web.setVisibility(View.VISIBLE);
			jibenxinxi_layout.setVisibility(View.VISIBLE);
			// 隐藏
			xiangqing_web_kongzhi.setVisibility(View.GONE);
			xiangqing_list.setVisibility(View.GONE);
			fengxiankongzhi_layout.setVisibility(View.GONE);
			toubiaojilu_layout.setVisibility(View.GONE);
			jiekuanxieyi_layout.setVisibility(View.GONE);
			xiangqing_web_xieyi.setVisibility(View.GONE);
			break;
		case R.id.fengxiankongzhi:
			// 显示
			daikuanxinxi_xiangqing.setVisibility(View.GONE);
			xiangqing_web.setVisibility(View.GONE);
			fengxiankongzhi_layout.setVisibility(View.VISIBLE);
			// 隐藏
			xiangqing_web_kongzhi.setVisibility(View.VISIBLE);
			xiangqing_list.setVisibility(View.GONE);
			jibenxinxi_layout.setVisibility(View.GONE);
			toubiaojilu_layout.setVisibility(View.GONE);
			jiekuanxieyi_layout.setVisibility(View.GONE);
			xiangqing_web_xieyi.setVisibility(View.GONE);
			break;
		case R.id.toubiaojilu:
			// 显示
			daikuanxinxi_xiangqing.setVisibility(View.GONE);
			xiangqing_web.setVisibility(View.GONE);
			toubiaojilu_layout.setVisibility(View.VISIBLE);
			// 隐藏
			xiangqing_web_kongzhi.setVisibility(View.GONE);
			xiangqing_list.setVisibility(View.VISIBLE);
			jibenxinxi_layout.setVisibility(View.GONE);
			fengxiankongzhi_layout.setVisibility(View.GONE);
			jiekuanxieyi_layout.setVisibility(View.GONE);
			xiangqing_web_xieyi.setVisibility(View.GONE);
			break;
		case R.id.jiekuanxieyi:
			// 显示
			jiekuanxieyi_layout.setVisibility(View.VISIBLE);
			xiangqing_web_xieyi.setVisibility(View.VISIBLE);
			// 隐藏
			toubiaojilu_layout.setVisibility(View.GONE);
			daikuanxinxi_xiangqing.setVisibility(View.GONE);
			xiangqing_web.setVisibility(View.GONE);
			xiangqing_web_kongzhi.setVisibility(View.GONE);
			xiangqing_list.setVisibility(View.GONE);
			jibenxinxi_layout.setVisibility(View.GONE);
			fengxiankongzhi_layout.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	/**
	 * 重写WebViewClient,自定义webview内的事件拦截过滤
	 */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.indexOf("tel:") < 0) {// 页面上有数字会导致连接电话
				view.loadUrl(url);
			}
			return true;
		}
	}

	// 详情
	public void isopen() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("id", id);
		if (!load_id.equals("")) {
			params.addBodyParameter("load_id", load_id);
		}

		pushData.httpClientSendWithToken(params, Constant.DEAL_ARTICLE, this);
	}

	// 关注
	public void guanzhu() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("id", id);

		pushData.httpClientSendWithToken(params, Constant.COLLECT, this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo)
			throws JSONException {
		dialogLoading.dismiss(); // 隐藏加载中对话框
		if (jo == null) {
			Toast.makeText(this, "连接网络失败，请检查网络", Toast.LENGTH_SHORT).show();
		} else {
			if (url.equals(Constant.COLLECT)) {
				Log.e("关注", jo.toString());
				String msg = jo.getString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setAction(flag);
				sendBroadcast(intent);
			} else if (url.equals(Constant.DEAL_ARTICLE)) {
				Log.e("详情", jo.toString());
				if (jo.getString("status").equals("0")) {
					showMessage("您没有投过该标，不能查看此标详情");
					finish();
				} else {
					JSONObject deal = jo.getJSONObject("deal");
					id = deal.getString("id");
					String name = deal.getString("name");
					String deal_sn = deal.getString("deal_sn");
					String repay_time = deal.getString("repay_time");
					String min_loan_money = deal.getString("min_loan_money");
					String borrow_amount_format = deal
							.getString("borrow_amount_format");
					String rate = deal.getString("rate");
					String loantype_format = deal.getString("loantype_format");
					String loantype_name = deal.getString("loantype_name");
					deal_status = deal.getString("deal_status");
					remain_time = deal.getString("remain_time");
					String loan_agreement = jo.getString("loan_agreement");
					final int progress_point = deal.getInt("progress_point");
					new Thread(new Runnable() {

						@Override
						public void run() {
							while (progress_point <= 100) {
								sink.setProgress(progress_point);
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}

						}
					}).start();

					biaoti.setText(name);
					xiangqing_qixian.setText(repay_time);
					xiangqing_jine.setText(rate);
					xiangqing_zuidi.setText(borrow_amount_format);

					// 名称
					xiangqing_mingcheng.setText("标题：" + name);
					// 借款编号
					xiangqing_bianhao.setText("借款编号：" + deal_sn);
					// 借款金额
					xiangqing_kuan.setText("借款金额：" + borrow_amount_format);
					// 借款年利率
					xiangqing_lilv.setText("借款年利率：" + rate + "%");
					// 借款期限
					xiangqing_qi.setText("投资期限：" + repay_time + "个月");
					// 最低投标金额
					xiangqing_toubiao.setText("最低投标金额：" + min_loan_money + "元");
					// 还款方式
					xiangqing_fangshi.setText("还款方式：" + loantype_format);
					// 借款用途
					xiangqing_yongtu.setText("借款用途：" + loantype_name);

					if (deal_status.equals("0")) {
						// 状态
						zhuangtai.setText("状态：申请中");
						mashangtoubiao_xiangqing
								.setImageResource(R.drawable.home_invest_btn_04);
					} else if (deal_status.equals("5")) {
						// 状态
						zhuangtai.setText("状态：已还清");
						mashangtoubiao_xiangqing
								.setImageResource(R.drawable.home_invest_btn_04);
					} else if (deal_status.equals("4")) {
						// 状态
						zhuangtai.setText("状态：还款中");
						mashangtoubiao_xiangqing
								.setImageResource(R.drawable.home_invest_btn_04);
					} else if (deal_status.equals("1")
							&& Integer.valueOf(remain_time).intValue() > 0) {
						// 状态
						zhuangtai.setText("状态：已开始");
						mashangtoubiao_xiangqing
								.setImageResource(R.drawable.home_invest_btn_04);
					} else if (deal_status.equals("2")) {
						// 状态
						zhuangtai.setText("状态：已满标");
						mashangtoubiao_xiangqing
								.setImageResource(R.drawable.home_invest_btn_04);
					} else if (deal_status.equals("3")) {
						// 状态
						zhuangtai.setText("状态：已流标");
						mashangtoubiao_xiangqing
								.setImageResource(R.drawable.home_invest_btn_04);
					} else if (deal_status.equals("1")
							&& Integer.valueOf(remain_time).intValue() <= 0) {
						// 状态
						zhuangtai.setText("状态：已过期");
						mashangtoubiao_xiangqing
								.setImageResource(R.drawable.home_invest_btn_04);
					}

					if (loan_agreement.equals("")) {
						jiekuanxieyi.setVisibility(View.GONE);
						xiangqing_web_xieyi.setVisibility(View.GONE);
					} else {
						// 访问数据
						String tou = "<html> <head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"> <style type=\"text/css\">div{width:200px;height:200px; background-color:#DDDDDD;}</style> </head> <body>";
						String wei = "</body></html>";
						String zong = tou + loan_agreement + wei;
						xiangqing_web_xieyi.loadDataWithBaseURL(null, zong,
								"text/html", "utf-8", null);
					}

					String cnt = deal.getString("cnt");
					// 访问数据
					String tou = "<html> <head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"> <style type=\"text/css\">div{width:200px;height:200px; background-color:#DDDDDD;}</style> </head> <body>";
					String wei = "</body></html>";
					String zong = tou + cnt + wei;
					xiangqing_web.loadDataWithBaseURL(null, zong, "text/html",
							"utf-8", null);

					String risk_cnt = deal.getString("risk_cnt");
					// 访问数据
					String toux = "<html> <head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"> <style type=\"text/css\">div{width:200px;height:200px; background-color:#DDDDDD;}</style> </head> <body>";
					String weix = "</body></html>";
					String zongx = toux + risk_cnt + weix;
					xiangqing_web_kongzhi.loadDataWithBaseURL(null, zongx,
							"text/html", "utf-8", null);

					JSONArray jsonArray = jo.getJSONArray("load_list");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String user_name = jsonObject.getString("user_name");
						String money = jsonObject.getString("money");
						String add_time = jsonObject.getString("add_time");

						map = new HashMap<String, Object>();
						// 一条新闻的字典
						map.put("user_name", user_name);
						map.put("money", money);
						map.put("add_time", add_time);
						data.add(map);// 放入列表
					}
					adapter = new XiangQingAdapter(this, data);
					xiangqing_list.setAdapter(adapter);
				}
			}
		}
	}

	/** 刷新列表广播接收器 */
	private BroadcastReceiver successReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			data.clear();
			isopen();
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		XiangQing.this.unregisterReceiver(successReceiver);
	}
}
