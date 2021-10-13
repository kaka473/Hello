package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateList2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TAG="RateList2Activity";
    Handler handler;
    ListView mylist2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list2);
        mylist2=findViewById(R.id.list);
        ProgressBar bar=findViewById(R.id.progressBar);
        handler =new Handler(Looper.myLooper()){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==6)
                {
                    ArrayList<HashMap<String,String>> listItems=(ArrayList<HashMap<String,String>>)msg.obj;
                    //SimpleAdapter listItemAdapter =new SimpleAdapter(RateList2Activity.this,listItems,R.layout.list_item,new String[]{"ItemTitle","ItemDetail"},new int[]{R.id.itemTitle,R.id.itemDetail});
                    //mylist2.setAdapter(listItemAdapter);
                    MyAdapter myAdapter=new MyAdapter(RateList2Activity.this,R.layout.list_item,listItems);
                    mylist2.setAdapter(myAdapter);

                    bar.setVisibility(View.GONE);
                    mylist2.setVisibility(View.VISIBLE);
                }
            }
        };
        RateTask2 rtask=new RateTask2();
        rtask.setHandler(handler);
        Thread t=new Thread(rtask);
        t.start();
        mylist2.setOnItemClickListener(this);
    }
    String titlestr,detailstr;
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Object itemAtPositon=mylist2.getItemAtPosition(position);
        HashMap<String,String> map=(HashMap<String,String>)itemAtPositon;
        titlestr=map.get("ItemTitle");
        detailstr=map.get("ItemDetail");
        Intent config=new Intent(this,RateList3Activity.class);
        config.putExtra("ItemTitle",titlestr);
        config.putExtra("ItemDetail",detailstr);
        startActivity(config);
        Log.i(TAG, "onItemClick: "+titlestr);
    }
//    public void open(View v){
//
//        //startActivityForResult(config,1001);
//    }
}