package com.test.ncyu.mathdroid;

public class GameLevel {
    private int currentLevel = 1;
    public static final int MAX_LEVEL = 12;
    protected boolean hasLevel = true;

    public GameLevel(){
        init();
    }

    //初始化
    public void init(){
        currentLevel = 1;
        hasLevel = true;
    }

    public void setLevel(int level){
        currentLevel = level;
        LevelInf();
    }

    public int getLevel(){
        return currentLevel;
    }

    private void LevelInf(){

        //方格初始化
        for (int i = 0; i < GridBoard.mColumn; i++)
            for (int j = 0; j < GridBoard.mRow; j++) {
                MainActivity._GraphExist[i][j] = 0;
            }

        switch (currentLevel){
            case 1:
                Lv01();
                break;
            case 2:
                Lv02();
                break;
            case 3:
                Lv03();
                break;
            case 4:
                Lv04();
                break;
            case 5:
                Lv05();
                break;
            case 6:
                Lv06();
                break;
            case 7:
                Lv07();
                break;
            case 8:
                Lv08();
                break;
            case 9:
                Lv09();
                break;
            case 10:
                Lv10();
                break;
            case 11:
                Lv11();
                break;
            case 12:
                Lv12();
                break;
            case 13:
                Lv13();
                break;
            case 14:
                Lv14();
                break;
            case 15:
                Lv15();
                break;
            case 16:
                Lv16();
                break;
            default:
                hasLevel = false;
                break;
        }
    }

    private void Lv01(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[0][7] = 0;
        MainActivity._GraphExist[0][8] = 0;
        MainActivity._GraphExist[1][7] = 0;
        MainActivity._GraphExist[1][8] = 0;

    }

    private void Lv02(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[1][6] = 0;
        MainActivity._GraphExist[1][7] = 0;
        MainActivity._GraphExist[1][8] = 0;
        MainActivity._GraphExist[2][6] = 0;
        MainActivity._GraphExist[2][7] = 0;
        MainActivity._GraphExist[2][8] = 0;

    }

    private void Lv03(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[4][4] = 0;
        MainActivity._GraphExist[5][4] = 0;

        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;

        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[4][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;

        MainActivity._GraphExist[4][7] = 0;
        MainActivity._GraphExist[5][7] = 0;

    }

    private void Lv04(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[3][3] = 0;
        MainActivity._GraphExist[3][4] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[3][6] = 0;

        MainActivity._GraphExist[4][3] = 0;
        MainActivity._GraphExist[4][4] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[4][6] = 0;

        MainActivity._GraphExist[5][3] = 0;
        MainActivity._GraphExist[5][4] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[5][6] = 0;

        MainActivity._GraphExist[6][3] = 0;
        MainActivity._GraphExist[6][4] = 0;
        MainActivity._GraphExist[6][5] = 0;
        MainActivity._GraphExist[6][6] = 0;



    }

    private void Lv05(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[3][2] = 0;
        MainActivity._GraphExist[4][2] = 0;
        MainActivity._GraphExist[5][2] = 0;

        MainActivity._GraphExist[2][3] = 0;
        MainActivity._GraphExist[3][3] = 0;
        MainActivity._GraphExist[5][3] = 0;
        MainActivity._GraphExist[6][3] = 0;

        MainActivity._GraphExist[2][4] = 0;
        MainActivity._GraphExist[6][4] = 0;

        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;

        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[4][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
    }

    private void Lv06(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[3][2] = 0;
        MainActivity._GraphExist[5][2] = 0;

        MainActivity._GraphExist[3][3] = 0;
        MainActivity._GraphExist[5][3] = 0;

        MainActivity._GraphExist[2][4] = 0;
        MainActivity._GraphExist[3][4] = 0;
        MainActivity._GraphExist[4][4] = 0;
        MainActivity._GraphExist[5][4] = 0;
        MainActivity._GraphExist[6][4] = 0;

        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;

        MainActivity._GraphExist[2][6] = 0;
        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;

        MainActivity._GraphExist[2][7] = 0;
        MainActivity._GraphExist[4][7] = 0;
        MainActivity._GraphExist[6][7] = 0;

        MainActivity._GraphExist[2][8] = 0;
        MainActivity._GraphExist[3][8] = 0;
        MainActivity._GraphExist[4][8] = 0;
        MainActivity._GraphExist[5][8] = 0;
        MainActivity._GraphExist[6][8] = 0;
    }

    private void Lv07(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[6][2] = 0;
        MainActivity._GraphExist[7][2] = 0;

        MainActivity._GraphExist[2][3] = 0;
        MainActivity._GraphExist[6][3] = 0;

        MainActivity._GraphExist[2][4] = 0;
        MainActivity._GraphExist[3][4] = 0;
        MainActivity._GraphExist[4][4] = 0;
        MainActivity._GraphExist[5][4] = 0;
        MainActivity._GraphExist[6][4] = 0;
        MainActivity._GraphExist[7][4] = 0;

        MainActivity._GraphExist[1][5] = 0;
        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;
        MainActivity._GraphExist[7][5] = 0;

        MainActivity._GraphExist[1][6] = 0;
        MainActivity._GraphExist[2][6] = 0;
        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[4][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;

        MainActivity._GraphExist[3][7] = 0;
        MainActivity._GraphExist[4][7] = 0;
        MainActivity._GraphExist[5][7] = 0;
        MainActivity._GraphExist[6][7] = 0;

        MainActivity._GraphExist[3][8] = 0;
        MainActivity._GraphExist[4][8] = 0;
        MainActivity._GraphExist[5][8] = 0;
    }

    private void Lv08(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[3][0] = 0;
        MainActivity._GraphExist[4][0] = 0;
        MainActivity._GraphExist[5][0] = 0;
        MainActivity._GraphExist[6][0] = 0;
        MainActivity._GraphExist[7][0] = 0;
        MainActivity._GraphExist[8][0] = 0;

        MainActivity._GraphExist[3][1] = 0;
        MainActivity._GraphExist[4][1] = 0;
        MainActivity._GraphExist[5][1] = 0;
        MainActivity._GraphExist[6][1] = 0;
        MainActivity._GraphExist[7][1] = 0;
        MainActivity._GraphExist[8][1] = 0;

        MainActivity._GraphExist[2][2] = 0;
        MainActivity._GraphExist[3][2] = 0;
        MainActivity._GraphExist[4][2] = 0;
        MainActivity._GraphExist[5][2] = 0;
        MainActivity._GraphExist[6][2] = 0;
        MainActivity._GraphExist[7][2] = 0;

        MainActivity._GraphExist[0][3] = 0;
        MainActivity._GraphExist[1][3] = 0;
        MainActivity._GraphExist[2][3] = 0;
        MainActivity._GraphExist[3][3] = 0;
        MainActivity._GraphExist[4][3] = 0;
        MainActivity._GraphExist[5][3] = 0;
        MainActivity._GraphExist[6][3] = 0;
        MainActivity._GraphExist[7][3] = 0;

        MainActivity._GraphExist[0][4] = 0;
        MainActivity._GraphExist[1][4] = 0;
        MainActivity._GraphExist[2][4] = 0;
        MainActivity._GraphExist[3][4] = 0;
        MainActivity._GraphExist[4][4] = 0;
        MainActivity._GraphExist[5][4] = 0;
        MainActivity._GraphExist[6][4] = 0;
        MainActivity._GraphExist[7][4] = 0;

        MainActivity._GraphExist[0][5] = 0;
        MainActivity._GraphExist[1][5] = 0;
        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;
        MainActivity._GraphExist[7][5] = 0;

        MainActivity._GraphExist[2][6] = 0;
        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[4][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;
        MainActivity._GraphExist[7][6] = 0;

    }

    private void Lv09(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[5][0] = 0;

        MainActivity._GraphExist[3][1] = 0;
        MainActivity._GraphExist[4][1] = 0;
        MainActivity._GraphExist[5][1] = 0;
        MainActivity._GraphExist[6][1] = 0;

        MainActivity._GraphExist[3][2] = 0;
        MainActivity._GraphExist[4][2] = 0;
        MainActivity._GraphExist[5][2] = 0;
        MainActivity._GraphExist[6][2] = 0;

        MainActivity._GraphExist[2][3] = 0;
        MainActivity._GraphExist[3][3] = 0;
        MainActivity._GraphExist[4][3] = 0;
        MainActivity._GraphExist[5][3] = 0;
        MainActivity._GraphExist[6][3] = 0;
        MainActivity._GraphExist[7][3] = 0;

        MainActivity._GraphExist[2][4] = 0;
        MainActivity._GraphExist[3][4] = 0;
        MainActivity._GraphExist[4][4] = 0;
        MainActivity._GraphExist[5][4] = 0;
        MainActivity._GraphExist[6][4] = 0;
        MainActivity._GraphExist[7][4] = 0;
        MainActivity._GraphExist[8][4] = 0;

        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;
        MainActivity._GraphExist[7][5] = 0;

        MainActivity._GraphExist[1][6] = 0;
        MainActivity._GraphExist[2][6] = 0;
        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[4][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;
        MainActivity._GraphExist[7][6] = 0;

        MainActivity._GraphExist[5][7] = 0;
        MainActivity._GraphExist[6][7] = 0;
        MainActivity._GraphExist[7][7] = 0;

        MainActivity._GraphExist[5][8] = 0;
        MainActivity._GraphExist[6][8] = 0;
    }

    private void Lv10(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[2][1] = 0;
        MainActivity._GraphExist[3][1] = 0;
        MainActivity._GraphExist[6][1] = 0;
        MainActivity._GraphExist[7][1] = 0;

        MainActivity._GraphExist[2][2] = 0;
        MainActivity._GraphExist[3][2] = 0;
        MainActivity._GraphExist[4][2] = 0;
        MainActivity._GraphExist[5][2] = 0;
        MainActivity._GraphExist[6][2] = 0;
        MainActivity._GraphExist[7][2] = 0;

        MainActivity._GraphExist[2][3] = 0;
        MainActivity._GraphExist[3][3] = 0;
        MainActivity._GraphExist[4][3] = 0;
        MainActivity._GraphExist[5][3] = 0;
        MainActivity._GraphExist[6][3] = 0;
        MainActivity._GraphExist[7][3] = 0;

        MainActivity._GraphExist[1][4] = 0;
        MainActivity._GraphExist[2][4] = 0;
        MainActivity._GraphExist[3][4] = 0;
        MainActivity._GraphExist[4][4] = 0;
        MainActivity._GraphExist[5][4] = 0;
        MainActivity._GraphExist[6][4] = 0;
        MainActivity._GraphExist[7][4] = 0;

        MainActivity._GraphExist[1][5] = 0;
        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;
        MainActivity._GraphExist[7][5] = 0;

        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[4][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;
        MainActivity._GraphExist[7][6] = 0;

        MainActivity._GraphExist[3][7] = 0;
        MainActivity._GraphExist[4][7] = 0;
        MainActivity._GraphExist[5][7] = 0;
        MainActivity._GraphExist[6][7] = 0;
        MainActivity._GraphExist[7][7] = 0;

        MainActivity._GraphExist[6][8] = 0;
        MainActivity._GraphExist[7][8] = 0;

    }

    private void Lv11(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[5][0] = 0;

        MainActivity._GraphExist[3][1] = 0;
        MainActivity._GraphExist[5][1] = 0;

        MainActivity._GraphExist[3][2] = 0;
        MainActivity._GraphExist[4][2] = 0;
        MainActivity._GraphExist[5][2] = 0;
        MainActivity._GraphExist[6][2] = 0;
        MainActivity._GraphExist[7][2] = 0;
        MainActivity._GraphExist[8][2] = 0;

        MainActivity._GraphExist[3][3] = 0;
        MainActivity._GraphExist[4][3] = 0;
        MainActivity._GraphExist[5][3] = 0;
        MainActivity._GraphExist[6][3] = 0;
        MainActivity._GraphExist[7][3] = 0;
        MainActivity._GraphExist[8][3] = 0;

        MainActivity._GraphExist[2][4] = 0;
        MainActivity._GraphExist[3][4] = 0;
        MainActivity._GraphExist[4][4] = 0;
        MainActivity._GraphExist[5][4] = 0;
        MainActivity._GraphExist[6][4] = 0;
        MainActivity._GraphExist[7][4] = 0;
        MainActivity._GraphExist[8][4] = 0;
        MainActivity._GraphExist[9][4] = 0;

        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;
        MainActivity._GraphExist[7][5] = 0;
        MainActivity._GraphExist[8][5] = 0;
        MainActivity._GraphExist[9][5] = 0;

        MainActivity._GraphExist[2][6] = 0;
        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[4][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;
        MainActivity._GraphExist[7][6] = 0;
        MainActivity._GraphExist[9][6] = 0;

        MainActivity._GraphExist[3][7] = 0;
        MainActivity._GraphExist[4][7] = 0;
        MainActivity._GraphExist[5][7] = 0;
        MainActivity._GraphExist[6][7] = 0;
        MainActivity._GraphExist[7][7] = 0;
        MainActivity._GraphExist[8][7] = 0;
        MainActivity._GraphExist[9][7] = 0;

        MainActivity._GraphExist[4][8] = 0;
        MainActivity._GraphExist[6][8] = 0;
        MainActivity._GraphExist[7][8] = 0;

        MainActivity._GraphExist[4][9] = 0;

    }

    private void Lv12(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[2][1] = 0;
        MainActivity._GraphExist[3][1] = 0;
        MainActivity._GraphExist[4][1] = 0;
        MainActivity._GraphExist[5][1] = 0;
        MainActivity._GraphExist[6][1] = 0;
        MainActivity._GraphExist[7][1] = 0;

        MainActivity._GraphExist[2][2] = 0;
        MainActivity._GraphExist[3][2] = 0;
        MainActivity._GraphExist[4][2] = 0;
        MainActivity._GraphExist[5][2] = 0;
        MainActivity._GraphExist[6][2] = 0;
        MainActivity._GraphExist[7][2] = 0;

        MainActivity._GraphExist[2][3] = 0;
        MainActivity._GraphExist[3][3] = 0;
        MainActivity._GraphExist[4][3] = 0;
        MainActivity._GraphExist[5][3] = 0;
        MainActivity._GraphExist[6][3] = 0;
        MainActivity._GraphExist[7][3] = 0;

        MainActivity._GraphExist[2][4] = 0;
        MainActivity._GraphExist[3][4] = 0;
        MainActivity._GraphExist[4][4] = 0;
        MainActivity._GraphExist[5][4] = 0;
        MainActivity._GraphExist[6][4] = 0;
        MainActivity._GraphExist[7][4] = 0;
        MainActivity._GraphExist[8][4] = 0;

        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;
        MainActivity._GraphExist[7][5] = 0;
        MainActivity._GraphExist[8][5] = 0;

        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[4][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;
        MainActivity._GraphExist[7][6] = 0;
        MainActivity._GraphExist[8][6] = 0;

        MainActivity._GraphExist[3][7] = 0;
        MainActivity._GraphExist[4][7] = 0;
        MainActivity._GraphExist[5][7] = 0;
        MainActivity._GraphExist[6][7] = 0;
        MainActivity._GraphExist[7][7] = 0;

        MainActivity._GraphExist[3][8] = 0;
        MainActivity._GraphExist[4][8] = 0;
        MainActivity._GraphExist[5][8] = 0;
        MainActivity._GraphExist[6][8] = 0;
        MainActivity._GraphExist[7][8] = 0;
    }

    private void Lv13(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[1][1] = 0;
        MainActivity._GraphExist[2][1] = 0;
        MainActivity._GraphExist[3][1] = 0;
        MainActivity._GraphExist[8][1] = 0;

        MainActivity._GraphExist[2][2] = 0;
        MainActivity._GraphExist[3][2] = 0;
        MainActivity._GraphExist[4][2] = 0;
        MainActivity._GraphExist[5][2] = 0;
        MainActivity._GraphExist[6][2] = 0;
        MainActivity._GraphExist[7][2] = 0;
        MainActivity._GraphExist[8][2] = 0;

        MainActivity._GraphExist[2][3] = 0;
        MainActivity._GraphExist[4][3] = 0;
        MainActivity._GraphExist[7][3] = 0;
        MainActivity._GraphExist[8][3] = 0;

        MainActivity._GraphExist[2][4] = 0;
        MainActivity._GraphExist[4][4] = 0;
        MainActivity._GraphExist[5][4] = 0;
        MainActivity._GraphExist[6][4] = 0;
        MainActivity._GraphExist[7][4] = 0;

        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[7][5] = 0;

        MainActivity._GraphExist[1][6] = 0;
        MainActivity._GraphExist[2][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[7][6] = 0;

        MainActivity._GraphExist[1][7] = 0;
        MainActivity._GraphExist[2][7] = 0;
        MainActivity._GraphExist[3][7] = 0;
        MainActivity._GraphExist[4][7] = 0;
        MainActivity._GraphExist[5][7] = 0;
        MainActivity._GraphExist[6][7] = 0;
        MainActivity._GraphExist[7][7] = 0;

        MainActivity._GraphExist[1][8] = 0;
        MainActivity._GraphExist[6][8] = 0;
        MainActivity._GraphExist[7][8] = 0;
        MainActivity._GraphExist[8][8] = 0;
    }

    private void Lv14(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[8][0] = 0;

        MainActivity._GraphExist[8][1] = 0;
        MainActivity._GraphExist[9][1] = 0;

        MainActivity._GraphExist[7][2] = 0;
        MainActivity._GraphExist[8][2] = 0;

        MainActivity._GraphExist[7][3] = 0;
        MainActivity._GraphExist[8][3] = 0;

        MainActivity._GraphExist[7][4] = 0;

        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;
        MainActivity._GraphExist[7][5] = 0;

        MainActivity._GraphExist[2][6] = 0;
        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[4][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;

        MainActivity._GraphExist[2][7] = 0;
        MainActivity._GraphExist[3][7] = 0;
        MainActivity._GraphExist[4][7] = 0;
        MainActivity._GraphExist[5][7] = 0;

        MainActivity._GraphExist[2][8] = 0;
        MainActivity._GraphExist[3][8] = 0;
        MainActivity._GraphExist[4][8] = 0;
        MainActivity._GraphExist[5][8] = 0;

        MainActivity._GraphExist[2][9] = 0;
        MainActivity._GraphExist[5][9] = 0;
    }

    private void Lv15(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[4][1] = 0;

        MainActivity._GraphExist[3][2] = 0;
        MainActivity._GraphExist[4][2] = 0;

        MainActivity._GraphExist[4][3] = 0;
        MainActivity._GraphExist[4][4] = 0;

        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[3][5] = 0;
        MainActivity._GraphExist[4][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[6][5] = 0;

        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[4][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;
        MainActivity._GraphExist[7][6] = 0;

        MainActivity._GraphExist[1][7] = 0;
        MainActivity._GraphExist[2][7] = 0;
        MainActivity._GraphExist[3][7] = 0;
        MainActivity._GraphExist[4][7] = 0;
        MainActivity._GraphExist[5][7] = 0;
        MainActivity._GraphExist[6][7] = 0;
        MainActivity._GraphExist[7][7] = 0;
        MainActivity._GraphExist[8][7] = 0;

        MainActivity._GraphExist[2][8] = 0;
        MainActivity._GraphExist[6][8] = 0;

    }

    private void Lv16(){
        for(int i = 0 ; i <  GridBoard.mColumn ; i++)
            for(int j = 0 ; j < GridBoard.mRow ; j++)
                MainActivity._GraphExist[i][j] = -1;

        MainActivity._GraphExist[3][0] = 0;
        MainActivity._GraphExist[6][0] = 0;

        MainActivity._GraphExist[1][1] = 0;
        MainActivity._GraphExist[3][1] = 0;
        MainActivity._GraphExist[6][1] = 0;
        MainActivity._GraphExist[8][1] = 0;

        MainActivity._GraphExist[3][2] = 0;
        MainActivity._GraphExist[4][2] = 0;
        MainActivity._GraphExist[6][2] = 0;

        MainActivity._GraphExist[0][3] = 0;
        MainActivity._GraphExist[1][3] = 0;
        MainActivity._GraphExist[2][3] = 0;
        MainActivity._GraphExist[3][3] = 0;
        MainActivity._GraphExist[5][3] = 0;
        MainActivity._GraphExist[6][3] = 0;
        MainActivity._GraphExist[7][3] = 0;
        MainActivity._GraphExist[8][3] = 0;
        MainActivity._GraphExist[9][3] = 0;

        MainActivity._GraphExist[3][4] = 0;
        MainActivity._GraphExist[9][4] = 0;

        MainActivity._GraphExist[2][5] = 0;
        MainActivity._GraphExist[5][5] = 0;
        MainActivity._GraphExist[7][5] = 0;
        MainActivity._GraphExist[9][5] = 0;

        MainActivity._GraphExist[0][6] = 0;
        MainActivity._GraphExist[1][6] = 0;
        MainActivity._GraphExist[2][6] = 0;
        MainActivity._GraphExist[3][6] = 0;
        MainActivity._GraphExist[5][6] = 0;
        MainActivity._GraphExist[6][6] = 0;
        MainActivity._GraphExist[8][6] = 0;

        MainActivity._GraphExist[3][7] = 0;
        MainActivity._GraphExist[6][7] = 0;
        MainActivity._GraphExist[9][7] = 0;

        MainActivity._GraphExist[1][8] = 0;
        MainActivity._GraphExist[3][8] = 0;
        MainActivity._GraphExist[7][8] = 0;

        MainActivity._GraphExist[3][9] = 0;
        MainActivity._GraphExist[5][9] = 0;
        MainActivity._GraphExist[8][9] = 0;

    }
}
