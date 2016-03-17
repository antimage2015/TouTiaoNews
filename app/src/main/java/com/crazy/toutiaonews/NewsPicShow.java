package com.crazy.toutiaonews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Created by antimage on 2016/1/18.
 */
public class NewsPicShow extends Activity {

    private List<String> urlList;
    private TextView nums_txt, num_txt;
    private NewsPicShowViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_pict_show_layout);

        initData();
    }

    private void initData(){
        nums_txt = (TextView)findViewById(R.id.news_pic_show_nums);
        num_txt = (TextView)findViewById(R.id.news_pic_show_current);
        viewPager = (NewsPicShowViewPager)findViewById(R.id.news_pic_show_viewpager);
        Intent intent = getIntent();
        String new_pics_url = intent.getStringExtra("NEWS_PICS_URLS");
        ArrayList<String> url_extras = intent.getStringArrayListExtra("NEWS_PICS_URLS_EXTRA");

        urlList = new ArrayList<>();
        urlList.add(new_pics_url);

        for (String urls : url_extras) {
            urlList.add(urls);
        }
        getForInfo();
    }

    private void getForInfo(){

        ImagePagerAdapter adapter = new ImagePagerAdapter(NewsPicShow.this, urlList);
        viewPager.setAdapter(adapter);

        nums_txt.setText(urlList.size() + "");

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                num_txt.setText(position + 1 + "");
            }
        });
    }

    public class ImagePagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<String> urlList;

        public ImagePagerAdapter(Context context, List<String> urlList) {
            super();
            mContext = context;
            this.urlList = urlList;
        }

        @Override
        public int getCount() {
            return urlList.size();
        }

        @Override
        public float getPageWidth(int position) {
            return 1f;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(mContext);
            Glide.with(mContext).load(urlList.get(position)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            viewPager.setObjectForPosition(imageView, position);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return (view == object);
        }


    }

}
