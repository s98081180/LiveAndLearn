package com.example.livelearn;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import myObject.DataBaseHelper;
import myObject.LearningCenterCourse;

public class LearningCenter extends AppCompatActivity {
    private String spnChoice; //Spinner選擇的項目內容

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_center);

        setSpn();
        recViewLearningCenter();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//新增左上角(標題欄)返回鍵
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.setClass(LearningCenter.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void recViewLearningCenter(){
        RecyclerView recViewLearningCenter = findViewById(R.id.recViewLearningCenter);
        class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.RecViewHolder>{
            private Context context;
            private List<LearningCenterCourse> data;

            public RecViewAdapter(Context context, Cursor cursor){
                this.data = new ArrayList<>();
                for(int i = 0;i < cursor.getCount();i++){
                    LearningCenterCourse thisCourse = new LearningCenterCourse();
                    thisCourse.setID(cursor.getString(0));
                    thisCourse.setLEARNINGCENTER(cursor.getString(1));
                    thisCourse.setCOURSESACTIVITIES(cursor.getString(2));
                    thisCourse.setDATE(cursor.getString(3));
                    thisCourse.setLOCATION(cursor.getString(4));
                    thisCourse.setLECTURER(cursor.getString(5));
                    thisCourse.setREGISTRATIONSITUATION(cursor.getString(6));
                    thisCourse.setCOST(cursor.getString(7));
                    thisCourse.setADDRESS(cursor.getString(8));
                    thisCourse.setPHONENUMBER(cursor.getString(9));
                    data.add(thisCourse);
                    cursor.moveToNext();
                }

                this.context = context;
            }

            @Override
            public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learning_center_item,parent,false);
                RecViewHolder recViewHolder = new RecViewHolder(view);
                recViewHolder.txtLearningCenter = view.findViewById(R.id.txtLearningCenter);
                recViewHolder.txtCoursesactivites = view.findViewById(R.id.txtCoursesactivites);
                recViewHolder.txtDate = view.findViewById(R.id.txtDate);
                recViewHolder.txtLocation = view.findViewById(R.id.txtLocation);
                recViewHolder.txtLecturer = view.findViewById(R.id.txtLecturer);
                recViewHolder.txtRegistrationsituation = view.findViewById(R.id.txtRegistrationsituation);
                recViewHolder.txtCost = view.findViewById(R.id.txtCost);
                recViewHolder.txtAddress = view.findViewById(R.id.txtAddress);
                recViewHolder.txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
                return recViewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
                LearningCenterCourse thisLearningCeterCourse = data.get(position);
                holder.txtLearningCenter.setText(thisLearningCeterCourse.getLEARNINGCENTER());
                holder.txtCoursesactivites.setText(thisLearningCeterCourse.getCOURSESACTIVITIES());
                holder.txtDate.setText(thisLearningCeterCourse.getDATE());
                holder.txtLocation.setText(thisLearningCeterCourse.getLOCATION());
                holder.txtLecturer.setText(thisLearningCeterCourse.getLECTURER());
                holder.txtRegistrationsituation.setText(thisLearningCeterCourse.getREGISTRATIONSITUATION());
                holder.txtCost.setText(thisLearningCeterCourse.getCOST());
                holder.txtAddress.setText(thisLearningCeterCourse.getADDRESS());
                holder.txtPhoneNumber.setText(thisLearningCeterCourse.getPHONENUMBER());
            }

            @Override
            public int getItemCount() {
                return data.size();
            }

            class RecViewHolder extends RecyclerView.ViewHolder{
                TextView txtLearningCenter;
                TextView txtCoursesactivites;
                TextView txtDate;
                TextView txtLocation;
                TextView txtLecturer;
                TextView txtRegistrationsituation;
                TextView txtCost;
                TextView txtAddress;
                TextView txtPhoneNumber;

                public RecViewHolder(@NonNull View itemView) {
                    super(itemView);
                }
            }
        }

        Button btnQuery = findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recViewLearningCenter.setLayoutManager(new LinearLayoutManager(LearningCenter.this));
                recViewLearningCenter.setAdapter(new RecViewAdapter(LearningCenter.this, getLearningCenterCourse()));
            }
        });
    }

    //設定下拉選單
    private void setSpn(){
        spnChoice = "";
        Spinner spnCenterArea = findViewById(R.id.spnCenterArea);
        String[] area=new String[]{"選擇地區","全部區域","士林區","中山區","文山區","大同區","南港區","信義區","內湖區","北投區","中正區","樂齡學習示範中心"};

        ArrayAdapter<String> adapAreaList = new ArrayAdapter<>(LearningCenter.this,
                android.R.layout.simple_spinner_dropdown_item, area);
        spnCenterArea.setAdapter(adapAreaList);
        spnCenterArea.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { //i:選擇項目的id
                if(i != 0 && i != 1) spnChoice = adapterView.getSelectedItem().toString();
                else spnChoice = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private Cursor getLearningCenterCourse(){
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor cursor = db.SELECT_LearningCenter(spnChoice);
        db.close();
        return cursor;
    }
}
