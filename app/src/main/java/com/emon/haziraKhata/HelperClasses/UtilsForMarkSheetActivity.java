package com.emon.haziraKhata.HelperClasses;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.emon.haziraKhata.Model.DistributionVSnumberTable;
import com.emon.haziraKhata.R;

import java.util.ArrayList;

/**
 * Created by GK on 6/14/2018.
 */

public class UtilsForMarkSheetActivity {


    public static Double getTotalNumberOfUserDistributionEntity(ArrayList<Double> integers) {
        Double totalNumber = 0.00;

        for (int i = 0; i < integers.size(); i++) {
            totalNumber += integers.get(i);
        }

        Log.d("GK", "Total number of entity :" + totalNumber);
        return totalNumber;
    }

    public static void CreateErrorDialog(Activity activity, ArrayList<String> distributions, ArrayList<Double> numOfDistributions, Double totalNumber) {


        String text = "নাম্বার বন্টনে আপনার ইনপুট দেয়া নাম্বারগুলোর সমষ্টি মোট নাম্বারের সমান নয় ।ইনপুট দেয়া নাম্বারগুলোর সমষ্টি অবশ্যই মোট নাম্বারের সমান হতে হবে। । নিচে আপনার ইনপুটগুলো দেখানো হল :\n\n";
        String temp = " এই সাবজেক্টের মোট নাম্বার " + totalNumber + "\n\n";
        for (int i = 0; i < distributions.size(); i++) {
            temp += " নাম্বার বন্টনের নাম : " + distributions.get(i) + " এবং এর নাম্বার : " + numOfDistributions.get(i) + "\n\n";
        }
        text += temp;


        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_total_mark_error_in_marksheet, null);
        TextView errorTextView = dialogView.findViewById(R.id.error_text_for_total_mark);
        errorTextView.setText(text);


        final Dialog dialog = new Dialog(activity); // Context, this, etc.
        dialog.setContentView(dialogView);
        dialog.setTitle("ভুল সংশোধন করুন");

        dialog.show();
    }

    public static ArrayList<DistributionVSnumberTable> createDistributionObjectList(Integer numOfDistribution, ArrayList<String> listOfDistribution, ArrayList<Double> listOfDistributionNumber) {

        ArrayList<DistributionVSnumberTable> temp = new ArrayList<>();

        //Which means a field is blank
        if (listOfDistribution.size() != listOfDistributionNumber.size()) {
            Log.d("GK", "Which means a field is blank");
            return null;

        }

        for (int i = 0; i < numOfDistribution; i++) {
            DistributionVSnumberTable distributionVSnumberTable = new DistributionVSnumberTable();
            distributionVSnumberTable.setDistributionName(listOfDistribution.get(i));
            distributionVSnumberTable.setDistributionNumber(listOfDistributionNumber.get(i));
            temp.add(distributionVSnumberTable);
        }

        return temp;
    }

}
