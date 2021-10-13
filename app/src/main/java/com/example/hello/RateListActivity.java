package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class RateListActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);
        ListView mylist=(ListView)findViewById(R.id.mylist1);

        handler =new Handler(Looper.myLooper()){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==6)
                {
                    List<String> list=(List<String>)msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1,list);
                    mylist.setAdapter(adapter);
                }
            }
        };
//        RateTask rtask=new RateTask();
//        rtask.setHandler(handler);
//        Thread t=new Thread(rtask);
//        t.start();
    }

}