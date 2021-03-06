package com.emon.haziraKhata.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.emon.haziraKhata.Constant.Constant;
import com.emon.haziraKhata.Firebase.FirebaseCaller;
import com.emon.haziraKhata.MarksheetEditActivity;
import com.emon.haziraKhata.Model.SubjectMarkSheet;
import com.emon.haziraKhata.R;

import java.util.List;

import static com.emon.haziraKhata.Constant.Constant.HASH_KEY_FOR_RESULT_EDIT_ACTIVITY;
import static com.emon.haziraKhata.Constant.Constant.MARK_SHEET_OBJECT_FOR_EDIT;

/**
 * Created by GK on 6/14/2018.
 */

public class SubjectMarkSheetAdaper extends RecyclerView.Adapter<SubjectMarkSheetAdaper.MyViewHolder> {

    public Activity activity;
    private List<SubjectMarkSheet> subjectList;
    private List<String> keys;
    private String className;
    private String sectionName;

    public SubjectMarkSheetAdaper(String className, String sectionName, List<SubjectMarkSheet> subjectList, List<String> keys, Activity activity) {
        this.className = className;
        this.sectionName = sectionName;
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
        final SubjectMarkSheet subjectMarkSheet = subjectList.get(position);
        holder.subjectName.setText(subjectMarkSheet.getSubjectName());

        holder.Subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, MarksheetEditActivity.class);
                intent.putExtra(MARK_SHEET_OBJECT_FOR_EDIT, subjectList.get(position));
                intent.putExtra(HASH_KEY_FOR_RESULT_EDIT_ACTIVITY, keys.get(position));
                intent.putExtra(Constant.CLASS_NAME, className);
                intent.putExtra(Constant.CLASS_SECTION, sectionName);

                activity.startActivity(intent);
            }
        });

        holder.Subject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // setup the alert builder
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("সতর্কীকরণ");
                builder.setIcon(R.drawable.warnig_for_delete);
                builder.setMessage("আপনি কি " + subjectMarkSheet.getSubjectName() + " এর সকল তথ্য ডিলিট করতে চান?");

                // add the buttons
                builder.setPositiveButton("ডিলিট", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseCaller firebaseCaller = new FirebaseCaller();
                        firebaseCaller.deleteData(className, sectionName, keys.get(position));
                        subjectList.remove(position);
                        notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("না", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();


                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectName;
        public View Subject;

        public MyViewHolder(View view) {
            super(view);

            subjectName = (TextView) view.findViewById(R.id.subject_name);
            Subject = view;
        }
    }
}
