package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class RateList3Activity extends AppCompatActivity{

    private static final String TAG ="RateList3Activity";
    TextView ratevalue;
    EditText rmb;
    float result,count;
    String titlestr,detailstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list3);
        Intent intent = getIntent();
        titlestr = intent.getStringExtra("ItemTitle");
        detailstr = intent.getStringExtra("ItemDetail");
        ratevalue=findViewById(R.id.ratevalue);
        rmb=findViewById(R.id.rmb);
        ratevalue.setText(titlestr);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                count = Float.parseFloat(s.toString());
                Log.i(TAG, "count: " + count);
                result = count * Float.parseFloat(detailstr);
                ratevalue.setText(String.valueOf(result));
            }
        };
        rmb.addTextChangedListener(watcher);
    }
}