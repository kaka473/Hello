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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FirstActivity extends AppCompatActivity{

    private static final String TAG ="FirstActivity";
    float result=0;
    float dollar_rate,euro_rate,won_rate;
    long time;
    Handler handler;
    Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        //Thread thread =new Thread(this);

        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollar_rate=sharedPreferences.getFloat("dollar_rate",0.0f);
        euro_rate=sharedPreferences.getFloat("euro_rate",0.0f);
        won_rate=sharedPreferences.getFloat("won_rate",0.0f);
        time=sharedPreferences.getLong("time",0);
        handler =new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG,"收到消息");
                if(msg.what==6){
                    Bundle bud=(Bundle) msg.obj;
                    dollar_rate=bud.getFloat("dollar");
                    euro_rate=bud.getFloat("euro");
                    won_rate=bud.getFloat("won");

                    SharedPreferences sp=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putFloat("dollar_rate",dollar_rate);
                    editor.putFloat("euro_rate",euro_rate);
                    editor.putFloat("won_rate",won_rate);
                    editor.apply();
                }
                super.handleMessage(msg);
            }
        };
        if(new Date().getTime()-time>86400000) {
            Log.i(TAG, "onCreate: 周期调用进程");
           timer.schedule(task,0,86400000);
        }
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
    TimerTask task= new TimerTask() {
        public void run () {
            SharedPreferences sp=getSharedPreferences("myrate",Activity.MODE_PRIVATE);

            SharedPreferences.Editor editor=sp.edit();
            editor.putLong("time",new Date().getTime());
            Log.i(TAG, "run: renewtime="+new Date().getTime());
            editor.apply();

            Document doc = null;
            try {
                doc = Jsoup.connect("https://usd-cny.com").get();
                Log.i(TAG, "run: title=" + doc.title());
                Element firsttable = doc.getElementsByTag("table").first();
                Elements trs = firsttable.getElementsByTag("tr");
                trs.remove(0);
                Bundle bud = new Bundle();
                Message msg = handler.obtainMessage(6);
                for (Element tr : trs) {
                    Elements tds = tr.getElementsByTag("td");
                    Element td1 = tds.get(0);
                    Element td2 = tds.get(4);
                    if ("美元".equals(td1.text())) {
                        bud.putFloat("dollar", 100 / Float.parseFloat(td2.text()));
                    } else if ("欧元".equals(td1.text())) {
                        bud.putFloat("euro", 100 / Float.parseFloat(td2.text()));
                    } else if ("韩币".equals(td1.text())) {
                        bud.putFloat("won", 100 / Float.parseFloat(td2.text()));
                    }
                }
                msg.obj = bud;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "run1: 执行进程");
        }
    };

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