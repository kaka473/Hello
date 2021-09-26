package com.example.hello;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    double result=0;
    double dollar_rate=0.1547;
    double euro_rate=0.132;
    double won_rate=182.4343;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }
    public void click(View btn){

        TextView show = findViewById(R.id.result);
        EditText input=findViewById(R.id.input);
        String str=input.getText().toString().trim();
        if(TextUtils.isEmpty(str))
        {
            Toast.makeText(getApplicationContext(),
                    "输入非法数据", Toast.LENGTH_SHORT).show();
        }
        else {
            double rmb=Double.parseDouble(input.getText().toString());
            if(btn.getId()==R.id.btn1){
                result=rmb*dollar_rate;
            }else if(btn.getId()==R.id.btn2){
                result=rmb*euro_rate;
            }else if(btn.getId()==R.id.btn3){
                result=rmb*won_rate;
            }
            show.setText(String.format("%.2f",result));
        }
    }
    public void open(View v){
        Intent config=new Intent(this,FirstActivity_1.class);
        config.putExtra("dollar_rate_key",dollar_rate);
        config.putExtra("euro_rate_key",euro_rate);
        config.putExtra("won_rate_key",won_rate);
        //startActivity(config);
        startActivityForResult(config,1001);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1001 &&resultCode==1002)
        {
            dollar_rate=data.getDoubleExtra("dollar_rate_key2",0.0);
            euro_rate=data.getDoubleExtra("euro_rate_key2",0.0);
            won_rate=data.getDoubleExtra("won_rate_key2",0.0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}