package com.bwie.uu.randomcolor;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.Random;

/**
 * Created by 闫雷 on 2017/3/10.
 */
public class CustomView extends View {

    private TypedArray a;
    private String text="";
    private int color;
    private int size;
    private int def;
    private Paint paint;
    private boolean isStart=true;
    private ThreadUtil thread;
    private Rect rect;
    private int textcolor;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0);
        int indexCount = a.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = a.getIndex(i);
            switch (index){
                case R.styleable.CustomView_text:
                    text = a.getString(index);

                    break;
                case R.styleable.CustomView_textSize:
                    def = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, getResources().getDisplayMetrics());
                    size = a.getDimensionPixelSize(index, def);
                    break;
                case R.styleable.CustomView_textColor:
                    textcolor = a.getColor(index, Color.RED);
                    break;
                case R.styleable.CustomView_viewBackbroud:
                    color = a.getColor(index, Color.BLUE);
                    break;
            }


        }

        a.recycle();

        paint = new Paint();
        paint.setTextSize(size);
        paint.setAntiAlias(true);

        rect = new Rect();
        paint.getTextBounds(text,0,text.length(), rect);


        startUpdata();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStart){
                    isStart=false;

                }else{
                    isStart=true;
                    startUpdata();
                }
            }
        });



    }

    private void startUpdata() {
        thread = new ThreadUtil(new Runnable() {
            @Override
            public void run() {
                while(isStart){
                    String randColorCode = getRandColorCode();
                    text=randColorCode;
                    color = Color.parseColor(randColorCode);
                    postInvalidate();
                    Log.e("CCCCCCCCCc",randColorCode);
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        },isStart);
        thread.start();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        paint.setColor(color);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);
        paint.setColor(textcolor);
        canvas.drawText(text,getMeasuredWidth()/2-rect.width()/2,getMeasuredHeight()/2+rect.height()/2,paint);
        Log.e("aaaaa",(getMeasuredWidth()/2)+"  "+(getMeasuredHeight()/2));
        Log.e("aaaaa",(rect.width()/2)+"  "+(rect.height()/2));
        Log.e("aaaaa",(getMeasuredWidth()/2-rect.width()/2)+"  "+(getMeasuredHeight()/2-rect.height()/2));
    }

    public static String getRandColorCode(){
        String r,g,b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length()==1 ? "0" + r : r ;
        g = g.length()==1 ? "0" + g : g ;
        b = b.length()==1 ? "0" + b : b ;

        return "#"+r+g+b;
    }
}
