package com.example.livelearn;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import myObject.DataBaseHelper;

public class LikeCourse extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.like_course);

        ListView lstLikeCourse = findViewById(R.id.lstLikeCourse);
        College.setLstViewCourse(lstLikeCourse, LikeCourse.this, 2);
        College.UpdateAdapter(lstLikeCourse, LikeCourse.this, getLikeCourse());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//新增左上角(標題欄)返回鍵
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.setClass(LikeCourse.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private Cursor getLikeCourse(){
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor cursor = db.SELECT_Basic(new String[]{null,null},2);
        db.close();
        return cursor;
    }
}
