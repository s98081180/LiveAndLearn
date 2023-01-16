package com.example.livelearn;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import myObject.Course;
import myObject.DataBaseHelper;

public class College extends AppCompatActivity {
    private Spinner spnCollegeArea; //大學地區
    private Spinner spnCollegeCategory; //大學課程類別
    private static DataBaseHelper db;
    private String[] spnChoices; //Spinner選擇的項目內容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.college);

        setActionBar();
        setSpn();
        setLstViewCourse(findViewById(R.id.lstViewCourse), College.this, 1);
        copy_live_and_learnDB();
        setBtnQuery();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.setClass(College.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void copy_live_and_learnDB(){
        db = new DataBaseHelper(this);

        try {
            db.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
    }

    //設定上方欄
    private void setActionBar(){
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //新增左上角(標題欄)返回鍵
    }

    //設定下拉選單
    private void setSpn(){
        spnCollegeArea = findViewById(R.id.spnCollegeArea);
        spnCollegeCategory = findViewById(R.id.spnCollegeCategory);
        String[] area=new String[]{"選擇地區","全部區域","中正區","大同區","中山區","松山區","大安區","萬華區","信義區","士林區","北投區","內湖區","南港區","文山區"};
        String[] category = new String[]{"選擇類別","全部類別","學術－人文學類","學術－社會科學類","學術－自然科學類","社團活動－公共性社團類","社團活動－非公共性社團類",
                "生活藝能－資訊科技類","生活藝能－國際語文類","生活藝能－美術工藝類","生活藝能－表演藝術類","生活藝能－影像視覺類",
                "生活藝能－運動舞蹈類","生活藝能－養生保健類","生活藝能－生活應用類","生活藝能－烹飪美食類","生活藝能－投資理財類"};

        ArrayAdapter<String> adapAreaList = new ArrayAdapter<>(College.this,
                android.R.layout.simple_spinner_dropdown_item, area);
        spnCollegeArea.setAdapter(adapAreaList);
        ArrayAdapter<String> adapCategoryList = new ArrayAdapter<>(College.this,
                android.R.layout.simple_spinner_dropdown_item, category);
        spnCollegeCategory.setAdapter(adapCategoryList);

        spnChoices = new String[2];
        Spinner.OnItemSelectedListener spnSelListener = new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { //i:選擇項目的id
                int adpChoiceIndex = 0;
                switch (adapterView.getId()){
                    case R.id.spnCollegeArea:
                        adpChoiceIndex = 0;
                        break;
                    case R.id.spnCollegeCategory:
                        adpChoiceIndex = 1;
                        break;
                }
                if(i != 0 && i != 1) spnChoices[adpChoiceIndex] = adapterView.getSelectedItem().toString();
                else spnChoices[adpChoiceIndex] = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };

        spnCollegeArea.setOnItemSelectedListener(spnSelListener);
        spnCollegeCategory.setOnItemSelectedListener(spnSelListener);
    }

    public static void setLstViewCourse(ListView listView, Context thisContext,int type){
        listView.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(thisContext, College_details.class);
                int _ID = Integer.parseInt(String.valueOf(((TextView)adapterView.findViewById(R.id.txtId)).getText()));
                intent.putExtra("_ID", _ID);
                intent.putExtra("type", type);
                thisContext.startActivity(intent);
            }
        });
    }

    public static void UpdateAdapter(ListView listView, Context thisContext, Cursor cursor){
        List<Course> courses = new ArrayList<>(); //課程List

        for(int i = 0;i < cursor.getCount();i++){
            Course thisCourse = new Course();
            thisCourse.setCLASSIFICATION(cursor.getString(0));
            thisCourse.setNAME(cursor.getString(1));
            thisCourse.setDATE(cursor.getString(2));
            thisCourse.setLECTURER(cursor.getString(3));
            thisCourse.setSCHOOL(cursor.getString(4));
            thisCourse.setWEEK(cursor.getString(5));
            thisCourse.setSTATUS(cursor.getString(6));
            thisCourse.setID(cursor.getInt(7));
            thisCourse.setLIKECOURSE(cursor.getString(8));
            thisCourse.setMYCOURSE(cursor.getString(9));
            Log.d("courseLib",cursor.getString(1) + " " + cursor.getString(8) + " " + cursor.getString(9));
            courses.add(thisCourse);
            cursor.moveToNext();
        }


        class LstViewCourseAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                return courses.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = LayoutInflater.from(thisContext).inflate(R.layout.college_course, null);
                ((TextView)view.findViewById(R.id.txtClassification)).setText(courses.get(i).getCLASSIFICATION());
                ((TextView)view.findViewById(R.id.txtName)).setText(courses.get(i).getNAME());
                ((TextView)view.findViewById(R.id.txtDate)).setText(courses.get(i).getDATE());
                ((TextView)view.findViewById(R.id.txtLecturer)).setText(courses.get(i).getLECTURER());
                ((TextView)view.findViewById(R.id.txtSchool)).setText(courses.get(i).getSCHOOL());
                ((TextView)view.findViewById(R.id.txtWeek)).setText(courses.get(i).getWEEK());
                ((TextView)view.findViewById(R.id.txtStatus)).setText(courses.get(i).getSTATUS());
                ((TextView)view.findViewById(R.id.txtId)).setText(String.valueOf(courses.get(i).getID()));

                Log.d("txtIdText", String.valueOf(courses.get(i).getNAME() + " " + courses.get(i).getID()));

                setBtnLike(thisContext, view, courses.get(i).getLIKECOURSE());
                setBtnMyCourse(thisContext, view, courses.get(i).getMYCOURSE());
                return view;
            }
        }

        if (cursor != null && cursor.getCount() >= 0){
            listView.setAdapter(new LstViewCourseAdapter());
        }
    }

    public static void setBtnLike(Context context, View itemView, String LIKECOURSE){
        ImageButton btnLike = itemView.findViewById(R.id.btnLike);

        int like = R.drawable.ic_favorite_black_24dp;
        int noLike = R.drawable.ic_favorite_border_black_24dp;
        Drawable drawable = ContextCompat.getDrawable(context, (LIKECOURSE.equals("0") ? noLike : like));
        btnLike.setImageDrawable(drawable);

        btnLike.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _ID = String.valueOf(((TextView)itemView.findViewById(R.id.txtId)).getText());
                if(LIKECOURSE.equals("0")) {
                    db.INSERT_LikeCourse(_ID);
                    Drawable drawable = ContextCompat.getDrawable(context, like);
                    btnLike.setImageDrawable(drawable);
                }
                else {
                    db.DELETE_LikeCourse(_ID);
                    Drawable drawable = ContextCompat.getDrawable(context, noLike);
                    btnLike.setImageDrawable(drawable);
                }
            }
        });
    }

    public static void setBtnMyCourse(Context context, View itemView,String MYCOURSE){
        ImageButton btnMyCourse = itemView.findViewById(R.id.btnMyCourse);

        int like = R.drawable.ic_school_black_24dp;
        int noLike =  R.drawable.ic_school_24px;
        Drawable drawable = ContextCompat.getDrawable(context, (MYCOURSE.equals("0") ? noLike : like));
        btnMyCourse.setImageDrawable(drawable);

        btnMyCourse.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _ID = String.valueOf(((TextView)itemView.findViewById(R.id.txtId)).getText());
                if(MYCOURSE.equals("0")) {
                    db.INSERT_MyCourse(_ID);
                    Drawable drawable = ContextCompat.getDrawable(context, like);
                    btnMyCourse.setImageDrawable(drawable);
                }
                else {
                    db.DELETE_MyCourse(_ID);
                    Drawable drawable = ContextCompat.getDrawable(context, noLike);
                    btnMyCourse.setImageDrawable(drawable);
                }
            }
        });
    }

    private void setBtnQuery(){
        Button btnQuery=(Button) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                UpdateAdapter(findViewById(R.id.lstViewCourse), College.this, getCourseBasic());
            }
        });
    }

    private Cursor getCourseBasic(){
        Cursor cursor = db.SELECT_Basic(spnChoices,1);
        db.close();
        return cursor;
    }

}
