package com.example.livelearn;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Senior extends AppCompatActivity{
    private Spinner seniorarea;//樂齡中心地區
    //private Spinner seniorcategory;

    String[] area=new String[]{"選擇地區","中正區","大同區","中山區","松山區","大安區","萬華區","信義區","士林區","北投區","內湖區","南港區","文山區"};
    //String[] category = new String[]{"選擇類別"};

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.senior);
        seniorarea = (Spinner) findViewById(R.id.seniorarea);
        //seniorcategory= (Spinner) findViewById(R.id.seniorcategory);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//新增左上角(標題欄)返回鍵
    }
}
