package com.emon.haziraKhata.Tabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.emon.haziraKhata.R;


public class TotthojhuriFragment extends Fragment {

    public TotthojhuriFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.thottho_jhuri, container, false);
        Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11;
        button1 = (Button) view.findViewById(R.id.btn_dialog_1);
        button2 = (Button) view.findViewById(R.id.btn_dialog_2);
        button3 = (Button) view.findViewById(R.id.btn_dialog_3);
        button4 = (Button) view.findViewById(R.id.btn_dialog_4);
        button5 = (Button) view.findViewById(R.id.btn_dialog_5);
        button6 = (Button) view.findViewById(R.id.btn_dialog_6);
        button7 = (Button) view.findViewById(R.id.btn_dialog_7);
        button8 = (Button) view.findViewById(R.id.btn_dialog_8);
        button9 = (Button) view.findViewById(R.id.btn_dialog_9);
        button10 = (Button) view.findViewById(R.id.btn_dialog_10);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openInAppBrowser("http://www.moedu.gov.bd/");

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openInAppBrowser("http://www.mopme.gov.bd/");


            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openInAppBrowser("http://www.educationboard.gov.bd/");


            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openInAppBrowser("http://www.dshe.gov.bd/");


            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openInAppBrowser("http://www.dpe.gov.bd/");


            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openInAppBrowser("http://www.dpe.gov.bd/");


            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInAppBrowser("http://www.ntrca.gov.bd/");
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInAppBrowser("https://www.teachers.gov.bd/");
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openInAppBrowser("http://shikkhok.com/");

            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openInAppBrowser("https://bn.khanacademy.org/");

            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInAppBrowser("http://10minuteschool.com/");
            }
        });

        return view;
    }

    private void openInAppBrowser(String url) {

        //TODO: for opening webview
//        Intent intent = new Intent(MainActivity.activity, BrowsingActivity.class_room);
//        intent.putExtra("URL", url);
//        startActivity(intent);
        //TODO: for opening default browser
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }


}
