package com.example.jinglinzichan.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.jinglinzichan.BaseFragment;
import com.example.jinglinzichan.R;
import com.example.jinglinzichan.utils.Constant;
import com.example.jinglinzichan.utils.PushData;
import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//邀请码
@SuppressLint("InflateParams")
public class InvitationCodeFragment extends BaseFragment {

	private ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageView qrcode_url;
	private TextView activation_code;

	@Override
	public int getContentId() {
		return R.layout.invitationcode;
	}

	@Override
	public void init(View view) {
		// 二维码
		qrcode_url = (ImageView) view.findViewById(R.id.qrcode_url);
		// 邀请码
		activation_code = (TextView) view.findViewById(R.id.activation_code);
		initImageLoader();
	}

	@Override
	public void onResume() {
		super.onResume();
		initData();
	}

	// 个人资料的列表
	public void initData() {
		PushData pushData = PushData.getInstance();
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_id",
				mySharedPreferences.getString("user_id", ""));

		pushData.httpClientSendWithToken(params, Constant.MY_ACCOUNT,
				new BusinessResponse() {

					@Override
					public void OnMessageResponse(String url, JSONObject jo)
							throws JSONException {
						if (jo == null) {
							showMessage("连接网络失败，请检查网络");
						} else {
							Log.e("个人资料", jo.toString());
							activation_code.setText(jo.getJSONObject(
									"true_user").getString("activation_code"));
							imageLoader.displayImage(
									jo.getString("qrcode_url"), qrcode_url);
						}
					}
				});
	}

	// 图片加载
	private void initImageLoader() {
		@SuppressWarnings("deprecation")
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				getActivity()).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache());

		ImageLoaderConfiguration config = builder.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}
}
