package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        double dollar2=intent.getDoubleExtra("dollar_rate_key",0.00);
        double euro2=intent.getDoubleExtra("euro_rate_key",0.00);
        double won2=intent.getDoubleExtra("won_rate_key",0.00);

        dollar_rate=findViewById(R.id.dollar_rate);
        euro_rate=findViewById(R.id.euro_rate);
        won_rate=findViewById(R.id.won_rate);

        dollar_rate.setText(String.valueOf(dollar2));
        euro_rate.setText(String.valueOf(euro2));
        won_rate.setText(String.valueOf(won2));
    }
    public void save(View btn){
        Intent save=getIntent();
        save.putExtra("dollar_rate_key2",Double.parseDouble(dollar_rate.getText().toString()));
        save.putExtra("euro_rate_key2",Double.parseDouble(euro_rate.getText().toString()));
        save.putExtra("won_rate_key2",Double.parseDouble(won_rate.getText().toString()));
        setResult(1002,save);
        finish();
    }
}