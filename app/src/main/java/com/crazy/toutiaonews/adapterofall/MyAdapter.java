package com.crazy.toutiaonews.adapterofall;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crazy.toutiaonews.NewsAllActivity;
import com.crazy.toutiaonews.NewsPicShow;
import com.crazy.toutiaonews.R;
import com.crazy.toutiaonews.parsejson.ImgextraT;
import com.crazy.toutiaonews.parsejson.StringT;
import com.crazy.toutiaonews.utils.NetState;
import com.squareup.picasso.Picasso;
import com.warmtel.android.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by scxh on 2016/1/11.
 */
public class MyAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    // 定义两种状态
    private static final int STATUS_1 = 0;
    private static final int STATUS_2 = 1;
    private LayoutInflater inflater;
    private Context context;
    // 初始化，不然会有空指针
    private List<StringT> list = new ArrayList<>();

    private XListView listView;
    private List<ImgextraT> imgextras;

    public MyAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setListInfo(List<StringT> list, XListView listView){
        this.list = list;
        this.listView = listView;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *  返回两种状态的列表，一种是竖直状态，一种是水平状态
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        StringT stringT = (StringT)getItem(position);
        if (stringT.getDigest().equals("")) {
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

    public View getFirstView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_content_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.list_image = (ImageView)convertView.findViewById(R.id.list_image);
            viewHolder.list_title = (TextView)convertView.findViewById(R.id.list_title);
            viewHolder.list_content = (TextView)convertView.findViewById(R.id.list_content);
            viewHolder.list_read_counts = (TextView)convertView.findViewById(R.id.list_read_counts);
            viewHolder.list_res_come = (TextView)convertView.findViewById(R.id.list_res_come);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        StringT stringT = (StringT)getItem(position);

        Glide.with(context).load(stringT.getImgsrc()).into(viewHolder.list_image);
        viewHolder.list_title.setText(stringT.getTitle());
        viewHolder.list_content.setText(stringT.getDigest());
        viewHolder.list_read_counts.setText(stringT.getReplyCount());
        viewHolder.list_res_come.setText(stringT.getSource());

        listView.setOnItemClickListener(this);

//        final String urls = stringT.getUrl();
//        viewHolder.list_content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, NewsAllActivity.class);
//                intent.putExtra("NEWS_URLS", urls);
//                context.startActivity(intent);
//            }
//        });

        return convertView;
    }

    public View getTwoView(int position, View convertView, ViewGroup parent) {

        ViewHolderTwo viewHolderTwo;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_second_content_layout, parent, false);

            viewHolderTwo = new ViewHolderTwo();
            viewHolderTwo.list_image_second_one =
                    (ImageView)convertView.findViewById(R.id.list_image_second_one);
            viewHolderTwo.list_image_second_two =
                    (ImageView)convertView.findViewById(R.id.list_image_second_two);
            viewHolderTwo.list_image_second_three =
                    (ImageView)convertView.findViewById(R.id.list_image_second_three);

            viewHolderTwo.list_title_second =
                    (TextView)convertView.findViewById(R.id.list_title_second);

            convertView.setTag(viewHolderTwo);

        } else {
            viewHolderTwo = (ViewHolderTwo)convertView.getTag();
        }
        StringT stringT = (StringT)getItem(position);
        imgextras = stringT.getImgextra();

        Glide.with(context).load(stringT.getImgsrc()).into(viewHolderTwo.list_image_second_one);
        Glide.with(context).load(imgextras.get(0).getImgsrc()).into(viewHolderTwo.list_image_second_two);
        Glide.with(context).load(imgextras.get(1).getImgsrc()).into(viewHolderTwo.list_image_second_three);

        viewHolderTwo.list_title_second.setText(stringT.getTitle());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       if (!list.get(position - 2).getDigest().equals("")) {

            if (NetState.getAPNType(context) == -1){
                Toast.makeText(context, "无网络连接", Toast.LENGTH_SHORT).show();
            } else {

                String urls = list.get(position - 2).getUrl();
                Intent intent = new Intent(context, NewsAllActivity.class);
                intent.putExtra("NEWS_URLS", urls);
                context.startActivity(intent);
            }

        } else {

            String url = list.get(position - 2).getImgsrc();
    //        String url_extra = imgextras.get(0).getImgsrc();

            ArrayList<String> url_extras = new ArrayList<>();
            for (ImgextraT u : imgextras) {
                url_extras.add(u.getImgsrc());
            }
            Intent intent = new Intent(context, NewsPicShow.class);
            intent.putExtra("NEWS_PICS_URLS", url);
            intent.putStringArrayListExtra("NEWS_PICS_URLS_EXTRA", url_extras);
            context.startActivity(intent);
        }
    }


    class ViewHolder {
        private ImageView list_image;
        private TextView list_title, list_content, list_read_counts, list_res_come;
    }

    class ViewHolderTwo {
        private ImageView list_image_second_one, list_image_second_two, list_image_second_three;
        private TextView list_title_second;
    }
}
