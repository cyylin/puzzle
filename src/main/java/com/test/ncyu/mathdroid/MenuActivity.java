package com.test.ncyu.mathdroid;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.nostra13.universalimageloader.core.ImageLoader;


public class MenuActivity extends AppCompatActivity {
    public static final int ITEM_DELAY = 190;
    public static AppCompatActivity menuActivity;

    private int transY;
    private FrameLayout menuLayout;

    public static String backgroundURL = "drawable://" + R.drawable.menu_background;
    public static ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuActivity = this;

        transY =(int) (DeviceInf.getScreenHeightPx(this)*0.15) ;
        menuLayout = new FrameLayout(MenuActivity.this);
        menuLayout.setId(R.id.menu);
        init();
        Bitmap bitmap = imageLoader.loadImageSync(backgroundURL);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources() , bitmap);
        menuLayout.setBackground(bitmapDrawable);
        setContentView(menuLayout);
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private void init(){
        setupWindowAnimations();
        closeOldActivity();
        setupButton();
        setButtonAnimations();
    }

    private void setupWindowAnimations(){
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);

        Fade fade_in = new Fade();
        fade_in.setMode(Fade.IN);
        fade_in.setDuration(500);
        getWindow().setEnterTransition(fade_in);

        Fade fade_out = new Fade();
        fade_out.setMode(Fade.OUT);
        fade_out.setDuration(500);
        getWindow().setReturnTransition(fade_out);
    }

    private void setupButton(){
        String [] menu_button = getResources().getStringArray(R.array.menu_button);
        MenuButton [] menuButton= new MenuButton[menu_button.length];

        for(int i = 0 ; i < menu_button.length ; i ++) {
            menuButton [i]  = new MenuButton(MenuActivity.this, menu_button[i]);
            menuLayout.addView(menuButton [i]);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) menuButton [i].getLayoutParams();
            params.topMargin = (int) (DeviceInf.getScreenHeightPx(this) * 0.075 + transY + i*DeviceInf.getScreenWidthPx(this)*0.275 );
            params.gravity = Gravity.CENTER_HORIZONTAL;
            menuButton [i].setLayoutParams(params);
        }

        //開始按鈕
        menuButton [0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Turn2GameMenu();
            }
        });


        //設定(重設)按鈕
        menuButton [1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting();
            }
        });

        //關於按鈕
        menuButton [2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAbout();
            }
        });

        //離開按鈕
        menuButton [3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }

    private void setButtonAnimations(){
        for(int i = 0 ; i < menuLayout.getChildCount() ; i ++){
            View view = menuLayout.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;
            viewAnimator = ViewCompat.animate(view)
                    .translationY(-transY).alpha(1)
                    .setStartDelay((ITEM_DELAY *i) + 300)
                    .setDuration(760);

            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new AccelerateInterpolator());
            fadeIn.setDuration(2000);

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
            view.startAnimation(fadeIn);
        }
    }

    private void closeOldActivity(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if (StartActivity.startActivity != null)
                        if(!StartActivity.startActivity.isFinishing() )
                            StartActivity.startActivity.finish();//關閉起始畫面
                }catch (Exception e){}
            }
        }).start();
    }

    private void Turn2GameMenu(){
        Intent intent = new Intent(this, GameMenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void setting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.reset);
        builder.setCancelable(false);
        builder.setMessage("需要重設所有遊戲通關紀錄嗎?");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GameData gameData = new GameData(MenuActivity.this);
                gameData.ResetData();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showAbout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.about);
        builder.setCancelable(false);
        builder.setMessage("本程式\n係由嘉義大學應用數學系106級\n專題學生製作\n\n指導老師：鄭富國 老師");
        builder.setPositiveButton("確定", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void exit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("離開");
        builder.setCancelable(false);
        builder.setMessage("確定要離開嗎?");
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());//自殺
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
