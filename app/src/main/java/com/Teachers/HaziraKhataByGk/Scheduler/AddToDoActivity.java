package com.Teachers.HaziraKhataByGk.Scheduler;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.Teachers.HaziraKhataByGk.Scheduler.StoreRetrieveData.loadFromFile;
import static com.Teachers.HaziraKhataByGk.Scheduler.scheduleActivity.FILENAME;
import static com.Teachers.HaziraKhataByGk.Scheduler.scheduleActivity.PREVIOUS_ITEM;
import static com.Teachers.HaziraKhataByGk.Scheduler.scheduleActivity.getLocallyStoredData;

public class AddToDoActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private EditText mToDoTextBodyEditText;
    public SwitchCompat mToDoDateSwitch,switchCompatForDailyRemind;
    private LinearLayout mUserDateSpinnerContainingLinearLayout;
    private TextView mReminderTextView;
    private EditText mDateEditText;
    private EditText mTimeEditText;
    public EditText mToDoContentEditText;
    private ToDoItem mUserToDoItem,mPreviousItem;
    public FloatingActionButton mToDoSendFloatingActionButton;
    private String mUserEnteredText;
    private boolean mUserHasReminder;
    private Date mUserReminderDate;
    private String mTodoContent;
    private int mUserColor;
    public LinearLayout mContainerLayout;
    private String theme;
    public LinearLayout adlayout;
    public AdView mAdView;
    public LinearLayout DailyRemainderLayout;
    public InterstitialAd mInterstitialAd;
    public ProgressBar progressBar;
    private StoreRetrieveData storeRetrieveData;
    private Boolean IsNewToDo;//For dialog showing because we don't need to show warning for a new to do item
    public ArrayList<ToDoItem> mToDoItemsArrayList=scheduleActivity.mToDoItemsArrayList;
    public Context context;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageButton reminderIconImageButton;
        TextView reminderRemindMeTextView;
        theme = getSharedPreferences(scheduleActivity.THEME_PREFERENCES, MODE_PRIVATE).getString(scheduleActivity.THEME_SAVED, scheduleActivity.LIGHTTHEME);
        if(theme.equals(scheduleActivity.LIGHTTHEME)){
            setTheme(R.style.CustomStyle_LightTheme);
            Log.d("OskarSchindler", "Light Theme");
        }
        else{
            setTheme(R.style.CustomStyle_DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_test);
        mDateEditText = (EditText)findViewById(R.id.newTodoDateEditText);
        mTimeEditText = (EditText)findViewById(R.id.newTodoTimeEditText);
        DailyRemainderLayout=(LinearLayout)findViewById(R.id.dailyReminder);


        progressBar=(ProgressBar)findViewById(R.id.progressBarInScheduleActivity);
        progressBar.setVisibility(View.GONE);


        mUserToDoItem = (ToDoItem)getIntent().getSerializableExtra(scheduleActivity.TODOITEM);
        mPreviousItem = (ToDoItem)getIntent().getSerializableExtra(scheduleActivity.TODOITEM);


        Log.d("GK","mToDoItemsArrayList "+mToDoItemsArrayList.size()+"in create");


        mToDoItemsArrayList = (ArrayList<ToDoItem>)getIntent().getSerializableExtra("TODOLIST");

        //To find that the to do item is newly created
        if(mUserToDoItem.getToDoText().equals("")&&mUserToDoItem.getToDoContent().equals("")){
            IsNewToDo=true;
            mPreviousItem=mUserToDoItem;
          //  Log.d("GK","true");
        }
        else
        {
          //  Log.d("GK","false");
            IsNewToDo=false;
        }


        mUserEnteredText = mUserToDoItem.getToDoText().trim();
        mUserHasReminder = mUserToDoItem.hasReminder();
        mUserReminderDate = mUserToDoItem.getToDoDate();
        mTodoContent=mUserToDoItem.getToDoContent();
        Log.d("eee","This is content "+mTodoContent);
        mUserColor = mUserToDoItem.getTodoColor();


//TODO: for debug
//        if(mUserReminderDate!=null)
//        Toast.makeText(this,mUserReminderDate.toString(),Toast.LENGTH_LONG).show();
//        else Toast.makeText(this,"DATE IS NULL",Toast.LENGTH_LONG).show();

        reminderIconImageButton = (ImageButton)findViewById(R.id.userToDoReminderIconImageButton);
        reminderRemindMeTextView = (TextView)findViewById(R.id.userToDoRemindMeTextView);
        if(theme.equals(scheduleActivity.DARKTHEME)){
            reminderIconImageButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_alarm_add_white_24dp));
            reminderRemindMeTextView.setTextColor(Color.WHITE);
        }

        mContainerLayout = (LinearLayout)findViewById(R.id.todoReminderAndDateContainerLayout);
        mUserDateSpinnerContainingLinearLayout = (LinearLayout)findViewById(R.id.toDoEnterDateLinearLayout);
        mToDoTextBodyEditText = (EditText)findViewById(R.id.userToDoEditText);
        mToDoContentEditText =(EditText)findViewById(R.id.userToDoEditTextTask);
        mToDoDateSwitch = (SwitchCompat)findViewById(R.id.toDoHasDateSwitchCompat);
        switchCompatForDailyRemind=(SwitchCompat)findViewById(R.id.toDoHasDateSwitchCompatForDailyRemain);
        mToDoSendFloatingActionButton = (FloatingActionButton)findViewById(R.id.makeToDoFloatingActionButton);
        mReminderTextView = (TextView)findViewById(R.id.newToDoDateTimeReminderTextView);



        mContainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(mToDoTextBodyEditText);
            }
        });


        if(mUserHasReminder && (mUserReminderDate!=null)){
            setReminderTextView();
            setEnterDateLayoutVisibleWithAnimations(true);
        }
        //for daily button
        if(mUserHasReminder && (mUserReminderDate!=null)&&mUserToDoItem.isDaily())
            switchCompatForDailyRemind.setChecked(true);
        else switchCompatForDailyRemind.setChecked(false);

        if(mUserReminderDate==null){

            mToDoDateSwitch.setChecked(false);
            mReminderTextView.setVisibility(View.INVISIBLE);
        }
        mToDoTextBodyEditText.requestFocus();
        mToDoTextBodyEditText.setText(mUserEnteredText.trim());
        mToDoContentEditText.setText(mTodoContent.trim());

        //TODO: KEYBOARD
//        InputMethodManager imm = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        mToDoTextBodyEditText.setSelection(mToDoTextBodyEditText.length());
        mToDoTextBodyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserEnteredText = s.toString().trim();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mToDoContentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTodoContent = s.toString().trim();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


     setEnterDateLayoutVisible(mToDoDateSwitch.isChecked());

        mToDoDateSwitch.setChecked(mUserHasReminder && (mUserReminderDate != null));
        mToDoDateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    mUserReminderDate = null;
                }

                mUserHasReminder = isChecked;
                mUserToDoItem.setHasReminder(isChecked);

                setDateAndTimeEditText();
                setEnterDateLayoutVisibleWithAnimations(isChecked);
                hideKeyboard(mToDoTextBodyEditText);
            }
        });


        mToDoSendFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get to do items
                GetToDoItems();

                //Check duplicate

                if(CheckDuplicateForFAB()){
                    return;
                }


               if (mToDoTextBodyEditText.length() <= 0){
                    mToDoTextBodyEditText.setError(getString(R.string.todo_error));
                }
                else if(mUserReminderDate!=null && mUserReminderDate.before(new Date())){
                    makeResult(RESULT_CANCELED);
                }
                else{
                   progressBar.setVisibility(View.VISIBLE);
                    makeResult(RESULT_OK);
                   finish();

                   mInterstitialAd = new InterstitialAd(AddToDoActivity.this);
                   // set the ad unit ID
                   mInterstitialAd.setAdUnitId("ca-app-pub-8499573931707406/1629454676");

                   AdRequest adRequest = new AdRequest.Builder()
                           .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                           // Check the LogCat to get your test device ID
                           .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
                           .build();

                   // Load ads into Interstitial Ads
//                   mInterstitialAd.loadAd(adRequest);
//                   mInterstitialAd.setAdListener(new AdListener() {
//                       public void onAdLoaded() {
//                           showInterstitial();
//                       }
//
//                       @Override
//                       public void onAdFailedToLoad(int i) {
//                           finish();
//                           super.onAdFailedToLoad(i);
//                       }
//
//                       @Override
//                       public void onAdClosed() {
//                           finish();
//                           super.onAdClosed();
//                       }
//                   });
//

                }
                hideKeyboard(mToDoTextBodyEditText);
            }
        });
        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                hideKeyboard(mToDoTextBodyEditText);
                if(mUserToDoItem.getToDoDate()!=null){
                    date = mUserReminderDate;
                }
                else{
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddToDoActivity.this, year, month, day);
                if(theme.equals(scheduleActivity.DARKTHEME)){
                    datePickerDialog.setThemeDark(true);
                }
                datePickerDialog.show(getFragmentManager(), "DateFragment");

            }
        });

        mTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                hideKeyboard(mToDoTextBodyEditText);
                if(mUserToDoItem.getToDoDate()!=null){
                    date = mUserReminderDate;
                }
                else{
                    date = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddToDoActivity.this, hour, minute, DateFormat.is24HourFormat(AddToDoActivity.this));
                if(theme.equals(scheduleActivity.DARKTHEME)){
                    timePickerDialog.setThemeDark(true);
                }
                timePickerDialog.show(getFragmentManager(), "TimeFragment");
            }
        });
       // setDateAndTimeEditText();
    }

    private void setDateAndTimeEditText(){
        //SET DATE (TODAY) AND TIME IN TEXT
        if(mUserToDoItem.hasReminder() && mUserReminderDate!=null){
            String userDate = formatDate("d MMM, yyyy", mUserReminderDate);
            String formatToUse;
            if(DateFormat.is24HourFormat(this)){
                formatToUse = "k:mm";
            }
            else{
                formatToUse = "h:mm a";
            }
            String userTime = formatDate(formatToUse, mUserReminderDate);
            mTimeEditText.setText(userTime);
            mDateEditText.setText(userDate);
        }
        else{
            mDateEditText.setText(getString(R.string.date_reminder_default));
            Toast.makeText(AddToDoActivity.this,mDateEditText.getText().toString()+" স্বংক্রিয়ভাবে সেট হল",Toast.LENGTH_SHORT).show();
            boolean time24 = DateFormat.is24HourFormat(this);
            Calendar cal = Calendar.getInstance();
            if(time24){
                cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+1);
            }
            else{
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)+1);
            }
            cal.set(Calendar.MINUTE, 0);
            mUserReminderDate = cal.getTime();
            String timeString;
            if(time24){
                timeString = formatDate("k:mm", mUserReminderDate);
            }
            else{
                timeString = formatDate("h:mm a", mUserReminderDate);
            }
            mTimeEditText.setText(timeString);

        }
    }

    public void hideKeyboard(EditText et){
//        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public void setDate(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        int hour, minute;
        Calendar reminderCalendar = Calendar.getInstance();
        reminderCalendar.set(year, month, day);
        
        if(reminderCalendar.before(calendar)){
            Toast.makeText(this, "আপনার ডিভাইসের তারিখ এবং সময় ঠিক নেই", Toast.LENGTH_LONG).show();
            return;
        }
        if(mUserReminderDate!=null){
            calendar.setTime(mUserReminderDate);
        }
        if(DateFormat.is24HourFormat(this)){
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        }
        else{
            hour = calendar.get(Calendar.HOUR);
        }
        minute = calendar.get(Calendar.MINUTE);
        calendar.set(year, month, day, hour, minute);
        mUserReminderDate = calendar.getTime();
        setReminderTextView();
        setDateEditText();
    }

    public void setTime(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        if(mUserReminderDate!=null){
            calendar.setTime(mUserReminderDate);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minute, 0);
        mUserReminderDate = calendar.getTime();
        setReminderTextView();
        setTimeEditText();
    }

    public void  setDateEditText(){
        String dateFormat = "d MMM, yyyy";
        mDateEditText.setText(formatDate(dateFormat, mUserReminderDate));
    }

    public void  setTimeEditText(){
        String dateFormat;
        if(DateFormat.is24HourFormat(this)){
            dateFormat = "k:mm";
        }
        else{
            dateFormat = "h:mm a";

        }
        mTimeEditText.setText(formatDate(dateFormat, mUserReminderDate));
    }

    public void setReminderTextView(){
        if(mUserReminderDate!=null){
            mReminderTextView.setVisibility(View.VISIBLE);
            if(mUserReminderDate.before(new Date())){
                Log.d("OskarSchindler", "DATE is "+mUserReminderDate);
                mReminderTextView.setText(getString(R.string.date_error_check_again));
                mReminderTextView.setTextColor(Color.RED);
                return;
            }
            Date date = mUserReminderDate;
            String dateString = formatDate("d MMM, yyyy", date);
            String timeString;
            String amPmString = "";



            if(DateFormat.is24HourFormat(this)){
                timeString = formatDate("k:mm", date);
            }
            else{
                timeString = formatDate("h:mm", date);
                amPmString = formatDate("a", date);
            }
            String finalString;
            if(!mUserToDoItem.isDaily()){
                finalString = String.format(getResources().getString(R.string.remind_date_and_time), dateString, timeString, amPmString);
            }
            else
            {
                finalString = String.format(getResources().getString(R.string.remind_date_and_time_for_daily), dateString, timeString, amPmString);
                mUserToDoItem.setMassageForDailySchedule("প্রতিদিন "+timeString+" "+amPmString+" টায়");

            }


            //When is is Daily then it is will be fill with the bangla text
            if(mUserToDoItem.isDaily())
            {
                mDateEditText.setText("প্রতিদিন");
            }
            else mDateEditText.setText(dateString);


            mReminderTextView.setTextColor(getResources().getColor(R.color.secondary_text));
            mReminderTextView.setText(finalString);
            mTimeEditText.setText(timeString+" "+amPmString);



            if(switchCompatForDailyRemind.isChecked()){
                mDateEditText.setText("প্রতিদিন");
            }
            else
            mDateEditText.setText(dateString);

        }
        else{
            mReminderTextView.setVisibility(View.INVISIBLE);
        }
    }

    public void makeResult(int result) {
        Intent intent = new Intent();

        if(mUserEnteredText.length()>0){
            String capitalizedString = Character.toUpperCase(mUserEnteredText.charAt(0))+mUserEnteredText.substring(1);
            mUserToDoItem.setToDoText(capitalizedString);
        }
        else{
            mUserToDoItem.setToDoText(mUserEnteredText);
        }


        mUserToDoItem.setToDoContent(mTodoContent);
        mUserToDoItem.setDaily(switchCompatForDailyRemind.isChecked());
        //If remainder is not check then there is no scope to do it as an daily remainder

        if(!mToDoDateSwitch.isChecked())mUserToDoItem.setDaily(false);
        mUserToDoItem.setHasReminder(mToDoDateSwitch.isChecked());


        if(mUserReminderDate!=null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mUserReminderDate);
            calendar.set(Calendar.SECOND, 0);
            mUserReminderDate = calendar.getTime();
        }


        mUserToDoItem.setHasReminder(mUserHasReminder);
        mUserToDoItem.setToDoDate(mUserReminderDate);
        mUserToDoItem.setTodoColor(mUserColor);


        //TODO dummy
        setReminderTextView();



        //WE HAVE A BUG AT FIRST SO WE DO THAT
        storeRetrieveData = new StoreRetrieveData(this, FILENAME);

        if(IsNewToDo){
            Log.d("GK","IsNewToDo YES");


            storeRetrieveData.saveToFile(mUserToDoItem);
            mToDoItemsArrayList.add(mUserToDoItem);
            MainActivity.toDoItemsFromMainActivity=mToDoItemsArrayList;
            storeRetrieveData.saveToFile(mToDoItemsArrayList);
            mToDoItemsArrayList=getLocallyStoredData(storeRetrieveData);
            MainActivity.toDoItemsFromMainActivity=mToDoItemsArrayList;


        }
        else {
            Log.d("GK","IsNewToDo NO");

            //Re collecting to avoid error
            mToDoItemsArrayList=getLocallyStoredData(storeRetrieveData);
            mToDoItemsArrayList = loadFromFile();
            Log.e("GK","Array size :"+mToDoItemsArrayList.size());
            MainActivity.toDoItemsFromMainActivity=mToDoItemsArrayList;

            //This is for save the to do items to the server
            for(int i = 0; i<mToDoItemsArrayList.size();i++){
                ToDoItem item =mUserToDoItem;
                if((item.getToDoText()+item.getToDoContent()).equals(mToDoItemsArrayList.get(i).getToDoText()+mToDoItemsArrayList.get(i).getToDoContent())){
                    mToDoItemsArrayList.set(i, item);
                    MainActivity.toDoItemsFromMainActivity=mToDoItemsArrayList;
                    storeRetrieveData.saveToFile(mToDoItemsArrayList);
                    break;
                }
            }

            mToDoItemsArrayList=getLocallyStoredData(storeRetrieveData);
            MainActivity.toDoItemsFromMainActivity=mToDoItemsArrayList;
        }

        intent.putExtra(scheduleActivity.TODOITEM, mUserToDoItem);
        intent.putExtra(PREVIOUS_ITEM,mPreviousItem);
        setResult(result, intent);

    }

    @Override
    public void onBackPressed() {
        if(mUserReminderDate!=null) {
            if (mUserReminderDate.before(new Date())) {
                mUserToDoItem.setToDoDate(null);
            }
        }


        //IF we get any duplicate element then we can ignore it.
        Boolean isDuplicated= CheckDuplicateForOnBackPressed(true);
        if(!isDuplicated)
        makeResult(RESULT_OK);
        else makeResult(RESULT_CANCELED);


        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityName(this)!=null){
                    makeResult(RESULT_CANCELED);
                    NavUtils.navigateUpFromSameTask(this);
                }
                hideKeyboard(mToDoTextBodyEditText);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static String formatDate(String formatString, Date dateToFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString, Locale.ENGLISH);
        return simpleDateFormat.format(dateToFormat);
    }


    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minute) {
        setTime(hour, minute);
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        setDate(year, month, day);
    }


    public void setEnterDateLayoutVisible(boolean checked){
        if(checked){
            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
            DailyRemainderLayout.setVisibility(View.VISIBLE);
        }
        else{
            mUserDateSpinnerContainingLinearLayout.setVisibility(View.INVISIBLE);
            DailyRemainderLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ClickListenerForDailyRoutine();
        context=this;
    }

    public void setEnterDateLayoutVisibleWithAnimations(boolean checked){
        if(checked){
            setReminderTextView();
            mUserDateSpinnerContainingLinearLayout.animate().alpha(1.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mUserDateSpinnerContainingLinearLayout.setVisibility(View.VISIBLE);
                            DailyRemainderLayout.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {}
                        @Override
                        public void onAnimationCancel(Animator animation) {}
                        @Override
                        public void onAnimationRepeat(Animator animation) {}
                    }
            );
            DailyRemainderLayout.animate().alpha(1.0f).setDuration(500);
        }
        else{
            mUserDateSpinnerContainingLinearLayout.animate().alpha(0.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mUserDateSpinnerContainingLinearLayout.setVisibility(View.INVISIBLE);
                            DailyRemainderLayout.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }
            );

            DailyRemainderLayout.animate().alpha(1.0f).setDuration(500);
        }

    }

    public void ClickListenerForDailyRoutine(){


        switchCompatForDailyRemind.setChecked(mUserToDoItem!=null&&mUserToDoItem.hasReminder()&&mUserToDoItem.isDaily());
        switchCompatForDailyRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(mUserToDoItem!=null)

                    if(isChecked)
                mUserToDoItem.setDaily(true);
                else mUserToDoItem.setDaily(false);

                if(mToDoDateSwitch.isChecked())
                mUserToDoItem.setHasReminder(true);
                else mUserToDoItem.setHasReminder(false);
                setReminderTextView();

                if(mUserToDoItem.isDaily())Log.d("GK","DAILY ROUTINE IS SET");
            }
        });

    }
    public void GetToDoItems(){

        mToDoItemsArrayList=scheduleActivity.mToDoItemsArrayList;
        MainActivity.toDoItemsFromMainActivity=mToDoItemsArrayList;

        if(mToDoItemsArrayList==null){
            Log.d("GK","Total todo size : "+MainActivity.toDoItemsFromMainActivity.size());
        }
        else Log.d("GK","mToDoItemsArrayList not null in click. size is "+MainActivity.toDoItemsFromMainActivity.size());



    }
    public Boolean CheckDuplicateForOnBackPressed(Boolean isInBackPressed){


        String tempForTitle,tempForContent;
        if(mUserEnteredText.length()>0){
            tempForTitle = Character.toUpperCase(mUserEnteredText.charAt(0))+mUserEnteredText.substring(1);
        }
        else{
            tempForTitle=mUserEnteredText;
        }
        tempForContent = mTodoContent;

        Log.d("GK","TODO "+tempForTitle+tempForContent);



        //CHECK THAT THE ITEM IS UNIQUE
        for (int i = 0; i <MainActivity.toDoItemsFromMainActivity.size(); i++) {
            Log.d("GK","TODO NEXT"+MainActivity.toDoItemsFromMainActivity.get(i).getToDoText()+MainActivity.toDoItemsFromMainActivity.get(i).getToDoContent()+" "+IsNewToDo);
            if ((MainActivity.toDoItemsFromMainActivity.get(i).getToDoText()+MainActivity.toDoItemsFromMainActivity.get(i).getToDoContent()).equals(tempForTitle+tempForContent)&&IsNewToDo) {

                //when it is called from onbackpressed then don't need to show alertdialog
                if(isInBackPressed) return true;
                
                Log.d("GK","GET MILL");
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("সতর্কীকরণ");
                alertDialog.setIcon(R.drawable.warning_for_add);
                alertDialog.setMessage("এই একই শিডিউল ইতিমধ্যে এই ডাটাবেজে রয়েছে।নতুন শিডিউল ইনপুট দিন ,ধন্যবাদ।");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                makeResult(RESULT_CANCELED);
                                finish();
                            }
                        });
                alertDialog.show();
                //return true;
            }
        }

        return false;

    }

    //This is for fab button to check duplicate elements
    public Boolean CheckDuplicateForFAB(){


        String tempForTitle,tempForContent;
        if(mUserEnteredText.length()>0){
            tempForTitle = Character.toUpperCase(mUserEnteredText.charAt(0))+mUserEnteredText.substring(1);
        }
        else{
            tempForTitle=mUserEnteredText;
        }
        tempForContent = mTodoContent;


        Log.d("GK","TODO "+tempForTitle+tempForContent);



        //CHECK THAT THE ITEM IS UNIQUE
        for (int i = 0; i <MainActivity.toDoItemsFromMainActivity.size(); i++) {
            Log.d("GK","TODO NEXT"+MainActivity.toDoItemsFromMainActivity.get(i).getToDoText()+MainActivity.toDoItemsFromMainActivity.get(i).getToDoContent()+" "+IsNewToDo);
            if ((MainActivity.toDoItemsFromMainActivity.get(i).getToDoText()+MainActivity.toDoItemsFromMainActivity.get(i).getToDoContent()).equals(tempForTitle+tempForContent)&&IsNewToDo) {

                //when it is called from onbackpressed then don't need to show alertdialog

                Log.d("GK","GET MILL");
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("সতর্কীকরণ");
                alertDialog.setIcon(R.drawable.warning_for_add);
                alertDialog.setMessage("এই একই শিডিউল ইতিমধ্যে এই ডাটাবেজে রয়েছে।নতুন শিডিউল ইনপুট দিন ,ধন্যবাদ।");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
//                                makeResult(RESULT_CANCELED);
//                                finish();

                            }
                        });
                alertDialog.show();

                return true;
            }
        }
return false;
    }
}

