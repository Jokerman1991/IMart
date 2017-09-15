package com.virgo.mart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdeyu on 17-9-14.
 */

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private int[] imageIds = {
            R.drawable.splash_whale_blue,
            R.drawable.splash_whale_yellow,
            R.drawable.splash_whale_purple
    };
    private List<ImageView> mImageViews;
    private Button mSkipBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
    }

    private void initViews() {
        mSkipBtn = (Button) findViewById(R.id.skip_btn);
        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lunchMart();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.container);
        mImageViews = new ArrayList<>();
        for (int id : imageIds) {
            ImageView view = new ImageView(getBaseContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            view.setLayoutParams(layoutParams);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setImageResource(id);
            mImageViews.add(view);
        }

        mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return null == mImageViews ? 0 : mImageViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mImageViews.get(position));
                return mImageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews.get(position));
            }

            @Override
            public Parcelable saveState() {
                return super.saveState();
            }

            @Override
            public void restoreState(Parcelable state, ClassLoader loader) {
                super.restoreState(state, loader);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);
    }

    private void lunchMart() {
        startActivity(new Intent(GuideActivity.this, MartActivity.class));
        GuideActivity.this.finish();
    }

}
