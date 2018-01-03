package com.test.ncyu.mathdroid;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity{
    public static final int LOGO_DURATION = 2000;
    public static final int LOADING_TIME = 20;

    //基本
    public static AppCompatActivity startActivity;
    private FrameLayout startLayout;
    private boolean animationStarted = false;

    //進度條
    private RoundedRectProgressBar bar;
    private int progress;
    private Timer timer;

    //按任意觸繼續
    private TextView showMessage;

    //Preload
    public ImageView backgroundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }

        startActivity = this;

        startLayout = new FrameLayout(StartActivity.this);
        startLayout.setBackgroundColor(Color.BLACK);
        setLogo();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(startLayout);

        //---preload---
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }

    public View.OnClickListener clickTransfer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(StartActivity.this, MenuActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StartActivity.this).toBundle());
        }
    };

    private void setLogo(){
        ImageView logo = new ImageView(StartActivity.this);
        logo.setImageDrawable(getResources().getDrawable(R.drawable.logo, null));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        startLayout.addView(logo, params);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(LOGO_DURATION);

        fadeIn.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation) {
                setProgressBar();
                setMessage();
                setAnimate(showMessage);
                setAnimate(bar);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        ViewPropertyAnimatorCompat viewAnimator;
        int transY = (-100)*(DeviceInf.getScreenHeightPx(this)/1920);
        viewAnimator = ViewCompat.animate(logo)
                .translationY(transY).alpha(1)
                .setStartDelay(0)
                .setDuration(LOGO_DURATION);

        viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        logo.startAnimation(fadeIn);
    }

    private void setMessage() {

        Typeface face = Typeface.create("serif", Typeface.NORMAL);
        showMessage = new TextView(StartActivity.this);
        showMessage.setText(R.string.loading, TextView.BufferType.SPANNABLE);
        showMessage.setTypeface(face);
        showMessage.setTextSize(getResources().getDimension(R.dimen.textsize));
        showMessage.setTextColor(Color.WHITE);


        //---------------Animation--------------
        AnimationSet animationSet = new AnimationSet(true);
        Animation anim = new AlphaAnimation(0.25f, 1.0f);
        anim.setDuration(625); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setFillAfter(true);
        animationSet.addAnimation(anim);
        showMessage.startAnimation(animationSet);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (DeviceInf.getScreenHeightPx(this) * 0.75);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        startLayout.addView(showMessage, params);
    }

    private void setProgressBar() {
        bar = new RoundedRectProgressBar(StartActivity.this);
        int barLong = (int) (DeviceInf.getScreenWidthPx(this)*0.8);
        int barHeight = 60;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(barLong, barHeight);
        params.topMargin = (int) (DeviceInf.getScreenHeightPx(this) * 0.8);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        startLayout.addView(bar, params);

        AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
        alphaAnim.setFillAfter(true);
        alphaAnim.setDuration(2200);
        alphaAnim.setStartTime(500);
        bar.startAnimation(alphaAnim);

        backgroundView = new ImageView(StartActivity.this);
        progress = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                bar.setProgress(progress);
                progress ++;
                if(progress == 86){
                    MenuActivity.imageLoader.displayImage(MenuActivity.backgroundURL, backgroundView);
                }
                if (progress > 100) {
                    timer.cancel();
                    StartActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            showMessage.setText(R.string.press_any_key, TextView.BufferType.SPANNABLE);
                            showMessage.setTextColor(Color.CYAN);
                            startLayout.removeView(bar);
                            startLayout.setOnClickListener(clickTransfer);
                            startLayout.removeCallbacks(this);
                        }
                    });
                }
            }
        }, 0, LOADING_TIME);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        System.exit(0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        super.onWindowFocusChanged(hasFocus);
    }

    private void setAnimate(View v) {

        ViewPropertyAnimatorCompat viewAnimator;
        viewAnimator = ViewCompat.animate(v)
                .translationY(50).alpha(1)
                .setStartDelay(500)
                .setDuration(4500);

        viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
    }
}

