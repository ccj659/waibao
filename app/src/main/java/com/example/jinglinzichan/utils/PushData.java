package com.example.jinglinzichan.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.lidroid.xutils.BusinessResponse;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class PushData {

	private static PushData pushData;
	private static HttpUtils httpUtils;

	public static PushData getInstance() {
		if (pushData == null)
			pushData = new PushData();
		return pushData;
	}

	public PushData() {
		httpUtils = HttpUtils.getInstance();
	}

	public void httpClientSendWithToken(RequestParams params, final String url,
			final BusinessResponse response) {
		httpClientSend(response, params, url);
	}

	public void httpClientSend(final BusinessResponse response,
			RequestParams params, final String url) {
		httpUtils.send(HttpRequest.HttpMethod.POST, Constant.SERVER_URL + url,
				params, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						try {
							Log.e("fail", "failing");
							response.OnMessageResponse(url, null);
						} catch (JSONException e) {
							Log.e("error", e.toString());
						}
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						JSONObject jo;
						Log.e("success", "successing");
						try {
							jo = new JSONObject(arg0.result);
							response.OnMessageResponse(url, jo);
						} catch (Exception e) {
							Log.e("error", e.toString());
						}
					}
				});
	}

	public void httpClientSendWithToken1(RequestParams params,
			final String url, final BusinessResponse response) {
		httpClientSend1(response, params, url);
	}

	public void httpClientSend1(final BusinessResponse response,
			RequestParams params, final String url) {
		httpUtils.send(HttpRequest.HttpMethod.POST, Constant.SERVER_URL1 + url,
				params, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						try {
							Log.e("fail", "failing");
							response.OnMessageResponse(url, null);
						} catch (JSONException e) {
							Log.e("error", e.toString());
						}
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						JSONObject jo;
						Log.e("success", "successing");
						try {
							jo = new JSONObject(arg0.result);
							response.OnMessageResponse(url, jo);
						} catch (Exception e) {
							Log.e("error", e.toString());
						}
					}
				});
	}
}