package com.Teachers.HaziraKhataByGk.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClasses.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClasses.ViewUtils.CustomTextInputLayout;
import com.Teachers.HaziraKhataByGk.Model.DistributionVSnumberTable;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.Model.StudentVsDistributionTable;
import com.Teachers.HaziraKhataByGk.Model.SubjectMarkSheet;
import com.Teachers.HaziraKhataByGk.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.Teachers.HaziraKhataByGk.HelperClasses.UtilsCommon.logObject;

/**
 * Created by Gk emon on 2/4/2018.
 */

public class MarkSheetEditAdapter extends RecyclerView.Adapter<MarkSheetEditAdapter.markEditHolder> {

    public static HashMap<Integer, ArrayList<EditText>> editTextHashMap;//For avoiding auto checking
    public ArrayList<Student> Students;
    public SubjectMarkSheet subjectMarkSheet;
    public ArrayList<StudentVsDistributionTable> studentVsDistributionTableArrayList;
    public String className, sectionName, key;
    String mainNumberSheetText = "";
    private Activity activity;


    public MarkSheetEditAdapter(Activity activity, ArrayList<Student> Students, SubjectMarkSheet subjectMarkSheet, String className, String sectionName, String key) {
        this.activity = activity;
        this.Students = Students;
        this.subjectMarkSheet = subjectMarkSheet;
        this.studentVsDistributionTableArrayList = new ArrayList<>();
        this.className = className;
        this.key = key;
        this.sectionName = sectionName;

        for (int i = 0; i < Students.size(); i++) {
            StudentVsDistributionTable studentVsDistributionTable = new StudentVsDistributionTable();
            studentVsDistributionTable.setStudentID(Students.get(i).getId());
            studentVsDistributionTableArrayList.add(studentVsDistributionTable);
        }

        editTextHashMap = new HashMap<>();

        Log.d("GK", "studentVsDistributionTableArrayList size : " + studentVsDistributionTableArrayList.size());
        // checkHash = new HashMap<Integer, Boolean>();
    }

    private void add(Student item) {
        Students.add(item);
        notifyItemInserted(Students.size() - 1);
    }

    public void addAll(List<Student> studentList) {
        for (Student student : studentList) {
            add(student);
        }
    }

    public void remove(Student item) {
        int position = Students.indexOf(item);
        if (position > -1) {
            Students.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Student getItem(int position) {
        return Students.get(position);
    }

    @Override
    public markEditHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_card_mark_sheet, parent, false);


        LinearLayout layout = (LinearLayout) view.findViewById(R.id.student_mark_edit_layout);

        for (int i = 0; i < subjectMarkSheet.getDistributionVSnumberTable().size(); i++) {

            CustomTextInputLayout customTextInputLayout = new CustomTextInputLayout(activity);
            customTextInputLayout.setGravity(Gravity.CENTER);
            customTextInputLayout.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
            customTextInputLayout.setHintTextAppearance(R.style.Hints);


            EditText editText = new EditText(activity);
            editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            editText.setId(i);
            editText.setTextSize(14);
            editText.setTextColor(activity.getResources().getColor(R.color.primary_dark));
            editText.setHint(subjectMarkSheet.getDistributionVSnumberTable().get(i).distributionName);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            customTextInputLayout.addView(editText);

            //add button toTime the layout
            layout.addView(customTextInputLayout);

        }


        final markEditHolder holder = new markEditHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(markEditHolder holder, int position) {
        final Student student = Students.get(position);
        String name = "নাম: " + student.getStudentName() + " ( রোল: " + student.getId() + " )";
        holder.name.setText(name);

        //for get the right edit text which i created on on create view because i need the righ edit text toTime set the data toTime appropiate position


        ArrayList<EditText> editTextArrayList = new ArrayList<>();
        for (int i = 0; i < subjectMarkSheet.getDistributionVSnumberTable().size(); i++) {
            EditText editText = (EditText) holder.itemView.findViewById(i);


            String number = "";
            if (subjectMarkSheet.getStudentVsDistributionTableArrayList() != null)
                if (subjectMarkSheet.getStudentVsDistributionTableArrayList().get(position) != null)
                    if (subjectMarkSheet.getStudentVsDistributionTableArrayList().get(position).getDistributionVSnumberTableArrayList() != null)
                        if (subjectMarkSheet.getStudentVsDistributionTableArrayList().get(position).getDistributionVSnumberTableArrayList().get(i) != null)
                            if (subjectMarkSheet.getStudentVsDistributionTableArrayList().get(position).getDistributionVSnumberTableArrayList().get(i).distributionNumber != null)
                                number = subjectMarkSheet.getStudentVsDistributionTableArrayList().get(position).getDistributionVSnumberTableArrayList().get(i).distributionNumber.toString();


            if (number == null)
                editText.setText("");
            else editText.setText(number);


            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.addTextChangedListener(new MyTextWatcher(editText, position, i));
            editTextArrayList.add(editText);

            if (editText.getText().toString() == null) {
                Log.d("GK", "Edit text is null");
            } else {
                Log.d("GK", "Edit text is not null : " + editText.getText().toString() + " possition : " + position + " :" + subjectMarkSheet.getDistributionVSnumberTable().get(0).getDistributionNumber());
            }
        }
        editTextHashMap.put(position, editTextArrayList);
        Log.d("GK", editTextHashMap.size() + " hash map size");


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return Students.size();
    }

    public void saveDataToServer() {

        doProcessOfAllData();

        Toast.makeText(activity, "সেভ করা হয়েছে", Toast.LENGTH_LONG).show();
        activity.finish();
    }

    public void printData() {

        doProcessOfAllData();
        String temp = "বিষয় :" + subjectMarkSheet.getSubjectName() + "\n" + "মোট নাম্বার :" + subjectMarkSheet.getTotalNumber() + "\n" + "মোট বন্টন সংখ্যা : " + subjectMarkSheet.getDistributionVSnumberTable().size() + " টি " + "\n" + "বন্টনের নামগুলো এবং প্রতিটি বন্টনের মোট নাম্বারগুলো নিচে দেয়া হল \n\n";

        for (int i = 0; i < subjectMarkSheet.getDistributionVSnumberTable().size(); i++) {
            temp += (i + 1 + ") " + subjectMarkSheet.getDistributionVSnumberTable().get(i).distributionName + " (" + subjectMarkSheet.getDistributionVSnumberTable().get(i).distributionNumber + " নাম্বার )\n");
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                temp + mainNumberSheetText);
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);

    }

    public void doProcessOfAllData() {
        mainNumberSheetText = "";

        for (int i = 0; i < editTextHashMap.size(); i++) {
            ArrayList<DistributionVSnumberTable> distributionVSnumberTableArrayList = new ArrayList<>();

            String temp2 = "";
            for (int j = 0; j < editTextHashMap.get(i).size(); j++) {
                Log.d("GK", "editTextHashMap :[ " + i + " ] [ " + j + " ] " + editTextHashMap.get(i).get(j).getText().toString());

                DistributionVSnumberTable distributionVSnumberTable = new DistributionVSnumberTable();

                distributionVSnumberTable.setDistributionName(subjectMarkSheet.getDistributionVSnumberTable().get(j).distributionName);
                temp2 += "\n" + subjectMarkSheet.getDistributionVSnumberTable().get(j).distributionName + " ( " + editTextHashMap.get(i).get(j).getText().toString() + " নাম্বার )";

                if (UtilsCommon.isNumeric(editTextHashMap.get(i).get(j).getText().toString()))
                    distributionVSnumberTable.setDistributionNumber(Double.valueOf(editTextHashMap.get(i).get(j).getText().toString()));
                distributionVSnumberTableArrayList.add(distributionVSnumberTable);

            }

            String temp1 = "\n নাম : " + Students.get(i).getStudentName() + " ( রোল :" + Students.get(i).getId() + " )\n";
            mainNumberSheetText += (temp1 + temp2) + "\n";

            StudentVsDistributionTable studentVsDistributionTable = new StudentVsDistributionTable();
            studentVsDistributionTable.setStudentID(Students.get(i).getId());
            studentVsDistributionTable.setDistributionVSnumberTableArrayList(distributionVSnumberTableArrayList);


            //logObject(studentVsDistributionTable);

            studentVsDistributionTableArrayList.set(i, studentVsDistributionTable);
            subjectMarkSheet.setStudentVsDistributionTableArrayList(studentVsDistributionTableArrayList);

            logObject(studentVsDistributionTableArrayList);

        }

        Log.d("GK", "KEY :" + key);

        FirebaseCaller firebaseCaller = new FirebaseCaller();
        firebaseCaller.pushAndRemoveSubjectToServer(className, sectionName, subjectMarkSheet, key);


    }

    public static class markEditHolder extends RecyclerView.ViewHolder {

        TextView name;

        public markEditHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.subject_name);
        }
    }

    class MyTextWatcher implements TextWatcher {

        private View view;

        private int position, positionDistribution;


        private MyTextWatcher(View view, int position, int positionDistribution) {
            this.view = view;
            this.position = position;
            this.positionDistribution = positionDistribution;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {

            try {
                Integer.valueOf(editable.toString());
            } catch (Exception e) {
                return;
            }

//            ArrayList<DistributionVSnumberTable> distributionVSnumberTableArrayList =new ArrayList<>();
//
//            for(int i=0;i<subjectMarkSheet.getDistributionVSnumberTable().size();i++){
//
//                if(positionDistribution==i){
//                    DistributionVSnumberTable distributionVSnumberTable = new DistributionVSnumberTable();
//                    logObject(subjectMarkSheet);
//                    distributionVSnumberTable.setDistributionName(subjectMarkSheet.getDistributionVSnumberTable().get(i).distributionName);
//                    distributionVSnumberTable.setDistributionNumber(Integer.valueOf(editable.toString()));
//                    distributionVSnumberTableArrayList.set(i,distributionVSnumberTable);
//
//
//
//                    StudentVsDistributionTable studentVsDistributionTable = new StudentVsDistributionTable();
//                    studentVsDistributionTable.setStudentID(Students.get(position).getId());
//                    studentVsDistributionTable.setDistributionVSnumberTableArrayList(distributionVSnumberTableArrayList);
//
//
//                    logObject(studentVsDistributionTable);
//
//                    studentVsDistributionTableArrayList.set(position,studentVsDistributionTable);
//                    subjectMarkSheet.setStudentVsDistributionTableArrayList(studentVsDistributionTableArrayList);
//
//                   // logObject(studentVsDistributionTableArrayList);
//                }


        }


    }

}
