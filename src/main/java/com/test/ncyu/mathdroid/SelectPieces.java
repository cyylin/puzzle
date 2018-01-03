package com.test.ncyu.mathdroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class SelectPieces extends View {

    private final int mColumn_Num = Pieces.mColumn_Num;
    private final int mRow_Num = Pieces.mRow_Num;
    private int[][] PiecesArray = new int[mColumn_Num][mRow_Num];
    private int size;

    public SelectPieces(Context context){
        super(context);
        size = GridBoard.GridW;
        this.setBackgroundColor(Color.WHITE);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(); //創建畫筆
        paint.setAntiAlias(true);

        paint.setStrokeWidth((float) 3.0);
        for(int i = 0 ; i < mColumn_Num ; i++){
            for(int j = 0 ; j < mRow_Num ; j++){
                if(PiecesArray[i][j] == 1){
                    paint.setColor(Color.CYAN);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(i*(size), j*(size),(i+1)*(size), (j+1)*(size), paint);

                    paint.setColor(Color.BLACK);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(3);
                    canvas.drawRect(i*(size), j*(size),(i+1)*(size), (j+1)*(size), paint);
                };
            }
        }

        paint.setColor(Color.argb(100, 0, 0, 0));
        paint.setStrokeWidth((float) 2.0);

        for(int i = 1 ; i < mColumn_Num ; i++)
            canvas.drawLine( (size)*i , 0, (size)*i ,getHeight(), paint);

        for(int i = 1 ; i < mRow_Num ; i++)
            canvas.drawLine( 0, (size)*i, getWidth(), (size)*i,paint);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float) 10.0);
        canvas.drawRect(0, 0 , getWidth(), getHeight(), paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(size*mColumn_Num, size*mRow_Num);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom){
        super.onLayout(changed, left, top, right, bottom);
    }

    public void update(){
        invalidate();
    }

    private void init(){
        for(int i = 0 ; i < mColumn_Num ; i++){
            for(int j = 0 ; j < mRow_Num ; j++){
                PiecesArray[i][j] = 0;
            }
        }

        PiecesArray[0][1] = 1;
        PiecesArray[0][2] = 1;
        PiecesArray[0][3] = 1;
        PiecesArray[1][3] = 1;
    }

    public void setArray(int[][] Array){
        for(int i = 0 ; i < mColumn_Num ; i++){
            for(int j = 0 ; j < mRow_Num ; j++){
                PiecesArray[i][j] = Array[i][j];
            }
        }
        invalidate();
    }
}
