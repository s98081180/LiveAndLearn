package com.example.livelearn;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBtnMyCourse();
        setBtnLikeCourse();

        setItemSetUp(findViewById(R.id.btmNavView),MainActivity.this);

    }

    private void setBtnMyCourse(){
        Button btnSchedule = findViewById(R.id.btnSchedule);
        btnSchedule.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, Schedule.class);
                startActivity(intent);
            }
        });
    }

    private void setBtnLikeCourse(){
        Button btnLikeCourse = findViewById(R.id.btnLikeCourse);
        btnLikeCourse.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, LikeCourse.class);
                startActivity(intent);
            }
        });
    }

    //test
    private void setbtnHowtouse(){
        int[] image = new int[]{R.drawable.test};
        int imageIndex = 0;
        LinearLayout linLaySetUp = findViewById(R.id.linLaySetUp);
        View howtouseView = LayoutInflater.from(MainActivity.this).inflate(R.layout.howtouse, linLaySetUp, false);
        Drawable drawable = ContextCompat.getDrawable(MainActivity.this, image[imageIndex]);
        CardView cardViewHowtouse = howtouseView.findViewById(R.id.cardViewHowtouse);
        cardViewHowtouse.setBackground(drawable);
        linLaySetUp.addView(howtouseView);
        linLaySetUp.findViewById(R.id.lstSetup).setVisibility(View.GONE);
    }

    private static void setItemSetUp(BottomNavigationView btmNavView, Context thisContext){
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent=new Intent();
                switch (item.getItemId()) {
                    case R.id.itemHome:
                        intent.setClass(thisContext, MainActivity.class);
                        break;
                    case R.id.itemCommunityCollege:
                        intent.setClass(thisContext, College.class);
                        break;
                    case R.id.itemLearningCenter:
                        intent.setClass(thisContext, LearningCenter.class);
                        break;
                    case R.id.itemSetUp:
                        intent.setClass(thisContext, SetUp.class);
                        break;
                }
                thisContext.startActivity(intent);
                return true;
            }
        });
    }
}