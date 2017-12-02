package com.example.user.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Sonuc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);

        TextView sonucLabel=(TextView)findViewById(R.id.scoreLabel);
        TextView highScoreLAbel=(TextView)findViewById(R.id.highScoreLabel);
        Button tryButton=(Button)findViewById(R.id.TryAgainButton);

        int score =getIntent().getIntExtra("SCORE",0);
        sonucLabel.setText(score+"");
        SharedPreferences settings=getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore=settings.getInt("HIGH_SCORE",0);

        if(score>highScore){
            highScoreLAbel.setText("High Score: "+score);
            SharedPreferences.Editor editor=settings.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.commit();
        }else {
            highScoreLAbel.setText("High Score: "+highScore);
        }
    }
    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(),StartActivity.class));
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
