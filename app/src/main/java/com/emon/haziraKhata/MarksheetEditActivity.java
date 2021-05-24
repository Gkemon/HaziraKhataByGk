package com.emon.haziraKhata;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.emon.haziraKhata.Constant.Constant;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.HelperClasses.UtilsForMarkSheetEditAcitvity;
import com.emon.haziraKhata.HelperClasses.ViewUtils.BaseActivity;
import com.emon.haziraKhata.Model.SubjectMarkSheet;

public class MarksheetEditActivity extends BaseActivity {

    public SubjectMarkSheet subjectMarkSheet;
    public Button saveButton, printButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UtilsCommon.hideNotificationStatus(this);

        setContentView(R.layout.activity_marksheet_edit);

        TextView topText = findViewById(R.id.top_text_view);

        saveButton = findViewById(R.id.button_save_edited_marksheet);
        printButton = findViewById(R.id.button_print_edited_marksheet);


        RecyclerView recyclerView = findViewById(R.id.recycler_view_subject);


        subjectMarkSheet = (SubjectMarkSheet) getIntent().getSerializableExtra(Constant.MARK_SHEET_OBJECT_FOR_EDIT);

        String temp = "বিষয় :" + subjectMarkSheet.getSubjectName() + "\n" + "মোট নাম্বার :" + subjectMarkSheet.getTotalNumber() + "\n" + "মোট বন্টন সংখ্যা : " + subjectMarkSheet.getDistributionVSnumberTable().size() + " টি " + "\n" + "বন্টনের নামগুলো এবং প্রতিটি বন্টনের মোট নাম্বারগুলো নিচে দেয়া হল \n";
        topText.setText(temp);
        for (int i = 0; i < subjectMarkSheet.getDistributionVSnumberTable().size(); i++) {
            topText.append(i + 1 + ") " + subjectMarkSheet.getDistributionVSnumberTable().get(i).distributionName + " (" + subjectMarkSheet.getDistributionVSnumberTable().get(i).distributionNumber + " নাম্বার )\n");
        }


        UtilsForMarkSheetEditAcitvity.getStudentList(getIntent().getStringExtra(Constant.CLASS_NAME), getIntent().getStringExtra(Constant.CLASS_SECTION), recyclerView, this, subjectMarkSheet, getIntent().getStringExtra(Constant.HASH_KEY_FOR_RESULT_EDIT_ACTIVITY), saveButton, printButton);


    }

}
