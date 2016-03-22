package com.crazy.toutiaonews;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.crazy.toutiaonews.newsfragments.LeftFragment;
import com.crazy.toutiaonews.newsfragments.MyViewPagerFragment;
import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingActivity;

import java.util.ArrayList;
import java.util.List;

// 以前继承 SlidingActivity
public class MainActivity extends SlidingActivity
        implements LeftFragment.Callbacks {

//    private boolean mFlag = true;
    private List<Button> buttonList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_activity_for_fragment, MyViewPagerFragment.newInstance())
                .commit();

        // 显示侧面的内容
        setBehindContentView(R.layout.sliding_leftfragment_layout);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.sliding_left_layout, LeftFragment.newInstance())
                .commit();

        SlidingMenu slidingMenu = getSlidingMenu();
        // sliding_menu_offset 的值越大，如果从左侧划出，则主界面能看见的部分就越多
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        // 侧滑在左侧
        slidingMenu.setMode(SlidingMenu.LEFT);
        // 在 左侧 边缘滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

    }


    @Override
    public void mediaClick() {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
        toggle();

    }

    @Override
    public void musicClick(Button[] buttons) {
        int sum = buttons.length;
        for (int i = 0; i < sum; i++) {
            buttonList.add(buttons[i]);
        }

//        if (mFlag) {
//            buttons[0].setText("关闭");
//            startAnim();
//            timerClose();
//        }
//        else {
//            buttons[0].setText("打开");
//            closeAnim();
//        }
        buttons[0].setText("正在关闭");
        startAnim();
        setRotateView();
        timerClose();
    }

    /**
     *  定时关闭动画
     */
    private void timerClose(){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                buttonList.get(0).setText("点击打开");
                closeAnim();
            }
        }.execute();

    }

    @Override
    public void pictureClick() {
        Intent intent = new Intent(this, PictureActivity.class);
        startActivity(intent);

        toggle();

    }

    @Override
    public void loginClick() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        toggle();
    }

    @Override
    public void signinClick() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        toggle();
    }

    /**
     *  关闭动画
     */
    private void closeAnim() {
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(buttonList.get(0),
                "alpha", 0.5F, 1F);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(buttonList.get(1),
                "translationY", 130F, 0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(buttonList.get(2),
                "translationX", 130F, 0);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(buttonList.get(3),
                "translationY", -130F, 0);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(buttonList.get(4),
                "translationX", -130F, 0);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new BounceInterpolator());
        set.playTogether(animator0, animator1, animator2, animator3, animator4);
        set.start();
 //       mFlag = true;
    }

    /**
     *  开始动画
     */
    private void startAnim() {
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(
                buttonList.get(0),
                "alpha",
                1F,
                0.5F);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                buttonList.get(1),
                "translationY",
                130F);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                buttonList.get(2),
                "translationX",
                130F);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(
                buttonList.get(3),
                "translationY",
                -130F);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(
                buttonList.get(4),
                "translationX",
                -130F);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new BounceInterpolator());
        set.playTogether(
                animator0,
                animator1,
                animator2,
                animator3,
                animator4);
        set.start();
 //       mFlag = false;
    }

    /**
     *  旋转动画
     */
    private void setRotateView(){

        int a = buttonList.get(1).getTop();
        int b = buttonList.get(0).getHeight()/2;
        int c = buttonList.get(0).getTop();
        int wd = buttonList.get(0).getWidth()/2;
        Log.e("tag", "a = "+ a + ", b = " + b + " , c = " + c);

        int w = a - c;
        int h = w+ b;

        RotateAnimation rotate1 = new RotateAnimation(
                0,
                360,
                RotateAnimation.ABSOLUTE, wd - w,
                RotateAnimation.ABSOLUTE, -buttonList.get(1).getHeight()/2 + h);
        rotate1.setDuration(1000);
        buttonList.get(1).startAnimation(rotate1);

        RotateAnimation rotate2 = new RotateAnimation(
                0,
                360,
                RotateAnimation.ABSOLUTE, wd - w,
                RotateAnimation.ABSOLUTE, -buttonList.get(2).getHeight()/2 + h);
        rotate2.setDuration(1000);
        buttonList.get(2).startAnimation(rotate2);

        RotateAnimation rotate3 = new RotateAnimation(
                0,
                360,
                RotateAnimation.ABSOLUTE, wd - w,
                RotateAnimation.ABSOLUTE, -buttonList.get(3).getHeight()/2 + h);
        rotate3.setDuration(1000);
        buttonList.get(3).startAnimation(rotate3);

        RotateAnimation rotate4 = new RotateAnimation(
                0,
                360,
                RotateAnimation.ABSOLUTE, wd - w,
                RotateAnimation.ABSOLUTE, -buttonList.get(4).getHeight()/2 + h);
        rotate4.setDuration(1000);
        buttonList.get(4).startAnimation(rotate4);
    }

    /**
     * 返回键两次点击，第一次点击的时间
     */
    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
        // 这里处理逻辑代码，大家注意：该方法仅适用于2.0或更新版的sdk
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
