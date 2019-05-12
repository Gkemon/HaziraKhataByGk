package com.Teachers.HaziraKhataByGk;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.Adapter.ModelTestAdapter;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.ModelTestDatabase.ModelTestDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by uy on 11/26/2017.
 */

public class ModelTestActivity extends AppCompatActivity  implements RecyclerItemClickListener {
    TextView textViewForRestTime,textViewForTotalTime;
    public int second,minutes=0,count=0;
    public String secondBangla,minutesbangla;
    public static SparseBooleanArray selectedItems;
    private LinearLayoutManager linearLayoutManager;
    private ModelTestAdapter ModelTestAdapter;
    public RecyclerView question_recycler;
    public Context context;
    String time;
    public LinearLayout adlayout;


    //TODO QUESTION
    public List<String> Gk_question;
    public List<String> Bangla_question;
    public List<String> English_question;
    public List<String> Math_question;
    public List<String> Total_question;

    //TODO: Answer
    public static List<String> Gk_Answer;
    public static List<String> Bangla_Answer;
    public static List<String> English_Answer;
    public static List<String> Math_Answer;
    public static List<String> Total_Answer;

    //TODO: Answer key
    public List<String> Bangla_Answer_key;
    public List<String> English_Answer_key;
    public List<String> Math_Answer_key;
    public List<String> Gk_Answer_key;
    public List<String> Total_Answer_key;

    //TODO: Question and answer
    public static ArrayList<String[]> GK_Q_A;
    public static ArrayList<String[]> EN_Q_A;
    public static ArrayList<String[]> BN_Q_A;
    public static ArrayList<String[]> MATH_Q_A;
    public static ArrayList<String[]> TOTAL_Q_A;

    public static ArrayList<String[]> wrong_Answer_correct_list;
    public static List<String> user_answer;

    public static int correct_answer,wrong_answer;
    static int selected;
    private CountDownTimer countDownTimer;
    private Activity activity;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //  setContentView(R.layout.text_view_with_share_fab_and_header_image);
        setContentView(R.layout.model_test);
        question_recycler=(RecyclerView)findViewById(R.id.recycleForModelTest);
        context=this;
        activity=this;
        selectedItems=new SparseBooleanArray();
        time=getIntent().getStringExtra("time");

        correct_answer=0;
        wrong_answer=0;






        //TODO QUESTION
        Gk_question=new ArrayList<String>();
        Bangla_question=new ArrayList<String>();
        English_question=new ArrayList<String>();
        Math_question=new ArrayList<String>();
        Total_question=new ArrayList<String>();


        //TODO: Answer
         Gk_Answer=new ArrayList<String>();
         Bangla_Answer=new ArrayList<String>();
         English_Answer=new ArrayList<String>();
         Math_Answer=new ArrayList<String>();
         Total_Answer=new ArrayList<String>();

        //TODO: Answer key
        Bangla_Answer_key=new ArrayList<String>();
        English_Answer_key=new ArrayList<String>();
        Math_Answer_key=new ArrayList<String>();
        Gk_Answer_key=new ArrayList<String>();
        Total_Answer_key=new ArrayList<String>();

        //TODO: Question and answer
        GK_Q_A=new ArrayList<String[]>();
        EN_Q_A=new ArrayList<String[]>();
        BN_Q_A=new ArrayList<String[]>();
        MATH_Q_A=new ArrayList<String[]>();
        TOTAL_Q_A=new ArrayList<String[]>();


        user_answer=new ArrayList<String>();
        wrong_Answer_correct_list =new ArrayList<String[]>();


        ModelTestDatabase.onbind();



        if(time.equals("15")) {

            Random rand = new Random();

            for (int i = 0; i < 25; i++) {
                user_answer.add("5");//TODO: By default they are wrong so we put "5" in it.
            }


            for(int i=0;i<6;i++){

                //int value = rand.nextInt(6);

                String[] temp= ModelTestDatabase.General_knowledge.get(i);
                GK_Q_A.add(temp);
                Gk_question.add(temp[0]);
                Gk_Answer.add(temp[1]);
                Gk_Answer_key.add(temp[2]);
            }

            for(int i=0;i<6;i++){
                //int value = rand.nextInt(6);
                String[] temp= ModelTestDatabase.bangla.get(i);
                BN_Q_A.add(temp);
                Bangla_question.add(temp[0]);
                Bangla_Answer.add(temp[1]);
                Bangla_Answer_key.add(temp[2]);
            }

            for(int i=0;i<6;i++){
                //int value = rand.nextInt(6);
                String[] temp= ModelTestDatabase.English.get(i);
                EN_Q_A.add(temp);
                English_question.add(temp[0]);
                English_Answer.add(temp[1]);
                English_Answer_key.add(temp[2]);
            }

            for(int i=0;i<7;i++){
                //int value = rand.nextInt(7);
                String[] temp= ModelTestDatabase.math.get(i);
                MATH_Q_A.add(temp);
                Math_question.add(temp[0]);
                Math_Answer.add(temp[1]);
                Math_Answer_key.add(temp[2]);
            }

            Total_question.addAll(Math_question);
            Total_question.addAll(English_question);
            Total_question.addAll(Bangla_question);
            Total_question.addAll(Gk_question);

            Log.d("GK",Total_question.size()+" Total_question.size");

            Total_Answer_key.addAll(Math_Answer_key);
            Total_Answer_key.addAll(English_Answer_key);
            Total_Answer_key.addAll(Bangla_Answer_key);
            Total_Answer_key.addAll(Gk_Answer_key);

            Log.d("GK",Total_Answer_key.size()+" Total_Answer_key.size");

            TOTAL_Q_A.addAll(MATH_Q_A);
            TOTAL_Q_A.addAll(EN_Q_A);
            TOTAL_Q_A.addAll(BN_Q_A);
            TOTAL_Q_A.addAll(GK_Q_A);
            Log.d("GK",TOTAL_Q_A.size()+" TOTAL_Q_A.size");

        }
        else if(time.equals("30")){

            Random rand = new Random();
            for (int i = 0; i <50; i++) {
                user_answer.add("5");//TODO: By default they are wrong so we put "5" in it.
            }


            for(int i=0;i<14;i++){
              //  int value = rand.nextInt(14);
                String[] temp= ModelTestDatabase.General_knowledge.get(i);
                GK_Q_A.add(temp);
                Gk_question.add(temp[0]);
                Gk_Answer.add(temp[1]);
                Gk_Answer_key.add(temp[2]);
            }

            for(int i=0;i<12;i++){
                //int value = rand.nextInt(12);
                String[] temp= ModelTestDatabase.bangla.get(i);
                BN_Q_A.add(temp);
                Bangla_question.add(temp[0]);
                Bangla_Answer.add(temp[1]);
                Bangla_Answer_key.add(temp[2]);
            }

            for(int i=0;i<12;i++){
                //int value = rand.nextInt(12);
                String[] temp= ModelTestDatabase.English.get(i);
                EN_Q_A.add(temp);
                English_question.add(temp[0]);
                English_Answer.add(temp[1]);
                English_Answer_key.add(temp[2]);
            }

            for(int i=0;i<12;i++){
                //int value = rand.nextInt(12);
                String[] temp= ModelTestDatabase.math.get(i);
                MATH_Q_A.add(temp);
                Math_question.add(temp[0]);
                Math_Answer.add(temp[1]);
                Math_Answer_key.add(temp[2]);
            }



            Total_question.addAll(Math_question);
            Total_question.addAll(English_question);
            Total_question.addAll(Bangla_question);
            Total_question.addAll(Gk_question);

            Log.d("GK",Total_question.size()+" Total_question.size");

            Total_Answer_key.addAll(Math_Answer_key);
            Total_Answer_key.addAll(English_Answer_key);
            Total_Answer_key.addAll(Bangla_Answer_key);
            Total_Answer_key.addAll(Gk_Answer_key);

            Log.d("GK",Total_Answer_key.size()+" Total_Answer_key.size");

            TOTAL_Q_A.addAll(MATH_Q_A);
            TOTAL_Q_A.addAll(EN_Q_A);
            TOTAL_Q_A.addAll(BN_Q_A);
            TOTAL_Q_A.addAll(GK_Q_A);
            Log.d("GK",TOTAL_Q_A.size()+" TOTAL_Q_A.size");

        }else {

            Random rand = new Random();
            for (int i = 0; i <95; i++) {
                user_answer.add("5");//TODO: By default they are wrong so we put "5" in it.
            }

            for(int i=0;i<25;i++){
                //int value = rand.nextInt(25);
                String[] temp= ModelTestDatabase.General_knowledge.get(i);
                GK_Q_A.add(temp);
                Gk_question.add(temp[0]);
                Gk_Answer.add(temp[1]);
                Gk_Answer_key.add(temp[2]);
            }


            for(int i=0;i<25;i++){
                //int value = rand.nextInt(25);
                String[] temp= ModelTestDatabase.bangla.get(i);
                BN_Q_A.add(temp);
                Bangla_question.add(temp[0]);
                Bangla_Answer.add(temp[1]);
                Bangla_Answer_key.add(temp[2]);
            }


            for(int i=0;i<20;i++){
                //int value = rand.nextInt(20);
                String[] temp= ModelTestDatabase.English.get(i);
                EN_Q_A.add(temp);
                English_question.add(temp[0]);
                English_Answer.add(temp[1]);
                English_Answer_key.add(temp[2]);
            }


            for(int i=0;i<25;i++){
                //int value = rand.nextInt(25);
                String[] temp= ModelTestDatabase.math.get(i);
                MATH_Q_A.add(temp);
                Math_question.add(temp[0]);
                Math_Answer.add(temp[1]);
                Math_Answer_key.add(temp[2]);
            }

            Total_question.addAll(Math_question);
            Total_question.addAll(English_question);
            Total_question.addAll(Bangla_question);
            Total_question.addAll(Gk_question);

            Log.d("GK",Total_question.size()+" Total_question.size");

            Total_Answer_key.addAll(Math_Answer_key);
            Total_Answer_key.addAll(English_Answer_key);
            Total_Answer_key.addAll(Bangla_Answer_key);
            Total_Answer_key.addAll(Gk_Answer_key);

            Log.d("GK",Total_Answer_key.size()+" Total_Answer_key.size");

            TOTAL_Q_A.addAll(MATH_Q_A);
            TOTAL_Q_A.addAll(EN_Q_A);
            TOTAL_Q_A.addAll(BN_Q_A);
            TOTAL_Q_A.addAll(GK_Q_A);

            Log.d("GK",TOTAL_Q_A.size()+" TOTAL_Q_A.size");

        }

        textViewForRestTime = (TextView) findViewById(R.id.rest_time);
        textViewForTotalTime=(TextView) findViewById(R.id.total_time);
        submit=(Button)findViewById(R.id.submit_answer);





        linearLayoutManager = new LinearLayoutManager(this);
        ModelTestAdapter =new ModelTestAdapter(this);
        ModelTestAdapter.setOnItemClickListener(this);



        ModelTestAdapter.addAll(Total_question);
        question_recycler.setLayoutManager(linearLayoutManager);//TODO: DON'T FORGET TO SET LAYOUTMANAGER
        question_recycler.setAdapter(ModelTestAdapter);


        if(time.equals("15")){
            textViewForTotalTime.setText("১৫ মিনিট");

            //TODO: MUST CORRECT
          countDownTimer=new CountDownTimer(16*60*1000, 1000){
                public void onTick(long millisUntilFinished){

                    if(count==60){second=0;}
                    else second=count;

                    if(count%60==0&&count>=60){
                        ++minutes;
                        count=0;
                    }
                    //TODO: FOR converting second english to bangla
//                    if(second==1){
//                        secondBangla="১";
//                    }
//                    else if(second==2){
//                        secondBangla="২";
//                    }
//                    else if(second==3){
//                        secondBangla="৩";
//                    }
//                    else if(second==4){
//                        secondBangla="৪";
//                    }
//                    else if(second==5){
//                        secondBangla="৫";
//                    }
//                    else if(second==6){
//                        secondBangla="৬";
//                    }
//                    else if(second==7){
//                        secondBangla="৭";
//                    }
//                    else if(second==8){
//                        secondBangla="৮";
//                    }
//                    else if(second==9){
//                        secondBangla="৯";
//                    }
//                    else if(second==0){
//                        secondBangla="০";
//                    }

                    //TODO: FOR converting minutes english to bangla


//                    if(minutes==1){
//                        minutesbangla="১";
//                    }
//                    else if(minutes==2){
//                        minutesbangla="২";
//                    }
//                    else if(minutes==3){
//                        minutesbangla="৩";
//                    }
//                    else if(minutes==4){
//                        minutesbangla="৪";
//                    }
//                    else if(minutes==5){
//                        minutesbangla="৫";
//                    }
//                    else if(minutes==6){
//                        minutesbangla="৬";
//                    }
//                    else if(minutes==7){
//                        minutesbangla="৭";
//                    }
//                    else if(minutes==8){
//                        minutesbangla="৮";
//                    }
//                    else if(minutes==9){
//                        minutesbangla="৯";
//                    }
//                    else if(minutes==0){
//                        minutesbangla="০";
//                    }
                    String totalTime=" "+minutes+" : "+second;
                    textViewForRestTime.setText(totalTime);
                    count++;
                }

                public  void onFinish(){

                    for(int i=0;i<Total_Answer_key.size();i++){

                        if(user_answer.get(i).equals(Total_Answer_key.get(i))){
                            correct_answer++;
                        }
                        else{
                            wrong_Answer_correct_list.add(TOTAL_Q_A.get(i));
                            wrong_answer++;
                        }
                    }


                    Intent intent=new Intent(ModelTestActivity.this, ModelTestResultShowActivity.class);
                    intent.putExtra("total","২৫ ");
                    startActivity(intent);
                    Log.d("GK",correct_answer+" correct_answer");
                    Log.d("GK",wrong_answer+" wrong answer");
                    Log.d("GK", wrong_Answer_correct_list.size()+" wrong_Answer_correct_list.size()");

                }
            }.start();
        }
        else if(time.equals("30"))
        {
            textViewForTotalTime.setText("৩০ মিনিট");

            //TODO: MUST CORRECT

            countDownTimer=  new CountDownTimer(32*60*1000, 1000){
                public void onTick(long millisUntilFinished){
                    if(count==60){
                        second=0;
                    }
                    else
                        second=count;

                    if(count%60==0&&count>=60){
                        ++minutes;
                        count=0;
                    }
                    //TODO: FOR converting second english to bangla
//                    if(second==1){
//                        secondBangla="১";
//                    }
//                    else if(second==2){
//                        secondBangla="২";
//                    }
//                    else if(second==3){
//                        secondBangla="৩";
//                    }
//                    else if(second==4){
//                        secondBangla="৪";
//                    }
//                    else if(second==5){
//                        secondBangla="৫";
//                    }
//                    else if(second==6){
//                        secondBangla="৬";
//                    }
//                    else if(second==7){
//                        secondBangla="৭";
//                    }
//                    else if(second==8){
//                        secondBangla="৮";
//                    }
//                    else if(second==9){
//                        secondBangla="৯";
//                    }
//                    else if(second==0){
//                        secondBangla="০";
//                    }

                    //TODO: FOR converting minutes english to bangla


//                    if(minutes==1){
//                        minutesbangla="১";
//                    }
//                    else if(minutes==2){
//                        minutesbangla="২";
//                    }
//                    else if(minutes==3){
//                        minutesbangla="৩";
//                    }
//                    else if(minutes==4){
//                        minutesbangla="৪";
//                    }
//                    else if(minutes==5){
//                        minutesbangla="৫";
//                    }
//                    else if(minutes==6){
//                        minutesbangla="৬";
//                    }
//                    else if(minutes==7){
//                        minutesbangla="৭";
//                    }
//                    else if(minutes==8){
//                        minutesbangla="৮";
//                    }
//                    else if(minutes==9){
//                        minutesbangla="৯";
//                    }
//                    else if(minutes==0){
//                        minutesbangla="০";
//                    }
                    String totalTime=" "+minutes+" : "+second;
                    textViewForRestTime.setText(totalTime);
                    count++;
                }
                public  void onFinish(){
                    for(int i=0;i<Total_Answer_key.size();i++){

                        if(user_answer.get(i).equals(Total_Answer_key.get(i))){
                            correct_answer++;
                        }
                        else{
                            wrong_Answer_correct_list.add(TOTAL_Q_A.get(i));
                            wrong_answer++;
                        }
                    }


                    Intent intent=new Intent(ModelTestActivity.this, ModelTestResultShowActivity.class);
                    intent.putExtra("total","৫০");
                    startActivity(intent);
                    Log.d("GK",correct_answer+" correct_answer");
                    Log.d("GK",wrong_answer+" wrong answer");
                    Log.d("GK", wrong_Answer_correct_list.size()+" wrong_Answer_correct_list.size()");


                }
            }.start();
        }
        else {

            textViewForTotalTime.setText("১ ঘন্টা");

            //TODO: MUST CORRECT

            countDownTimer= new CountDownTimer(63*60*1000, 1000){
                public void onTick(long millisUntilFinished){
                    if(count==60){
                        second=0;
                    }
                    else
                        second=count;

                    if(count%60==0&&count>=60){
                        ++minutes;
                        count=0;
                    }

                    //TODO: FOR converting second english to bangla
//                    if(second==1){
//                        secondBangla="১";
//                    }
//                    else if(second==2){
//                        secondBangla="২";
//                    }
//                    else if(second==3){
//                        secondBangla="৩";
//                    }
//                    else if(second==4){
//                        secondBangla="৪";
//                    }
//                    else if(second==5){
//                        secondBangla="৫";
//                    }
//                    else if(second==6){
//                        secondBangla="৬";
//                    }
//                    else if(second==7){
//                        secondBangla="৭";
//                    }
//                    else if(second==8){
//                        secondBangla="৮";
//                    }
//                    else if(second==9){
//                        secondBangla="৯";
//                    }
//                    else if(second==0){
//                        secondBangla="০";
//                    }

                    //TODO: FOR converting minutes english to bangla


//                    if(minutes==1){
//                        minutesbangla="১";
//                    }
//                    else if(minutes==2){
//                        minutesbangla="২";
//                    }
//                    else if(minutes==3){
//                        minutesbangla="৩";
//                    }
//                    else if(minutes==4){
//                        minutesbangla="৪";
//                    }
//                    else if(minutes==5){
//                        minutesbangla="৫";
//                    }
//                    else if(minutes==6){
//                        minutesbangla="৬";
//                    }
//                    else if(minutes==7){
//                        minutesbangla="৭";
//                    }
//                    else if(minutes==8){
//                        minutesbangla="৮";
//                    }
//                    else if(minutes==9){
//                        minutesbangla="৯";
//                    }
//                    else if(minutes==0){
//                        minutesbangla="০";
//                    }

                    String totalTime=" "+minutes+" : "+second;
                    textViewForRestTime.setText(totalTime);
                    count++;
                }
                public  void onFinish(){
                    for(int i=0;i<Total_Answer_key.size();i++){

                        if(user_answer.get(i).equals(Total_Answer_key.get(i))){
                            correct_answer++;
                        }
                        else{
                            wrong_Answer_correct_list.add(TOTAL_Q_A.get(i));
                            wrong_answer++;
                        }
                    }

                    Intent intent=new Intent(ModelTestActivity.this, ModelTestResultShowActivity.class);
                    intent.putExtra("total","১০০ ");
                    startActivity(intent);

                    Log.d("GK",correct_answer+" correct_answer");
                    Log.d("GK",wrong_answer+" wrong answer");
                    Log.d("GK", wrong_Answer_correct_list.size()+" wrong_Answer_correct_list.size()");

                }
            }.start();
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                for(int i=0;i<Gk_Answer_key.size();i++){
//                    if(user_answer.get(i).equals(Gk_Answer_key.get(i))){correct_answer++;}
//                    else{
//                        wrong_Answer_correct_list.add(GK_Q_A.get(i));wrong_answer++;}
//                }

                for(int i=0;i<Total_Answer_key.size();i++){

                    if(user_answer.get(i).equals(Total_Answer_key.get(i))){
                        correct_answer++;
                    }
                    else{
                        wrong_Answer_correct_list.add(TOTAL_Q_A.get(i));
                        wrong_answer++;
                    }
                }


                countDownTimer.cancel();

                Intent intent=new Intent(ModelTestActivity.this, ModelTestResultShowActivity.class);
                if(time.equals("15")){
                    intent.putExtra("total","২৫");
                }
                else if(time.equals("30")){
                    intent.putExtra("total","৫০");
                }
                else if(time.equals("60")){
                    intent.putExtra("total","১০০");
                }

                startActivity(intent);

            }
        });
    }



    @Override
    public void onItemClick(final int position, final View view) {
        //TODO: BE AWARE TO SET CLICK LISTENER TO RIGHT ID;
        String previousAnswer=user_answer.get(position);

        int previousAnswerCheckItem=-1;//TODO: -1 for not check any of check box
        if(previousAnswer.equals("5")){
            previousAnswerCheckItem=-1;
        }
        else if(previousAnswer.equals("1")){
            previousAnswerCheckItem=0;
        }
        else if(previousAnswer.equals("2")){
            previousAnswerCheckItem=1;
        }
        else if(previousAnswer.equals("3")){
            previousAnswerCheckItem=2;
        }
        else if(previousAnswer.equals("4")){
            previousAnswerCheckItem=3;
        }

        selected=0;//TODO: TO SELECT OPTION "ক" automatically
        String[] singleChoiceItems = new String[]{"ক","খ","গ","ঘ"};
        new AlertDialog.Builder(context)
                .setSingleChoiceItems(singleChoiceItems,previousAnswerCheckItem,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected=which;
                    }
                })
                .setTitle("সঠিক উত্তরটি নির্বাচন করুন")
                .setNegativeButton("বাদ", null)
.setPositiveButton("উত্তর দিন ", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
            user_answer.set(position,String.valueOf(selected+1));
            selected=0;
//        com.Teachers.HaziraKhataByGk.adapter.ModelTestAdapter.questionHolder questionHolder=new ModelTestAdapter.questionHolder(view);
//
//
//        // Save the selected positions to the SparseBooleanArray
//        if (selectedItems.get(position, false)) {
//            selectedItems.delete(position);
//            view.getRootView().setSelected(false);
//        }
//        else {
//            selectedItems.put(position, true);
//            view.getRootView().setSelected(true);
//        }
//
//        ModelTestAdapter.onBindViewHolder(questionHolder,position);
//
//
    }
}).show();
    }

    @Override
    public void onBackPressed() {
        
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("সতর্কীকরণ");
        alertDialog.setIcon(R.drawable.warnig_for_delete);
        alertDialog.setMessage("আপনি কি মডেল টেষ্ট থেকে বের হতে চান?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"বেরিয়ে যান",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"বাদ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        
        super.onDestroy();
    }





}
