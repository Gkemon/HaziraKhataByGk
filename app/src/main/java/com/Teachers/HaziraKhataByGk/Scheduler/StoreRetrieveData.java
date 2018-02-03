package com.Teachers.HaziraKhataByGk.Scheduler;

import android.content.Context;
import android.util.Log;

import com.Teachers.HaziraKhataByGk.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.Teachers.HaziraKhataByGk.MainActivity.databaseReference;
import static com.Teachers.HaziraKhataByGk.MainActivity.mUserId;

public class StoreRetrieveData {
    private Context mContext;
    private String mFileName;

    public StoreRetrieveData(Context context, String filename){
        mContext = context;
        mFileName = filename;
    }

//    public static JSONArray toJSONArray(ArrayList<ToDoItem> items) throws JSONException {
//        JSONArray jsonArray = new JSONArray();
//        for(ToDoItem item : items){
//            JSONObject jsonObject = item.toJSON();
//            jsonArray.put(jsonObject);
//        }
//        return  jsonArray;
//    }

    public void saveToFile(ArrayList<ToDoItem> items){
        databaseReference.child("Users").child(mUserId).child("Schedule").removeValue();
        Log.e("eee","from saveToFile");

        for(ToDoItem toDoItem:items) {
            databaseReference.child("Users").child(mUserId).child("Schedule").push().setValue(toDoItem);
            Log.d("eee",toDoItem.getToDoContent()+" from for loop");
        }

//        FileOutputStream fileOutputStream;
//        OutputStreamWriter outputStreamWriter;
//        fileOutputStream = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
//        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
//        outputStreamWriter.write(toJSONArray(items).toString());
//        outputStreamWriter.close();
//        fileOutputStream.close();


    }

    public static ArrayList<ToDoItem> loadFromFile(){
       final ArrayList<ToDoItem> items = new ArrayList<>();
        MainActivity.databaseReference.child("Users").child(mUserId).child("Schedule").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("eee","from loadFromFile");
                for(DataSnapshot toDoitemData:dataSnapshot.getChildren()){
                    ToDoItem toDoItem=new ToDoItem();
                    toDoItem=toDoitemData.getValue(ToDoItem.class);
                    Log.d("eee",toDoItem.getToDoContent()+" FROM LOAD FOR LOOP");
                    items.add(toDoItem);
                }
                if(items.isEmpty())Log.e("eee","Item is empty");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

//        ArrayList<ToDoItem> items = new ArrayList<>();
//        BufferedReader bufferedReader = null;
//        FileInputStream fileInputStream = null;
//        try {
//            fileInputStream =  mContext.openFileInput(mFileName);
//            StringBuilder builder = new StringBuilder();
//            String line;
//            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
//            while((line = bufferedReader.readLine())!=null){
//                builder.append(line);
//
//            }
//
//            JSONArray jsonArray = (JSONArray)new JSONTokener(builder.toString()).nextValue();
//            for(int i =0; i<jsonArray.length();i++){
//                ToDoItem item = new ToDoItem(jsonArray.getJSONObject(i));
//                items.add(item);
//            }
//
//
//        } catch (FileNotFoundException fnfe) {
//            //do nothing about it
//            //file won't exist first time app is run
//        }
//        finally {
//            if(bufferedReader!=null){
//                bufferedReader.close();
//            }
//            if(fileInputStream!=null){
//                fileInputStream.close();
//            }
//
//        }
        return items;
    }



}
