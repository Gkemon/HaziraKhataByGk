package com.emon.haziraKhata.Tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emon.haziraKhata.Adapter.JobListAdapter;
import com.emon.haziraKhata.Firebase.FirebaseCaller;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.Listener.RecyclerItemClickListener;
import com.emon.haziraKhata.Model.JobItems;
import com.emon.haziraKhata.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.emon.haziraKhata.home.MainActivity.Job_list;
import static com.emon.haziraKhata.R.id.clickerForJob;
import static com.emon.haziraKhata.R.id.loveClickerForjob;
import static com.emon.haziraKhata.R.id.saveClickerForjob;
import static com.emon.haziraKhata.R.id.shareClickerForjob;

public class JobFragment extends Fragment implements RecyclerItemClickListener {
    public RecyclerView jobRecycle;

    Context context;
    View view;

    public JobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jobRecycle = (RecyclerView) view.findViewById(R.id.jobFragmentRecycle);

        FirebaseCaller.getFirebaseDatabase().child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("GK", "Jobfragment retriving data fromTime server");
                ArrayList<JobItems> JobItemlist = new ArrayList<JobItems>();
                for (DataSnapshot JobItemsData : dataSnapshot.getChildren()) {
                    JobItems Single_JobItems;
                    Single_JobItems = JobItemsData.getValue(JobItems.class);
                    JobItemlist.add(Single_JobItems);
                }
                Job_list = JobItemlist;
                if (Job_list.size() > 0 && jobRecycle != null) {
                    Collections.reverse(Job_list);
                    JobListAdapter jobListAdapter = new JobListAdapter(Job_list);
                    jobListAdapter.setOnItemClickListener(com.emon.haziraKhata.Tabs.JobFragment.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    jobRecycle.setAdapter(jobListAdapter);
                    jobRecycle.setLayoutManager(MyLayoutManager);
                    Log.d("GK", "job list is not null");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.job_fragment, container, false);
        return view;
    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case shareClickerForjob:

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "পদ : " + Job_list.get(position).getPost() + "\nপ্রতিষ্ঠান : " + Job_list.get(position).getInstitute() + "\nস্থান : " + Job_list.get(position).getPlace() + "\n" + "যোগাযোগ :" + Job_list.get(position).getURL() + "\n\n সংগ্রহ : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.teachers.HaziraKhataByGk");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "শেয়ার করুন।"));
                break;


            case saveClickerForjob:

                UtilsCommon.saveJob(context, Job_list.get(position).getPost(), Job_list.get(position).getInstitute(), Job_list.get(position).getPlace(), Job_list.get(position).getURL());
                ImageView savedIcon = (ImageView) view.findViewById(R.id.saveClickerIconForJob);

                if (UtilsCommon.isJobSaved(getContext(), Job_list.get(position).getPost(), Job_list.get(position).getInstitute(), Job_list.get(position).getPlace(), Job_list.get(position).getURL())) {
                    savedIcon.setImageResource(R.drawable.ic_saved_icon);
                    Toast.makeText(context, "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                } else {
                    savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(context, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                }

                break;


            case loveClickerForjob:


                UtilsCommon.LoveJob(context, Job_list.get(position).getPost(), Job_list.get(position).getInstitute(), Job_list.get(position).getPlace(), Job_list.get(position).getURL());
                ImageView loveIcon = (ImageView) view.findViewById(R.id.lovedIconForJob);

                if (UtilsCommon.isJobLoved(context, Job_list.get(position).getPost(), Job_list.get(position).getInstitute(), Job_list.get(position).getPlace(), Job_list.get(position).getURL())) {
                    loveIcon.setImageResource(R.drawable.ic_love_icon);
                    Toast.makeText(context, "পছন্দ", Toast.LENGTH_SHORT).show();
                } else {
                    loveIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(context, "অপছন্দ", Toast.LENGTH_SHORT).show();
                }
                break;

            case clickerForJob:
                final int pos = position;
                UtilsCommon.openInAppBrowser(Job_list.get(pos).getURL(), context);

                break;

        }
    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }


}
