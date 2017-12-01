package com.loys.keyboardapp;

/**
 * Created by loy on 12/1/17.
 */

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class MyView extends GestureOverlayView {


    Context myContext;

    public MyView(Context c){
        super(c);
        myContext = c;
        this.setGestureVisible(false);
        this.setBackgroundColor(Color.WHITE);
    }


    private MyKeyboard keyboard;

    public void setInputMethodService(MyKeyboard k){
        keyboard = k;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        canvas.drawOval(new RectF(20,40,70,10),paint);
    }


    float x = 0;
    float y = 0;

    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        if(event.getAction()== event.ACTION_UP) keyboard.type("a");
        invalidate();
        return true;
    }

}