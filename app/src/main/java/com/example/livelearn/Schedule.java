package com.example.livelearn;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import myObject.Course;
import myObject.DataBaseHelper;

public class Schedule extends AppCompatActivity {
    private RecyclerView[] recView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//新增左上角(標題欄)返回鍵

        setRecView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.setClass(Schedule.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Course>[] getData(){
        Cursor cursor = getMyCourse();
        List<Course>[] data = new List[]{
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
        };

        for(int i = 0;i < cursor.getCount();i++){
            Course thisCourse = new Course();
            thisCourse.setNAME(cursor.getString(0));
            thisCourse.setWEEK(cursor.getString(1));
            thisCourse.setID(cursor.getInt(2));
            thisCourse.getDETAILS().setADDRESS(cursor.getString(3));
            int dataIndex = -1;
            switch (thisCourse.getWEEK()){
                case "星期一": dataIndex = 0; break;
                case "星期二": dataIndex = 1; break;
                case "星期三": dataIndex = 2; break;
                case "星期四": dataIndex = 3; break;
                case "星期五": dataIndex = 4; break;
                case "星期六": dataIndex = 5; break;
                case "星期日": dataIndex = 6; break;
            }
            data[dataIndex].add(thisCourse);
            cursor.moveToNext();
        }

        return data;
    }

    private void setRecView(){
        recView = new RecyclerView[]{
                findViewById(R.id.recViewMon),
                findViewById(R.id.recViewTue),
                findViewById(R.id.recViewWen),
                findViewById(R.id.recViewThur),
                findViewById(R.id.recViewFri),
                findViewById(R.id.recViewSat),
                findViewById(R.id.recViewSun),
        };

        class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.RecViewHolder>{
            private Context context;
            private List<Course> data;

            public RecViewAdapter(Context context, List<Course> data){
                this.data = data;
                this.context = context;
            }

            @Override
            public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item,parent,false);
                RecViewHolder recViewHolder = new RecViewHolder(view);
                recViewHolder.txtName = view.findViewById(R.id.txtName);
                recViewHolder.txtTime = view.findViewById(R.id.txtTime);
                recViewHolder.txtId = view.findViewById(R.id.txtId);
                return recViewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
                Course course = data.get(position);
                holder.txtName.setText(course.getNAME());
//                holder.txtTime.setText();
                holder.txtId.setText(String.valueOf(course.getID()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LinearLayout linLayoutSchedule = findViewById(R.id.linLayoutSchedule);
                        linLayoutSchedule.setVisibility(View.GONE);
                        FrameLayout fraLayout = findViewById(R.id.fraLayout);
                        View schedule_click_itemView = LayoutInflater.from(Schedule.this).inflate(R.layout.schedule_click_item, fraLayout, false);
                        ((TextView)schedule_click_itemView.findViewById(R.id.txtId)).setText(String.valueOf(course.getID()));
                        ((TextView)schedule_click_itemView.findViewById(R.id.txtName)).setText(course.getNAME());
//                        ((TextView)schedule_click_itemView.findViewById(R.id.txtTime)).setText(course.getTime());
                        College.setBtnMyCourse(Schedule.this,schedule_click_itemView,"1");
                        fraLayout.addView(schedule_click_itemView);
                        Button btnBack = schedule_click_itemView.findViewById(R.id.btnBack);
                        setMap(Schedule.this, schedule_click_itemView);
                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fraLayout.removeView(schedule_click_itemView);
                                linLayoutSchedule.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }

            @Override
            public int getItemCount() {
                return data.size();
            }

            class RecViewHolder extends RecyclerView.ViewHolder{
                TextView txtName;
                TextView txtTime;
                TextView txtId;
                public RecViewHolder(@NonNull View itemView) {
                    super(itemView);
                }
            }
        }

        for(int i = 0; i < recView.length; i++){
            recView[i].setLayoutManager(new LinearLayoutManager(this));
            recView[i].setAdapter(new RecViewAdapter(Schedule.this, getData()[i]));
        }
    }

    private Cursor getMyCourse(){
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor cursor = db.SELECT_MyCourse();
        db.close();
        return cursor;
    }

    //改變背景颜色
    private void darkenBackground(Float bgcolor){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp); //指定自定義窗口屬性
    }

    private void setMap(Context parentContext, View view){
        FrameLayout frmLayioutMap = view.findViewById(R.id.frmLayoutMap);
        View mapView = LayoutInflater.from(parentContext).inflate(R.layout.map, frmLayioutMap, false);
        ImageButton btnGoogleMap = mapView.findViewById(R.id.btnGoogleMap);
        MapsActivity.setBtnGoogleMap(Schedule.this, btnGoogleMap, 25.04510836449097, 121.58773325519928,"南港社區大學");
        frmLayioutMap.addView(mapView);
    }
}
