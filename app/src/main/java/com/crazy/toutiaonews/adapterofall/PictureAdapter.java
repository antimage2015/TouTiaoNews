package com.crazy.toutiaonews.adapterofall;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazy.toutiaonews.R;
import com.crazy.toutiaonews.SecondPictureActivity;
import com.crazy.toutiaonews.picturenewsparsejson.PictureGenTieT;
import com.crazy.toutiaonews.picturenewsparsejson.PictureListT;
import com.crazy.toutiaonews.picturenewsparsejson.PicturePicsListT;
import com.crazy.toutiaonews.picturenewsparsejson.PicturePicsT;
import com.crazy.toutiaonews.utils.MyParcelable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  Created by scxh on 2016/1/14.
 */
public class PictureAdapter extends BaseAdapter{

    private static final int STATUS_1 = 0;
    private static final int STATUS_2 = 1;


    private LayoutInflater inflater;
    private List<PictureListT> pictureListTs = new ArrayList<>();
    private Context context;

    public PictureAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setPicAdapterListInfo(List<PictureListT> pictureListTs){
        this.pictureListTs = pictureListTs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pictureListTs.size();
    }

    @Override
    public Object getItem(int position) {
        return pictureListTs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *  返回两种状态的列表
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 != 0) {
            return STATUS_2;
        } else {
            return STATUS_1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == STATUS_2) {
            return getTwoView(position, convertView, parent);
        } else {
            return getFirstView(position, convertView, parent);
        }
    }

    public View getFirstView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.picture_activity_layout_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.picture_activity_image);
            viewHolder.title = (TextView)convertView.findViewById(R.id.picture_activity_content);
            viewHolder.genTieSums = (TextView)convertView.findViewById(R.id.picture_activity_num);
            viewHolder.subPicSum = (Button)convertView.findViewById(R.id.picture_activity_pics);
            viewHolder.subPicSum.setAlpha(0.4f);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        PictureListT pLT = (PictureListT)getItem(position);

        Glide.with(context).load(pLT.getPic()).into(viewHolder.imageView);
        viewHolder.title.setText(pLT.getTitle());

        // 跟帖的数量
        PictureGenTieT pictureGenTieT = pictureListTs.get(position).getComment_count_info();
        String genTieSum = pictureGenTieT.getQreply();
        viewHolder.genTieSums.setText(genTieSum);

        // pics (position)下共有几张 pic (二级视图可用)
        PicturePicsT picturePicsT = pictureListTs.get(position).getPics();
        List<PicturePicsListT> picturePicsListTs = picturePicsT.getList();
        viewHolder.subPicSum.setText(picturePicsListTs.size() + "");

        viewHolder.subPicSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SecondPictureActivity.class);
                intent.putExtra("PICTURES_NUM", position);
                context.startActivity(intent);
            }
        });

        return convertView;
    }


    public View getTwoView(final int position, View convertView, ViewGroup parent) {
        ViewHolderTwo viewHolderTwo ;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.picture_activity_layout_item_sec, parent, false);

            viewHolderTwo = new ViewHolderTwo();
            // 3 张图片
            viewHolderTwo.img_two_1 = (ImageView)convertView.findViewById(R.id.picture_act_image_sec);
            viewHolderTwo.img_two_2 = (ImageView)convertView.findViewById(R.id.picture_ac_image_sec_right);
            viewHolderTwo.img_two_3 = (ImageView)convertView.findViewById(R.id.pic_act_image_sec_right_bot);

            viewHolderTwo.title_two = (TextView)convertView.findViewById(R.id.pic_act_content_sec);

            viewHolderTwo.genTieSums_two = (TextView)convertView.findViewById(R.id.pic_act_num_sec);

            viewHolderTwo.subPicSum_two = (Button)convertView.findViewById(R.id.pic_act_pics_sec);
            viewHolderTwo.subPicSum_two.setAlpha(0.4f);

            convertView.setTag(viewHolderTwo);
        } else {
            viewHolderTwo = (ViewHolderTwo)convertView.getTag();
        }
        PictureListT pLT = (PictureListT)getItem(position);

        // 跟帖的数量
        PictureGenTieT pictureGenTieT = pictureListTs.get(position).getComment_count_info();
        String genTieSum = pictureGenTieT.getQreply();
        viewHolderTwo.genTieSums_two.setText(genTieSum);

        // pics (position)下共有几张 pic (二级菜单可用)
        PicturePicsT picturePicsT = pictureListTs.get(position).getPics();
        //           final List<PicturePicsListT> picturePicsListTs = picturePicsT.getList();


        List<PicturePicsListT> picturePicsListTs = picturePicsT.getList();

        viewHolderTwo.subPicSum_two.setText(picturePicsListTs.size() + "");

        Glide.with(context).load(pLT.getPic()).into(viewHolderTwo.img_two_1);

        // 加载例外的两张图片
        if (picturePicsListTs.size() != 0 && picturePicsListTs.size() >= 2) {
            Glide.with(context)
                    .load(picturePicsListTs.get(0).getPic()).into(viewHolderTwo.img_two_2);

            Glide.with(context)
                    .load(picturePicsListTs.get(1).getPic()).into(viewHolderTwo.img_two_3);
        } else if(picturePicsListTs.size() != 0 && picturePicsListTs.size() ==1 ){
            Glide.with(context)
                    .load(picturePicsListTs.get(0).getPic()).into(viewHolderTwo.img_two_2);
        } else {
            viewHolderTwo.img_two_2.setImageResource(R.drawable.evi);
            viewHolderTwo.img_two_3.setImageResource(R.drawable.evi);
        }

        viewHolderTwo.subPicSum_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondPictureActivity.class);
                intent.putExtra("PICTURES_NUM", position);
                context.startActivity(intent);
            }
        });

        viewHolderTwo.title_two.setText(pLT.getTitle());

        return convertView;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView title, genTieSums;
        Button subPicSum;
    }

    public class ViewHolderTwo {
        ImageView img_two_1, img_two_2, img_two_3;
        TextView title_two, genTieSums_two;
        Button subPicSum_two;
    }
}
