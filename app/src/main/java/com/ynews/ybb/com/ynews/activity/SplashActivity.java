package com.ynews.ybb.com.ynews.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ynews.ybb.com.ynews.R;
import com.ynews.ybb.com.ynews.untils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.activity_splash)
    RelativeLayout activitySplash;
    private AnimatorSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        //butterKnife框架需要绑定当前activity
        ButterKnife.bind(this);

        ivSplash.post(new Runnable() {
            @Override
            public void run() {

                //获取图片宽和高
                int h = ivSplash.getMeasuredHeight();
                int w = ivSplash.getMeasuredWidth();


               int sreemW = getSreemWight();


                startAnimator(sreemW,0);

            }
        });

    }
/**
 * 获取屏幕宽度

 * @return int

 * @author Administrator

 * @date   2017/4/10 0010 15:32

 **/


    public int getSreemWight(){
        Display play = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        play.getSize(point);
        return point.x;
    }

    /**
     * 播放动画

     * @author Administrator

     * @date   2017/4/10 0010 14:54

     **/
    public void startAnimator(int startx,int endx){
        set = new AnimatorSet();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(ivSplash, "translationX", startx, endx);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(ivSplash, "translationY", -100, 90, -80, 70, -60, 50);

//        //设置动运动速度
//        set.setInterpolator(new Interpolator() {
//            @Override
//            public float getInterpolation(float input) {
//                return 0;
//            }
//        });
        set.playTogether(translationX,translationY);

        set.setDuration(2000);

        set.start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                try {
                    Thread.currentThread().sleep(500);
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
