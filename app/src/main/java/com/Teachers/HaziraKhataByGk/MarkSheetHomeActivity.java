package com.Teachers.HaziraKhataByGk;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.ClassRoom.ClassRoomActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsForMarkSheetActivity;
import com.Teachers.HaziraKhataByGk.Constant.Constant;
import com.Teachers.HaziraKhataByGk.Model.SubjectMarkSheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MarkSheetHomeActivity extends AppCompatActivity {


    public String subjectName;
    public Integer numberOfDistribution;
    public Double totalNumberOfDistribution;
    public RecyclerView recyclerViewOfSubject;
    public ArrayList<String> listDistribution;//example HW ,CT
    public ArrayList<Double> listOfNumOfDistribution;// HW 's number 15 ,CT's number 20

    public HashMap<String,Double> distributionVSnumberTable;// example: "home work" <--> 15

    String className,sectionName;


    public Context context;
    private FloatingActionButton fab;
    private TextView emptyText;

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseCaller firebaseCaller =new FirebaseCaller();
        firebaseCaller.getTotalSubject(className,sectionName,recyclerViewOfSubject,MarkSheetHomeActivity.this,emptyText);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mark_sheet_home);
        emptyText=findViewById(R.id.empty_text_for_mark_sheet_home);

        context=this;

        recyclerViewOfSubject=findViewById(R.id.recycler_view_subject);

        try {
            className=getIntent().getExtras().getString(Constant.CLASS_NAME);
            sectionName=getIntent().getExtras().getString(Constant.CLASS_SECTION);

        }catch (Exception c){
            className= UtilsCommon.getCurrentClass(MarkSheetHomeActivity.this).getName();
            sectionName= UtilsCommon.getCurrentClass(MarkSheetHomeActivity.this).getSection();
        }





        fab =findViewById(R.id.fabForAddSubject);
        fab.setOnClickListener(view -> {

            listDistribution =new ArrayList<>();
            distributionVSnumberTable=new HashMap<>();
            listOfNumOfDistribution =new ArrayList<>();

            CreatingSubjectEntryDialog();

        });
    }

    public void CreatingSubjectEntryDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.subject_sub_division_number, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.subject_sub_division_number);
        edt.setInputType(InputType.TYPE_CLASS_TEXT);


        dialogBuilder.setTitle("বিষয়ের নাম");
        dialogBuilder.setMessage("আপনি যেই বিষয়ের মার্কশীট তৈরী করতে চান তা নাম ইনপুট দিন।(যেমন : বাংলা,ইংলিশ,এলগোরিদম ইত্যাদি)");
        dialogBuilder.setPositiveButton("ইনপুট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                subjectName=edt.getText().toString().trim();

                if(!subjectName.equals("")){
                    CreatingEntityDialog();
                    dialog.dismiss();
                }
                else Toast.makeText(MarkSheetHomeActivity.this,"বিষয়ের নাম ইনপুট করা হয়নি",Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    public void CreatingEntityDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.subject_sub_division_number, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.subject_sub_division_number);
        edt.setHint("নাম্বার বন্টনের সংখ্যা");
        final EditText totalNumberEditText =(EditText) dialogView.findViewById(R.id.subject_sub_division_number_total_number);
        totalNumberEditText.setVisibility(View.VISIBLE);
        totalNumberEditText.setHint("মোট নাম্বার");

        dialogBuilder.setTitle("বিষয়ের মোট নাম্বার বন্টন");
        dialogBuilder.setMessage("আপনি যেই বিষয়ের মার্কশীট তৈরী করতে চান তার কয়টি নাম্বার বন্টন চান তার সংখ্যা এবং নাম্বার বন্টনের মোট নাম্বার ইনপুট দিন ।(যেমন :কোন বিষয়ের মাসিক পরীক্ষা,সাময়িক পরীক্ষা,ক্লাস টেষ্ট,বাড়ির কাজের নাম্বার থাকলে প্রথমে ইনপুট দিন 4 এরপর ইনপুট দিন এদের মোট নাম্বার যেমন : 100 )");
        dialogBuilder.setPositiveButton("ইনপুট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


            }
        });
        dialogBuilder.setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        final AlertDialog b = dialogBuilder.create();
        b.show();

        b.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(edt.getText().toString().trim().equals("")){
                    Toast.makeText(context,"দয়া করে নাম্বার বন্টনের সংখ্যা ইনপুট দিন",Toast.LENGTH_LONG).show();
                }
                else if(totalNumberEditText.getText().toString().trim().equals("")){
                    Toast.makeText(context,"দয়া করে নাম্বার বন্টনের মোট নাম্বার নাম্বার ইনপুট দিন",Toast.LENGTH_LONG).show();
                }
                else {

                    String num = edt.getText().toString().trim();

                    numberOfDistribution=Integer.valueOf(num);
                    totalNumberOfDistribution=Double.valueOf(totalNumberEditText.getText().toString().trim());
                    CreatingDistributionNameIntakeDialog();
                    b.dismiss();

                }



            }
        });



    }


    //Example Enter "HW" <--> 15
    public void CreatingDistributionNameIntakeDialog(){
        for ( int i=1;i<=numberOfDistribution;i++){

            final int i1=i;

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.subject_sub_division_number, null);
            dialogBuilder.setView(dialogView);

            final EditText entityNameEditText = (EditText) dialogView.findViewById(R.id.subject_sub_division_number);

            //Todo Have toTime fixed that bug , i want toTime small the hint size but cannot
            entityNameEditText.setHint(numberOfDistribution+1-i+ Html.fromHtml("<small><small>" +
                    getString(R.string.hint_for_distribution_entry)+ "</small></small>").toString()+"");
            entityNameEditText.setInputType(InputType.TYPE_CLASS_TEXT);



            final EditText entityNumberEditText =(EditText) dialogView.findViewById(R.id.subject_sub_division_number_total_number);
            entityNumberEditText.setVisibility(View.VISIBLE);
            entityNumberEditText.setHint(numberOfDistribution+1-i+"নং বন্টনের নাম্বার ইনপুট দিন");
            entityNumberEditText.setInputType(InputType.TYPE_CLASS_NUMBER);



            dialogBuilder.setTitle("নাম্বার বন্টনের নাম এবং নাম্বারগুলো ইনপুট দিন");
            dialogBuilder.setMessage(" আপনি যতগুলো নাম্বার বন্টন দিয়েছেন একে একে তার নাম এবং বন্টনের নাম্বারগুলো ইনপুট দিন । যেমন :প্রথম বক্সে ইনপুট দিন \"বাড়ির কাজ \" এরপর ইনপুট দিন \"10\" )");
            dialogBuilder.setPositiveButton("ইনপুট করুন", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            dialogBuilder.setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
           final AlertDialog b = dialogBuilder.create();
            b.show();

            b.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    if(entityNameEditText.getText().toString().trim().equals("")){
                        Toast.makeText(context,"দয়া করে নাম্বার বন্টনের নাম ইনপুট দিন",Toast.LENGTH_LONG).show();
                    }
                    else if(entityNumberEditText.getText().toString().trim().equals("")){
                        Toast.makeText(context,"দয়া করে নাম্বার বন্টনের নাম্বার ইনপুট দিন",Toast.LENGTH_LONG).show();
                    }
                    else {
                        String name=entityNameEditText.getText().toString().trim();
                        String number=entityNumberEditText.getText().toString().trim();


                        //Check for duplicate
                        if(Collections.frequency(listDistribution, name)>1){
                            Toast.makeText(context,name+ "এই নামটি ইতিমধ্যে ইনপুট করা হয়েছে। অন্য নাম দিন ।",Toast.LENGTH_LONG).show();
                        }
                        else {
                            listDistribution.add( name);
                            listOfNumOfDistribution.add(Double.valueOf(number));
                            distributionVSnumberTable.put(name,Double.valueOf(number));

                            if(i1==1){
                                if(!UtilsForMarkSheetActivity.getTotalNumberOfUserDistributionEntity(listOfNumOfDistribution).equals(totalNumberOfDistribution)){

                                    UtilsForMarkSheetActivity.CreateErrorDialog(MarkSheetHomeActivity.this, listDistribution, listOfNumOfDistribution,totalNumberOfDistribution);
                                    Log.d("GK","ERROR");
                                }
                                else {

                                    SubjectMarkSheet subjectMarkSheet=new SubjectMarkSheet();
                                    subjectMarkSheet.setSubjectName(subjectName);
                                    subjectMarkSheet.setTotalNumber(totalNumberOfDistribution);

                                    subjectMarkSheet.setDistributionVSnumberTable(UtilsForMarkSheetActivity.createDistributionObjectList(numberOfDistribution,listDistribution,listOfNumOfDistribution));


                                    FirebaseCaller firebaseCaller = new FirebaseCaller();
                                    firebaseCaller.pushSubjectToServer(className,sectionName,subjectMarkSheet);

                                    firebaseCaller.getTotalSubject(className,sectionName,recyclerViewOfSubject,MarkSheetHomeActivity.this,emptyText);



                                    Log.d("GK","Not error");
                                }
                            }
                            b.dismiss();
                        }

                        Log.d("GK","listDistribution : "+ listDistribution.size());

                    }



                }
            });


        }
    }




}
