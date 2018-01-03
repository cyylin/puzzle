package com.test.ncyu.mathdroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class GridBoard extends View {
    private int mScreenH;
    private int mScreenW;
    private int mBoardH;
    private int mBoardW;
    public static final int mColumn = 10;
    public static final int mRow = 10;
    public static int GridW;
    public static int GridH;

    private Paint paint;

    public GridBoard(Context context){
        super(context);
        mScreenH = DeviceInf.getScreenHeightPx(context);
        mScreenW = DeviceInf.getScreenWidthPx(context);
        mBoardH = (int)(mScreenH*0.75/mRow)*mRow;
        mBoardW = (int)(mScreenW*0.9/mColumn)*mColumn;
        GridW = mBoardW/mColumn;
        GridH = mBoardH/mRow;
        paint = new Paint(); //創建畫筆
        this.setBackgroundColor(Color.TRANSPARENT);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Paint paint = new Paint(); //創建畫筆
        paint.setColor(Color.argb(100, 0, 0, 0));
        paint.setStrokeWidth((float) 3.0);
        paint.setAntiAlias(true);

       /* for(int i = 1 ; i < mColumn ; i++)
            canvas.drawLine( (GridW)*i , 0, (GridW)*i ,getHeight(), paint);

        for(int i = 1 ; i < mRow ; i++)
            canvas.drawLine( 0, (GridW)*i, getWidth(), (GridW)*i,paint);*/

        for(int i = 0 ; i < mColumn ; i ++ ){
            for(int j = 0 ; j < mRow ; j ++ ){
                if(MainActivity._GraphExist [i][j] != -1){

                    //內部
                    paint.setColor(Color.argb(150,255,255,255));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect( GridW*i, GridW*j, GridW*(i+1), GridW*(j+1),paint);

                    //線框
                    paint.setColor(Color.argb(75,0,0,0));
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawRect( GridW*i, GridW*j, GridW*(i+1), GridW*(j+1),paint);
                }
            }
        }

        /*paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float) 10.0);
        paint.setColor(Color.RED);
        canvas.drawLine( 0, getHeight(), getWidth(), getHeight(),paint);*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(mBoardW, mBoardW);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom){
        super.onLayout(changed, left, top, right, bottom);
    }

    public void update(){
        invalidate();
    }

    public int getCount(){
        int Count = 0;
        for(int i = 0 ; i < mColumn ; i++){
            for(int j = 0 ; j < mRow ; j++){
                if( MainActivity._GraphExist[i][j] != -1 )
                    Count ++ ;
            }
        }
        return Count;
    }
}
