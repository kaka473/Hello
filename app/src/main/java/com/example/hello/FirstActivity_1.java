package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstActivity_1 extends AppCompatActivity {

    private static final String TAG ="FirstActivity_1";
    EditText dollar_rate,euro_rate,won_rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first1);

        Intent intent=getIntent();
        float dollar2=intent.getFloatExtra("dollar_rate_key",0.00f);
        float euro2=intent.getFloatExtra("euro_rate_key",0.00f);
        float won2=intent.getFloatExtra("won_rate_key",0.00f);

        dollar_rate=findViewById(R.id.dollar_rate);
        euro_rate=findViewById(R.id.euro_rate);
        won_rate=findViewById(R.id.won_rate);

        dollar_rate.setText(String.valueOf(dollar2));
        euro_rate.setText(String.valueOf(euro2));
        won_rate.setText(String.valueOf(won2));
    }
    public void save(View btn){
        Intent save=getIntent();
        save.putExtra("dollar_rate_key2",Float.parseFloat(dollar_rate.getText().toString()));
        save.putExtra("euro_rate_key2",Float.parseFloat(euro_rate.getText().toString()));
        save.putExtra("won_rate_key2",Float.parseFloat(won_rate.getText().toString()));
        setResult(1002,save);
        finish();
    }
}