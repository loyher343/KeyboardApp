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

import static java.lang.Math.*;

import static java.lang.Math.*;

public class MyView extends GestureOverlayView {

    Context myContext;

    public MyView(Context c){
        super(c);
        myContext = c;
        //this.setGestureVisible(false);
        this.setBackgroundColor(Color.WHITE);
        createKeys();
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
        paint.setTextSize(30);
        drawKeyboard(canvas, paint);
    }

    //int y = 100;
    int bubbleRadius = 7;
    boolean focused = false;
    int bigIndex = -1;
    int focusIndex = -1;
    int keyWidth = 0;

    private  void drawKeyboard(Canvas c, Paint p){
        int keysInRow = 7;
        keyWidth = this.getWidth()/keysInRow;
        int keyHeight = keyWidth;
        this.setMinimumHeight(keyHeight*4);


        x = new int[keys.length];
        y = new int[keys.length];

        for(int i=0; i<keys.length; i++){
            x[i] = (i%keysInRow)*keyWidth+(keyWidth/2);
            y[i] = (int)(i/keysInRow)*keyHeight+(keyHeight/2);

            c.drawText(keys[i],x[i],y[i],p);
        }

        for(int i=1; i<7; i++){
            c.drawLine(keyWidth*i,0,keyWidth*i,this.getHeight(),p);
        }

        for(int i=1; i<4; i++){
            c.drawLine(0,keyHeight*i,this.getWidth(),keyHeight*i,p);
        }

        //drawBubble((int)xTouch,(int)yTouch,20,c,p);
        //p.setColor(Color.RED);
        //c.drawText("timePressed: "+timePressed,70,70,p);
        //p.setColor(Color.BLACK);
    }

    private int distance(int i){
        int a = (int) abs(x[i]-xTouch);
        int b = (int) abs(100-yTouch);
        int c = (int) sqrt(a*a + b*b);

        return  c;
    }

    int[] x, y;
    String[] keys = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","'","_"};

    private  void createKeys(){

    }

    private  boolean touchingKey(int i){
        int xDistance = (int) abs(x[i]-xTouch);
        int yDistance = (int) abs(y[i]-yTouch);

        return (xDistance<(keyWidth/2) && yDistance < (keyWidth/2));
    }

    float xTouch = 0;
    float yTouch = 0;
    long timeEntered = System.currentTimeMillis();
    long timePressed = 0;
    int currentKeyIndex = -1;

    public boolean onTouchEvent(MotionEvent event) {
        xTouch = event.getX();
        yTouch = event.getY();

        String output = "-1";


        for(int i=0; i<keys.length; i++){
            if(touchingKey(i)) {
                if(currentKeyIndex != i){
                    timeEntered = System.currentTimeMillis();
                    currentKeyIndex = i;
                }
                else{
                    timePressed = System.currentTimeMillis()-timeEntered;
                    if(timePressed > 1000){
                        keyboard.type(keys[i]);
                        timePressed = 0;
                        timeEntered = System.currentTimeMillis();
                    }
                }


                output = keys[i];
            }
        }

        if(event.getAction()== event.ACTION_UP) keyboard.type(output);
        invalidate();
        return true;
    }

    private void drawBubble(int centerX, int centerY, int radius, Canvas c, Paint p){
        float left = centerX - radius;
        float top = centerY - radius;
        float right = centerX + radius;
        float bottom = centerY + radius;

        c.drawOval(new RectF(left, top, right, bottom), p);
    }

}
