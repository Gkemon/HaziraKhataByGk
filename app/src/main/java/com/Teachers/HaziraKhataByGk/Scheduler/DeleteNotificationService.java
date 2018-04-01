package com.Teachers.HaziraKhataByGk.Scheduler;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class DeleteNotificationService extends IntentService {
    private StoreRetrieveData storeRetrieveData;
    private ArrayList<ToDoItem> mToDoItems;
    private ToDoItem mItem;
    private int position;
    public DeleteNotificationService(){
        super("DeleteNotificationService");
    }     @Override
    protected void onHandleIntent(Intent intent) {
        storeRetrieveData = new StoreRetrieveData(this, scheduleActivity.FILENAME);
        int position = (int)intent.getSerializableExtra(scheduleActivity.ITEM_POSITION);
        mToDoItems = loadData();
        if(mToDoItems!=null){


            for(int i=0;i<mToDoItems.size();i++){
                if(position==i){
                    mItem = mToDoItems.get(i);
                    break;
                }
            }


            if(mItem!=null){
                mToDoItems.remove(mItem);
                dataChanged();
                saveData();
            }
        }
    }     private void dataChanged(){

        SharedPreferences sharedPreferences = getSharedPreferences(scheduleActivity.SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(scheduleActivity.CHANGE_OCCURED, true);
        editor.apply();

    }


    private void saveData(){

        try{
            storeRetrieveData.saveToFile(mToDoItems);
        }         catch (Exception e) {
            e.printStackTrace();
        }     }

        @Override     public void onDestroy() {
            super.onDestroy();
            saveData();
    }
            private ArrayList<ToDoItem> loadData(){
            try{
                return storeRetrieveData.loadFromFile();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
}