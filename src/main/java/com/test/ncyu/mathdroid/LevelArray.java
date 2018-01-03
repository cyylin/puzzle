package com.test.ncyu.mathdroid;

public class LevelArray {
    private int level = 1;
    private int userSelect = 1;
    private int[][] Array = new int[Pieces.mColumn_Num][Pieces.mRow_Num];
    protected final int MAX_LEVEL = GameLevel.MAX_LEVEL;

    public LevelArray(){

    }

    public LevelArray(int level , int userSelect){
        this.level = level;
        this.userSelect = userSelect;
        init();
    }

    private void init(){
        for(int i = 0 ; i < Pieces.mColumn_Num ; i++ ){
            for(int j = 0 ; j < Pieces.mRow_Num ; j++){
                Array[i][j] = 0;
            }
        }
    }

    public int[][] getArray(){
        init();
        switch (level){
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
                break;
        }
        return Array;
    }

    private void Lv01(){
        MainActivity.total = 1;
        switch (userSelect){
            case 1:
                square15();
                break;
        }
    }

    private void Lv02(){
        MainActivity.total = 1;
        switch (userSelect){
            case 1:
                square18();
                break;
            default:
                break;
        }
    }

    private void Lv03(){
        MainActivity.total = 1;
        switch (userSelect){
            case 1:
                square14();
                break;
            default:
                break;
        }
    }

    private void Lv04(){
        MainActivity.total = 1;
        switch (userSelect){
            case 1:
                square3();
                break;
            default:
                break;
        }
    }

    private void Lv05(){
        MainActivity.total = 1;
        switch (userSelect){
            case 1:
                square10();
                break;
            default:
                break;
        }
    }

    private void Lv06(){
        MainActivity.total = 1;
        switch (userSelect){
            case 1:
                square7();
                break;
            default:
                break;
        }
    }

    private void Lv07(){
        MainActivity.total = 1;
        switch (userSelect){
            case 1:
                square19();
                break;
            default:
                break;
        }
    }

    private void Lv08(){
        MainActivity.total = 1;
        switch (userSelect){
            case 1:
                square20();
                break;
            default:
                break;
        }
    }

    private void Lv09(){
        MainActivity.total = 1;
        switch (userSelect){
            case 1:
                square21();
                break;
            default:
                break;
        }
    }

    private void Lv10(){
        MainActivity.total = 1;
        switch (userSelect){
            case 1:
                square11();
                break;
            default:
                break;
        }
    }

    private void Lv11(){
        MainActivity.total = 2;
        switch (userSelect){
            case 1:
                square9();
                break;
            case 2:
                square22();
                break;
            default:
                break;
        }
    }

    private void Lv12(){
        MainActivity.total = 2;
        switch (userSelect){
            case 1:
                square9();
                break;
            case 2:
                square23();
                break;
            default:
                break;
        }
    }

    private void Lv13(){
        switch (userSelect){
            case 1:
                square2();
                break;
            case 2:
                square9();
                break;
            case 3:
                //ans
                square13();
                break;
            case 4:
                square1();
                break;
            case 5:
                square14();
                break;
            case 6:
                square3();
                break;
            default:
                break;
        }
    }

    private void Lv14(){
        switch (userSelect){
            case 1:
                square10();
                break;
            case 2:
                square9();
                break;
            case 3:
                square11();
                break;
            case 4:
                square6();
                break;
            case 5:
                square2();
                break;
            case 6:
                //ans
                square3();
                break;
            default:
                break;
        }
    }

    private void Lv15(){
        switch (userSelect){
            case 1:
                //ans
                square7();
                break;
            case 2:
                square10();
                break;
            case 3:
                square14();
                break;
            case 4:
                square3();
                break;
            case 5:
                square1();
                break;
            case 6:
                square16();
                break;
            default:
                break;
        }
    }

    private void Lv16(){
        switch (userSelect){
            case 1:
                square8();
                break;
            case 2:
                square8();
                break;
            case 3:
                square8();
                break;
            case 4:
                square8();
                break;
            case 5:
                square8();
                break;
            case 6:
                square8();
                break;
            default:
                break;
        }
    }

    private void square1(){
        /*╔ ╗
         *╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         *   ╔ ╗╔ ╗
         *   ╚ ╝╚ ╝
         */
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[1][2] = 1;
        Array[1][3] = 1;
        Array[2][3] = 1;
    }

    private void square2(){
        /*╔ ╗
         *╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         */
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[0][3] = 1;
        Array[1][2] = 1;
        Array[1][3] = 1;
    }

    private void square3(){
        /*╔ ╗
         *╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         *╔ ╗
         *╚ ╝
         */
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[0][3] = 1;
        Array[1][2] = 1;
    }

    private void square4(){
        /*   ╔ ╗
         *   ╚ ╝
         *╔ ╗╔ ╗╔ ╗
         *╚ ╝╚ ╝╚ ╝
         *   ╔ ╗
         *   ╚ ╝
         */

        Array[0][2] = 1;
        Array[1][1] = 1;
        Array[1][2] = 1;
        Array[1][3] = 1;
        Array[2][2] = 1;
    }

    private void square5(){
        /*   ╔ ╗╔ ╗
         *   ╚ ╝╚ ╝
         *╔ ╗╔ ╗╔ ╗
         *╚ ╝╚ ╝╚ ╝
         *   ╔ ╗
         *   ╚ ╝
         */

        Array[0][2] = 1;
        Array[1][1] = 1;
        Array[1][2] = 1;
        Array[1][3] = 1;
        Array[2][1] = 1;
        Array[2][2] = 1;
    }

    private void square6(){
        /*╔ ╗      ╔ ╗
         *╚ ╝      ╚ ╝
         *╔ ╗╔ ╗╔ ╗╔ ╗
         *╚ ╝╚ ╝╚ ╝╚ ╝
         *╔ ╗╔ ╗╔ ╗╔ ╗
         *╚ ╝╚ ╝╚ ╝╚ ╝
         *╔ ╗      ╔ ╗
         *╚ ╝      ╚ ╝
         */

        Array[0][0] = 1;
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[0][3] = 1;
        Array[3][0] = 1;
        Array[3][1] = 1;
        Array[3][2] = 1;
        Array[3][3] = 1;
        Array[1][1] = 1;
        Array[1][2] = 1;
        Array[2][1] = 1;
        Array[2][2] = 1;
    }

    private void square7(){
        /*╔ ╗
         *╚ ╝
         *╔ ╗
         *╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         *╔ ╗
         *╚ ╝
         */
        Array[0][0] = 1;
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[0][3] = 1;
        Array[1][2] = 1;
    }

    private void square8(){
        /*╔ ╗
         *╚ ╝
         */
        Array[0][3] = 1;
    }

    private void square9(){
        /*╔ ╗
         *╚ ╝
         *╔ ╗
         *╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         */
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[0][3] = 1;
        Array[1][3] = 1;
    }

    private void square10(){
        /*╔ ╗
         *╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         *   ╔ ╗
         *   ╚ ╝
         */
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[1][2] = 1;
        Array[1][3] = 1;
    }

    private void square11(){
        /*╔ ╗╔ ╗
         *╚ ╝╚ ╝
         *╔ ╗
         *╚ ╝
         *╔ ╗
         *╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         */
        Array[0][0] = 1;
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[0][3] = 1;
        Array[1][0] = 1;
        Array[1][3] = 1;
    }

    private void square12(){
        /*╔ ╗
         *╚ ╝
         *╔ ╗
         *╚ ╝
         *╔ ╗
         *╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         */
        Array[0][0] = 1;
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[0][3] = 1;
        Array[1][3] = 1;
    }

    private void square13(){
        /*
         *
         *   ╔ ╗
         *   ╚ ╝
         *   ╔ ╗
         *   ╚ ╝
         *╔ ╗╔ ╗╔ ╗
         *╚ ╝╚ ╝╚ ╝
         */
        Array[0][3] = 1;
        Array[1][1] = 1;
        Array[1][2] = 1;
        Array[1][3] = 1;
        Array[2][3] = 1;
    }

    private void square14(){
        /*╔ ╗
         *╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         */
        Array[0][2] = 1;
        Array[0][3] = 1;
        Array[1][3] = 1;
    }

    private void square15(){
        /*╔ ╗╔ ╗
         *╚ ╝╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         */
        Array[1][1] = 1;
        Array[1][2] = 1;
        Array[2][1] = 1;
        Array[2][2] = 1;
    }

    private void square16(){
        /*╔ ╗
         *╚ ╝
         *   ╔ ╗
         *   ╚ ╝
         */
        Array[1][1] = 1;
        Array[2][2] = 1;
    }

    private void square17(){
        /*╔ ╗╔ ╗
         *╚ ╝╚ ╝
         *╔ ╗
         *╚ ╝
         *╔ ╗╔ ╗
         *╚ ╝╚ ╝
         */
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[0][3] = 1;
        Array[1][1] = 1;
        Array[1][3] = 1;
    }

    private void square18(){
        Array[0][1] = 1;
        Array[0][2] = 1;
        Array[0][3] = 1;
    }

    private void square19(){
        Array[0][1] = 1;

        Array[0][2] = 1;
        Array[1][2] = 1;
        Array[2][2] = 1;

        Array[0][3] = 1;
        Array[2][3] = 1;
    }

    private void square20(){

        Array[1][1] = 1;
        Array[2][1] = 1;
        Array[3][1] = 1;

        Array[1][2] = 1;
        Array[3][2] = 1;

        Array[1][3] = 1;
    }

    private void square21(){

        Array[1][1] = 1;

        Array[0][2] = 1;
        Array[1][2] = 1;
        Array[2][2] = 1;

        Array[2][3] = 1;
    }

    private void square22(){

        Array[2][0] = 1;

        Array[0][1] = 1;
        Array[2][1] = 1;

        Array[0][2] = 1;
        Array[1][2] = 1;
        Array[2][2] = 1;

        Array[0][3] = 1;

    }

    private void square23(){

        Array[0][1] = 1;
        Array[1][1] = 1;

        Array[0][2] = 1;
        Array[1][2] = 1;

        Array[0][3] = 1;
        Array[1][3] = 1;
    }
}
