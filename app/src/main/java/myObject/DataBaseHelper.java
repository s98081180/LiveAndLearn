package myObject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.livelearn/databases";
    private static String DB_NAME = "live_and_learn.db";
    private SQLiteDatabase db;
    private final Context context;

    private String basicCondition = "";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }


    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(!dbExist){
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }catch(SQLiteException e){
            //如果資料庫不存在
            e.printStackTrace();
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            outputStream.write(buffer, 0, length);
        }

        //Close the streams
        outputStream.flush();
        outputStream.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if(db != null)
            db.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //查詢所有詳細資訊 //type:1(ALL)、2(LikeCourse)
    public Cursor SELECT_Basic(String[] spnChoice,int type){
        openDataBase();
        String sql = "SELECT CLASSIFICATION, NAME, DATE, LECTURER, SCHOOL, WEEK, STATUS, _ID, " +
                "CASE " +
                    "WHEN _ID IN(SELECT COURSE_ID FROM LikeCourse) THEN 1 " +
                    "ELSE 0 " +
                "END AS LIKECOURSE, " +
                "CASE " +
                    "WHEN _ID IN(SELECT COURSE_ID FROM MyCourse) THEN 1 " +
                    "ELSE 0 " +
                "END AS MYCOURSE " +
                "FROM CommunityCollege ";


        basicCondition = "";
        if(type == 2) basicCondition += ", LikeCourse ";
        if(spnChoice[0] != null || spnChoice[1] != null || type == 2) basicCondition += "WHERE ";
        if(type == 2) basicCondition += "_ID = COURSE_ID";
        if(spnChoice[0] != null) basicCondition += "SCHOOL LIKE '%"+ spnChoice[0].substring(0,2) +"%'";
        if(spnChoice[0] != null && spnChoice[1] != null) basicCondition += " AND ";
        if(spnChoice[1] != null) basicCondition += "CLASSIFICATION = '"+ spnChoice[1] +"'";

        Log.d("SELECTBasic", sql + basicCondition);

        Cursor cursor= db.rawQuery(sql + basicCondition,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor SELECT_Basic_Details(int _ID, int type){
        openDataBase();
        String sql = "SELECT CLASSIFICATION, NAME, DATE, LECTURER, SCHOOL, WEEK, STATUS, PERIOD, FEES, OTHER_FEES, IDEAGOAL, METHOD, TEACHER_PRESENTATION, REQUIRE, REMARK, ADDRESS, _ID, " +
                "CASE " +
                    "WHEN _ID IN(SELECT COURSE_ID FROM LikeCourse) THEN 1 " +
                    "ELSE 0 " +
                "END AS LIKECOURSE, " +
                "CASE " +
                    "WHEN _ID IN(SELECT COURSE_ID FROM MyCourse) THEN 1 " +
                    "ELSE 0 " +
                "END AS MYCOURSE, SING_UP_HREF " +
                "FROM CommunityCollege ";
        if(basicCondition.equals("")) sql += "WHERE ";
        sql += basicCondition;
        if(type == 1) sql += "_ID BETWEEN "+ (_ID - 5) + " AND " + (_ID + 5);
        if(type == 2) sql += "_ID = " + _ID;

        Log.d("SELECTDetails", sql);

        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToFirst();
        return cursor;
    }

    public void INSERT_LikeCourse(String _ID){
        openDataBase();
        String sql = "INSERT INTO LikeCourse VALUES (" + _ID + ")";
        Log.d("INSERTLikeCourse",sql);
        db.execSQL(sql);
        db.close();
    }

    public void DELETE_LikeCourse(String _ID){
        openDataBase();
        String sql = "DELETE FROM LikeCourse WHERE COURSE_ID = " + _ID;
        Log.d("DELETELikeCourse",sql);
        db.execSQL(sql);
        db.close();
    }

    public void INSERT_MyCourse(String _ID){
        openDataBase();
        String sql = "INSERT INTO MyCourse VALUES (" + _ID + ")";
        Log.d("INSERTMyCourse",sql);
        db.execSQL(sql);
        db.close();
    }

    public void DELETE_MyCourse(String _ID){
        openDataBase();
        String sql = "DELETE FROM MyCourse WHERE COURSE_ID = " + _ID;
        Log.d("DELETEMyCourse",sql);
        db.execSQL(sql);
        db.close();
    }

    public Cursor SELECT_MyCourse(){
        openDataBase();
        String sql = "SELECT NAME, WEEK, _ID, ADDRESS " +
                "FROM CommunityCollege,MyCourse " +
                "WHERE _ID = COURSE_ID";
        Log.d("SELECTMyCourse", sql);
        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor SELECT_LearningCenter(String spnChoice){
        openDataBase();
        String sql = "SELECT LearningCenter._ID,LEARNINGCENTER,COURSESACTIVITIES,DATE,LOCATION,LECTURER,REGISTRATIONSITUATION,COST,ADDRESS,TELEPHONENUMBER " +
                "FROM LearningCenter, LearningConnection " +
                "WHERE LEARNINGCENTER = CENTER AND LEARNINGCENTER LIKE '%"+ spnChoice +"%'";
        Log.d("SELECTLearningCenter", sql);
        Cursor cursor= db.rawQuery(sql,null);
        cursor.moveToFirst();
        return cursor;
    }
}
