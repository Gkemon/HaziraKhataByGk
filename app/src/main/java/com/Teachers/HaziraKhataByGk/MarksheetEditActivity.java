package com.Teachers.HaziraKhataByGk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsForMarkSheetEditAcitvity;
import com.Teachers.HaziraKhataByGk.constant.ContantsForGlobal;
import com.Teachers.HaziraKhataByGk.model.SubjectMarkSheet;

public class MarksheetEditActivity extends AppCompatActivity {

    public SubjectMarkSheet subjectMarkSheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UtilsCommon.HideNotifiationBar(this);

        setContentView(R.layout.activity_marksheet_edit);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_subject);


        subjectMarkSheet=(SubjectMarkSheet)getIntent().getSerializableExtra(ContantsForGlobal.MARK_SHEET_OBJECT_FOR_EDIT);


        UtilsForMarkSheetEditAcitvity.getStudentList(getIntent().getStringExtra(ContantsForGlobal.CLASS_NAME),getIntent().getStringExtra(ContantsForGlobal.CLASS_SECTION),recyclerView,this,subjectMarkSheet);




    }

}
