package com.crazy.toutiaonews;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.crazy.toutiaonews.picturefragment.PictureViewPagerFragment;

/**
 *  Created by scxh on 2016/1/13.
 */
public class PictureActivity extends FragmentActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.copy_picture_activity_layout);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.copy_picture_layout_for_fragment, PictureViewPagerFragment.newInstance())
                .commit();

    }

}
