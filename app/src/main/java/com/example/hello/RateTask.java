package com.example.hello;

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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class RateTask implements Runnable{
    private static final String TAG = "RateTask";
    private Handler handler;
    public void  setHandler(Handler handler){
        this.handler = handler;
    }
    @Override
    public void run()
    {
        List<String> list1=new ArrayList<String>();
        Document doc = null;
        float rate;
        try {
            doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj").get();
            Log.i(TAG, "run: title=" + doc.title());
            Element firsttable = doc.getElementsByTag("table").get(1);
            Elements trs=firsttable.getElementsByTag("tr");
            trs.remove(0);
            for (Element tr : trs) {
                Log.i(TAG, "run: "+tr.text());
                Elements tds = tr.getElementsByTag("td");
                Element td1 = tds.get(0);
                Element td2 = tds.get(4);
                String name=td1.text();
                if(td2.hasText()) rate =100/Float.parseFloat(td2.text());
                else rate=0.0f;
                list1.add(name+"=========>"+rate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(6,list1);
        handler.sendMessage(msg);
        Log.i(TAG, "run: send message");
    }

}
