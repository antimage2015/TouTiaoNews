package com.crazy.toutiaonews;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.crazy.toutiaonews.videofragments.VideoViewPagerFragment;

/**
 *  Created by scxh on 2016/1/13.
 */
public class VideoActivity extends FragmentActivity {

    private  MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity_layout);

        mPlayer = new MediaPlayer();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.video_act_frame, VideoViewPagerFragment.newInstance())
                .commit();

    }


    // 当其他Activity被打开时，暂停播放
    @Override
    protected void onPause() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        // 停止播放
        if (mPlayer.isPlaying()) mPlayer.stop();
        // 释放资源
        mPlayer.release();
        super.onDestroy();
    }

}
