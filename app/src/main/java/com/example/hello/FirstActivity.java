package com.example.hello;

import static java.lang.Thread.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FirstActivity extends AppCompatActivity implements Runnable{

    private static final String TAG ="FirstActivity";
    float result=0;
    float dollar_rate= (float) 0.15;
    float euro_rate= (float) 0.13;
    float won_rate= (float) 182.43;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Thread thread =new Thread(this);
        thread.start();

        TextView show = findViewById(R.id.result);
        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);

        dollar_rate=sharedPreferences.getFloat("dollar_rate",0.0f);
        euro_rate=sharedPreferences.getFloat("euro_rate",0.0f);
        won_rate=sharedPreferences.getFloat("won_rate",0.0f);

       /* handler =new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG,"收到消息");
                if(msg.what==6){
                    String str=(String)msg.obj;
                    Log.i(TAG,"str:"+str);
                    show.setText(str);
                }
                super.handleMessage(msg);
            }
        };*/


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
            float rmb=Float.parseFloat(input.getText().toString());
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
            dollar_rate=data.getFloatExtra("dollar_rate_key2",0.0f);
            euro_rate=data.getFloatExtra("euro_rate_key2",0.0f);
            won_rate=data.getFloatExtra("won_rate_key2",0.0f);

            SharedPreferences sp=getSharedPreferences("myrate",Activity.MODE_PRIVATE);

            SharedPreferences.Editor editor=sp.edit();
            editor.putFloat("dollar_rate",dollar_rate);
            editor.putFloat("euro_rate",euro_rate);
            editor.putFloat("won_rate",won_rate);
            editor.apply();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public void run()
    {
        URL url=null;
        try{
            url = new URL("http://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http=(HttpURLConnection) url.openConnection();
            InputStream in=http.getInputStream();

            String html=inputStream2String(in);
            Log.i(TAG,"run:html="+html);
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    private String inputStream2String(InputStream inputStream) throws IOException
    {
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out =new StringBuilder();
        Reader in=new InputStreamReader(inputStream,"gb2312");
        while(true){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0) break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}