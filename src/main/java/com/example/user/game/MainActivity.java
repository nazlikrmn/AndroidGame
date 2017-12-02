package com.example.user.game;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreL;
    private TextView startL;
    private ImageView kirmiziG;
    private ImageView maviG;
    private ImageView beyazG;
    private ImageView siyahG;

    private int whiteY;
    private int siyahX;
    private int siyahY;
    private int kirmiziX;
    private int kirmiziY;
    private int dostX;
    private int dostY;

    private int score=0;

    private Handler handler=new Handler();
    private Timer timer=new Timer();

    private boolean action=false;
    private boolean start=false;

    private int frameHeight;
    private int beyazGSize;

    private int screenWidth;
    private int screenHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreL=(TextView) findViewById(R.id.scoreLabel);
        startL=(TextView)findViewById(R.id.startlabel);
        kirmiziG=(ImageView)findViewById(R.id.red);
        maviG=(ImageView)findViewById(R.id.dost);
        beyazG=(ImageView)findViewById(R.id.white);
        siyahG=(ImageView)findViewById(R.id.black);

        WindowManager winM=getWindowManager();
        Display display=winM.getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);

        screenWidth=point.x;
        screenHeight=point.y;

        kirmiziG.setX(0);
        kirmiziG.setY(0);
        maviG.setX(0);
        maviG.setY(0);
        siyahG.setX(0);
        siyahG.setY(0);

        scoreL.setText("Score: 0");
        startL.setVisibility(View.INVISIBLE);

    }
    public void changePos(){
        hit();

        kirmiziX-=12;
        if(kirmiziX<0){
            kirmiziX=screenWidth+20;
            kirmiziY=(int)Math.floor(Math.random()*(frameHeight-kirmiziG.getHeight()));
        }
        kirmiziG.setX(kirmiziX);
        kirmiziG.setY(kirmiziY);

        siyahX-=10;
        if (siyahX<0){
            siyahX=screenWidth+10;
            siyahY=(int)Math.floor(Math.random()*(frameHeight-siyahG.getHeight()));
        }
        siyahG.setX(siyahX);
        siyahG.setY(siyahY);

        dostX-=10;
        if (dostX<0){
            dostX=screenWidth+10;
            dostY=(int)Math.floor(Math.random()*(frameHeight-maviG.getHeight()));
        }
        maviG.setX(dostX);
        maviG.setY(dostY);

        beyazG.setY((whiteY));
        if(action){
            whiteY-=20;
        }
        else {
            whiteY+=20;
        }

        if (whiteY<0)
            whiteY=0;
        if(whiteY>frameHeight-beyazGSize)
            whiteY=frameHeight-beyazGSize;

        scoreL.setText("Score: "+score);

    }
    public void hit(){
        int siyahCenterX=siyahX+siyahG.getWidth()/2;
        int siyahCenterY=siyahY+siyahG.getHeight()/2;
        if(0<=siyahCenterX&&siyahCenterX<=beyazGSize&&whiteY<=siyahCenterY&&siyahCenterY<=whiteY+beyazGSize){
            score+=10;
            siyahX=0;
        }
        int kirmiziCenterX=kirmiziX+kirmiziG.getWidth()/2;
        int kirmiziCenterY=kirmiziY+kirmiziG.getHeight()/2;
        if(0<=kirmiziCenterX&&kirmiziCenterX<=beyazGSize&&whiteY<=kirmiziCenterY&&kirmiziCenterY<=whiteY+beyazGSize){
            score+=30;
            kirmiziX=0;
        }
        int dostCenterX=dostX+maviG.getWidth()/2;
        int dostCenterY=dostY+maviG.getHeight()/2;
        if(0<=dostCenterX&&dostCenterX<=beyazGSize&&whiteY<=dostCenterY&&dostCenterY<=whiteY+beyazGSize){
            timer.cancel();
            timer=null;
            Intent intent=new Intent(getApplicationContext(), Sonuc.class);
            intent.putExtra("SCORE",score);
            startActivity(intent);
        }

    }
    public boolean onTouchEvent(MotionEvent me){
        if(start){
            if(me.getAction()==MotionEvent.ACTION_DOWN){
                action=true;
            }
            else if (me.getAction()==MotionEvent.ACTION_UP){
                action=false;

                FrameLayout frame=(FrameLayout)findViewById(R.id.frame);
                frameHeight=frame.getHeight();

                whiteY=(int)beyazG.getY();
                beyazGSize=beyazG.getHeight();
            }
        }
        else {
            start=true;
            startL.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,20);
        }
        return true;
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
