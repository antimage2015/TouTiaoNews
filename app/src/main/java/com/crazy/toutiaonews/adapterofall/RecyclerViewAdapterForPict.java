package com.crazy.toutiaonews.adapterofall;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazy.toutiaonews.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by antimage on 2016/1/17.
 */
public class RecyclerViewAdapterForPict extends
        RecyclerView.Adapter<RecyclerViewAdapterForPict.CrazyViewHolder> {

    private List<String> urlPLists;
    private LayoutInflater inflater;
    private List<String> mDatas;
    private int mScreenWidth;
    private int mScreenHeight;
    private Context context;

    public RecyclerViewAdapterForPict(Context context,
                                      List<String> urlPLists,
                                      List<String> mDatas){
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (urlPLists == null || mDatas == null) {
            urlPLists = new ArrayList<>();
            mDatas = new ArrayList<>();
        } else {
            this.urlPLists = urlPLists;
            this.mDatas = mDatas;
        }

        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public CrazyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pict_erjishitu_recycler_item, parent, false);
        CrazyViewHolder cViewHolder = new CrazyViewHolder(view);

        cViewHolder.imageView = (ImageView)view.findViewById(R.id.sec_pic_activity_image);
        cViewHolder.textView = (TextView)view.findViewById(R.id.sec_pic_activity_txt);
        return cViewHolder;
    }


    @Override
    public void onBindViewHolder(CrazyViewHolder holder, int position) {
        Glide.with(context).load(urlPLists.get(position)).into(holder.imageView);

        ViewGroup.LayoutParams paramsImg = holder.imageView.getLayoutParams();
        paramsImg.height=mScreenHeight-200;
        paramsImg.width =mScreenWidth;
        holder.imageView.setLayoutParams(paramsImg);

        ViewGroup.LayoutParams paramsTxt = holder.textView.getLayoutParams();
        paramsTxt.height = 150;
        paramsTxt.width = mScreenWidth;

        holder.textView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return urlPLists.size();
    }

    public class CrazyViewHolder extends RecyclerView.ViewHolder  {

        ImageView imageView;
        TextView textView;

        public CrazyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
