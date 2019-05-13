package com.Teachers.HaziraKhataByGk.Scheduler;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;

public class DeleteNotificationService extends IntentService {
    private StoreRetrieveData storeRetrieveData;
    private ArrayList<ToDoItem> mToDoItems;
    private ToDoItem mItem;
    public DeleteNotificationService(){
        super("DeleteNotificationService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
       // storeRetrieveData = new StoreRetrieveData(this, ScheduleActivity.FILENAME);
        Integer position = (Integer) intent.getSerializableExtra(ScheduleActivity.ITEM_POSITION);
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


    }


    private void saveData(){

        try{
            storeRetrieveData.saveToServer(mToDoItems);
        }         catch (Exception e) {
            e.printStackTrace();
        }     }

        @Override     public void onDestroy() {
            super.onDestroy();
            saveData();
    }
            private ArrayList<ToDoItem> loadData(){
            try{
               // return storeRetrieveData.loadToDoFromServer();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
}