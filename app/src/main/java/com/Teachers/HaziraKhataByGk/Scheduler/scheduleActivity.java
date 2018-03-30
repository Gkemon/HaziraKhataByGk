package com.Teachers.HaziraKhataByGk.Scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by uy on 9/9/2017.
 */

public class scheduleActivity extends AppCompatActivity {

    private RecyclerViewEmptySupport mRecyclerView;
    private FloatingActionButton mAddToDoItemFAB;
    public static ArrayList<ToDoItem> mToDoItemsArrayList;
    private CoordinatorLayout mCoordLayout;
    public static final String TODOITEM = "com.Teachers.HaziraKhataByGk.Scheduler.scheduleActivity";
    private BasicListAdapter adapter;
    private static final int REQUEST_ID_TODO_ITEM = 100;
    private ToDoItem mJustDeletedToDoItem,mResultTodo=new ToDoItem(),mPreviousItem = new ToDoItem();
    private int mIndexOfDeletedToDoItem;
    public static final String DATE_TIME_FORMAT_12_HOUR = "MMM d, yyyy  h:mm a";
    public static final String DATE_TIME_FORMAT_24_HOUR = "MMM d, yyyy  k:mm";
    public static final String ITEMS_PREVIOUS_POS="pos";
    public static final String HAVE_ITEMS_DAILY_REMAINDER="daily_remainder";
    public static final String HAVE_ITEMS_NORMAL_REMAINDER="normal_remainder";
    public static final String PREVIOUS_ITEM="PREVIOUS_ITEM";
    public static final String FILENAME = "todoitems.json";
    private StoreRetrieveData storeRetrieveData;
    public ItemTouchHelper itemTouchHelper;
    private CustomRecyclerScrollViewListener customRecyclerScrollViewListener;
    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.Teachers.HaziraKhataByGk.Scheduler.datasetchanged";
    public static final String CHANGE_OCCURED = "com.Teachers.HaziraKhataByGk.Scheduler.changeoccured";
    private int mTheme = -1;
    private String theme = "name_of_the_theme";
    public static final String THEME_PREFERENCES = "com.Teachers.HaziraKhataByGk.Scheduler.themepref";
    public static final String RECREATE_ACTIVITY = "com.Teachers.HaziraKhataByGk.Scheduler.recreateactivity";
    public static final String THEME_SAVED = "com.Teachers.HaziraKhataByGk.Schedulerrktheme";
    public static final String LIGHTTHEME = "com.Teachers.HaziraKhataByGk.Scheduler.lighttheme";
    public static final String DARKTHEME = "com.Teachers.HaziraKhataByGk.Scheduler.darktheme";
  //  private AnalyticsApplication app;
    public LinearLayout adlayout;
    public AdView mAdView;
    public Context context;
    public SharedPreferences prefForSchedule;
    public static HashMap<String,Integer> HashForDailyScheduler  = new HashMap<>();
    public static HashMap<String,Integer> HashForNormalScheduler  = new HashMap<>();
    public static int EditedToDoPossition;

    @Override
    protected void onResume() {
        super.onResume();

        Refreshing();

        if(mResultTodo!=null){
            ResultCreate(mResultTodo);
            mResultTodo=null;
           // Log.d("GK","mResultTodo!=null");
        }
        else {
         //   Log.d("GK","mResultTodo==null");
        }

    }

    @Override
    protected void onStart() {

        //ADMOB
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                // Check the LogCat to get your test device ID
//                .addTestDevice("26CA880D6BB164E39D8DF26A04B579B6")
//                .build();
//        adlayout=findViewById(R.id.ads);
//        mAdView = (AdView) findViewById(R.id.adViewInHome);
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                adlayout.setVisibility(View.GONE);
//                // Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onAdLeftApplication() {
//                // Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//            }
//        });
//        mAdView.loadAd(adRequest);
//
//
     //   Log.d("GK","onStart");
        Refreshing();

        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHANGE_OCCURED, false);
        editor.apply();
    }

    protected void onCreate(Bundle savedInstanceState) {



        theme = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getString(THEME_SAVED, LIGHTTHEME);
        if(theme.equals(LIGHTTHEME)){
            mTheme = R.style.CustomStyle_LightTheme;
        }
        else{
            mTheme = R.style.CustomStyle_DarkTheme;
        }
        this.setTheme(mTheme);


        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);



        mToDoItemsArrayList=MainActivity.toDoItemsFromMainActivity;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHANGE_OCCURED, false);
        editor.apply();

        storeRetrieveData = new StoreRetrieveData(this, FILENAME);
        mToDoItemsArrayList= getLocallyStoredData(storeRetrieveData);

//        //FOR SCHEDULES
//        MainActivity.toDoItemsFromMainActivity =new ArrayList<>();
//        storeRetrieveData = new StoreRetrieveData(this, scheduleActivity.FILENAME);
//        MainActivity.toDoItemsFromMainActivity= StoreRetrieveData.loadFromFile();

        setAlarms();
        mCoordLayout = (CoordinatorLayout)findViewById(R.id.myCoordinatorLayout);
        mAddToDoItemFAB = (FloatingActionButton)findViewById(R.id.addToDoItemFAB);

        if(mAddToDoItemFAB!=null)
            mAddToDoItemFAB.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onClick(View v) {
                    // app.send(this, "Action", "FAB pressed");
                    Intent newTodo = new Intent(scheduleActivity.this, AddToDoActivity.class);
                    ToDoItem item = new ToDoItem("", false, null,"");

                    int color = ColorGenerator.MATERIAL.getRandomColor();
                    item.setTodoColor(color);
                    newTodo.putExtra(TODOITEM, item);
                    newTodo.putExtra("TODOLIST",mToDoItemsArrayList);
                    startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM);
                }
            });
        mRecyclerView = (RecyclerViewEmptySupport)findViewById(R.id.toDoRecyclerView);

        if (theme.equals(LIGHTTHEME)) {
            mRecyclerView.setBackgroundColor(getResources().getColor(R.color.primary_lightest));
        }
        mRecyclerView.setEmptyView(findViewById(R.id.toDoEmptyView));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        customRecyclerScrollViewListener = new CustomRecyclerScrollViewListener() {
            @Override
            public void show() {

                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }

            @Override
            public void hide() {

                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)mAddToDoItemFAB.getLayoutParams();
                int fabMargin = lp.bottomMargin;
                mAddToDoItemFAB.animate().translationY(mAddToDoItemFAB.getHeight()+fabMargin).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }
        };


        if(mRecyclerView!=null)
            mRecyclerView.addOnScrollListener(customRecyclerScrollViewListener);
        adapter = new BasicListAdapter(mToDoItemsArrayList);
        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        //  Log.d("GK","onCreate");
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= RESULT_CANCELED && requestCode == REQUEST_ID_TODO_ITEM){
            mResultTodo =(ToDoItem) data.getSerializableExtra(TODOITEM);
            mPreviousItem =(ToDoItem)data.getSerializableExtra(PREVIOUS_ITEM);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeRetrieveData.saveToFile(mToDoItemsArrayList);
    }

    @Override
    protected void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
        super.onDestroy();
        mRecyclerView.removeOnScrollListener(customRecyclerScrollViewListener);
    }



    public static ArrayList<ToDoItem> getLocallyStoredData(StoreRetrieveData storeRetrieveData){


        ArrayList<ToDoItem> items = MainActivity.toDoItemsFromMainActivity;

        if(items == null) {
            items = new ArrayList<>();
            Log.d("eee","From arraylist item == null");
        }
        else Log.d("eee","From arraylist item is not null");
        return items;

    }






    public void CreatingHashMapForDailyScheduler(){


    HashForDailyScheduler.clear();
    HashForNormalScheduler.clear();

    mToDoItemsArrayList=getLocallyStoredData(storeRetrieveData);


    //Hash for daily scheduler
    for(int i=0;i<mToDoItemsArrayList.size();i++){

        ToDoItem Item=mToDoItemsArrayList.get(i);
        String key=Item.getToDoContent()+Item.getToDoText();


        //TODO dummy
        if(EditedToDoPossition==i&&Item.equals(mPreviousItem))

        if(!HashForDailyScheduler.containsValue(i)&&Item.isDaily()){
            Log.d("GK","A daily remainder is added to hashmap");
            HashForDailyScheduler.put(key,i);
        }





        //REMOVE THE NON DAILY REMAINDERS

        if((!Item.isDaily()&&!Item.hasReminder()&&(mPreviousItem.isDaily()&&mPreviousItem.hasReminder()))&&
                (Item.isDaily()&&Item.hasReminder()&&(!mPreviousItem.isDaily()&&!mPreviousItem.hasReminder()))   ){

            Intent in = new Intent(scheduleActivity.this,TodoNotificationService.class);
            deleteDaily(in,HashForDailyScheduler.get(key));
            HashForDailyScheduler.remove(key);
            Log.d("GK","REMOVE THE NON DAILY REMAINDERS");

        }


    }

    //Hash for normal scheduler
    for(int i=0;i<mToDoItemsArrayList.size();i++){

        ToDoItem Item=mToDoItemsArrayList.get(i);
        String key=Item.getToDoContent()+Item.getToDoText();


        if(!HashForNormalScheduler.containsValue(i)&&(!Item.isDaily()&&Item.hasReminder())){
            HashForNormalScheduler.put(Item.getToDoContent()+Item.getToDoText(),i);
            Log.d("GK","A normal remainder is added to hashmap");
        }



        //REMOVE THE NON PREVIOUS REMAINDERS BECAUSE OF CHANGING FROM ADD ACTIVITY

        if((!Item.hasReminder()&&Item.isDaily())&&(mPreviousItem.isDaily()&&!mPreviousItem.hasReminder())){

            Intent in = new Intent(scheduleActivity.this,TodoNotificationService.class);
            deleteAlarm(in,HashForNormalScheduler.get(key));
            HashForNormalScheduler.remove(key);
            Log.d("GK","REMOVE THE NON PREVIOUS REMAINDERS BECAUSE OF CHANGING FROM ADD ACTIVITY");
        }

    }

    for(int i=0;i<mToDoItemsArrayList.size();i++){
        Log.d("GK","Hash element for daily scheduler "+HashForDailyScheduler.get(mToDoItemsArrayList.get(i).getToDoContent()+mToDoItemsArrayList.get(i).getToDoText()));
    }

    for(int i=0;i<mToDoItemsArrayList.size();i++){
        Log.d("GK","Hash element for Normal scheduler "+HashForNormalScheduler.get(mToDoItemsArrayList.get(i).getToDoContent()+mToDoItemsArrayList.get(i).getToDoText()));
    }

}


    private void setAlarms(){
        if(mToDoItemsArrayList!=null){
            for(ToDoItem item : mToDoItemsArrayList){
                if(item.hasReminder() && item.getToDoDate()!=null){
                    if(item.getToDoDate().before(new Date())){
                        item.setToDoDate(null);
                        continue;
                    }

                    Log.d("GK","SET ALARMS");
                    Intent i = new Intent(this, TodoNotificationService.class);

                    i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());

                    //TODO dummy
                    if(item.isDaily())
                    {
                        i.putExtra(TodoNotificationService.IsDailyOrNot,String.valueOf(item.isDaily()) );
                        Log.d("GK","item.isDaily()");
                    }
                    else
                    {

                        Log.d("GK","not item.isDaily()");
                        i.putExtra(TodoNotificationService.IsDailyOrNot,String.valueOf(!item.isDaily()) );

                    }


                    //Is reapeated?
                    if(item.isDaily())
                    {
                        if(HashForDailyScheduler.containsKey(item.getToDoText()+item.getToDoContent())&&HashForDailyScheduler.get(item.getToDoText()+item.getToDoContent())!=null)
                        {
                            i.putExtra(TodoNotificationService.TODOUUID, HashForDailyScheduler.get(item.getToDoText()+item.getToDoContent()));
                            createAlarm(i,HashForDailyScheduler.get(item.getToDoText()+item.getToDoContent()), item.getToDoDate().getTime(),true);
                        }

                    }
                    else
                    {
                        if(HashForNormalScheduler.containsKey(item.getToDoText()+item.getToDoContent())&&HashForNormalScheduler.get(item.getToDoText()+item.getToDoContent())!=null){

                            i.putExtra(TodoNotificationService.TODOUUID, HashForNormalScheduler.get(item.getToDoText()+item.getToDoContent()));
                            createAlarm(i,HashForNormalScheduler.get(item.getToDoText()+item.getToDoContent()), item.getToDoDate().getTime(),false);



                        }

                    }

                }
            }
        }
    }


    public AlarmManager getAlarmManager(){
        return (AlarmManager)getSystemService(ALARM_SERVICE);
    }

    private boolean doesPendingIntentExist(Intent i, int requestCode){
        PendingIntent pi = PendingIntent.getService(this,requestCode, i, PendingIntent.FLAG_NO_CREATE);
        return pi!=null;
    }


    private void createAlarm(Intent i, int requestCode, long timeInMillis,boolean willRepeate){

        AlarmManager am = getAlarmManager();
        PendingIntent pi = PendingIntent.getService(this,requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);

        context=this;
        prefForSchedule = context.getSharedPreferences("IsDaily", 0); // 0 - for private mode
        SharedPreferences.Editor editor = prefForSchedule.edit();


        if(prefForSchedule.getBoolean(String.valueOf(requestCode),true)){

        if(!willRepeate)
        {

            am.set(AlarmManager.RTC_WAKEUP, timeInMillis, pi);
            Log.d("GK","NOT SCHEDULED ALARM IS FIXED");

        }
        else {

            Log.d("GK","SCHEDULED ALARM IS FIXED AS A DAILY IN CREATE METHODE");
            am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, 10000, pi);

        }
            editor.putBoolean(String.valueOf(requestCode),false);
            editor.commit();
            editor.apply();

            Log.d("GK", "createAlarm "+requestCode+" time: "+timeInMillis+" PI "+pi.toString());

        }
        else {
            Log.d("GK", " SCHEDULED ALARM IS NOT FIXED BECAUSE THEY ARE ALREADY EXIST");
        }



    }

    private boolean doesDailyPendingIntentExist(Intent i, int requestCode){
        PendingIntent pi = PendingIntent.getService(this,requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean b =pi!=null;
        Log.d("GK","DOES DAILY EXIST "+b);
        return pi!=null;
    }
    private void  deleteDaily(Intent i, int requestCode){

        if(doesDailyPendingIntentExist(i,requestCode)){
            PendingIntent pi = PendingIntent.getService(this, requestCode,i, PendingIntent.FLAG_UPDATE_CURRENT);
            pi.cancel();
            getAlarmManager().cancel(pi);


            Log.d("GK", "DAILY PI Cancelled + request code "+requestCode + doesDailyPendingIntentExist(i, requestCode)+" "+prefForSchedule.getBoolean(String.valueOf(requestCode),false));

            prefForSchedule = context.getSharedPreferences("IsDaily", 0); // 0 - for private mode
            SharedPreferences.Editor editor = prefForSchedule.edit();

            editor.remove(String.valueOf(requestCode));
            editor.commit();
            editor.apply();
        }


    }
    private void deleteAlarm(Intent i, int requestCode){
        if(doesPendingIntentExist(i, requestCode)){

            Log.d("GK","IF  DELETE");
            PendingIntent pi = PendingIntent.getService(this, requestCode,i, PendingIntent.FLAG_NO_CREATE);
            pi.cancel();
            getAlarmManager().cancel(pi);

            //To remove the request code from sharedPreference
            prefForSchedule = context.getSharedPreferences("IsDaily", 0); // 0 - for private mode
            SharedPreferences.Editor editor = prefForSchedule.edit();

            editor.remove(String.valueOf(requestCode));
            editor.commit();
            editor.apply();



            Log.d("GK", "PI Cancelled + request code "+requestCode + doesPendingIntentExist(i, requestCode)+" "+prefForSchedule.getBoolean(String.valueOf(requestCode),false));
       }
       else {
            Log.d("GK","else DELETE");
        }

    }

    private void addToDataStore(ToDoItem item) {
        mToDoItemsArrayList.add(item);
        adapter.notifyItemInserted(mToDoItemsArrayList.size() - 1);
        MainActivity.toDoItemsFromMainActivity=mToDoItemsArrayList;
        storeRetrieveData.saveToFile(mToDoItemsArrayList);
    }


    public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter{
        private ArrayList<ToDoItem> items;
        @Override
        public void onItemMoved(int fromPosition, int toPosition) {
            Log.d("eee","from BasicListAdapter onItemMoved");
            if(fromPosition<toPosition){
                for(int i=fromPosition; i<toPosition; i++){
                    Collections.swap(items, i, i+1);
                }
            }
            else{
                for(int i=fromPosition; i > toPosition; i--){
                    Collections.swap(items, i, i-1);
                }
            }
            mToDoItemsArrayList=MainActivity.toDoItemsFromMainActivity=items;
            adapter = new BasicListAdapter(mToDoItemsArrayList);
            mRecyclerView.setAdapter(adapter);
            storeRetrieveData.saveToFile(mToDoItemsArrayList);
        }

        @Override
        public void onItemRemoved(final int position) {
            mJustDeletedToDoItem =  items.remove(position);
            Log.d("eee","from BasicListAdapter onItem Reoved ,item is "+mJustDeletedToDoItem.getToDoText());
            mIndexOfDeletedToDoItem = position;

            Intent i = new Intent(scheduleActivity.this,TodoNotificationService.class);

//IF it is daily

            if(mJustDeletedToDoItem.isDaily()){

                if(HashForDailyScheduler.containsKey(mJustDeletedToDoItem.getToDoText()+mJustDeletedToDoItem.getToDoContent())){
                    deleteDaily(i,HashForDailyScheduler.get(mJustDeletedToDoItem.getToDoText()+mJustDeletedToDoItem.getToDoContent()));
                    HashForDailyScheduler.remove(mJustDeletedToDoItem.getToDoText()+mJustDeletedToDoItem.getToDoContent());
                }

            }
            else
            {

                if(HashForNormalScheduler.containsKey(mJustDeletedToDoItem.getToDoText()+mJustDeletedToDoItem.getToDoContent()))
                {
                    deleteAlarm(i,HashForNormalScheduler.get(mJustDeletedToDoItem.getToDoText()+mJustDeletedToDoItem.getToDoContent()));
                    HashForNormalScheduler.remove(mJustDeletedToDoItem.getToDoText()+mJustDeletedToDoItem.getToDoContent());
                }

            }


            mToDoItemsArrayList=MainActivity.toDoItemsFromMainActivity=items;
            adapter = new BasicListAdapter(mToDoItemsArrayList);
            mRecyclerView.setAdapter(adapter);
            storeRetrieveData.saveToFile(mToDoItemsArrayList);
            String toShow = "Todo";
            Snackbar.make(mCoordLayout, "Deleted "+toShow,Snackbar.LENGTH_SHORT)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            items.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);
                            if(mJustDeletedToDoItem.getToDoDate()!=null && mJustDeletedToDoItem.hasReminder()){
                                Intent i = new Intent(scheduleActivity.this, TodoNotificationService.class);
                                i.putExtra(TodoNotificationService.TODOTEXT, mJustDeletedToDoItem.getToDoText());
                                i.putExtra(TodoNotificationService.TODOUUID,position);

                                //TODO dummy
                                if(mJustDeletedToDoItem.isDaily())
                                {
                                    i.putExtra(TodoNotificationService.IsDailyOrNot,String.valueOf(mJustDeletedToDoItem.isDaily()) );
                                    Log.d("GK","item.isDaily()");
                                }
                                else
                                {
                                    Log.d("GK","not item.isDaily()");
                                    i.putExtra(TodoNotificationService.IsDailyOrNot,String.valueOf(!mJustDeletedToDoItem.isDaily()) );
                                }


                                //Is reapeated?


                                if(mJustDeletedToDoItem.isDaily())
                                {
                                    if(HashForDailyScheduler.containsKey(mJustDeletedToDoItem.getToDoText()+mJustDeletedToDoItem.getToDoContent()))
                                        createAlarm(i,HashForDailyScheduler.get(mJustDeletedToDoItem.getToDoText()+mJustDeletedToDoItem.getToDoContent()), mJustDeletedToDoItem.getToDoDate().getTime(),true);
                                }
                                else
                                    createAlarm(i,HashForNormalScheduler.get(mJustDeletedToDoItem.getToDoText()+mJustDeletedToDoItem.getToDoContent()),mJustDeletedToDoItem.getToDoDate().getTime(),false);


                            }
                            mToDoItemsArrayList=MainActivity.toDoItemsFromMainActivity=items;
                            adapter = new BasicListAdapter(mToDoItemsArrayList);
                            mRecyclerView.setAdapter(adapter);
                            storeRetrieveData.saveToFile(mToDoItemsArrayList);
                        }
                    }).show();
        }

        @Override
        public BasicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_circle_try, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final BasicListAdapter.ViewHolder holder, final int position) {
            ToDoItem item = items.get(position);

            if(item.getToDoDate()!=null && item.getToDoDate().before(new Date())){
                item.setToDoDate(null);
            }


            SharedPreferences sharedPreferences = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE);
            //Background color for each to-do item. Necessary for night/day mode
            int bgColor;
            //color of title text in our to-do item. White for night mode, dark gray for day mode
            int todoTextColor;
            if(sharedPreferences.getString(THEME_SAVED, LIGHTTHEME).equals(LIGHTTHEME)){
                bgColor = Color.WHITE;
                todoTextColor = getResources().getColor(R.color.secondary_text);
            }
            else{
                bgColor = Color.DKGRAY;
                todoTextColor = Color.WHITE;
            }

            if(item.getToDoDate()!=null){

                //Set Daily msg
                if(item.getMassageForDailySchedule()!=null&&item.isDaily()){
                    holder.mTimeTextView.setText(item.getMassageForDailySchedule());
                }
                else if(item.hasReminder()){
                    String timeToShow;
                    if(android.text.format.DateFormat.is24HourFormat(scheduleActivity.this)){
                        timeToShow = AddToDoActivity.formatDate(scheduleActivity.DATE_TIME_FORMAT_24_HOUR, item.getToDoDate());
                    }
                    else{
                        timeToShow = AddToDoActivity.formatDate(scheduleActivity.DATE_TIME_FORMAT_12_HOUR, item.getToDoDate());
                    }

                    holder.mTimeTextView.setText(timeToShow);
                }

            }

            holder.linearLayout.setBackgroundColor(bgColor);

            if((item.hasReminder() && item.getToDoDate()!=null||item.isDaily())){
                holder.mToDoTextview.setMaxLines(1);
                holder.mTimeTextView.setVisibility(View.VISIBLE);
                Log.d("GK","IS DAILY");
               // Log.d("GK","DATE IS "+item.getToDoDate().toString());
//                holder.mToDoTextview.setVisibility(View.GONE);
            }
            else{

                Log.d("GK","IS NOT DAILY");
                holder.mTimeTextView.setVisibility(View.GONE);
                holder.mToDoTextview.setMaxLines(2);
            }
            holder.mToDoTextview.setText(item.getToDoText());
            holder.mToDoTextview.setTextColor(todoTextColor);
            TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                    .textColor(Color.WHITE)
                    .useFont(Typeface.DEFAULT)
                    .toUpperCase()
                    .endConfig()
                    .buildRound(item.getToDoText().substring(0,1),item.getTodoColor());

            holder.mColorImageView.setImageDrawable(myDrawable);

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        BasicListAdapter(ArrayList<ToDoItem> items){
            this.items = items;
        }
        @SuppressWarnings("deprecation")
        public class ViewHolder extends RecyclerView.ViewHolder{

            View mView;
            LinearLayout linearLayout;
            TextView mToDoTextview;
            ImageView mColorImageView;
            TextView mTimeTextView;
//            int color = -1;

            public ViewHolder(View v){
                super(v);
                mView = v;
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToDoItem item = items.get(ViewHolder.this.getAdapterPosition());

                        Intent i = new Intent(scheduleActivity.this, AddToDoActivity.class);
                        i.putExtra(TODOITEM, item);
                        EditedToDoPossition=ViewHolder.this.getAdapterPosition();
//                        i.putExtra(ITEMS_PREVIOUS_POS,ViewHolder.this.getAdapterPosition());
//                        i.putExtra(HAVE_ITEMS_DAILY_REMAINDER,item.hasReminder());
//                        i.putExtra(HAVE_ITEMS_DAILY_REMAINDER,item.isDaily());
//
                        startActivityForResult(i, REQUEST_ID_TODO_ITEM);
                    }
                });
                mToDoTextview = (TextView)v.findViewById(R.id.toDoListItemTextview);
                mTimeTextView = (TextView)v.findViewById(R.id.todoListItemTimeTextView);
                mColorImageView = (ImageView)v.findViewById(R.id.toDoListItemColorImageView);
                linearLayout = (LinearLayout)v.findViewById(R.id.listItemLinearLayout);
            }
        }
    }




    public void Refreshing(){
        context=this;

        Log.d("GK","Refreshing");

        //Empty View and set alarms

        CreatingHashMapForDailyScheduler();


        //TODO: Dummy for seeing HashMap

        if(mToDoItemsArrayList.isEmpty())
            Log.d("GK","LIST IS EMPTY IN RESUME");


        adapter = new BasicListAdapter(mToDoItemsArrayList);
        mRecyclerView.setAdapter(adapter);
        setAlarms();


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, MODE_PRIVATE);
        if(sharedPreferences.getBoolean(ReminderActivity.EXIT, false)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(ReminderActivity.EXIT,false);
            editor.apply();
            finish();
        }

        if(getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).getBoolean(RECREATE_ACTIVITY, false)){
            SharedPreferences.Editor editor = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE).edit();
            editor.putBoolean(RECREATE_ACTIVITY, false);
            editor.apply();
            recreate();
        }
    }


    //Create The To do from add activity
    public void ResultCreate(ToDoItem item){

        Log.d("GK","item return from add activity,so title is "+item.getToDoText()+" and content "+item.getToDoContent()+" Has remainder "+item.hasReminder()+" Is daily "+item.isDaily());
        if(item.getToDoText().length()<=0){
            return;
        }
        boolean existed = false;
        if(item.hasReminder() && item.getToDoDate()!=null){
            Intent i = new Intent(this, TodoNotificationService.class);
            i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());


            if(item.isDaily())
            {

                //Check first that actually we have the element
                if(HashForDailyScheduler.containsKey(item.getToDoText()+item.getToDoContent())&&HashForDailyScheduler.get(item.getToDoText()+item.getToDoContent())!=null)
                {
                    i.putExtra(TodoNotificationService.TODOUUID,HashForDailyScheduler.get(item.getToDoText()+item.getToDoContent()) );
                    createAlarm(i,HashForDailyScheduler.get(item.getToDoText()+item.getToDoContent()), item.getToDoDate().getTime(),true);
                }
                else {
                    Log.d("GK","NOT CREATE DAILY ALARM");
                }

            }
            else{


                if(HashForNormalScheduler.containsKey(item.getToDoText()+item.getToDoContent())&&HashForNormalScheduler.get(item.getToDoText()+item.getToDoContent())!=null)
                {

                    i.putExtra(TodoNotificationService.TODOUUID,HashForNormalScheduler.get(item.getToDoText()+item.getToDoContent()) );

                    createAlarm(i,HashForNormalScheduler.get(item.getToDoText()+item.getToDoContent()), item.getToDoDate().getTime(),false);
                }
                else {
                    Log.d("GK","NOT CREATE NORMAL ALARM");
                }

            }


        }

        //This is for save the to do items to the server
        for(int i = 0; i<mToDoItemsArrayList.size();i++){
            if((item.getToDoText()+item.getToDoContent()).equals(mToDoItemsArrayList.get(i).getToDoText()+mToDoItemsArrayList.get(i).getToDoContent())){

                mToDoItemsArrayList.set(i, item);
                existed = true;
                adapter.notifyDataSetChanged();
                MainActivity.toDoItemsFromMainActivity=mToDoItemsArrayList;
                Log.d("GK","CHANGED and existed:"+existed);
                storeRetrieveData.saveToFile(mToDoItemsArrayList);
                Log.d("eee","Get similarity with Content "+mToDoItemsArrayList.get(i).getToDoContent());
                break;
            }
        }
        if(!existed){
            addToDataStore(item);
        }
        mToDoItemsArrayList=getLocallyStoredData(storeRetrieveData);
        MainActivity.toDoItemsFromMainActivity=mToDoItemsArrayList;
        Log.d("eee","Result");

        adapter = new BasicListAdapter(mToDoItemsArrayList);

        if(adapter.getItemCount()!=0)Log.d("eee","adapers item from result which is not zero");
        mRecyclerView.setAdapter(adapter);
        setAlarms();
    }



}
