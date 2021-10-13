package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends ListActivity{

    private static final String TAG ="MyListActivity";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler =new Handler(Looper.myLooper()){
          public void handleMessage(Message msg){
              super.handleMessage(msg);
              if(msg.what==6)
              {
                  List<String> list=(List<String>)msg.obj;
                  ListAdapter adapter=new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1,list);
                  setListAdapter(adapter);
              }
          }
        };
        RateTask rtask=new RateTask();
        rtask.setHandler(handler);
        Thread t=new Thread(rtask);
        t.start();
    }

}