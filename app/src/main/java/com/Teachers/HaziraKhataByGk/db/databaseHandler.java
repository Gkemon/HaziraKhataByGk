package com.Teachers.HaziraKhataByGk.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.AttendanceActivity;
import com.Teachers.HaziraKhataByGk.ClassRoom_activity;
import com.Teachers.HaziraKhataByGk.constant.StudentField;
import com.Teachers.HaziraKhataByGk.model.Notes;
import com.Teachers.HaziraKhataByGk.model.class_item;
import com.Teachers.HaziraKhataByGk.model.student;
import com.Teachers.HaziraKhataByGk.studentActivity;

public class databaseHandler extends SQLiteOpenHelper{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "student.db";
    private static class_item classitem;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private  Context context;
    static String query;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StudentField.TABLE_NAME+" (" +
                    StudentField.COLUMN_ID + " TEXT PRIMARY KEY ," +
                    StudentField.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    StudentField.COLUMN_PHONE + TEXT_TYPE + COMMA_SEP +
                    StudentField.COLUMN_ParentsName+TEXT_TYPE+COMMA_SEP+
                    StudentField.COLUMN_ParentsNumber+TEXT_TYPE+COMMA_SEP+
                    StudentField.COLUMN_noteForStudent+TEXT_TYPE+COMMA_SEP+
                    StudentField.COLUMN_CLASS +TEXT_TYPE+" );";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudentField.TABLE_NAME;

    private static final String attendence="CREATE TABLE ATTENDENCE ("
            +StudentField.COLUMN_ID+" TEXT,"+"datex DATE , subject TEXT,"
            +StudentField.COLUMN_CLASS+TEXT_TYPE+COMMA_SEP+" IsAttend BOOLEAN );";
    private static final String Notes="CREATE TABLE NOTES ("+StudentField.COLUMN_CLASS+" TEXT ,Title TEXT,Content TEXT );";


//Main Query with PK,FK
//    private static final String attendence="CREATE TABLE ATTENDENCE ("+StudentField.COLUMN_ID+" INTEGER PRIMARY KEY,"+"datex DATE , subject TEXT,"+StudentField.COLUMN_CLASS+TEXT_TYPE+COMMA_SEP+"IsAttend BOOLEAN,"+" FOREIGN KEY ("+StudentField.COLUMN_ID+")  REFERENCES "+StudentField.TABLE_NAME+
//            "("+StudentField.COLUMN_ID+"),"+" FOREIGN KEY ("+StudentField.COLUMN_CLASS+")  REFERENCES "+StudentField.TABLE_NAME+
//            "("+StudentField.COLUMN_CLASS+"));";
//    public databaseHandler(Context context,class_item classitem) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        this.classitem=classitem;
//    }


    public databaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Toast.makeText(context, "Edited!", Toast.LENGTH_SHORT).show();
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(attendence);
        db.execSQL(Notes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }
    public void createAttendence(String id,String date,String subject,boolean b){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StudentField.COLUMN_ID,id);
        values.put(StudentField.COLUMN_CLASS, ClassRoom_activity.classitem.getName());
        values.put("IsAttend",b);
        values.put("datex",date);
        values.put("subject",subject);
        long newRowId;
        newRowId=db.insert("ATTENDENCE",null,values);
        Toast.makeText(context, "ATTENDENCE! "+newRowId+subject+id, Toast.LENGTH_SHORT).show();

    }
//    public Cursor totalClassNumber(){
//        SQLiteDatabase db=getReadableDatabase();
//
//        String[] projection = {StudentField.COLUMN_ID,"datex","subject",StudentField.COLUMN_CLASS,"IsAttend"};
//        Cursor c = db.query(
//                "ATTENDENCE",                    // The table to query
//                projection,                                 // The columns to return
//                StudentField.COLUMN_CLASS+" = '"+ AttendanceActivity.classitemAttendence.getStudentName()+"'",                                       // The columns for the WHERE clause
//                null,                                       // The values for the WHERE clause
//                null,                                       // don't group the rows
//                null,                                       // don't filter by row groups
//                null                                   // The sort order
//        );
//        Toast.makeText(context,c.getInt(0)+"this is id", Toast.LENGTH_SHORT).show();
//
//       return  c;
//    }


    public void create(student student){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(StudentField.COLUMN_ID,student.getId());
        values.put(StudentField.COLUMN_NAME,student.getStudentName());
        values.put(StudentField.COLUMN_PHONE,student.getPhone());
        values.put(StudentField.COLUMN_ParentsName,student.getParentName());
        values.put(StudentField.COLUMN_ParentsNumber,student.getParentContact());
        values.put(StudentField.COLUMN_CLASS, studentActivity.contactofSA.getName());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                StudentField.TABLE_NAME,
                null,
                values);
        if(newRowId==-1){
            //if same roll number insert twice then db.insert return -1
            Toast.makeText(context, "একই রোল বার বার দেয়া যাবে নাহ"+newRowId, Toast.LENGTH_LONG).show();
        }
        Toast.makeText(context, "Table created"+newRowId, Toast.LENGTH_SHORT).show();
    }
    public void createNotes(com.Teachers.HaziraKhataByGk.model.Notes Notes){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(StudentField.COLUMN_CLASS, ClassRoom_activity.classitem.getName());
        values.put("Title",Notes.getheading());
        values.put("Content",Notes.getContent());
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
               "NOTES",
                null,
                values);
        if(newRowId==-1){
            //if same roll number insert twice then db.insert return -1
            Toast.makeText(context, "একই নোট বার বার দেয়া যাবে নাহ"+newRowId, Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(context, "ডাটাবেসে নোট এড হয়েছে।"+newRowId, Toast.LENGTH_LONG).show();

    }
    public Cursor retrieve(){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StudentField.COLUMN_ID,
                StudentField.COLUMN_NAME,
                StudentField.COLUMN_PHONE,
                StudentField.COLUMN_CLASS,
                StudentField.COLUMN_ParentsName,
                StudentField.COLUMN_ParentsNumber};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                StudentField.COLUMN_ID + " ASC";

        Cursor c = db.query(
                StudentField.TABLE_NAME,                    // The table to query
                projection,                                 // The columns to return
                StudentField.COLUMN_CLASS+" = '"+ studentActivity.contactofSA.getName()+"'",                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        return c;
    }
    public Cursor retrieveNotes(){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                "Title","Content"
        };

        // How you want the results sorted in the resulting Cursor


        Cursor c = db.query(
                "NOTES",                    // The table to query
                projection,                                 // The columns to return
                StudentField.COLUMN_CLASS+" = '"+ClassRoom_activity.classitem.getName()+"'",                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                   // The sort order
        );

        return c;
    }
    public Cursor retrieveDataForASingleStudent(String id){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StudentField.COLUMN_ID,
                StudentField.COLUMN_NAME,
                StudentField.COLUMN_PHONE,
                StudentField.COLUMN_CLASS,
                StudentField.COLUMN_ParentsName,
                StudentField.COLUMN_ParentsNumber};


        Cursor c = db.query(
                StudentField.TABLE_NAME,                    // The table to query
                projection,                                 // The columns to return
 StudentField.COLUMN_CLASS+" = '"+ AttendanceActivity.classitemAttendence.getName()
         +"' AND "+StudentField.COLUMN_ID+" = '"+id+"'",                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                  // The sort order
        );

        return c;
    }

    public Cursor totalClassNumber(String id){
        SQLiteDatabase database;
        database=getReadableDatabase();
        String qc = "SELECT * FROM ATTENDENCE WHERE student_class = '" + AttendanceActivity.classitemAttendence.getName()+ "' AND student_roll = '"+id+"';";

            Cursor cursor =database.rawQuery(qc,null);
            return cursor;

    }
    public Cursor attendClass(String id){
        SQLiteDatabase database;
        database=getReadableDatabase();
        String qc = "SELECT * FROM ATTENDENCE WHERE student_class = '" + AttendanceActivity.classitemAttendence.getName()+ "' AND student_roll = '"+id+"' AND IsAttend = 1 ;";
        Cursor cursor =database.rawQuery(qc,null);
        return cursor;
    }
    public Cursor AbsentClass(String id){
        SQLiteDatabase database;
        database=getReadableDatabase();
        String qc = "SELECT * FROM ATTENDENCE WHERE student_class = '" + AttendanceActivity.classitemAttendence.getName()+ "' AND student_roll = '"+id+"' AND IsAttend = 0 ;";
        Cursor cursor =database.rawQuery(qc,null);
        return cursor;
    }

    public Cursor retrieveForAttendence(){

        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StudentField.COLUMN_ID,
                StudentField.COLUMN_NAME,
                StudentField.COLUMN_PHONE,
                StudentField.COLUMN_CLASS};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                StudentField.COLUMN_ID + " ASC";

        Cursor cursor = db.query(
                StudentField.TABLE_NAME,                    // The table to query
                projection,                                 // The columns to return
                StudentField.COLUMN_CLASS+" = '"+ AttendanceActivity.classitemAttendence.getName()+"'",                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );
        return cursor;
    }
    public void update(student student){
        SQLiteDatabase db = getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();

        values.put(StudentField.COLUMN_ID,student.getId());
        values.put(StudentField.COLUMN_NAME,student.getStudentName());
        values.put(StudentField.COLUMN_PHONE,student.getPhone());
        values.put(StudentField.COLUMN_ParentsName,student.getParentName());
        values.put(StudentField.COLUMN_ParentsNumber,student.getParentContact());
        values.put(StudentField.COLUMN_CLASS, studentActivity.contactofSA.getName());

        // Which row to update, based on the ID
        String selection = StudentField.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { student.getId() };

        int count = db.update(
                StudentField.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        Toast.makeText(context,"Update"+count, Toast.LENGTH_SHORT).show();

    }
    public void updateNote(Notes Notes){
        SQLiteDatabase db = getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();

        values.put("Content",Notes.getContent());
        values.put("Title",Notes.getheading());
        values.put(StudentField.COLUMN_CLASS, ClassRoom_activity.classitem.getName());

        // Which row to update, based on the ID
        String selection = "Title LIKE ?";
        String[] selectionArgs = { Notes.getheading() };

        int count = db.update(
                "NOTES",
                values,
                selection,
                selectionArgs);
        Toast.makeText(context,"Update"+count, Toast.LENGTH_SHORT).show();

    }

    public void delete(String id){
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = StudentField.COLUMN_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { id };
        // Issue SQL statement.
        db.delete(StudentField.TABLE_NAME, selection, selectionArgs);
    }
    public void deleteNote(String title){
        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = "Title LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { title };
        // Issue SQL statement.
        db.delete("NOTES", selection, selectionArgs);
    }
    public Cursor RetrieveAttendenceListForSingleStudent(String id){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                "datex",
                "subject",
                "IsAttend"};

        Cursor c = db.query("ATTENDENCE",                    // The table to query
                projection,                                 // The columns to return
                StudentField.COLUMN_CLASS+" = '"+ AttendanceActivity.classitemAttendence.getName()
                        +"' AND "+StudentField.COLUMN_ID+" = '"+id+"'",                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                null                                // The sort order
        );

        return c;
    }

    }







