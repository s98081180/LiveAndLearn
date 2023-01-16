package com.example.livelearn;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import myObject.Course;
import myObject.DataBaseHelper;

public class College_details extends AppCompatActivity {

    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.college_details);

//        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
//        if(!sdExist) {//如果不存在,
//            Log.e("SD卡管理：", "SD卡不存在，请加载SD卡");
//        }
//        Log.e("SD卡管理：", "SD卡存在");
//
//        Log.d("Environment", Environment.getExternalStorageDirectory().toString());

        int this_ID = this.getIntent().getIntExtra("_ID",-1);
        int type =  this.getIntent().getIntExtra("type",-1);
        setDB();
        setRecView(this_ID, type);
    }

    private void setDB(){
        db = new DataBaseHelper(this);
    }

    private void setRecView(int _ID, int type){
        RecyclerView recView = findViewById(R.id.recView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recView.setLayoutManager(linearLayoutManager);
        recView.setAdapter(new College_details_itemAdapter(getBasicDetails(_ID, type),this));
    }

    public class College_details_itemAdapter extends RecyclerView.Adapter<College_details_itemAdapter.college_details_itemHolder> {
        private List<Course> courses;
        private Context context;

        public College_details_itemAdapter(Cursor cursor, Context context) {
            List<Course> courses = new ArrayList<>();
            for(int i = 0;i < cursor.getCount();i++){
                Course thisCourse = new Course();
                thisCourse.setCLASSIFICATION(cursor.getString(0));
                thisCourse.setNAME(cursor.getString(1));
                thisCourse.setDATE(cursor.getString(2));
                thisCourse.setLECTURER(cursor.getString(3));
                thisCourse.setSCHOOL(cursor.getString(4));
                thisCourse.setWEEK(cursor.getString(5));
                thisCourse.setSTATUS(cursor.getString(6));
                Course.Details thisDetails = thisCourse.getDETAILS();
                thisDetails.setPERIOD(cursor.getString(7));
                thisDetails.setFEES(cursor.getString(8));
                thisDetails.setOTHER_FEES(cursor.getString(9));
                thisDetails.setIDEAGOAL(cursor.getString(10));
                thisDetails.setMETHOD(cursor.getString(11));
                thisDetails.setTEACHER_PRESENTATION(cursor.getString(12));
                thisDetails.setREQUIRE(cursor.getString(13));
                thisDetails.setREMARK(cursor.getString(14));
                thisDetails.setADDRESS(cursor.getString(15));
                thisCourse.setID(cursor.getInt(16));
                thisCourse.setLIKECOURSE(cursor.getString(17));
                thisCourse.setMYCOURSE(cursor.getString(18));
                thisDetails.setSING_UP_HREF(cursor.getString(19));
                courses.add(thisCourse);
                cursor.moveToNext();

            }

            this.courses = courses;
            this.context = context;
        }

        class college_details_itemHolder extends RecyclerView.ViewHolder{
            private TextView[] txt;
            private Button btnSingUp;
            public college_details_itemHolder(View itemView) {
                super(itemView);

                txt = new TextView[]{
                        itemView.findViewById(R.id.txtClassification),
                        itemView.findViewById(R.id.txtName) ,
                        itemView.findViewById(R.id.txtDate),
                        itemView.findViewById(R.id.txtLecturer),
                        itemView.findViewById(R.id.txtSchool),
                        itemView.findViewById(R.id.txtWeek),
                        itemView.findViewById(R.id.txtStatus),
                        itemView.findViewById(R.id.txtPeriod),
                        itemView.findViewById(R.id.txtFees),
                        itemView.findViewById(R.id.txtOtherFees),
                        itemView.findViewById(R.id.txtIdeagoal),
                        itemView.findViewById(R.id.txtMethod),
                        itemView.findViewById(R.id.txtTeacher_Preaentation),
                        itemView.findViewById(R.id.txtRequire),
                        itemView.findViewById(R.id.txtRemark),
                        itemView.findViewById(R.id.txtAddress),
                        itemView.findViewById(R.id.txtId)
//                        itemView.findViewById(R.id.txtWeekLyContens)
                };

                btnSingUp = itemView.findViewById(R.id.btnSingUp);
            }
        }

        @Override
        public college_details_itemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            college_details_itemHolder holder =new college_details_itemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.college_details_item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull college_details_itemHolder holder, int position) {
            holder.txt[0].setText(courses.get(position).getCLASSIFICATION());
            holder.txt[1].setText(courses.get(position).getNAME());
            holder.txt[2].setText(courses.get(position).getDATE());
            holder.txt[3].setText(courses.get(position).getLECTURER());
            holder.txt[4].setText(courses.get(position).getSCHOOL());
            holder.txt[5].setText(courses.get(position).getWEEK());
            holder.txt[6].setText(courses.get(position).getSTATUS());
            Course.Details details = courses.get(position).getDETAILS();
            holder.txt[7].setText(details.getPERIOD());
            holder.txt[8].setText(details.getFEES());
            holder.txt[9].setText(details.getOTHER_FEES());
            holder.txt[10].setText(details.getIDEAGOAL());
            holder.txt[11].setText(details.getMETHOD());
            holder.txt[12].setText(details.getTEACHER_PRESENTATION());
            holder.txt[13].setText(details.getREQUIRE());
            holder.txt[14].setText(details.getREMARK());
            holder.txt[15].setText(details.getADDRESS());
            holder.txt[16].setText(String.valueOf(courses.get(position).getID()));
//            ((college_details_itemHolder)holder).txt[17].setText(details.getWEEKLY_CONTENTS());
            String SING_UP_HREF = courses.get(position).getDETAILS().getSING_UP_HREF();
            if(SING_UP_HREF.equals("None")){
                holder.btnSingUp.setVisibility(View.GONE);
            }
            else {
                holder.btnSingUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SING_UP_HREF));
                        startActivity(intent);
                    }
                });
            }
            College.setBtnLike(College_details.this, holder.itemView, courses.get(position).getLIKECOURSE());
            College.setBtnMyCourse(College_details.this, holder.itemView, courses.get(position).getMYCOURSE());

            ConstraintLayout conLayDetailsItem = holder.itemView.findViewById(R.id.conLayDetailsItem);
            setMap(College_details.this, holder.itemView);
        }

        @Override
        public int getItemCount() {
            return courses.size();
        }
    }

    private void setMap(Context parentContext, View view){
        FrameLayout frmLayoutMap = view.findViewById(R.id.frmLayoutMap);
        View mapView = LayoutInflater.from(parentContext).inflate(R.layout.map, frmLayoutMap, false);
        ImageButton btnGoogleMap = mapView.findViewById(R.id.btnGoogleMap);
        MapsActivity.setBtnGoogleMap(College_details.this, btnGoogleMap, 25.04510836449097, 121.58773325519928,"南港社區大學");
        frmLayoutMap.addView(mapView);
    }

    private Cursor getBasicDetails(int _ID, int type){
        Cursor cursor = db.SELECT_Basic_Details(_ID, type);
        db.close();
        return cursor;
    }

}
