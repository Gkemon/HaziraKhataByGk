package com.Teachers.HaziraKhataByGk.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
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

import com.Teachers.HaziraKhataByGk.HelperClassess.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Scheduler.CustomTextInputLayout;
import com.Teachers.HaziraKhataByGk.model.DistributionVSnumberTable;
import com.Teachers.HaziraKhataByGk.model.StudentVsDistributionTable;
import com.Teachers.HaziraKhataByGk.model.SubjectMarkSheet;
import com.Teachers.HaziraKhataByGk.model.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon.logObject;

/**
 * Created by Gk emon on 2/4/2018.
 */

public class MarkSheetEditAdapter extends RecyclerView.Adapter<MarkSheetEditAdapter.markEditHolder> {

    public ArrayList<student> students;
    public SubjectMarkSheet subjectMarkSheet;
    public ArrayList<StudentVsDistributionTable> studentVsDistributionTableArrayList;
    private Activity activity;
    public String className, sectionName, key;
    public static HashMap<Integer, ArrayList<EditText>> editTextHashMap;//For avoiding auto checking


    public MarkSheetEditAdapter(Activity activity, ArrayList<student> students, SubjectMarkSheet subjectMarkSheet, String className, String sectionName, String key) {
        this.activity = activity;
        this.students = students;
        this.subjectMarkSheet = subjectMarkSheet;
        this.studentVsDistributionTableArrayList = new ArrayList<>();
        this.className = className;
        this.key = key;
        this.sectionName = sectionName;

        for (int i = 0; i < students.size(); i++) {
            StudentVsDistributionTable studentVsDistributionTable = new StudentVsDistributionTable();
            studentVsDistributionTable.setStudentID(students.get(i).getId());
            studentVsDistributionTableArrayList.add(studentVsDistributionTable);
        }

        editTextHashMap = new HashMap<>();

        Log.d("GK", "studentVsDistributionTableArrayList size : " + studentVsDistributionTableArrayList.size());
        // checkHash = new HashMap<Integer, Boolean>();
    }

    private void add(student item) {
        students.add(item);
        notifyItemInserted(students.size() - 1);
    }

    public void addAll(List<student> studentList) {
        for (student student : studentList) {
            add(student);
        }
    }

    public void remove(student item) {
        int position = students.indexOf(item);
        if (position > -1) {
            students.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public student getItem(int position) {
        return students.get(position);
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

            //add button to the layout
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
        final student student = students.get(position);
        String name = "নাম: " + student.getStudentName() + " ( রোল: " + student.getId() + " )";
        holder.name.setText(name);

        //for get the right edit text which i created on on create view because i need the righ edit text to set the data to appropiate position

        ArrayList<EditText> editTextArrayList = new ArrayList<>();
        for (int i = 0; i < subjectMarkSheet.getDistributionVSnumberTable().size(); i++) {
            EditText editText = (EditText) holder.itemView.findViewById(i);
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
        return students.size();
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
//                    studentVsDistributionTable.setStudentID(students.get(position).getId());
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


    public void saveDataToServer() {

        for (int i = 0; i < editTextHashMap.size(); i++) {
            ArrayList<DistributionVSnumberTable> distributionVSnumberTableArrayList = new ArrayList<>();
            for (int j = 0; j < editTextHashMap.get(i).size(); j++) {
                Log.d("GK", "editTextHashMap :[ " + i + " ] [ " + j + " ] " + editTextHashMap.get(i).get(j).getText().toString());

                DistributionVSnumberTable distributionVSnumberTable = new DistributionVSnumberTable();

                distributionVSnumberTable.setDistributionName(subjectMarkSheet.getDistributionVSnumberTable().get(j).distributionName);
                distributionVSnumberTable.setDistributionNumber(Integer.valueOf(editTextHashMap.get(i).get(j).getText().toString()));
                distributionVSnumberTableArrayList.add(distributionVSnumberTable);

            }

            StudentVsDistributionTable studentVsDistributionTable = new StudentVsDistributionTable();
            studentVsDistributionTable.setStudentID(students.get(i).getId());
            studentVsDistributionTable.setDistributionVSnumberTableArrayList(distributionVSnumberTableArrayList);


            //logObject(studentVsDistributionTable);

            studentVsDistributionTableArrayList.set(i, studentVsDistributionTable);
            subjectMarkSheet.setStudentVsDistributionTableArrayList(studentVsDistributionTableArrayList);

            logObject(studentVsDistributionTableArrayList);

        }

        Log.d("GK","KEY :"+key);

        FirebaseCaller firebaseCaller = new  FirebaseCaller();
        firebaseCaller.pushSubjectToServer(className,sectionName,subjectMarkSheet,key);
        activity.finish();
    }

}
