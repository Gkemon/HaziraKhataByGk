package com.Teachers.HaziraKhataByGk.StudentAdd;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import com.Teachers.HaziraKhataByGk.HelperClassess.GlobalContext;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.R;

import java.util.List;

public class StudentAddUtils {
    public static boolean isRollExisted(List<Student> students, String targetRoll, Context context){
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(targetRoll)){
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("সতর্কীকরণ");
                alertDialog.setIcon(R.drawable.warning_for_add);
                alertDialog.setMessage("এই একই রোল ইতিমধ্যে  এই ক্লাসের  ডাটাবেজে রয়েছে।নতুন রোল ইনপুট দিন,ধন্যবাদ।");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
                return true;
            }
        }
        return false;
    }

}
