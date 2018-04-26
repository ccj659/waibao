package com.example.jinglinzichan.activity;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.MainActivity;
import com.example.jinglinzichan.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

// ����ҳ��
@SuppressLint("HandlerLeak")
public class Loading extends BaseActivity {

	int i = 2;// ����ʱ������ʱ����
	private Handler mHandler = new Handler();// ȫ��handler

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		new Thread(new ClassCut()).start();
	}

	class ClassCut implements Runnable {// ����ʱ�߼����߳�
		@Override
		public void run() {
			while (i > 0) {// ��������ʱִ�е�ѭ��
				i--;
				try {
					Thread.sleep(1000);// �߳�����һ���� ������ǵ���ʱ�ļ��ʱ��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// �����ǵ���ʱ�����߼�
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					// ��ת��������
					startActivity(new Intent(Loading.this, MainActivity.class));
					finish();
				}
			});
			i = 1;// �޸ĵ���ʱʣ��ʱ�����Ϊ60��
		}
	}
}
