package com.test.ncyu.mathdroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class RoundedRectProgressBar extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int barColor;
    private int backColor;
    private int textColor;
    private float radius;
    int progress = 0;
    public RoundedRectProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        barColor = Color.rgb(28, 253, 155);
        backColor = Color.GRAY;
        textColor = Color.rgb(136, 0, 255);
    }
    public RoundedRectProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public RoundedRectProgressBar(Context context) {
        this(context, null);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        radius = this.getMeasuredHeight() / 5;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景
        mPaint.setColor(backColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight()), radius, radius, mPaint);
        //进度条
        mPaint.setColor(barColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0, 0, this.getMeasuredWidth() * progress / 100f, this.getMeasuredHeight()), radius, radius, mPaint);
        //进度
        Typeface face = Typeface.create("serif", Typeface.NORMAL);
        mPaint.setColor(textColor);
        mPaint.setTypeface(face);
        mPaint.setTextSize(this.getMeasuredHeight() / 1.2f);
        String text = "" + progress + "%";
        float x = this.getMeasuredWidth() * progress / 100 - mPaint.measureText(text) - 10;
        float y = this.getMeasuredHeight() / 2f - mPaint.getFontMetrics().ascent / 2f - mPaint.getFontMetrics().descent / 2f;
        canvas.drawText(text, x, y, mPaint);
    }
    /*设置进度条进度, 外部调用*/
    public void setProgress(int progress) {
        if (progress > 100) {
            this.progress = 100;
        } else if (progress < 0) {
            this.progress = 0;
        } else {
            this.progress = progress;
        }
        postInvalidate();
    }
}
