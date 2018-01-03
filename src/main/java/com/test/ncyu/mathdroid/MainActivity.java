package com.test.ncyu.mathdroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    protected static int piecesNum = 1;
    protected static int CurrentNum = 0;
    public static int total = 6;
    private int select = 1;

    private FrameLayout mainLayout;
    public static GridBoard gridBoard;
    private Pieces[] pieces = new Pieces[piecesNum];
    private View piecesFloor;
    private SelectPieces selectPieces;
    private Button selectGraph;
    private Button mRotate;
    private Button Again;

    //---關卡---
    private GameLevel gameLevel;
    private int level = 1;
    private int PiecesGridNumber = 0;

    //--------------------------------
    public static int [][] _GraphExist = new int[GridBoard.mColumn][GridBoard.mRow];
    public static boolean [][] inIllegalRegion = new boolean[GridBoard.mColumn][GridBoard.mRow];//紀錄非法區域
    public int [][] ExistTemp = new int[GridBoard.mColumn][GridBoard.mRow];

    //--------------------------------
    private boolean GeneratingNext = false;
    private boolean Game_Finish = false;
    public static boolean LastGraphDown = false;

    //----for Dev
    public Button showAssistLine;
    private Button NextLevel;//輔助通關

    //----執行緒----//
    private Handler handler_Game;
    public static Boolean isLeave = false;

    //------圖形處理------
    private final String backgroundURL = "drawable://" + R.drawable.game_background;
    private ImageView imageView;

    //------遊戲資訊------
    private View Information_Background;
    private TextView level_text;
    private TextView remainder_pieces;
    private TextView game_steps_text;
    public static int game_steps = 0;
    private TextView time_text;
    private Handler Time_Handler;
    private long startTime;
    private long play_min = 0;
    private long play_sec = 0;
    private boolean hasStartTime = false;
    private boolean AlertMessage = false;


    private GameData gameData;

    //數字格式
    private NumberFormat numberFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //-----取得關卡資料-----
        Bundle MenuData = getIntent().getExtras();
        if (MenuData != null ) {
            level = MenuData.getInt("LEVEL");
        }else{
            level = 1;
        }

        //-----圖形處理-----
        imageView = new ImageView(MainActivity.this);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        ImageLoader.getInstance().init(configuration);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(backgroundURL, imageView);

        //-------------------

        mainLayout = new FrameLayout(MainActivity.this);
        mainLayout.setBackground(new BitmapDrawable(getResources(),imageLoader.loadImageSync(backgroundURL)));
        setLocation();
        init();
        //forDev();
        setContentView(mainLayout);

        //--------------------Testing--------------------//

        handler_Game = new Handler();
        handler_Game.post(gameUpdate);

        Time_Handler = new Handler();
        Time_Handler.post(TimeUpdate);

        //--------遊戲資訊格式--------
        numberFormat = NumberFormat.getIntegerInstance();
        numberFormat.setGroupingUsed(false); //設定數字是否會以逗號分組(ex:123,456,789)
        numberFormat.setMaximumIntegerDigits(2); //設定數字最多幾位數
        numberFormat.setMinimumIntegerDigits(2); //設定數字最少幾位數
        gameData = new GameData(MainActivity.this);
    }


    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onPause(){
        if(handler_Game != null) {
            handler_Game.removeCallbacks(gameUpdate);
        }
        super.onPause();
    }

    @Override
    protected void onResume(){
        if(handler_Game != null) {
            handler_Game.postDelayed(gameUpdate, (long) 1 / 60 * 1000);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(handler_Game != null) {
            handler_Game.removeCallbacks(gameUpdate);
            handler_Game = null;
        }
        if(Time_Handler != null) {
            Time_Handler.removeCallbacks(TimeUpdate);
            Time_Handler = null;
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    //返回鍵
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);//禁止返回鍵
        builder.setTitle("返回選單");
        builder.setMessage("確定要返回遊戲選單嗎?");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //關閉執行緒
                if(handler_Game != null) {
                    handler_Game.removeCallbacks(gameUpdate);
                    handler_Game = null;
                }
                if(Time_Handler != null) {
                    Time_Handler.removeCallbacks(TimeUpdate);
                    Time_Handler = null;
                }
                //切回選單
                BackToMenu();
            }
        });
        builder.setNegativeButton("繼續遊戲",null);

        AlertDialog alertDialog =  builder.create();
        alertDialog.show();
    }

    private void BackToMenu(){
        Intent intent = new Intent(this, GameMenuActivity.class);
        if(getPackageManager().resolveActivity(intent, 0) != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    private void init(){
        game_steps = 0;
        CurrentNum = 0;
        isLeave = false;
        LastGraphDown = false;
        Game_Finish = false;
        AlertMessage = false;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//keep screen ON

        gridBoard = new GridBoard(MainActivity.this);
        mainLayout.addView(gridBoard);
        FrameLayout.LayoutParams mBoardParams = ( FrameLayout.LayoutParams ) gridBoard.getLayoutParams();
        mBoardParams.gravity = Gravity.CENTER_HORIZONTAL;
        mBoardParams.topMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*0.05);
        gridBoard.setLayoutParams(mBoardParams);


        piecesFloor = new View(MainActivity.this);
        piecesFloor.setBackgroundColor(Color.argb(128,0,0,0));
        FrameLayout.LayoutParams mPiecesFloorParams = new FrameLayout.LayoutParams(GridBoard.GridW*Pieces.mColumn_Num ,GridBoard.GridW*Pieces.mRow_Num);
        mPiecesFloorParams.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*0.05);
        mPiecesFloorParams.topMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*1.0);
        mainLayout.addView(piecesFloor, mPiecesFloorParams);

        selectGraph = new Button(MainActivity.this);
        mainLayout.addView(selectGraph);
        FrameLayout.LayoutParams mButtonParams = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mButtonParams.topMargin = (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.85);
        mButtonParams.gravity = Gravity.CENTER_HORIZONTAL;
        selectGraph.setLayoutParams(mButtonParams);
        selectGraph.setText(R.string.select_button);
        selectGraph.setTypeface(Typeface.create("serif", Typeface.NORMAL));
        selectGraph.setTextColor(getResources().getColorStateList(R.color.game_button_text) );
        selectGraph.setBackgroundResource(R.drawable.game_button_background);
        selectGraph.setVisibility(View.VISIBLE);

        int[][] Array = setSelect(select);
        PiecesGridNumber = 0;
        for (int i = 0; i < Pieces.mColumn_Num; i++) {
            for (int j = 0; j < Pieces.mRow_Num; j++) {
                if (Array[i][j] == 1)
                    PiecesGridNumber++;
            }
        }
        gameLevel.setLevel(level);
        resetPieces((int) (gridBoard.getCount() / PiecesGridNumber));

        selectGraph.setOnClickListener(new View.OnClickListener() {
            private FrameLayout mLayout;

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.SelectedGraphDialog);
                mLayout = new FrameLayout(MainActivity.this);
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int[][] Array = setSelect(select);
                        for(int i = 0 ; i < pieces.length ; i ++) {
                            pieces[i].setArray(Array);
                        }
                        PiecesGridNumber = 0;
                        for(int i = 0 ; i < Pieces.mColumn_Num ; i++ ){
                            for(int j = 0 ; j < Pieces.mRow_Num ; j++){
                                if(Array[i][j] == 1)
                                    PiecesGridNumber ++ ;
                            }
                        }
                        resetPieces( (int) (gridBoard.getCount()/PiecesGridNumber) );
                    }
                });
                mLayout.setBackgroundColor(Color.argb(0,0,0,0));
                builder.setView(mLayout,0,0,0,0);
                setLayout();

                AlertDialog alertDialog = builder.create();

                if(CurrentNum == 0 && !pieces[0].inRegion){
                    alertDialog.show();


                    LinearLayout.LayoutParams buttonParams;
                    Button buttonPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    buttonParams.gravity = Gravity.CENTER;
                    buttonPositive.setLayoutParams(buttonParams);
                    buttonPositive.invalidate();

                    WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                    params.copyFrom(alertDialog.getWindow().getAttributes());
                    params.width = DeviceInf.getScreenWidthPx(MainActivity.this);
                    params.height = DeviceInf.getScreenHeightPx(MainActivity.this);
                    alertDialog.getWindow().setAttributes(params);

                    Button positive_button =  alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    positive_button.setBackgroundColor(Color.TRANSPARENT);
                    positive_button.setTextColor(getResources().getColorStateList(R.color.alertdialog_select_button_color));
                }
            }

            private void setLayout(){

                //圖形
                selectPieces = new SelectPieces(MainActivity.this);
                FrameLayout.LayoutParams selectPiecesParams = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                selectPiecesParams.gravity = Gravity.CENTER;
                mLayout.addView(selectPieces, selectPiecesParams);
                setSelect(select);

                //個數
                final TextView showNum = new TextView(MainActivity.this);
                showNum.setTextSize(20.0f);
                showNum.setTextColor(Color.WHITE);
                String string = getResources().getString(R.string.corrent_num) + Integer.toString(select) +"/"+ Integer.toString(total);
                showNum.setText(string);
                FrameLayout.LayoutParams TextViewParams = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                TextViewParams.gravity = Gravity.CENTER_HORIZONTAL;
                TextViewParams.topMargin = (int)(DeviceInf.getScreenHeightPx(MainActivity.this)*0.1);
                mLayout.addView(showNum, TextViewParams);

                //按鈕
                int width = (int)(DeviceInf.getScreenWidthPx(MainActivity.this)*0.5);
                Button mNext = new Button(MainActivity.this);
                Button mPrevious = new Button(MainActivity.this);
                mNext.setBackgroundResource(R.drawable.alertdialog_select_graph_button);
                mPrevious.setBackgroundResource(R.drawable.alertdialog_select_graph_button);
                mNext.setText(">>");
                mPrevious.setText("<<");
                mNext.setTextColor(getResources().getColorStateList(R.color.alertdialog_select_button_color));
                mPrevious.setTextColor(getResources().getColorStateList(R.color.alertdialog_select_button_color));
                FrameLayout.LayoutParams mNextParams = new FrameLayout.LayoutParams((int)(width*0.7), FrameLayout.LayoutParams.WRAP_CONTENT);
                mNextParams.topMargin = (int)(DeviceInf.getScreenHeightPx(MainActivity.this)*0.55);
                mNextParams.leftMargin = width;
                mLayout.addView(mNext,mNextParams);

                FrameLayout.LayoutParams mPreviousParams = new FrameLayout.LayoutParams((int)(width*0.7), FrameLayout.LayoutParams.WRAP_CONTENT);
                mPreviousParams.topMargin = (int)(DeviceInf.getScreenHeightPx(MainActivity.this)*0.55);
                mPreviousParams.leftMargin = (int)(width*0.25);
                mLayout.addView(mPrevious, mPreviousParams);

                mNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(select < total )
                            select ++;
                        else
                            select = 1;
                        String string = getResources().getString(R.string.corrent_num) + Integer.toString(select) +"/"+ Integer.toString(total);
                        showNum.setText(string);
                        setSelect(select);
                    }
                });

                mPrevious.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(select > 1 )
                            select --;
                        else
                            select = total;
                        String string = getResources().getString(R.string.corrent_num) + Integer.toString(select) +"/"+ Integer.toString(total);
                        showNum.setText(string);
                        setSelect(select);
                    }
                });

            }

        });

        mRotate = new Button(MainActivity.this);
        mainLayout.addView(mRotate);
        FrameLayout.LayoutParams mRotateParams = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mRotateParams.topMargin = (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.85);
        mRotateParams.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*0.05);
        mRotate.setLayoutParams(mRotateParams);
        mRotate.setText(R.string.rotate);
        mRotate.setTypeface(Typeface.create("serif", Typeface.NORMAL));
        mRotate.setTextColor(getResources().getColorStateList(R.color.game_button_text) );
        mRotate.setBackgroundResource(R.drawable.game_button_background);
        mRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentNum < piecesNum ) {
                    pieces[CurrentNum].touchRotate();
                }
            }
        });

        Again = new Button(MainActivity.this);
        mainLayout.addView(Again);
        FrameLayout.LayoutParams AgainParams = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        AgainParams.topMargin = (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.85);
        AgainParams.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*0.70);
        Again.setLayoutParams(AgainParams);
        Again.setTextColor(getResources().getColorStateList(R.color.game_button_text) );
        Again.setBackgroundResource(R.drawable.game_button_background);
        Again.setText(R.string.game_again);
        Again.setTypeface(Typeface.create("serif", Typeface.NORMAL));
        Again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Game_Finish) {
                    int[][] Array = setSelect(select);
                    gameLevel.setLevel(level);
                    for(int i = 0 ; i < pieces.length ; i ++) {
                        pieces[i].setArray(Array);
                    }
                    PiecesGridNumber = 0;
                    for(int i = 0 ; i < Pieces.mColumn_Num ; i++ ){
                        for(int j = 0 ; j < Pieces.mRow_Num ; j++){
                            if(Array[i][j] == 1)
                                PiecesGridNumber ++ ;
                        }
                    }
                    resetPieces( (int) (gridBoard.getCount()/PiecesGridNumber) );
                }
            }
        });

        NextLevel = new Button(MainActivity.this);
        FrameLayout.LayoutParams NextParams = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        NextParams.topMargin = (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.75);
        NextParams.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*0.70);
        mainLayout.addView(NextLevel,NextParams);

        //關閉上帝權限
        NextLevel.setVisibility(View.GONE);

        NextLevel.setText("上帝權限");
        NextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //builder.setTitle("訊息");
                builder.setTitle("檢測步數"+Integer.toString(gameData.getSteps(level)));
                builder.setMessage("恭喜通關");
                builder.setPositiveButton("下一關", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(level + 1 <= gameLevel.MAX_LEVEL) {
                            gameLevel.setLevel(++level);
                            for (int i = 0; i < GridBoard.mColumn; i++)
                                for (int j = 0; j < GridBoard.mRow; j++) {
                                    ExistTemp[i][j] = _GraphExist[i][j];
                                }
                            gridBoard.invalidate();//更新面板
                            select = 1;
                            int Array[][] = setSelect(select);
                            PiecesGridNumber = 0;
                            for(int i = 0 ; i < Pieces.mColumn_Num ; i++ ){
                                for(int j = 0 ; j < Pieces.mRow_Num ; j++){
                                    if(Array[i][j] == 1)
                                        PiecesGridNumber ++ ;
                                }
                            }
                            if(PiecesGridNumber != 0)
                                resetPieces( (int) (gridBoard.getCount()/PiecesGridNumber));
                        }else{
                            Log.d("關卡資訊","完成");
                            Game_Finish = true;
                            mRotate.setEnabled(false);
                            selectGraph.setEnabled(false);
                            Again.setEnabled(false);
                            if(handler_Game != null) {
                                handler_Game.removeCallbacks(gameUpdate);
                                handler_Game = null;
                            }//移除執行緒運行
                            if(Time_Handler != null) {
                                Time_Handler.removeCallbacks(TimeUpdate);
                                Time_Handler = null;
                            }
                            showFinish();
                        }
                    }
                });
                builder.setNegativeButton("返回選單",null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                LinearLayout.LayoutParams buttonParamsP;
                LinearLayout.LayoutParams buttonParamsN;

                Button buttonPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                buttonParamsP = (LinearLayout.LayoutParams) buttonPositive.getLayoutParams();
                buttonParamsP.weight = 1;

                Button buttonNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                buttonParamsN = (LinearLayout.LayoutParams) buttonNegative.getLayoutParams();
                buttonParamsN.weight = 1;

            }
        });


        //----關卡資訊----
        Information_Background = new View(MainActivity.this);
        FrameLayout.LayoutParams Information_Background_Params = new FrameLayout.LayoutParams( DeviceInf.getScreenWidthPx(MainActivity.this), (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.10));
        Information_Background_Params.topMargin = (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.95);
        //Information_Background.setBackgroundColor(Color.argb(128, 0, 0, 0));
        Information_Background.setBackgroundResource(R.drawable.background_inf);
        mainLayout.addView(Information_Background, Information_Background_Params);

        level_text = new TextView(MainActivity.this);
        FrameLayout.LayoutParams level_text_Params = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        level_text_Params.topMargin = (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.96);
        level_text_Params.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*0.05);
        mainLayout.addView(level_text,level_text_Params);
        level_text.setText("第"+Integer.toString(level)+"關 ");
        level_text.setTypeface(Typeface.create("serif", Typeface.NORMAL));
        level_text.setTextColor(Color.YELLOW);
        level_text.setTextSize(15.0f);

        remainder_pieces = new TextView(MainActivity.this);
        FrameLayout.LayoutParams remainder_pieces_Params = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        remainder_pieces_Params.topMargin = (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.96);
        remainder_pieces_Params.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*0.22);
        mainLayout.addView(remainder_pieces, remainder_pieces_Params);
        remainder_pieces.setText("剩餘"+Integer.toString(piecesNum)+"個 ");
        remainder_pieces.setTypeface(Typeface.create("serif", Typeface.NORMAL));
        remainder_pieces.setTextColor(Color.WHITE);
        remainder_pieces.setTextSize(15.0f);

        game_steps_text = new TextView(MainActivity.this);
        FrameLayout.LayoutParams game_steps_text_Params = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        game_steps_text_Params.topMargin = (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.96);
        game_steps_text_Params.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*0.45);
        mainLayout.addView(game_steps_text, game_steps_text_Params);
        game_steps_text.setText("已走"+Integer.toString(game_steps)+"步 ");
        game_steps_text.setTypeface(Typeface.create("serif", Typeface.NORMAL));
        game_steps_text.setTextColor(Color.WHITE);
        game_steps_text.setTextSize(15.0f);

        time_text = new TextView(MainActivity.this);
        FrameLayout.LayoutParams time_text_Params = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        time_text_Params.topMargin = (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.96);
        time_text_Params.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*0.80);
        mainLayout.addView(time_text, time_text_Params);
        time_text.setTypeface(Typeface.createFromAsset (getAssets(), "fonts/time.ttf"));
        time_text.setText("00 : 00");
        time_text.setTextColor(Color.GREEN);
        time_text.setTextSize(20.0f);
    }

    //選擇方塊
    public int[][] setSelect(int userSelect){
        LevelArray levelArray = new LevelArray(level, userSelect);
        int[][] Array = levelArray.getArray();

        if(selectPieces != null) {
            selectPieces.setArray(Array);
        }
        return Array;
    }

    private void forDev(){
        showAssistLine = new Button(MainActivity.this);
        mainLayout.addView(showAssistLine);
        FrameLayout.LayoutParams mButtonParams = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mButtonParams.topMargin = (int) (DeviceInf.getScreenHeightPx(MainActivity.this)*0.85);
        mButtonParams.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this)*0.70);
        showAssistLine.setLayoutParams(mButtonParams);
        showAssistLine.setText(R.string.ON);
        showAssistLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pieces[CurrentNum].AssistLine){
                    pieces[CurrentNum].AssistLine = true;
                    showAssistLine.setText(R.string.OFF);
                }
                else{
                    pieces[CurrentNum].AssistLine = false;
                    showAssistLine.setText(R.string.ON);
                }
                pieces[CurrentNum].update();

                DecimalFormat nf = new DecimalFormat("+##;-##");
                nf.setGroupingSize(2); //設定數字是否會以逗號分組(ex:123,456,789)
                nf.setMaximumIntegerDigits(2); //設定數字最多幾位數
                nf.setMinimumIntegerDigits(1); //設定數字最少幾位數

                for(int i = 0 ; i < GridBoard.mRow ; i++ ){
                    Log.d("第"+Integer.toString(i)+"排",
                            nf.format(_GraphExist[0][i]) + ","+
                            nf.format(_GraphExist[1][i]) + ","+
                            nf.format(_GraphExist[2][i]) + ","+
                            nf.format(_GraphExist[3][i]) + ","+
                            nf.format(_GraphExist[4][i]) + ","+
                            nf.format(_GraphExist[5][i]) + ","+
                            nf.format(_GraphExist[6][i]) + ","+
                            nf.format(_GraphExist[7][i]) + ","+
                            nf.format(_GraphExist[8][i]) + ","+
                            nf.format(_GraphExist[9][i]) );
                }
                Log.d("-------------------", "-------------------");
            }
        });
    }

    public void setLocation(){
        gameLevel = new GameLevel();
        for (int i = 0; i < GridBoard.mColumn; i++)
            for (int j = 0; j < GridBoard.mRow; j++) {
                _GraphExist[i][j] = 0;
            }

        //---關卡設計(塗黑, 不允許放置點
        gameLevel.setLevel(level);

        for (int i = 0; i < GridBoard.mColumn; i++)
            for (int j = 0; j < GridBoard.mRow; j++) {
                ExistTemp[i][j] = _GraphExist[i][j];
            }
    }

    private void GeneratePieces(){

        for(int i = 0 ; i < piecesNum ; i++ ) {
            pieces[i] = new Pieces(MainActivity.this);
            mainLayout.addView(pieces[i]);
            FrameLayout.LayoutParams mPiecesParams = (FrameLayout.LayoutParams) pieces[i].getLayoutParams();
            mPiecesParams.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this) * 0.05);
            mPiecesParams.topMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this) * 1.0);
            pieces[i].setLayoutParams(mPiecesParams);
            pieces[i].setVisibility(View.GONE);
        }
        pieces[CurrentNum].setVisibility(View.VISIBLE);
    }

    //重新生成
    private void resetPieces(int Num){
        selectGraph.setEnabled(true);
        selectGraph.setVisibility(View.VISIBLE);
        removePieces();
        pieces = null;
        game_steps = 0;
        hasStartTime = false;

        for (int i = 0; i < GridBoard.mColumn; i++)
            for (int j = 0; j < GridBoard.mRow; j++) {
                inIllegalRegion[i][j] = false;
            }//非法區域初始化 -- 沒有多聯骨牌進入非法區域

        if(Num == 0)//保證元素生成
            piecesNum = 1;
        else
            piecesNum = Num;


        pieces = new Pieces[piecesNum];
        for(int i = 0 ; i < piecesNum ; i++ ) {
            pieces[i] = new Pieces(MainActivity.this);
            mainLayout.addView(pieces[i]);
            pieces[i].setArray(setSelect(select));
            FrameLayout.LayoutParams mPiecesParams = (FrameLayout.LayoutParams) pieces[i].getLayoutParams();
            mPiecesParams.leftMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this) * 0.05);
            mPiecesParams.topMargin = (int) (DeviceInf.getScreenWidthPx(MainActivity.this) * 1.0);
            pieces[i].setLayoutParams(mPiecesParams);
            pieces[i].setVisibility(View.GONE);
        }
        CurrentNum = 0;
        pieces[CurrentNum].setVisibility(View.VISIBLE);
    }

    //移除方格
    private void removePieces(){
        for(int i = 0 ; i < piecesNum ; i++ ) {
            mainLayout.removeView(pieces[i]);
        }
    }

    //-----------執行緒-----------
    private Runnable gameUpdate = new Runnable() {
        @Override
        public void run() {
            int Count_for_InRegion = 0;

            //Update Information
            level_text.setText("第"+Integer.toString(level)+"關 ");
            if(pieces != null){
                for(int i = 0 ; i < pieces.length ; i ++)
                    if(pieces[i].inRegion)
                        Count_for_InRegion ++;
            }
            remainder_pieces.setText("剩餘"+Integer.toString(piecesNum - Count_for_InRegion)+"個 ");
            game_steps_text.setText("已走"+Integer.toString(game_steps)+"步 ");

            //Logic handle
            if(pieces != null ){
                if(pieces[0].inRegion){
                    selectGraph.setEnabled(false);
                    selectGraph.setVisibility(View.INVISIBLE);
                }
            }

            if(isLeave && pieces != null){
                if( CurrentNum +1 < piecesNum)
                    pieces[++CurrentNum].setVisibility(View.VISIBLE);
                isLeave = false;
                GeneratingNext = true;
            }

            if(GeneratingNext && (0 < CurrentNum) && (CurrentNum < piecesNum) && pieces != null){
                if(!pieces[CurrentNum-1].isOnGraph && !pieces[CurrentNum-1].inRegion) {
                    pieces[CurrentNum--].setVisibility(View.GONE);
                    GeneratingNext = false;
                }
            }


            if(CurrentNum == piecesNum-1 && pieces[CurrentNum].inRegion && LastGraphDown && pieces != null){
                CheckPassed();
                LastGraphDown = false;
            }

            handler_Game.postDelayed(gameUpdate, (long)1/60*1000);
        }
    };

    public void CheckPassed(){
        Log.d("通關","執行緒進入判定");
        Boolean isBreak = false;
        Boolean Passed = false;
        int temp_j = 0;

        for (int i = 0; i < GridBoard.mColumn; i++) {
            for (int j = 0; j < GridBoard.mRow; j++) {
                temp_j = j;
                if (ExistTemp[i][j] == 0) {
                    if (_GraphExist[i][j] != 1) {
                        isBreak = true;
                        Passed = false;
                        break;
                    }
                }
                if(inIllegalRegion[i][j]){
                    isBreak = true;
                    Passed = false;
                    break;
                }
            }
            if(isBreak)
                break;
            if(i == GridBoard.mColumn - 1 && temp_j == GridBoard.mRow - 1 && !isBreak){
                Passed = true;
            }
        }

        if(Passed){
            gameData.saveData(level, true, game_steps , play_min, play_sec);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            GridLayout passedLayout = new GridLayout(MainActivity.this);
            builder.setTitle(R.string.congratulations);
            builder.setCancelable(false);
            builder.setPositiveButton("下一關", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertMessage = false;
                    if(level + 1 <= gameLevel.MAX_LEVEL) {
                        gameLevel.setLevel(++level);
                        for (int i = 0; i < GridBoard.mColumn; i++)
                            for (int j = 0; j < GridBoard.mRow; j++) {
                                ExistTemp[i][j] = _GraphExist[i][j];
                            }
                        gridBoard.invalidate();//更新面板
                        select = 1;
                        int Array[][] = setSelect(select);
                        PiecesGridNumber = 0;
                        for(int i = 0 ; i < Pieces.mColumn_Num ; i++ ){
                            for(int j = 0 ; j < Pieces.mRow_Num ; j++){
                                if(Array[i][j] == 1)
                                    PiecesGridNumber ++ ;
                            }
                        }
                        resetPieces( (int) (gridBoard.getCount()/PiecesGridNumber));
                    }else{
                        Log.d("關卡資訊","完成");
                        Game_Finish = true;
                        mRotate.setEnabled(false);
                        selectGraph.setEnabled(false);
                        Again.setEnabled(false);
                        if(handler_Game != null) {
                            handler_Game.removeCallbacks(gameUpdate);
                            handler_Game = null;
                        }//移除執行緒運行
                        if(Time_Handler != null) {
                            Time_Handler.removeCallbacks(TimeUpdate);
                            Time_Handler = null;
                        }
                        showFinish();
                    }
                }
            });
            builder.setNegativeButton("返回選單", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertMessage = false;

                    //關閉執行緒
                    if(handler_Game != null) {
                        handler_Game.removeCallbacks(gameUpdate);
                        handler_Game = null;
                    }
                    if(Time_Handler != null) {
                        Time_Handler.removeCallbacks(TimeUpdate);
                        Time_Handler = null;
                    }
                    //切回選單
                    BackToMenu();
                }
            });

            //通關Layout設計
            builder.setView(passedLayout,0,0,0,0);
            TextView playedTime = new TextView(MainActivity.this);
            TextView playedSteps = new TextView(MainActivity.this);
            playedTime.setText("【"+getResources().getString(R.string.time) + "】" + String.valueOf(play_min) + "：" + String.valueOf(play_sec) );
            playedSteps.setText("【"+getResources().getString(R.string.steps)+"】" + String.valueOf(game_steps));

            passedLayout.setColumnCount(1);
            passedLayout.setRowCount(2);
            passedLayout.addView(playedTime);
            passedLayout.addView(playedSteps);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            AlertMessage = true;

            Passed = false;

            //版面設計
            LinearLayout.LayoutParams buttonParamsP;
            LinearLayout.LayoutParams buttonParamsN;

            Button buttonPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            buttonParamsP = (LinearLayout.LayoutParams) buttonPositive.getLayoutParams();
            buttonParamsP.weight = 1;

            Button buttonNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            buttonParamsN = (LinearLayout.LayoutParams) buttonNegative.getLayoutParams();
            buttonParamsN.weight = 1;
        }
    }

    public void showFinish(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);//禁止返回鍵
        builder.setTitle("關卡完成");
        builder.setMessage("恭喜您已經完成所有關卡!!!\n\n更多關卡敬請關注我們!");
        builder.setPositiveButton("返回選單", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BackToMenu();
            }
        });

        AlertDialog alertDialog =  builder.create();
        alertDialog.show();
    }

    private Runnable TimeUpdate = new Runnable() {
        @Override
        public void run() {

            if(pieces != null) {
                if(pieces[0].getFirst()) {
                    if(!hasStartTime){
                        startTime = System.currentTimeMillis();
                        hasStartTime = true;
                    }else{
                        Long spentTime = System.currentTimeMillis() - startTime;

                        Long min = (spentTime / 1000) / 60;
                        play_min = min;

                        Long sec = (spentTime / 1000) % 60;
                        play_sec = sec;

                        if(!AlertMessage)
                            time_text.setText(numberFormat.format(min)+" : "+numberFormat.format(sec));
                    }
                }else{
                    time_text.setText( "00 : 00" );
                }
            }
            Time_Handler.postDelayed(TimeUpdate,  (long)1/23*1000);
        }
    };
}
