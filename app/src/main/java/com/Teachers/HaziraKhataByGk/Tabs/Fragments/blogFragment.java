package com.Teachers.HaziraKhataByGk.Tabs.Fragments;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.BlogActivity;


public class blogFragment extends Fragment {
   View root;
   Button post,see;
    public blogFragment() {
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
        root=inflater.inflate(R.layout.blog_fragment, container, false);

        //TODO: FOR POST
        post=(Button)root.findViewById(R.id.write);
        post.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent =  getFacebookIntent("https://www.facebook.com/groups/2035798976667483/permalink/2045745182339529/");
                startActivity(intent);
                }
        });

        //TODO: FOR SEE
        see=(Button)root.findViewById(R.id.read);
        see.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.activity, BlogActivity.class);
                startActivity(intent);
            }
        });

        return root ;
    }


    //TODO: for opeing in fb app if it is installed.
    public Intent getFacebookIntent(String url) {

        PackageManager pm = MainActivity.context.getPackageManager();
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        }
        catch (PackageManager.NameNotFoundException ignored) {

        }

        return new Intent(Intent.ACTION_VIEW, uri);
    }

}
