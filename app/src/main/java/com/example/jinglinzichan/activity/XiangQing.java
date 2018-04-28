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

//����
@SuppressLint("HandlerLeak")
public class XiangQing extends BaseActivity implements OnClickListener,
		BusinessResponse {

	private LinearLayout fanhuijain_xiangqing;
	private RoundProgressBar sink;
	private TextView mashangtoubiao_xiangqing;
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
		// ע��ˢ���б�ɹ��㲥������
		IntentFilter filter = new IntentFilter(flag);
		XiangQing.this.registerReceiver(successReceiver, filter);
		// ���ؼ�
		fanhuijain_xiangqing = (LinearLayout) findViewById(R.id.fanhuijain_xiangqing);
		fanhuijain_xiangqing.setOnClickListener(this);
		// ����ͼƬ
		sink = (RoundProgressBar) findViewById(R.id.sink);
		// ����Ͷ��
		mashangtoubiao_xiangqing = (TextView) findViewById(R.id.mashangtoubiao_xiangqing);
		mashangtoubiao_xiangqing.setOnClickListener(this);
		// ����
		biaoti = (TextView) findViewById(R.id.biaoti);
		// ��������
		xiangqing_qixian = (TextView) findViewById(R.id.xiangqing_qixian);
		// ������
		xiangqing_jine = (TextView) findViewById(R.id.xiangqing_jine);
		// ��ͽ��
		xiangqing_zuidi = (TextView) findViewById(R.id.xiangqing_zuidi);
		// ������Ϣ
		jibenxinxi = (LinearLayout) findViewById(R.id.jibenxinxi);
		jibenxinxi.setOnClickListener(this);
		jibenxinxi_layout = (View) findViewById(R.id.jibenxinxi_layout);
		// ���տ���
		fengxiankongzhi = (LinearLayout) findViewById(R.id.fengxiankongzhi);
		fengxiankongzhi.setOnClickListener(this);
		fengxiankongzhi_layout = (View) findViewById(R.id.fengxiankongzhi_layout);
		// Ͷ���¼
		toubiaojilu = (LinearLayout) findViewById(R.id.toubiaojilu);
		toubiaojilu.setOnClickListener(this);
		toubiaojilu_layout = (View) findViewById(R.id.toubiaojilu_layout);
		// ����¼
		jiekuanxieyi = (LinearLayout) findViewById(R.id.jiekuanxieyi);
		jiekuanxieyi.setOnClickListener(this);
		jiekuanxieyi_layout = (View) findViewById(R.id.jiekuanxieyi_layout);
		// ������Ϣ
		daikuanxinxi_xiangqing = (LinearLayout) findViewById(R.id.daikuanxinxi_xiangqing);
		// ����
		xiangqing_mingcheng = (TextView) findViewById(R.id.xiangqing_mingcheng);
		// �����
		xiangqing_bianhao = (TextView) findViewById(R.id.xiangqing_bianhao);
		// �����
		xiangqing_kuan = (TextView) findViewById(R.id.xiangqing_kuan);
		// ���������
		xiangqing_lilv = (TextView) findViewById(R.id.xiangqing_lilv);
		// �������
		xiangqing_qi = (TextView) findViewById(R.id.xiangqing_qi);
		// ���Ͷ����
		xiangqing_toubiao = (TextView) findViewById(R.id.xiangqing_toubiao);
		// ���ʽ
		xiangqing_fangshi = (TextView) findViewById(R.id.xiangqing_fangshi);
		// �����;
		xiangqing_yongtu = (TextView) findViewById(R.id.xiangqing_yongtu);
		// ״̬
		zhuangtai = (TextView) findViewById(R.id.zhuangtai);
		// ����
		xiangqing_web = (WebView) findViewById(R.id.xiangqing_web);
		// ��webview��������
		WebSettings settingswebxiaotieshi = xiangqing_web.getSettings();
		// �������������
		settingswebxiaotieshi.setBuiltInZoomControls(true);
		// ������ҳĬ�����ֱ���
		settingswebxiaotieshi.setDefaultTextEncodingName("UTF-8");
		// �Ƿ���Ի�ȡ����
		xiangqing_web.requestFocus();
		// �����Զ���WebViewClient,ʵ�����ع���
		xiangqing_web.setWebViewClient(new MyWebViewClient());
		// ����
		xiangqing_web_kongzhi = (WebView) findViewById(R.id.xiangqing_web_kongzhi);
		// ��webview��������
		WebSettings settingswebxiaotieshix = xiangqing_web_kongzhi
				.getSettings();
		// �������������
		settingswebxiaotieshix.setBuiltInZoomControls(true);
		// ������ҳĬ�����ֱ���
		settingswebxiaotieshix.setDefaultTextEncodingName("UTF-8");
		// �Ƿ���Ի�ȡ����
		xiangqing_web_kongzhi.requestFocus();
		// �����Զ���WebViewClient,ʵ�����ع���
		xiangqing_web_kongzhi.setWebViewClient(new MyWebViewClient());
		// ����
		xiangqing_web_xieyi = (WebView) findViewById(R.id.xiangqing_web_xieyi);
		// ��webview��������
		WebSettings settingswebxiaotieshiy = xiangqing_web_xieyi.getSettings();
		// �������������
		settingswebxiaotieshiy.setBuiltInZoomControls(true);
		// ������ҳĬ�����ֱ���
		settingswebxiaotieshiy.setDefaultTextEncodingName("UTF-8");
		// �Ƿ���Ի�ȡ����
		xiangqing_web_xieyi.requestFocus();
		// �����Զ���WebViewClient,ʵ�����ع���
		xiangqing_web_xieyi.setWebViewClient(new MyWebViewClient());
		// �б�
		xiangqing_list = (DynamicListView) findViewById(R.id.xiangqing_list);

		// ��ʾ
		daikuanxinxi_xiangqing.setVisibility(View.VISIBLE);
		xiangqing_web.setVisibility(View.VISIBLE);
		jibenxinxi_layout.setVisibility(View.VISIBLE);
		// ����
		xiangqing_web_kongzhi.setVisibility(View.GONE);
		xiangqing_list.setVisibility(View.GONE);
		fengxiankongzhi_layout.setVisibility(View.GONE);
		toubiaojilu_layout.setVisibility(View.GONE);
		jiekuanxieyi_layout.setVisibility(View.GONE);
		xiangqing_web_xieyi.setVisibility(View.GONE);

		// ��ȡ�û���������
		mySharedPreferences = this.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		user_id = mySharedPreferences.getString("user_id", "");

		// ��������
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

			} else if (deal_status.equals("5")) {

			} else if (deal_status.equals("4")) {

			} else if (deal_status.equals("1")
					&& Integer.valueOf(remain_time).intValue() > 0) {
				// ��ת
				Intent intent = new Intent(this, ConfirmTender.class);
				intent.putExtra("id", id);
				startActivity(intent);

			} else if (deal_status.equals("2")) {

			} else if (deal_status.equals("3")) {

			} else if (deal_status.equals("1")
					&& Integer.valueOf(remain_time).intValue() <= 0) {

			}
			break;
		case R.id.jibenxinxi:
			// ��ʾ
			daikuanxinxi_xiangqing.setVisibility(View.VISIBLE);
			xiangqing_web.setVisibility(View.VISIBLE);
			jibenxinxi_layout.setVisibility(View.VISIBLE);
			// ����
			xiangqing_web_kongzhi.setVisibility(View.GONE);
			xiangqing_list.setVisibility(View.GONE);
			fengxiankongzhi_layout.setVisibility(View.GONE);
			toubiaojilu_layout.setVisibility(View.GONE);
			jiekuanxieyi_layout.setVisibility(View.GONE);
			xiangqing_web_xieyi.setVisibility(View.GONE);
			break;
		case R.id.fengxiankongzhi:
			// ��ʾ
			daikuanxinxi_xiangqing.setVisibility(View.GONE);
			xiangqing_web.setVisibility(View.GONE);
			fengxiankongzhi_layout.setVisibility(View.VISIBLE);
			// ����
			xiangqing_web_kongzhi.setVisibility(View.VISIBLE);
			xiangqing_list.setVisibility(View.GONE);
			jibenxinxi_layout.setVisibility(View.GONE);
			toubiaojilu_layout.setVisibility(View.GONE);
			jiekuanxieyi_layout.setVisibility(View.GONE);
			xiangqing_web_xieyi.setVisibility(View.GONE);
			break;
		case R.id.toubiaojilu:
			// ��ʾ
			daikuanxinxi_xiangqing.setVisibility(View.GONE);
			xiangqing_web.setVisibility(View.GONE);
			toubiaojilu_layout.setVisibility(View.VISIBLE);
			// ����
			xiangqing_web_kongzhi.setVisibility(View.GONE);
			xiangqing_list.setVisibility(View.VISIBLE);
			jibenxinxi_layout.setVisibility(View.GONE);
			fengxiankongzhi_layout.setVisibility(View.GONE);
			jiekuanxieyi_layout.setVisibility(View.GONE);
			xiangqing_web_xieyi.setVisibility(View.GONE);
			break;
		case R.id.jiekuanxieyi:
			// ��ʾ
			jiekuanxieyi_layout.setVisibility(View.VISIBLE);
			xiangqing_web_xieyi.setVisibility(View.VISIBLE);
			// ����
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
	 * ��дWebViewClient,�Զ���webview�ڵ��¼����ع���
	 */
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.indexOf("tel:") < 0) {// ҳ���������ֻᵼ�����ӵ绰
				view.loadUrl(url);
			}
			return true;
		}
	}

	// ����
	public void isopen() {
		dialogLoading.show(); // ��ʾ�����жԻ���
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("id", id);
		if (!load_id.equals("")) {
			params.addBodyParameter("load_id", load_id);
		}

		pushData.httpClientSendWithToken(params, Constant.DEAL_ARTICLE, this);
	}

	// ��ע
	public void guanzhu() {
		dialogLoading.show(); // ��ʾ�����жԻ���
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("id", id);

		pushData.httpClientSendWithToken(params, Constant.COLLECT, this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo)
			throws JSONException {
		dialogLoading.dismiss(); // ���ؼ����жԻ���
		if (jo == null) {
			Toast.makeText(this, "��������ʧ�ܣ���������", Toast.LENGTH_SHORT).show();
		} else {
			if (url.equals(Constant.COLLECT)) {
				Log.e("��ע", jo.toString());
				String msg = jo.getString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setAction(flag);
				sendBroadcast(intent);
			} else if (url.equals(Constant.DEAL_ARTICLE)) {
				Log.e("����", jo.toString());
				if (jo.getString("status").equals("0")) {
					showMessage("��û��Ͷ���ñ꣬���ܲ鿴�˱�����");
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

					// ����
					xiangqing_mingcheng.setText("���⣺" + name);
					// �����
					xiangqing_bianhao.setText("����ţ�" + deal_sn);
					// �����
					xiangqing_kuan.setText("����" + borrow_amount_format);
					// ���������
					xiangqing_lilv.setText("��������ʣ�" + rate + "%");
					// �������
					xiangqing_qi.setText("Ͷ�����ޣ�" + repay_time + "����");
					// ���Ͷ����
					xiangqing_toubiao.setText("���Ͷ���" + min_loan_money + "Ԫ");
					// ���ʽ
					xiangqing_fangshi.setText("���ʽ��" + loantype_format);
					// �����;
					xiangqing_yongtu.setText("�����;��" + loantype_name);

					if (deal_status.equals("0")) {
						// ״̬
						zhuangtai.setText("״̬��������");

					} else if (deal_status.equals("5")) {
						// ״̬
						zhuangtai.setText("״̬���ѻ���");

					} else if (deal_status.equals("4")) {
						// ״̬
						zhuangtai.setText("״̬��������");

					} else if (deal_status.equals("1")
							&& Integer.valueOf(remain_time).intValue() > 0) {
						// ״̬
						zhuangtai.setText("״̬���ѿ�ʼ");

					} else if (deal_status.equals("2")) {
						// ״̬
						zhuangtai.setText("״̬��������");

					} else if (deal_status.equals("3")) {
						// ״̬
						zhuangtai.setText("״̬��������");
					} else if (deal_status.equals("1")
							&& Integer.valueOf(remain_time).intValue() <= 0) {
						// ״̬
						zhuangtai.setText("״̬���ѹ���");
					}

					if (loan_agreement.equals("")) {
						jiekuanxieyi.setVisibility(View.GONE);
						xiangqing_web_xieyi.setVisibility(View.GONE);
					} else {
						// ��������
						String tou = "<html> <head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"> <style type=\"text/css\">div{width:200px;height:200px; background-color:#DDDDDD;}</style> </head> <body>";
						String wei = "</body></html>";
						String zong = tou + loan_agreement + wei;
						xiangqing_web_xieyi.loadDataWithBaseURL(null, zong,
								"text/html", "utf-8", null);
					}

					String cnt = deal.getString("cnt");
					// ��������
					String tou = "<html> <head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"> <style type=\"text/css\">div{width:200px;height:200px; background-color:#DDDDDD;}</style> </head> <body>";
					String wei = "</body></html>";
					String zong = tou + cnt + wei;
					xiangqing_web.loadDataWithBaseURL(null, zong, "text/html",
							"utf-8", null);

					String risk_cnt = deal.getString("risk_cnt");
					// ��������
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
						// һ�����ŵ��ֵ�
						map.put("user_name", user_name);
						map.put("money", money);
						map.put("add_time", add_time);
						data.add(map);// �����б�
					}
					adapter = new XiangQingAdapter(this, data);
					xiangqing_list.setAdapter(adapter);
				}
			}
		}
	}

	/** ˢ���б�㲥������ */
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
