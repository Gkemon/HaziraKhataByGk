package com.Teachers.HaziraKhataByGk.Scheduler;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.ganfra.materialspinner.MaterialSpinner;

public class ReminderActivity extends AppCompatActivity {
    private TextView mtoDoTextTextView;
    private Button mRemoveToDoButton,mExitButtom;
    private MaterialSpinner mSnoozeSpinner;
    private String[] snoozeOptionsArray;
    private StoreRetrieveData storeRetrieveData;
    private ArrayList<ToDoItem> mToDoItems;
    private ToDoItem mItem;
    public static final String EXIT = "com.Teachers.HaziraKhataByGk.Scheduler.exit";
    private TextView mSnoozeTextView;
    String theme;
    public LinearLayout adlayout;

   // AnalyticsApplication app;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        if(theme.equals(ScheduleActivity.LIGHTTHEME)){
            setTheme(R.style.CustomStyle_LightTheme);
        }
        else{
            setTheme(R.style.CustomStyle_DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_layout);
      //  storeRetrieveData = new StoreRetrieveData(this, ScheduleActivity.FILENAME);

     //   mToDoItems = storeRetrieveData.loadToDoFromServer();

       // if(mToDoItems.size()==0) mToDoItems = ScheduleActivity.getLocallyStoredData(storeRetrieveData);




        Intent i = getIntent();
        int id = (Integer) i.getSerializableExtra(TodoNotificationService.TODOUUID);
        mItem = new ToDoItem();
        for(int j=0;j<mToDoItems.size();j++){
            if (j==id){
                mItem = mToDoItems.get(j);
                break;
            }
        }

        snoozeOptionsArray = getResources().getStringArray(R.array.snooze_options);

        mRemoveToDoButton = (Button)findViewById(R.id.toDoReminderRemoveButton);
        mtoDoTextTextView = (TextView)findViewById(R.id.toDoReminderTextViewBody);
        mSnoozeTextView = (TextView)findViewById(R.id.reminderViewSnoozeTextView);
        mSnoozeSpinner = (MaterialSpinner)findViewById(R.id.todoReminderSnoozeSpinner);
        mExitButtom = (Button) findViewById(R.id.toDoReminderExit);

        mExitButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (mItem!=null)
        mtoDoTextTextView.setText(mItem.getToDoText());
        else{
            mtoDoTextTextView.setText("");
            Toast.makeText(this,"আপনার ডিভাইসের যান্ত্রিক গোলযোগের কারনে টাইটেলটি লোড হচ্ছেনা । দয়া করে আবার চেষ্টা করুন",Toast.LENGTH_LONG).show();
        }


        if(theme.equals(ScheduleActivity.LIGHTTHEME)){
            mSnoozeTextView.setTextColor(getResources().getColor(R.color.secondary_text));
        }
        else{
            mSnoozeTextView.setTextColor(Color.WHITE);
            mSnoozeTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_snooze_white_24dp,0,0,0
            );
        }

        mRemoveToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   app.send(this, "Action", "Todo Removed fromTime Reminder Activity");


                if(!mItem.isDaily())
                mToDoItems.remove(mItem);



                saveData();
                closeApp();
                finish();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_text_view, snoozeOptionsArray);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mSnoozeSpinner.setAdapter(adapter);


    }

    private void closeApp(){
        Intent i = new Intent(ReminderActivity.this, ScheduleActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    private Date addTimeToDate(int mins){
     //   app.send(this, "Action", "Snoozed", "For "+mins+" minutes");
        Log.d("GK",String.valueOf(mins)+" MIN");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, mins);
        return calendar.getTime();
    }

    private int valueFromSpinner(){
        switch (mSnoozeSpinner.getSelectedItemPosition()){
            case 0:
                return 10;

            case 1:
                return 30;
            case 2:
                return 60;
            default:
                return 0;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toDoReminderDoneMenuItem:
                Date date;
//                if(mItem.isDaily()&&valueFromSpinner()==0){
//                    date = addTimeToDate(50);
//                    Log.d("GK","HAS DAILY SCHEDULE");
//                }
//                else {
                    date = addTimeToDate(valueFromSpinner());
            //    }


                mItem.setToDoDate(date);
                mItem.setHasReminder(true);


                saveData();
                closeApp();

                //foo
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Date date;

            date = addTimeToDate(valueFromSpinner());




        mItem.setToDoDate(date);
        mItem.setHasReminder(true);


        saveData();
        closeApp();
    }


    private void saveData(){
       // try{
        if(mToDoItems!=null)
            storeRetrieveData.saveToServer(mToDoItems);
      //  }
       // catch (JSONException | IOException e){
       //     e.printStackTrace();
    //    }
    }






    @Override
    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
        super.onDestroy();



        Date date;

            date = addTimeToDate(valueFromSpinner());



        mItem.setToDoDate(date);
        mItem.setHasReminder(true);


        saveData();
        closeApp();
    }
}
