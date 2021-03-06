package com.emon.haziraKhata.StudentAdd;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.Model.Student;
import com.emon.haziraKhata.R;

import java.util.List;

public class StudentAddUtils {
    public static boolean isRollExisted(List<Student> students, String targetRoll, Context context) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(targetRoll)) {
                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("সতর্কীকরণ");
                    alertDialog.setIcon(R.drawable.warning_for_add);
                    alertDialog.setMessage("এই একই রোল ইতিমধ্যে  এই ক্লাসের  ডাটাবেজে রয়েছে।নতুন রোল ইনপুট দিন,ধন্যবাদ।");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ওকে",
                            (dialog, which) -> dialog.dismiss());
                    alertDialog.show();
                }catch (Exception e){
                    UtilsCommon.handleError(e);
                    return false;
                }

                return true;
            }
        }
        return false;
    }

}
