package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }
    /*public void onClick(View v) {
        EditText input=findViewById(R.id.inp);
        String name=input.getText().toString();

        TextView out=findViewById(R.id.show);
        out.setText("HELLO"+name);
    }*/
    public void onClick(View v){
        EditText input1=findViewById(R.id.height);
        double height=Double.parseDouble(input1.getText().toString());
        EditText input2=findViewById(R.id.weight);
        double weight=Double.parseDouble(input2.getText().toString());
        double val=weight/(height*height)*10000;
        System.out.println(val);
        TextView out=findViewById(R.id.show);
        out.setText(String.format("%.2f",val));
        TextView out1=findViewById(R.id.sug2);
        if(val<=18.5&&val>=0){
            out1.setText("偏瘦"+"\n"+"应寻找过轻的原因及对症下药");
        }else if(val>18.5&&val<23.9)
        {
            out1.setText("正常"+"\n"+"应做适量运动");
        }else if(val>24&&val<27.9)
        {
            out1.setText("偏胖"+"\n"+"要注意饮食，增加运动量");
        }else
        {
            out1.setText("肥胖"+"\n"+"需要减肥");
        }
    }
}