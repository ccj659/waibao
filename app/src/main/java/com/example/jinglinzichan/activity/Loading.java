package com.example.jinglinzichan.activity;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.MainActivity;
import com.example.jinglinzichan.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

// 启动页面
@SuppressLint("HandlerLeak")
public class Loading extends BaseActivity {

	int i = 2;// 倒计时的整个时间数
	private Handler mHandler = new Handler();// 全局handler

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		new Thread(new ClassCut()).start();
	}

	class ClassCut implements Runnable {// 倒计时逻辑子线程
		@Override
		public void run() {
			while (i > 0) {// 整个倒计时执行的循环
				i--;
				try {
					Thread.sleep(1000);// 线程休眠一秒钟 这个就是倒计时的间隔时间
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 下面是倒计时结束逻辑
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					// 跳转到主界面
					startActivity(new Intent(Loading.this, MainActivity.class));
					finish();
				}
			});
			i = 1;// 修改倒计时剩余时间变量为60秒
		}
	}
}
