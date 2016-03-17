package com.crazy.toutiaonews.adapterofall;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazy.toutiaonews.R;
import com.crazy.toutiaonews.videonewsparsejson.VideoStrT;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Created by scxh on 2016/1/14.
 */
public class VideoAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<VideoStrT> videoStrTs = new ArrayList<>();
    private Context context;
    private MediaPlayer mPlayer;
    private int pos;
    private WindowManager wManager;

    public VideoAdapter(Context context, MediaPlayer mPlayer, int pos, WindowManager wManager){
        this.context = context;
        this.mPlayer = mPlayer;
        this.pos = pos;
        this.wManager = wManager;
        inflater = LayoutInflater.from(context);
    }

    public void setVideoAdapterListInfo(List<VideoStrT> videoStrTs){
        this.videoStrTs = videoStrTs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return videoStrTs.size();
    }

    @Override
    public Object getItem(int position) {
        return videoStrTs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            //         convertView = inflater.inflate(R.layout.video_activity_layout_item, parent, false);
            convertView = inflater.inflate(R.layout.copy_video_activity_layout_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.video_title);
            viewHolder.description = (TextView)convertView.findViewById(R.id.video_description);
            viewHolder.fabuTime = (TextView)convertView.findViewById(R.id.video_time);
            viewHolder.videoLength = (TextView)convertView.findViewById(R.id.video_length);
            //             viewHolder.videoView = (VideoView)convertView.findViewById(R.id.video_vv);

            // ===========================  自定义的播放  ========================

            // 获取界面中的三个按钮
            viewHolder.play = (ImageButton) convertView.findViewById(R.id.play);
            viewHolder.pause = (ImageButton)convertView. findViewById(R.id.pause);
            viewHolder.stop = (ImageButton) convertView.findViewById(R.id.stop);

            viewHolder.surfaceView = (SurfaceView) convertView.findViewById(R.id.surfaceView);
            // 设置播放时打开屏幕
            viewHolder.surfaceView.getHolder().setKeepScreenOn(true);
            viewHolder.surfaceView.getHolder().addCallback(new SurfaceListener());
            // ===========================  自定义的播放  ========================

            viewHolder.video_img_play = (ImageView)convertView.findViewById(R.id.video_img_play);
            viewHolder.video_image = (ImageView)convertView.findViewById(R.id.video_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
            mPlayer.pause();

        }
        final VideoStrT vdt = (VideoStrT)getItem(position);
        viewHolder.title.setText(vdt.getTitle());
        viewHolder.description.setText(vdt.getDescription());
        viewHolder.fabuTime.setText(vdt.getPtime());
        viewHolder.videoLength.setText(vdt.getLength());

// =============================== 自定义的播放 ====================

        // 加载图片时，播放按钮不可见

        viewHolder.play.setVisibility(View.GONE);
        viewHolder.pause.setVisibility(View.GONE);
        viewHolder.stop.setVisibility(View.GONE);
        Glide.with(context).load(vdt.getCover()).into(viewHolder.video_image);

        viewHolder.video_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.reset();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                // 设置需要播放的视频
                try {
                    mPlayer.setDataSource(context, Uri.parse(vdt.getMp4_url()));

                    // 把视频画面输出到SurfaceView
                    mPlayer.setDisplay(viewHolder.surfaceView.getHolder());
                    mPlayer.prepare();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                DisplayMetrics metrics = new DisplayMetrics();
                // 获取屏幕大小
                wManager.getDefaultDisplay().getMetrics(metrics);
                // 设置视频保持纵横比缩放到占满整个屏幕
                viewHolder.surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(metrics.widthPixels
                        , mPlayer.getVideoHeight() * metrics.widthPixels
                        / mPlayer.getVideoWidth()));
                mPlayer.start();
                // 隐藏加载的图片，播放按钮可见
                viewHolder.video_img_play.setVisibility(View.GONE);
                viewHolder.video_image.setVisibility(View.GONE);
                viewHolder.play.setVisibility(View.VISIBLE);
                viewHolder.pause.setVisibility(View.VISIBLE);
                viewHolder.stop.setVisibility(View.VISIBLE);
            }
        });


        // 为三个按钮的单击事件绑定事件监听器
        viewHolder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPlayer.reset();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                // 设置需要播放的视频
                try {
                    mPlayer.setDataSource(context, Uri.parse(vdt.getMp4_url()));

                    // 把视频画面输出到SurfaceView
                    mPlayer.setDisplay(viewHolder.surfaceView.getHolder());
                    mPlayer.prepare();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                DisplayMetrics metrics = new DisplayMetrics();
                // 获取屏幕大小
                wManager.getDefaultDisplay().getMetrics(metrics);
                // 设置视频保持纵横比缩放到占满整个屏幕
                viewHolder.surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(metrics.widthPixels
                        , mPlayer.getVideoHeight() * metrics.widthPixels
                        / mPlayer.getVideoWidth()));
                mPlayer.start();
            }
        });
        viewHolder.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();

                }
                else {
                    mPlayer.start();
                }
            }
        });
        viewHolder.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying())
                    mPlayer.stop();
            }
        });

        // 播放完后，加载该视频的图片
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Glide.with(context).load(vdt.getCover()).into(viewHolder.video_image);
            }
        });


        // =============================== 自定义的播放 ========================

        return convertView;
    }

    public class ViewHolder {
        TextView title, description, fabuTime, videoLength;
        ImageView video_image, video_img_play;
        //       VideoView videoView;

        // ===========================  自定义的播放  ========================
        SurfaceView surfaceView;
        ImageButton play, pause, stop;
    }

    // ===========================  自定义的播放  ========================
    private class SurfaceListener implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format,
                                   int width, int height) {
        }
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (pos > 0) {
                try {
                    // 并直接从指定位置开始播放
                    mPlayer.seekTo(pos);
                    pos = 0;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
}
