package com.Teachers.HaziraKhataByGk.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.MarksheetEditActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.constant.ContantsForGlobal;
import com.Teachers.HaziraKhataByGk.model.SubjectMarkSheet;

import java.util.List;

import static com.Teachers.HaziraKhataByGk.constant.ContantsForGlobal.HASH_KEY_FOR_RESULT_EDIT_ACTIVITY;
import static com.Teachers.HaziraKhataByGk.constant.ContantsForGlobal.MARK_SHEET_OBJECT_FOR_EDIT;

/**
 * Created by uy on 6/14/2018.
 */

public class SubjectMarkSheetAdaper extends RecyclerView.Adapter<SubjectMarkSheetAdaper.MyViewHolder> {

    private List<SubjectMarkSheet> subjectList;
    private List<String> keys;
    private Activity activity;
    private String className;
    private String sectionName;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectName;
        public View Subject;

        public MyViewHolder(View view) {
            super(view);

            subjectName = (TextView) view.findViewById(R.id.subject_name);
            Subject = view;
        }
    }


    public SubjectMarkSheetAdaper(String className,String sectionName,List<SubjectMarkSheet> subjectList, List<String> keys, Activity activity) {
        this.className=className;
        this.sectionName=sectionName;
        this.subjectList = subjectList;
        this.keys = keys;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject_card_mark_sheet, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        SubjectMarkSheet subjectMarkSheet = subjectList.get(position);
        holder.subjectName.setText(subjectMarkSheet.getSubjectName());

        holder.Subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, MarksheetEditActivity.class);
                intent.putExtra(MARK_SHEET_OBJECT_FOR_EDIT,subjectList.get(position));
                intent.putExtra(HASH_KEY_FOR_RESULT_EDIT_ACTIVITY,keys.get(position));
                intent.putExtra(ContantsForGlobal.CLASS_NAME,className);
                intent.putExtra(ContantsForGlobal.CLASS_SECTION,sectionName);

                activity.startActivity(intent);
            }
        });

        holder.Subject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {



                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}