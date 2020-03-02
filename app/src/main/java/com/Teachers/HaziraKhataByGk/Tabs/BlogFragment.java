package com.Teachers.HaziraKhataByGk.Tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.Teachers.HaziraKhataByGk.BlogActivity;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.R;


public class BlogFragment extends Fragment {
    View root;
    Button post, see;
    Context context;

    public BlogFragment() {
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
        root = inflater.inflate(R.layout.blog_fragment, container, false);

        //TODO: FOR POST
        post = (Button) root.findViewById(R.id.write);
        post.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                UtilsCommon.openWithFaceBook("https://www.facebook.com/groups/2035798976667483/permalink/2045745182339529/", getActivity());
            }
        });

        //TODO: FOR SEE
        see = (Button) root.findViewById(R.id.read);
        see.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BlogActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
