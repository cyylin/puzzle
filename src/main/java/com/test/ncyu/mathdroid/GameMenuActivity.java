package com.test.ncyu.mathdroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.FrameLayout;

import static com.test.ncyu.mathdroid.MenuActivity.backgroundURL;
import static com.test.ncyu.mathdroid.MenuActivity.imageLoader;

public class GameMenuActivity extends AppCompatActivity {

    //選擇關卡介面
    private FrameLayout UI;
    private Button[] stageButton = new Button[GameLevel.MAX_LEVEL];
    private int button_space ;//按鈕間距
    private int button_size ;//按鈕大小

    //關卡通關資料
    private GameData gameData;//遊戲資料
    private int PassedCount = 0;//通關數量


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        button_space = (int)( DeviceInf.getScreenWidthPx(GameMenuActivity.this)*0.04);
        button_size = (int)( DeviceInf.getScreenWidthPx(GameMenuActivity.this)*0.2);
        UI = new FrameLayout(GameMenuActivity.this);
        Bitmap bitmap = imageLoader.loadImageSync("drawable://" + R.drawable.game_menu_background);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources() , bitmap);
        UI.setBackground(bitmapDrawable);
        init();
        setContentView(UI);
    }

    //返回鍵
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);//禁止返回鍵
        builder.setTitle("返回主頁");
        builder.setMessage("確定要返回主頁嗎?");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Back2Menu();
            }
        });
        builder.setNegativeButton("取消",null);

        AlertDialog alertDialog =  builder.create();
        alertDialog.show();
    }

    //初始化
    private void init(){
        PassedCount = 0;
        gameData = new GameData(this);

        for(int i = 0 ; i < stageButton.length ; i ++ ) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(button_size, button_size);
            params.leftMargin = (int) ( ( (i%4)+1 )*button_space + (i%4)*button_size );
            params.topMargin = (int)(DeviceInf.getScreenHeightPx(GameMenuActivity.this) * 0.2) + (int)(i/4)*(button_size+button_space);
            stageButton[i] = new Button(GameMenuActivity.this);
            stageButton[i].setText(Integer.toString(i+1));
            stageButton[i].setTextColor(Color.WHITE);
            stageButton[i].setTextSize(20);
            UI.addView(stageButton[i], params);
        }
        CheckPassed();
    }

    private void CheckPassed(){
        //確認是否通關
        for(int i = 1 ; i <= GameLevel.MAX_LEVEL ; i ++ ) {
            if(gameData.isPassed(i)) {
                PassedCount++;
                stageButton[i-1].setEnabled(true);
                stageButton[i-1].setBackgroundResource(R.drawable.game_menu_button);
                final int index = i;
                stageButton[i-1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EnterStage(index);
                        finish();//結束選單
                    }
                });
            }
            else if(i < stageButton.length){
                stageButton[i].setEnabled(false);
                stageButton[i].setBackgroundResource(R.drawable.game_menu_locked);
            }
        }
        //最新的一關背景圖
        if(PassedCount < stageButton.length) {
            stageButton[PassedCount].setEnabled(true);
            stageButton[PassedCount].setBackgroundResource(R.drawable.game_menu_button_new_pressed);
            stageButton[PassedCount].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EnterStage(PassedCount+1);
                }
            });

            //動畫特效
            AnimationSet animationSet = new AnimationSet(true);
            Animation anim = new AlphaAnimation(0.25f, 1.0f);
            anim.setDuration(625); //You can manage the time of the blink with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            anim.setFillAfter(true);
            animationSet.addAnimation(anim);
            stageButton[PassedCount].setAnimation(animationSet);
        }
    }

    private void EnterStage(int userSelected){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("LEVEL", userSelected);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void Back2Menu(){
        Intent intent = new Intent(this, MenuActivity.class);
        if(getPackageManager().resolveActivity(intent, 0) != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}
