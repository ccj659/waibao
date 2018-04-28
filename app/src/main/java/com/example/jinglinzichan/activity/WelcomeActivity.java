package com.example.jinglinzichan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.jinglinzichan.BaseActivity;
import com.example.jinglinzichan.MainActivity;
import com.example.jinglinzichan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchangjun on 18/4/27.
 */

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {

    private ViewPager mViewPager;
    private InnerPagerAdapter mAdapter;
    private List<View> viewList = new ArrayList<>();

    private ImageView iv_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guid);
        initImageList();
        initView();

    }

    private void initImageList() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_guid_1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.layout_guid_2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.layout_guid_3, null);

       // iv_start = (ImageView) view4.findViewById(R.id.iv_start);
        iv_start.setVisibility(View.INVISIBLE);
        iv_start.setOnClickListener(this);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
       // viewList.add(view4);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new InnerPagerAdapter();
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }


    @Override
    public void onPageScrolled(int page, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.anim_guide_alpha_in);
            iv_start.startAnimation(anim1);
            iv_start.setVisibility(View.VISIBLE);
        } else {
            iv_start.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        v.setClickable(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }



    class InnerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

    }

}

