package com.example.livelearn;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class SetUp extends AppCompatActivity {
    private ListView Setup;
    ListAdapter setAdapter;
    String[] menu={"通知管理","顏色更改","使用教學","建議與希望"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_up);

        Setup=(ListView)findViewById(R.id.lstSetup);
        setAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,menu);//將內容利用Adapter顯示在listview上
        Setup.setAdapter(setAdapter);
        Setup.setOnItemClickListener(onClickListView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//新增左上角(標題欄)返回鍵
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.setClass(SetUp.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private AdapterView.OnItemClickListener onClickListView=new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            toNewActivity(position);
        }
    };

    private void toNewActivity(int position){
        Intent i = null;
        switch (position){
            case 0:
                i = new Intent(SetUp.this, Notice.class);
                break;
            case 1:
                i = new Intent(SetUp.this, Color.class);
                break;
            case 2:
                setHowtouse();
                break;
            case 3:
                i = new Intent(SetUp.this, Suggest.class);
                break;
            default:
                i = new Intent(SetUp.this,SetUp.class);
                break;
        }
        if(position != 2) startActivity(i);
    }

    private void setHowtouse(){
        int[] image = new int[]{R.drawable.test};
        int imageIndex = 0;
        LinearLayout linLaySetUp = findViewById(R.id.linLaySetUp);
        View howtouseView = LayoutInflater.from(SetUp.this).inflate(R.layout.howtouse, linLaySetUp, false);
        Drawable drawable = ContextCompat.getDrawable(SetUp.this, image[imageIndex]);
        CardView cardViewHowtouse = howtouseView.findViewById(R.id.cardViewHowtouse);
        cardViewHowtouse.setBackground(drawable);
        linLaySetUp.addView(howtouseView);
        linLaySetUp.findViewById(R.id.lstSetup).setVisibility(View.GONE);
    }

}