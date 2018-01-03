package com.test.ncyu.mathdroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Pieces extends View {
    public static final int mColumn_Num = 4;
    public static final int mRow_Num = 4;
    private int[][] PiecesArray = new int[mColumn_Num][mRow_Num];
    private int[][] tempArray = new int[mColumn_Num][mRow_Num];
    private int size;
    private int InfX,SupX,InfY,SupY;
    private int pieces2board_i, pieces2board_j;

    //Moving
    private boolean isFirst = false;
    public boolean isOnGraph = false;
    public boolean inRegion = false;
    private int down_i, down_j;
    private int inner_i, inner_j;
    private float x, y;
    private int temp_x, temp_y;
    private int mx, my;
    private float end_x, end_y;
    private int end_i, end_j;
    private int grid_x, grid_y;
    private int Red,Green,Blue;

    //Dev
    public boolean AssistLine = false;

    public Pieces(Context context){
        super(context);
        size = GridBoard.GridW;
        InfX = InfY = 0;
        end_i = end_j = -100;
        SupX = mColumn_Num*size;
        SupY = mRow_Num*size;
        setElevation(1);
        setColor();
        this.setBackgroundColor(Color.argb (0 ,255 , 255, 255));
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
                    //paint.setColor(Color.rgb(255, 128, 0));
                    paint.setColor(Color.rgb(Red ,Green ,Blue ));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRoundRect(i*(size), j*(size),(i+1)*(size), (j+1)*(size), 10, 10, paint);

                    if(isOnGraph) {
                        paint.setStrokeWidth(6.0f);
                        paint.setColor(Color.CYAN);
                    }else{
                        paint.setStrokeWidth(3.0f);
                        paint.setColor(Color.BLACK);
                    }
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawRoundRect(i*(size), j*(size),(i+1)*(size), (j+1)*(size), 10, 10, paint);


                    //繪製放置於非法區域
                    paint.setStyle(Paint.Style.FILL);
                    paint.setStrokeWidth(6.0f);
                    paint.setColor(Color.argb(150,0,0,0));

                    try {
                        if (inRegion && !isOnGraph) {
                            pieces2board_i = i + ( (getLeft()-MainActivity.gridBoard.getLeft()) / size) ;
                            pieces2board_j = j + ( (getTop()-MainActivity.gridBoard.getTop()) / size);
                            if (MainActivity._GraphExist[pieces2board_i][pieces2board_j] == -1) {
                                canvas.drawRoundRect(i*(size), j*(size),(i+1)*(size), (j+1)*(size), 10, 10, paint);
                                paint.setColor(Color.WHITE);
                                canvas.drawLine(i * (size) + (int)((size)*0.2), j * (size)+(int)((size)*0.2), (i + 1) * (size)-(int)((size)*0.2), (j + 1) * (size)-(int)((size)*0.2), paint);
                                canvas.drawLine(i * (size) + (int)((size)*0.2), (j+1) * (size) - (int)((size)*0.2), (i + 1) * (size) - (int)((size)*0.2), j* (size) + (int)((size)*0.2), paint);
                            }
                        }
                    }catch (Exception e){}
                }
            }
        }
        findBound();

        if(AssistLine) {
            //輔助線
            /*paint.setColor(Color.argb(100, 0, 0, 0));
            paint.setStrokeWidth((float) 2.0);
            for (int i = 1; i < mColumn_Num; i++)
                canvas.drawLine((size) * i, 0, (size) * i, getHeight(), paint);

            for (int i = 1; i < mRow_Num; i++)
                canvas.drawLine(0, (size) * i, getWidth(), (size) * i, paint);*/

            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth((float) 10.0);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

            //判定邊界輔助線
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth((float) 7.5);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(InfX, InfY, SupX, SupY, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(size*mColumn_Num, size*mRow_Num);
    }

    @Override
    protected void onLayout (boolean changed, int left, int top, int right, int bottom){
        super.onLayout(changed, left, top, right, bottom);
        if(changed && isFirst) {
            if (isOnGraph) {
                layout(mx, my, mx + getWidth(), my + getHeight());
            }else if(inRegion) {
                layout(grid_x, grid_y,
                        grid_x + getWidth(), grid_y + getHeight());
            }
        }
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

        isFirst = false;
        isOnGraph = false;
        inRegion = false;
    }

    public void setArray(int[][] Array){
        for(int i = 0 ; i < mColumn_Num ; i++){
            for(int j = 0 ; j < mRow_Num ; j++){
                PiecesArray[i][j] = Array[i][j];
            }
        }
        invalidate();
    }

    public int[][] getArray(){
        return PiecesArray;
    }

    //旋轉
    public void touchRotate(){
        if(!inRegion ) {
            for (int i = 0; i < mColumn_Num; i++) {
                for (int j = 0; j < mRow_Num; j++) {
                    tempArray[i][j] = PiecesArray[i][j];
                }
            }

            for (int i = 0; i < mColumn_Num; i++) {
                for (int j = 0; j < mRow_Num; j++) {
                    PiecesArray[j][mColumn_Num - 1 - i] = tempArray[i][j];
                }
            }
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect RegionRect = new Rect(MainActivity.gridBoard.getLeft(), MainActivity.gridBoard.getTop(),
                                    MainActivity.gridBoard.getWidth(), MainActivity.gridBoard.getHeight() );
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                x = event.getX();
                y = event.getY();
                down_i = (int) ( x / size);
                down_j = (int) ( y / size);
                isOnGraph = false;
                MainActivity.isLeave = true;

                //判斷是否在圖形內
                if( PiecesArray[ down_i ][ down_j ] == 1 ) {
                    isOnGraph = true;
                    setElevation(1);
                    //移除位置
                    //Log.d("最後座標", "( i , j ) = (" + Integer.toString(end_i) +" , "+ Integer.toString(end_j) + ")");
                    if(end_i != -100 && end_j != -100) {
                        for (int i = 0; i < mColumn_Num; i++) {
                            for (int j = 0; j < mRow_Num; j++) {
                                if (PiecesArray[i][j] == 1) {
                                    inner_i = i + end_i;
                                    inner_j = j + end_j;

                                    if(  (0 <= inner_i) && (inner_i < GridBoard.mColumn) &&
                                            (0 <= inner_j) && (inner_j < GridBoard.mRow) ){

                                        if(MainActivity._GraphExist[inner_i][inner_j] != -1)
                                            MainActivity._GraphExist[inner_i][inner_j] --;
                                        else
                                            MainActivity.inIllegalRegion[inner_i][inner_j] = false;
                                       // Log.d("消去座標", "( i , j ) = (" + Integer.toString(inner_i) +" , "+ Integer.toString(inner_j) + ")");
                                       // Log.d("-------------------", "-------------------");
                                    }

                                }
                            }
                        }
                    }
                }else {
                    setElevation(0);
                }


                temp_x =  MainActivity.gridBoard.getLeft() +  MainActivity.gridBoard.getWidth() - SupX;
                temp_y =  MainActivity.gridBoard.getTop() +  MainActivity.gridBoard.getHeight() - SupY;
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE :
                if(isOnGraph) {
                    mx = (int) (event.getRawX() - x);
                    my = (int) (event.getRawY() - y);
                    if(RegionRect.contains((int)event.getRawX(), (int)event.getRawY())){
                        inRegion = true;
                    }
                    EnableBound(inRegion);
                    setZ(30.0f);
                    layout(mx, my, mx + getWidth(), my + getHeight());
                    isFirst = true;
                }
                break;

            case MotionEvent.ACTION_UP :
                if(isOnGraph) {
                    if(inRegion) {
                        MainActivity.game_steps ++;//算計步
                        isOnGraph = false;
                        EnableBound(true);
                        end_x = mx - MainActivity.gridBoard.getLeft() ;
                        end_y = my - MainActivity.gridBoard.getTop() ;

                        end_i = Math.round (end_x / size);
                        end_j = Math.round  (end_y / size);

                        grid_x = end_i * size + MainActivity.gridBoard.getLeft();
                        grid_y = end_j * size + MainActivity.gridBoard.getTop();

                        layout(grid_x, grid_y,
                                grid_x + getWidth(), grid_y + getHeight());
                        setZ(0.0f);

                        //設定位置參數
                        for(int i = 0 ; i <  mColumn_Num ; i++){
                            for(int j = 0 ; j < mRow_Num ; j++){
                                if(PiecesArray[i][j] == 1){
                                    inner_i = i + end_i;
                                    inner_j = j + end_j;
                                    if(  (0 <= inner_i) && (inner_i < GridBoard.mColumn) &&
                                            (0 <= inner_j) && (inner_j < GridBoard.mRow) ){

                                        if(MainActivity._GraphExist[inner_i][inner_j] != -1)
                                            MainActivity._GraphExist[inner_i][inner_j] ++;
                                        else
                                            MainActivity.inIllegalRegion[inner_i][inner_j] = true;
                                        //Log.d("著色座標", "( i , j ) = (" + Integer.toString(inner_i) +" , "+ Integer.toString(inner_j) + ")");
                                        //Log.d("-------------------", "-------------------");
                                    }
                                }
                            }
                        }
                    }else{
                        requestLayout();
                    }
                    //判定通關
                    if(MainActivity.CurrentNum == MainActivity.piecesNum - 1){
                        Log.d("通關","已進入判定");
                        MainActivity.LastGraphDown = true;
                    }
                    isOnGraph = false;
                    invalidate();
                }
                break;
        }

        if(getElevation() == 0){
            setElevation(1);
            return false;
        }
        return true;
    }

    /*public void checkIndex(){
        if (end_i >= GridBoard.mColumn)
            end_i =  GridBoard.mColumn - 1;
        if (end_j >= GridBoard.mRow)
            end_j =  GridBoard.mRow - 1;

        if (end_i < 0)
            end_i = 0;
        if (end_j < 0)
            end_j = 0;
    }*/

    public void EnableBound(boolean flag){
        if(flag){
            if (mx < MainActivity.gridBoard.getLeft() - InfX)
                mx = (int) MainActivity.gridBoard.getLeft() - InfX;
            if (my < MainActivity.gridBoard.getTop() - InfY)
                my = (int) MainActivity.gridBoard.getTop() - InfY;
            if (mx > temp_x )
                mx = (int) temp_x;
            if (my > temp_y )
                my = (int) temp_y ;
        }
    }

    //尋找邊界, 相對圖形位置
    private void findBound(){
        //X1
       for(int i = 0 ; i < mColumn_Num ; i++ ){
           boolean hasValue = false;
           for(int j = 0 ; j < mRow_Num ; j ++ ){
               if(PiecesArray[i][j] == 1){
                   InfX = i*size;
                   hasValue = true;
                   break;
               }
           }
           if(hasValue)
               break;
       }
        //Y1
        for(int j = 0 ; j < mRow_Num ; j++ ){
            boolean hasValue = false;
            for(int i = 0 ; i < mColumn_Num ; i ++ ){
                if(PiecesArray[i][j] == 1){
                    InfY = j*size;
                    hasValue = true;
                    break;
                }
            }
            if(hasValue)
                break;
        }

        //X2
        for(int i = 0 ; i < mColumn_Num ; i++ ){
            boolean hasValue = false;
            for(int j = 0 ; j < mRow_Num ; j ++ ){
                if(PiecesArray[mColumn_Num - 1 - i][mRow_Num - 1 - j] == 1){
                    SupX = (mColumn_Num - i)*size;
                    hasValue = true;
                    break;
                }
            }
            if(hasValue)
                break;
        }

        //Y2
        for(int j = 0 ; j < mRow_Num ; j++ ){
            boolean hasValue = false;
            for(int i = 0 ; i < mColumn_Num ; i ++ ){
                if(PiecesArray[mColumn_Num - 1 -i][mRow_Num - 1 - j] == 1){
                    SupY = (mRow_Num - j)*size;
                    hasValue = true;
                    break;
                }
            }
            if(hasValue)
                break;
        }
    }

    public int getCount(){
        int Count = 0;
        for(int i = 0 ; i < mColumn_Num ; i++){
            for(int j = 0 ; j < mRow_Num ; j++){
                if( PiecesArray[i][j] == 1 )
                   Count ++ ;
            }
        }
        return Count;
    }

    private void setColor(){
        Red = 0;
        Green = 0;
        Blue = 0;
        int select;

        select = (int) (Math.random()*100 + 1);

        switch (select%5){
            case 0:
                Red = 153;
                Green = 204;
                Blue = 255;
                break;
            case 1:
                Red = 123;
                Green = 254;
                Blue = 200;
                break;
            case 2:
                Red = 148;
                Green = 255;
                Blue = 148;
                break;
            case 3:
                Red = 255;
                Green = 255;
                Blue = 112;
                break;
            case 4:
                Red = 255 ;
                Green = 143;
                Blue = 87;
                break;
        }
        invalidate();
    }

    public boolean getFirst(){
        return isFirst;
    }
}
