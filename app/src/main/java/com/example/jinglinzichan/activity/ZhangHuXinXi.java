package com.example.jinglinzichan.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinglinzichan.R;
import com.example.jinglinzichan.adapter.ZhangHuXinXiAdapter;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.example.jinglinzichan.view.HkDialogLoading;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//账户信息
@SuppressLint("InflateParams")
public class ZhangHuXinXi extends Activity implements OnClickListener,
		BusinessResponse {

	private LinearLayout return_key;
	private TextView title;
	private EditText email, juzhudizhi;
	private TextView nicheng;
	private TextView zhenshixingming, shenfenzhenghao, shoujihao;
	private SharedPreferences mySharedPreferences;
	private String user_id;
	private RadioGroup RadioGroup;
	private RadioButton rb_nan, rb_nv;
	private String sex, nichengs, emails, juzhudizhis;
	private LinearLayout sheng_Layout;
	private TextView sheng_text;
	private LinearLayout shi_Layout;
	private TextView shi_text;
	private String x;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> dataa = new ArrayList<Map<String, Object>>();
	private Map<String, Object> map, mapa;
	private String id, ids;
	private ZhangHuXinXiAdapter adaptera, adapterb;
	private ListView shi_lv;
	private HkDialogLoading dialogLoading;
	private EditText yaoqingma;
	private String sheng_id = "";
	private String shi_id = "";
	private ImageView tupian_yaoqingma;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private LinearLayout sousuo_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zhanghuxinxi);
		dialogLoading = new HkDialogLoading(this);
		// 返回键
		return_key = (LinearLayout) findViewById(R.id.return_key);
		return_key.setOnClickListener(this);
		// 标题字体
		title = (TextView) findViewById(R.id.title);
		title.setText("账户信息");
		// 确认
		sousuo_layout = (LinearLayout) findViewById(R.id.sousuo_layout);
		sousuo_layout.setVisibility(View.VISIBLE);
		sousuo_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nichengs = nicheng.getText().toString();
				emails = email.getText().toString();
				juzhudizhis = juzhudizhi.getText().toString();
				baocunxinxi();
			}
		});
		// 昵称
		nicheng = (TextView) findViewById(R.id.nicheng);
		// Email
		email = (EditText) findViewById(R.id.email);
		// 真实姓名
		zhenshixingming = (TextView) findViewById(R.id.zhenshixingming);
		// 身份证号
		shenfenzhenghao = (TextView) findViewById(R.id.shenfenzhenghao);
		// 手机号码
		shoujihao = (TextView) findViewById(R.id.shoujihao);
		// 性 别
		RadioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
		// 给RadioGroup设置事件监听
		RadioGroup
				.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == rb_nan.getId()) {
							sex = "0";
							Log.e("sex", sex);
						} else if (checkedId == rb_nv.getId()) {
							sex = "1";
							Log.e("sexx", sex);
						}
					}
				});
		// 男
		rb_nan = (RadioButton) findViewById(R.id.rb_nan);
		// 女
		rb_nv = (RadioButton) findViewById(R.id.rb_nv);
		// 所在省
		sheng_Layout = (LinearLayout) findViewById(R.id.sheng_Layout);
		sheng_Layout.setOnClickListener(this);
		sheng_text = (TextView) findViewById(R.id.sheng_text);
		// 所在市
		shi_Layout = (LinearLayout) findViewById(R.id.shi_Layout);
		shi_Layout.setOnClickListener(this);
		shi_text = (TextView) findViewById(R.id.shi_text);
		// 居住地址
		juzhudizhi = (EditText) findViewById(R.id.juzhudizhi);
		// 邀请码
		yaoqingma = (EditText) findViewById(R.id.yaoqingma);
		yaoqingma.setFocusable(false);
		tupian_yaoqingma = (ImageView) findViewById(R.id.tupian_yaoqingma);

		// 提取用户名、密码
		mySharedPreferences = this.getSharedPreferences("user",
				Activity.MODE_PRIVATE);
		user_id = mySharedPreferences.getString("user_id", "");

		initImageLoader();
		isopen();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return_key:
			finish();
			break;
		case R.id.sheng_Layout:
			x = "1";
			showPopupWindow(v);
			break;
		case R.id.shi_Layout:
			x = "2";
			showPopupWindow(v);
			break;
		default:
			break;
		}
	}

	// 个人信息
	public void isopen() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);

		pushData.httpClientSendWithToken(params, Constant.MY_ACCOUNT, this);
	}

	// 获取市
	private void huoqushi() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("id", id);

		pushData.httpClientSendWithToken(params, Constant.AJAXCITY, this);
	}

	// 保存信息
	public void baocunxinxi() {
		dialogLoading.show(); // 显示加载中对话框
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id", user_id);
		params.addBodyParameter("province", sheng_id);
		params.addBodyParameter("city", shi_id);
		params.addBodyParameter("sex", sex);
		params.addBodyParameter("user_name", nichengs);
		params.addBodyParameter("email", emails);
		params.addBodyParameter("address", juzhudizhis);

		pushData.httpClientSendWithToken(params, Constant.ONMY_ACCOUNT, this);
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo)
			throws JSONException {
		dialogLoading.dismiss(); // 隐藏加载中对话框
		if (jo == null) {
			Toast.makeText(this, "连接网络失败，请检查网络", Toast.LENGTH_SHORT).show();
		} else {
			if (url.equals(Constant.MY_ACCOUNT)) {
				Log.e("个人信息", jo.toString());
				JSONObject true_user = jo.getJSONObject("true_user");
				String user_name = true_user.getString("user_name");
				String user_name_true = true_user.getString("user_name_true");
				String mobile = true_user.getString("mobile");
				String emailx = true_user.getString("email");
				String idno = true_user.getString("idno");
				sex = true_user.getString("sex");
				String province = true_user.getString("province");
				String city = true_user.getString("city");
				String address = true_user.getString("address");
				String activation_code = true_user.getString("activation_code");
				String qrcode_url = jo.getString("qrcode_url");

				nicheng.setText(user_name);
				email.setText(emailx);
				zhenshixingming.setText(user_name_true);
				shenfenzhenghao.setText(idno);
				shoujihao.setText(mobile);
				juzhudizhi.setText(address);
				yaoqingma.setText(activation_code);

				imageLoader.displayImage(qrcode_url, tupian_yaoqingma);

				if (sex.equals("0")) {
					RadioGroup.check(R.id.rb_nan);
				} else if (sex.equals("1")) {
					RadioGroup.check(R.id.rb_nv);
				}

				JSONArray sheng = jo.getJSONArray("province_list");
				for (int i = 0; i < sheng.length(); i++) {
					JSONObject ob = sheng.getJSONObject(i);
					id = ob.getString("id");
					String name = ob.getString("name");

					if (province.equals(id)) {
						sheng_text.setText(name);
						sheng_id = province;
						shi_id = city;
					}

					map = new HashMap<String, Object>();
					// 一条新闻的字典
					map.put("id", id);
					map.put("name", name);
					data.add(map);// 放入列表
				}

				JSONArray shi = jo.getJSONArray("city_list");
				for (int i = 0; i < shi.length(); i++) {
					JSONObject oc = shi.getJSONObject(i);
					ids = oc.getString("id");
					String names = oc.getString("name");

					if (city.equals(ids)) {
						shi_text.setText(names);
					}
				}

			} else if (url.equals(Constant.AJAXCITY)) {
				Log.e("获取市", jo.toString());
				JSONArray shi = jo.getJSONArray("data");
				for (int i = 0; i < shi.length(); i++) {
					JSONObject oc = shi.getJSONObject(i);
					ids = oc.getString("id");
					String name = oc.getString("name");

					mapa = new HashMap<String, Object>();
					// 一条新闻的字典
					mapa.put("ids", ids);
					mapa.put("name", name);
					dataa.add(mapa);// 放入列表

					shi_text.setText(name);
					shi_id = ids;
				}
			} else if (url.equals(Constant.ONMY_ACCOUNT)) {
				Log.e("保存信息", jo.toString());
				String status = jo.getString("status");
				String msg = jo.getString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				if (status.equals("1")) {
					huiyuanzhongxin();
					finish();
				}
			}
		}
	}

	public void showPopupWindow(View parent) {
		// 加载布局
		LinearLayout view = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.xuanxiang_list, null);
		// 找到布局的控件
		LinearLayout xxx = (LinearLayout) view.findViewById(R.id.xxx);
		xxx.setVisibility(LinearLayout.INVISIBLE);
		// 列表
		ListView sheng_lv = (ListView) view.findViewById(R.id.sheng_lv);
		adaptera = new ZhangHuXinXiAdapter(ZhangHuXinXi.this, data);
		sheng_lv.setAdapter(adaptera);

		shi_lv = (ListView) view.findViewById(R.id.shi_lv);
		adapterb = new ZhangHuXinXiAdapter(ZhangHuXinXi.this, dataa);
		shi_lv.setAdapter(adapterb);

		if (x.equals("1")) {
			sheng_lv.setVisibility(ListView.VISIBLE);
			shi_lv.setVisibility(ListView.GONE);
		} else if (x.equals("2")) {
			shi_lv.setVisibility(ListView.VISIBLE);
			sheng_lv.setVisibility(ListView.GONE);
		}

		// 实例化popupWindow
		final PopupWindow popupWindow = new PopupWindow(view,
				ViewGroup.LayoutParams.MATCH_PARENT, 500);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		// 设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
		WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		// 获取xoff
		int xpos = manager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;
		// xoff,yoff基于anchor的左下角进行偏移。
		popupWindow.showAsDropDown(parent, xpos, 0);
		// 监听
		sheng_lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				map = data.get(position);
				id = (String) map.get("id");
				String name = (String) map.get("name");
				sheng_text.setText(name);
				// 关闭popupWindow
				popupWindow.dismiss();
				dataa.clear();
				sheng_id = id;
				huoqushi();
			}
		});

		// 选项监听
		shi_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {
				mapa = dataa.get(position);
				ids = (String) mapa.get("ids");
				String name = (String) mapa.get("name");
				shi_text.setText(name);
				shi_id = ids;
				// 关闭popupWindow
				popupWindow.dismiss();
			}
		});
	}

	public void huiyuanzhongxin() {
		finish();
		Intent intent = new Intent();
		intent.setAction("com.zhongtoulvyou");
		intent.putExtra("index", 3);
		sendBroadcast(intent);
	}

	// 图片加载
	private void initImageLoader() {
		@SuppressWarnings("deprecation")
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(defaultOptions).memoryCache(
				new WeakMemoryCache());

		ImageLoaderConfiguration config = builder.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}
}
