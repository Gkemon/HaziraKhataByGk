package com.Teachers.HaziraKhataByGk.Scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.TodoItemServerListenter;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by uy on 9/9/2017.
 */

public class ScheduleActivity extends AppCompatActivity {

    public static final String TODOLIST = "ToDo_list";
    private RecyclerViewEmptySupport mRecyclerView;
    private FloatingActionButton mAddToDoItemFAB;
    public static ArrayList<ToDoItem> mToDoItemsArrayList;
    private CoordinatorLayout mCoordLayout;
    public static final String TODOITEM = "com.Teachers.HaziraKhataByGk.Scheduler.ScheduleActivity";
    private BasicListAdapter adapter;
    private static final int REQUEST_ID_TODO_ITEM = 100;
    private ToDoItem mJustDeletedToDoItem,mResultTodo=new ToDoItem();;
    private int mIndexOfDeletedToDoItem;
    public static final String DATE_TIME_FORMAT_12_HOUR = "MMM d, yyyy  h:mm a";
    public static final String DATE_TIME_FORMAT_24_HOUR = "MMM d, yyyy  k:mm";
    public static final String ITEM_POSITION="POSITION";
    public static final String PREVIOUS_ITEM="PREVIOUS_ITEM";
    public static final String FILENAME = "todoitems.json";

    public ItemTouchHelper itemTouchHelper;
    private CustomRecyclerScrollViewListener customRecyclerScrollViewListener;

    public  ArrayList<ToDoItem> toDoItems;

    public static final String LIGHTTHEME = "com.Teachers.HaziraKhataByGk.Scheduler.lighttheme";
    public static final String DARKTHEME = "com.Teachers.HaziraKhataByGk.Scheduler.darktheme";


    public Context context;
    public static int EditedToDoPosition;

    @Override
    protected void onResume() {
        super.onResume();

        refreshing();

        //If it's subjectName and content are blank so we don't need toTime create the result
        if(mResultTodo!=null&&!mResultTodo.getToDoText().equals("")){
            ResultCreate(mResultTodo);
            mResultTodo=null;
        }

    }


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);


        initView();

        StoreRetrieveData.loadToDoFromServer(new TodoItemServerListenter() {
            @Override
            public void onGetToDoItem(ArrayList<ToDoItem> toDoItems) {
                generateToDoList(toDoItems);
            }

            @Override
            public void onError(String msg) {
                  UtilsCommon.debugLog(msg);
                  UtilsCommon.showToast(msg);
            }
        });



    }

   public void initView(){

        mCoordLayout = (CoordinatorLayout)findViewById(R.id.myCoordinatorLayout);
        mAddToDoItemFAB = (FloatingActionButton)findViewById(R.id.addToDoItemFAB);

        if(mAddToDoItemFAB!=null)
            mAddToDoItemFAB.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onClick(View v) {
                    // app.send(this, "Action", "FAB pressed");
                    Intent newTodo = new Intent(ScheduleActivity.this, AddToDoActivity.class);
                    ToDoItem item = new ToDoItem("", false, null,"");

                    int color = ColorGenerator.MATERIAL.getRandomColor();
                    item.setTodoColor(color);
                    newTodo.putExtra(TODOITEM, item);
                    newTodo.putExtra(TODOLIST,toDoItems);
                    startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM);
                }
            });
    }


    public void generateToDoList(ArrayList<ToDoItem> toDoItems){

        this.toDoItems=toDoItems;

        customRecyclerScrollViewListener = new CustomRecyclerScrollViewListener() {
            @Override
            public void show() {
                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)mAddToDoItemFAB.getLayoutParams();
                int fabMargin = lp.bottomMargin;
                mAddToDoItemFAB.animate().translationY(mAddToDoItemFAB.getHeight()+fabMargin).setInterpolator(new AccelerateInterpolator(2.0f)).start();

            }
        };

        mRecyclerView = (RecyclerViewEmptySupport)findViewById(R.id.toDoRecyclerView);

        if(mRecyclerView!=null)
            mRecyclerView.addOnScrollListener(customRecyclerScrollViewListener);

        if(toDoItems!=null)
            adapter = new BasicListAdapter(toDoItems,mRecyclerView,findViewById(R.id.toDoEmptyView));
        else {

            startActivity(new Intent(this,MainActivity.class));
            UtilsCommon.showToast("Network problem");
            return;
        }

        mRecyclerView.setItemViewCacheSize(toDoItems.size());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= RESULT_CANCELED && requestCode == REQUEST_ID_TODO_ITEM){
            mResultTodo =(ToDoItem) data.getSerializableExtra(TODOITEM);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(toDoItems!=null)
        {
            toDoItems.clear();
            toDoItems.addAll(adapter.getItems());
            StoreRetrieveData.saveToServer(toDoItems);
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if(mRecyclerView!=null)
        mRecyclerView.removeOnScrollListener(customRecyclerScrollViewListener);


    }

    private void setUpAllAlarms(List<ToDoItem> mToDoItemsArrayList) {


        Log.d("GK", "TODO LIST SIZE IN SET ALARM IS " + mToDoItemsArrayList.size());

        if (mToDoItemsArrayList != null) {
            for (int i = 0; i < mToDoItemsArrayList.size(); i++) {

                ToDoItem item = mToDoItemsArrayList.get(i);
                if (item.hasReminder() && item.getToDoDate() != null&&!item.isDaily()) {
                    if (item.getToDoDate().before(new Date())) {
                        item.setToDoDate(null);
                        continue;
                    }

                    Intent in = new Intent(ScheduleActivity.this, TodoNotificationService.class);
                    in.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
                    in.putExtra(TodoNotificationService.TODOUUID,i);
                    in.putExtra(ScheduleActivity.ITEM_POSITION,i);


                    if (item.isDaily()) {
                        in.putExtra(TodoNotificationService.IsDailyOrNot, "true");
                        Log.d("GK", "DAILY ALARM IN SET ALARM");
                        Log.d("GK","item.isDaily() "+String.valueOf(item.isDaily()));

                    } else if(item.hasReminder()){

                        Log.d("GK", "NORMAL ALARM  IN SET ALARM");
                        Log.d("GK","item.isDaily() "+String.valueOf(!item.isDaily()));
                        in.putExtra(TodoNotificationService.IsDailyOrNot,"false");

                    }



                    //First delete all alarm
                    if (item.isDaily()) {
                        deleteDailyAlarm(in, i);
                    } else if(item.hasReminder()) {
                        deleteNormalAlarm(in, i);
                    }


                    //Then Create the Alarm
                    if (item.isDaily()) {
                        createAlarm(in, i, item.getToDoDate().getTime(), true);
                    } else {
                        createAlarm(in, i, item.getToDoDate().getTime(), false);
                    }

                }

            }
        }
    }

    public AlarmManager getAlarmManager(){
        return (AlarmManager)getSystemService(ALARM_SERVICE);
    }


    private boolean doesNormalPendingIntentExist(Intent i, int requestCode){
        PendingIntent pi = PendingIntent.getService(this,requestCode, i, PendingIntent.FLAG_NO_CREATE);
        boolean b =pi!=null;
       // Log.d("GK","A NORMAL REMINDER EXIST "+b+" WHERE REQUEST CODE IS "+requestCode);

        return pi!=null;
    }



    public boolean doesDailyPendingIntentExist(Intent i, int requestCode){

        PendingIntent pi = PendingIntent.getService(this,requestCode, i, PendingIntent.FLAG_NO_CREATE);

        return pi!=null;
    }


    public void createAlarm(Intent i, int requestCode, long timeInMillis,boolean willRepeate){

        context=this;

        if(!willRepeate)
        {
            AlarmManager am = getAlarmManager();
            PendingIntent pi = PendingIntent.getService(context,requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);

            Log.d("GK","A NORMAL ALARM CREATED");
            am.set(AlarmManager.RTC_WAKEUP, timeInMillis, pi);

        }
        else {
            AlarmManager am1 = getAlarmManager();
            PendingIntent pi = PendingIntent.getService(context,requestCode, i,0);


                Log.d("GK", "A DAILY IS ALARM CREATED");
                am1.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, 86400000, pi);

        }
    }

    public void deleteDailyAlarm(Intent i, int requestCode){

        if(doesDailyPendingIntentExist(i,requestCode)){

            PendingIntent pi0 = PendingIntent.getService(this, requestCode,i, PendingIntent.FLAG_NO_CREATE);
            pi0.cancel();
            getAlarmManager().cancel(pi0);

            Log.d("GK", "DAILY ALARM PI Cancelled + request code "+requestCode +" Does Existed:  "+doesDailyPendingIntentExist(i, requestCode));
        }
        else {
            Log.d("GK","CAN'T DELETE DAILY ALARM");
        }



    }
    public void deleteNormalAlarm(Intent i, int requestCode){
        if(doesNormalPendingIntentExist(i, requestCode)){


            PendingIntent pi = PendingIntent.getService(this, requestCode,i, PendingIntent.FLAG_NO_CREATE);
            pi.cancel();
            getAlarmManager().cancel(pi);


            Log.d("GK", "NORMAL ALARM PI Cancelled + request code "+requestCode +"Does Existed: "+doesNormalPendingIntentExist(i, requestCode));


       }
       else {
            Log.d("GK","CAN'T DELETE NORMAL ALARM");
        }

    }




    public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder> implements ItemTouchHelperClass.ItemTouchHelperAdapter{
        private List<ToDoItem> items;

        public List<ToDoItem> getItems() {
            return items;
        }

        @Override
        public void onItemMoved(int fromPosition, int toPosition) {
            UtilsCommon.debugLog("fromTime BasicListAdapter onItemMoved");
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
            adapter.addItemsList(items);
            adapter.notifyDataSetChanged();

            StoreRetrieveData.saveToServer(items);
        }

        @Override
        public void onItemRemoved(final int position) {
            mJustDeletedToDoItem =  items.remove(position);

            mIndexOfDeletedToDoItem = position;

            final Intent i = new Intent(ScheduleActivity.this,TodoNotificationService.class);


            if(mJustDeletedToDoItem.isDaily()) {
                    deleteDailyAlarm(i,position);
            }
            else if(mJustDeletedToDoItem.hasReminder())
            {
                    deleteNormalAlarm(i,position);
            }



            adapter.addItemsList(items);
            adapter.notifyDataSetChanged();
            StoreRetrieveData.saveToServer(items);


            //Visible Invisible empty view
            if(items.size()==0){
                recycler.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
            else {
                recycler.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
            }


            Snackbar.make(mCoordLayout, "ডিলিট হয়েছে",Snackbar.LENGTH_LONG)
                    .setAction("আবার ফিরিয়ে আনুন", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            items.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);
                            if(mJustDeletedToDoItem.getToDoDate()!=null && mJustDeletedToDoItem.hasReminder()){
                                Intent i = new Intent(ScheduleActivity.this, TodoNotificationService.class);
                                i.putExtra(TodoNotificationService.TODOTEXT, mJustDeletedToDoItem.getToDoText());
                                i.putExtra(TodoNotificationService.TODOUUID,position);


                                if(mJustDeletedToDoItem.isDaily())
                                {
                                    i.putExtra(TodoNotificationService.IsDailyOrNot,String.valueOf(mJustDeletedToDoItem.isDaily()) );
                                    Log.d("GK","item.isDaily() "+String.valueOf(mJustDeletedToDoItem.isDaily()));
                                }
                                else
                                {
                                    Log.d("GK","not item.isDaily() "+String.valueOf(!mJustDeletedToDoItem.isDaily()));
                                    i.putExtra(TodoNotificationService.IsDailyOrNot,String.valueOf(false) );
                                }


                                //Is reapeated?


                                if(mJustDeletedToDoItem.isDaily())
                                {
                                    createAlarm(i,position,mJustDeletedToDoItem.getToDoDate().getTime(),true);
                                }
                                else
                                    createAlarm(i,position,mJustDeletedToDoItem.getToDoDate().getTime(),false);


                            }

                            adapter.addItemsList(items);
                            adapter.notifyDataSetChanged();
                            StoreRetrieveData.saveToServer(items);

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


            //todo THERE was a bug which create the date 2015
            if((item.getToDoDate()!=null && item.getToDoDate().before(new Date()))&&!item.isDaily()){
                item.setToDoDate(null);
            }



            if(item.getToDoDate()!=null){

                Log.d("GK",item.isDaily()+" IS DAILY ?");
                Log.d("GK",item.getMassageForDailySchedule()+" HAS MASSEGE ?");
                Log.d("GK",item.hasReminder()+" HAS reminder ?");

                //Set Daily msg
                if(item.getMassageForDailySchedule()!=null&&item.isDaily()&&item.hasReminder()){
                    holder.mTimeTextView.setText(item.getMassageForDailySchedule());
                }
                else if(item.hasReminder()&&(!item.isDaily())){
                    String timeToShow;
                    if(android.text.format.DateFormat.is24HourFormat(ScheduleActivity.this)){
                        timeToShow = AddToDoActivity.formatDate(ScheduleActivity.DATE_TIME_FORMAT_24_HOUR, item.getToDoDate());
                    }
                    else{
                        timeToShow = AddToDoActivity.formatDate(ScheduleActivity.DATE_TIME_FORMAT_12_HOUR, item.getToDoDate());
                    }

                    holder.mTimeTextView.setText(timeToShow);
                }

            }




            if((item.hasReminder() && item.getToDoDate()!=null||item.isDaily())){
                holder.mToDoTextview.setMaxLines(1);
                holder.mTimeTextView.setVisibility(View.VISIBLE);
                Log.d("GK","IS DAILY");
            }
            else{

                Log.d("GK","IS NOT DAILY");
                holder.mTimeTextView.setVisibility(View.GONE);
                holder.mToDoTextview.setMaxLines(2);
            }
            holder.mToDoTextview.setText(item.getToDoText());


            if(!item.getToDoText().equals("")){
                TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.WHITE)
                        .useFont(Typeface.DEFAULT)
                        .toUpperCase()
                        .endConfig()
                        .buildRound(item.getToDoText().substring(0,1),item.getTodoColor());
                holder.mColorImageView.setImageDrawable(myDrawable);
            }


        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        View recycler,empty;
        BasicListAdapter(List<ToDoItem> items,View recycler,View empty){
            this.items = items;
            this.recycler=recycler;
            this.empty=empty;
        }

        public void addItemsList(List<ToDoItem> toDoItems){
            this.items=toDoItems;

            if(items.size()==0){
                recycler.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
            else {
                recycler.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
            }

        }
        @SuppressWarnings("deprecation")
        public class ViewHolder extends RecyclerView.ViewHolder{

            View mView;
            LinearLayout linearLayout;
            TextView mToDoTextview;
            ImageView mColorImageView;
            TextView mTimeTextView;

            public ViewHolder(View v){
                super(v);
                mView = v;
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToDoItem item = items.get(ViewHolder.this.getAdapterPosition());

                        Intent i = new Intent(ScheduleActivity.this, AddToDoActivity.class);
                        i.putExtra(TODOITEM, item);
                        EditedToDoPosition =ViewHolder.this.getAdapterPosition();
                        i.putExtra(ScheduleActivity.ITEM_POSITION,EditedToDoPosition);
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




    public void refreshing(){
        context=this;

        StoreRetrieveData.loadToDoFromServer(new TodoItemServerListenter() {
            @Override
            public void onGetToDoItem(ArrayList<ToDoItem> toDoItems) {


                adapter.addItemsList(toDoItems);
                adapter.notifyDataSetChanged();
                setUpAllAlarms(toDoItems);
            }

            @Override
            public void onError(String msg) {
                UtilsCommon.showToast(msg);
            }
        });

    }


    //Create The To do fromTime add activity
    public void ResultCreate(ToDoItem item){

     Log.d("GK","item return fromTime add activity,so subjectName is "+item.getToDoText()+" and content "+item.getToDoContent()+" Has remainder "+item.hasReminder()+" Is daily "+item.isDaily());
        if(item.getToDoText().length()<=0){
            return;
        }

        if(item.hasReminder() && item.getToDoDate()!=null){
            Intent in = new Intent(this, TodoNotificationService.class);
            in.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
            in.putExtra(ScheduleActivity.ITEM_POSITION, EditedToDoPosition);



            if (item.isDaily()) {
                in.putExtra(TodoNotificationService.IsDailyOrNot,"true");
                Log.d("GK", "DAILY ALARM IN SET ALARM");
                Log.d("GK","item.isDaily() "+String.valueOf(item.isDaily()));
            } else if(item.hasReminder()){

                Log.d("GK", "NORMAL ALARM  IN SET ALARM");
                in.putExtra(TodoNotificationService.IsDailyOrNot, "false");
                Log.d("GK","item.isDaily() "+String.valueOf(false));

            }

            //First delete all alarm
            if (item.isDaily()) {
                deleteDailyAlarm(in, EditedToDoPosition);
            } else {
                deleteNormalAlarm(in, EditedToDoPosition);
            }


            if(item.isDaily())
            {
                    //Check first that actually we have the element
                    in.putExtra(TodoNotificationService.TODOUUID, EditedToDoPosition);
                    createAlarm(in, EditedToDoPosition, item.getToDoDate().getTime(),true);
            }
            else{
                    in.putExtra(TodoNotificationService.TODOUUID, EditedToDoPosition);
                    createAlarm(in, EditedToDoPosition, item.getToDoDate().getTime(),false);
            }

        }

        Log.d("GK","EditedToDoPosition : "+ EditedToDoPosition);


    }



}
