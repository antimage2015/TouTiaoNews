package com.crazy.toutiaonews;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazy.toutiaonews.adapterofall.RecyclerViewAdapterForPict;
import com.crazy.toutiaonews.picturenewsparsejson.PictureDataT;
import com.crazy.toutiaonews.picturenewsparsejson.PictureListT;
import com.crazy.toutiaonews.picturenewsparsejson.PicturePicsListT;
import com.crazy.toutiaonews.picturenewsparsejson.PicturePicsT;
import com.crazy.toutiaonews.picturenewsparsejson.PictureTotalT;
import com.crazy.toutiaonews.utils.MyParcelable;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *  Created by scxh on 2016/1/14.
 */
public class SecondPictureActivity extends Activity{

    private List<String> urlPLists;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapterForPict adapterForPict;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_pic_activity_layout);

        init();
    }

    public void init(){

        Intent intent = getIntent();
        int pos = intent.getIntExtra("PICTURES_NUM", 0);

        String str = MyParcelable.str1;

 //       List<String> str_urls = MyParcelable.str_urls;
 //       String str = str_urls.get(pos);

        List<PictureListT> pictureListTs = forGson(str);

        PicturePicsT pics = pictureListTs.get(pos).getPics();

        final List<PicturePicsListT> ts = pics.getList();

        final int length = ts.size();
        urlPLists = new ArrayList<>();
        mDatas = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            mDatas.add(ts.get(i).getAlt());
            urlPLists.add(ts.get(i).getPic());
        }
        sendDatas();
    }

    private void sendDatas(){
        mRecyclerView = (RecyclerView)findViewById(R.id.sec_pic_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(layoutManager);

        adapterForPict = new RecyclerViewAdapterForPict(this, urlPLists, mDatas);

        mRecyclerView.setAdapter(adapterForPict);

    }

    /**
     *  解析 JSON
     */
    private List<PictureListT> forGson(String str){

        Gson gson = new Gson();

        PictureTotalT pictureTotalT = gson.fromJson(str, PictureTotalT.class);

        PictureDataT pictureDataT = pictureTotalT.getData();
        // 该集合包括（总数:下标从 0 到 19） title pic 和 跟帖的数量
        List<PictureListT> pictureListTs = pictureDataT.getList();

        return pictureListTs;
    }

}
