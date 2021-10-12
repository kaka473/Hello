package com.example.hello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BallActivity extends AppCompatActivity{

    int score1=0;
    int score2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);

    }
    public void click(View btn){
        if(btn.getId()==R.id.btn3){
            score1+=3;
        }else if(btn.getId()==R.id.btn2){
            score1+=2;
        }else if(btn.getId()==R.id.btn1){
            score1++;
        }else
        {
            score1=0;
            score2=0;
        }
        ShowScore();
    }

    private void ShowScore() {
        TextView show1 = findViewById(R.id.score1);
        show1.setText(String.valueOf(score1));
        TextView show2 = findViewById(R.id.score2);
        show2.setText(String.valueOf(score2));
    }

    public void clickb(View btn){
        if(btn.getId()==R.id.btnb3){
            score2+=3;
        }else if(btn.getId()==R.id.btnb2){
            score2+=2;
        }else if(btn.getId()==R.id.btnb1){
            score2++;
        }
        ShowScore();
    }
    //add something

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("score1",score1);
        outState.putInt("score2",score2);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        score1=savedInstanceState.getInt("score1",0);
        score2=savedInstanceState.getInt("score2",0);
        ShowScore();
    }
}