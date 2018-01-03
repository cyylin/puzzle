package com.test.ncyu.mathdroid;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.widget.Button;

public class MenuButton extends Button{

    public MenuButton(Context context){
        this(context, "No text");
    }

    public MenuButton(Context context, String text){
        super(context);

        Typeface face = Typeface.createFromAsset(getResources().getAssets(), "fonts/falconfont.ttf");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            this.setTextColor(getResources().getColorStateList(R.color.menu_button_text_color, null) );
        } else {
            this.setTextColor(getResources().getColorStateList(R.color.menu_button_text_color) );
        }

        this.setBackgroundResource(R.drawable.menu_button_background);
        this.setText(text);
        this.setTypeface(face);
        this.setTextSize(getResources().getDimension(R.dimen.menu_size));
        this.setGravity(Gravity.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension((int)(DeviceInf.getScreenWidthPx(MenuActivity.menuActivity)*0.35), (int)(DeviceInf.getScreenWidthPx(MenuActivity.menuActivity)*0.20) );
    }
}
