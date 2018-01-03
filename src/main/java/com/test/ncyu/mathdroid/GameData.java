package com.test.ncyu.mathdroid;

import android.content.Context;
import android.content.SharedPreferences;

/*遊戲資料
  *記錄遊戲是否通關
 */
public class GameData {

    private Context context;

    private SharedPreferences[] game = new SharedPreferences[GameLevel.MAX_LEVEL];
    private static final String data_passed = "PASSED";
    private static final String data_steps = "Steps";
    private static final String data_min = "MIN";
    private static final String data_sec = "SEC";

    public GameData(Context context){
        this.context = context;
        for(int i = 0 ; i < game.length ; i++) {
            game[i] = context.getSharedPreferences("LEVEL"+Integer.toString(i + 1), Context.MODE_PRIVATE);
        }
    }

    public void saveData(int level, boolean passed, int steps, long min, long sec){

        //判斷是否為最佳
        if( getSteps(level) < steps && steps != 0 )
            steps = getSteps(level);

        if( getMin(level) < min && min != 0 ) {
            min = getMin(level);
            sec = getSec(level);
        }else if( getMin(level) == min && getSec(level) < sec && min != 0 ){
            sec = getSec(level);
        }


        //儲存資料
        game[level-1].edit().putBoolean(data_passed, passed)
                .putInt(data_steps, steps)
                .putLong(data_min, min)
                .putLong(data_sec, sec)
                .apply();
    }

    //清除所有遊戲資料
    public void ResetData(){
        for(int i = 0 ; i < game.length ; i++) {
            if(game[i] != null) {
                SharedPreferences prefs = game[i];
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
            }
        }
    }

    public Boolean isPassed(int level){
        if(0 < level && level < game.length)
            return game[level-1].getBoolean(data_passed, false);
        return false;
    }

    public int getSteps(int level){
        if(0 < level && level < game.length)
            return game[level-1].getInt(data_steps, 0);
        return 0;
    }

    public long getMin(int level){
        if(0 < level && level < game.length)
            return game[level-1].getLong(data_min, 0);
        return 0;
    }

    public long getSec(int level){
        if(0 < level && level < game.length)
            return game[level-1].getLong(data_sec, 0);
        return 0;
    }
}
