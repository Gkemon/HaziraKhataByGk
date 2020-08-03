package com.Teachers.HaziraKhataByGk.SavedData;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.Adapter.JobListAdapter;
import com.Teachers.HaziraKhataByGk.BottomNavigationActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.JobItems;
import com.Teachers.HaziraKhataByGk.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.Teachers.HaziraKhataByGk.BottomNavigationActivity.Saved_job_list;
import static com.Teachers.HaziraKhataByGk.R.id.clickerForJob;
import static com.Teachers.HaziraKhataByGk.R.id.loveClickerForjob;
import static com.Teachers.HaziraKhataByGk.R.id.saveClickerForjob;
import static com.Teachers.HaziraKhataByGk.R.id.shareClickerForjob;

public class SavedJobFragment extends Fragment implements RecyclerItemClickListener {
    public static RecyclerView SavedJobRecycle;
    public FloatingActionButton delete_all;
    View Rootview;

    public SavedJobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (BottomNavigationActivity.isSavedJoblistEmpty) {
            BottomNavigationActivity.viewforSavedJob.setVisibility(View.VISIBLE);
            BottomNavigationActivity.view1forSavedJob.setVisibility(View.VISIBLE);
            SavedJobRecycle.setVisibility(View.GONE);
            delete_all.setVisibility(GONE);
        } else {
            BottomNavigationActivity.viewforSavedJob.setVisibility(View.GONE);
            BottomNavigationActivity.view1forSavedJob.setVisibility(View.GONE);
            delete_all.setVisibility(View.VISIBLE);
            SavedJobRecycle.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Rootview = inflater.inflate(R.layout.saved_job_fragment, container, false);
        Rootview.setPadding(0, 40, 0, 0);
        SavedJobRecycle = (RecyclerView) Rootview.findViewById(R.id.SavedJobRecycle);
        delete_all = (FloatingActionButton) Rootview.findViewById(R.id.delete_all_button);
        BottomNavigationActivity.viewforSavedJob = Rootview.findViewById(R.id.EmptyImage);
        BottomNavigationActivity.view1forSavedJob = Rootview.findViewById(R.id.txt_empty_view);

        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
        if (BottomNavigationActivity.isSavedJoblistEmpty) {
            BottomNavigationActivity.viewforSavedJob.setVisibility(View.VISIBLE);
            BottomNavigationActivity.view1forSavedJob.setVisibility(View.VISIBLE);
            SavedJobRecycle.setVisibility(View.GONE);
            delete_all.setVisibility(GONE);

        } else {
            BottomNavigationActivity.viewforSavedJob.setVisibility(View.GONE);
            BottomNavigationActivity.view1forSavedJob.setVisibility(View.GONE);
            SavedJobRecycle.setVisibility(View.VISIBLE);
            delete_all.setVisibility(View.VISIBLE);
        }


        //TODO: delete all saved news
        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog();
            }
        });


        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("GK", "Jobfragment retriving data fromTime server");
                ArrayList<JobItems> JobItemlist = new ArrayList<JobItems>();
                for (DataSnapshot JobItemsData : dataSnapshot.getChildren()) {
                    JobItems Single_JobItems;
                    Single_JobItems = JobItemsData.getValue(JobItems.class);
                    JobItemlist.add(Single_JobItems);
                }
                Saved_job_list = new ArrayList<JobItems>();
                Saved_job_list = JobItemlist;


                //        IT MAKES THE INSTRUCTION ON saved job FRAGMENT WHEN THERE IS NO saved job
//        For loading saved job fromTime Server
                Query queryReforSeeTheDataIsEmptyOrNot = FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs");
                queryReforSeeTheDataIsEmptyOrNot.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            BottomNavigationActivity.isSavedJoblistEmpty = false;
                        } else {
                            BottomNavigationActivity.isSavedJoblistEmpty = true;
                        }

                        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
                        if (BottomNavigationActivity.isSavedJoblistEmpty) {
                            BottomNavigationActivity.viewforSavedJob.setVisibility(View.VISIBLE);
                            BottomNavigationActivity.view1forSavedJob.setVisibility(View.VISIBLE);
                            SavedJobRecycle.setVisibility(View.GONE);
                            delete_all.setVisibility(GONE);
                        } else {
                            BottomNavigationActivity.viewforSavedJob.setVisibility(View.GONE);
                            BottomNavigationActivity.view1forSavedJob.setVisibility(View.GONE);
                            SavedJobRecycle.setVisibility(View.VISIBLE);
                            delete_all.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                if (Saved_job_list.size() > 0 && SavedJobRecycle != null) {
                    JobListAdapter jobListAdapter = new JobListAdapter(Saved_job_list);
                    jobListAdapter.setOnItemClickListener(SavedJobFragment.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    SavedJobRecycle.setAdapter(jobListAdapter);
                    SavedJobRecycle.setLayoutManager(MyLayoutManager);
                    Log.d("GK", "job list is not null");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        return Rootview;
    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case shareClickerForjob:

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "পদ : " + Saved_job_list.get(position).getPost() + "\nপ্রতিষ্ঠান : " + Saved_job_list.get(position).getInstitute() + "\nস্থান : " + Saved_job_list.get(position).getPlace() + "\n" + "যোগাযোগ :" + Saved_job_list.get(position).getURL() + "\n\n সংগ্রহ : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "পোষ্টটি শেয়ার করুন।"));

                break;


            case saveClickerForjob:
                UtilsCommon.saveJob(BottomNavigationActivity.context, Saved_job_list.get(position).getPost(), Saved_job_list.get(position).getInstitute(), Saved_job_list.get(position).getPlace(), Saved_job_list.get(position).getURL());
                ImageView savedIcon = (ImageView) view.findViewById(R.id.saveClickerIconForJob);

                if (UtilsCommon.isJobSaved(BottomNavigationActivity.context, Saved_job_list.get(position).getPost(), Saved_job_list.get(position).getInstitute(), Saved_job_list.get(position).getPlace(), Saved_job_list.get(position).getURL())) {
                    savedIcon.setImageResource(R.drawable.ic_saved_icon);
                    Toast.makeText(BottomNavigationActivity.activity, "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                } else {
                    savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(BottomNavigationActivity.activity, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                break;
            case loveClickerForjob:

                UtilsCommon.LoveJob(BottomNavigationActivity.context, Saved_job_list.get(position).getPost(), Saved_job_list.get(position).getInstitute(), Saved_job_list.get(position).getPlace(), Saved_job_list.get(position).getURL());
                ImageView loveIcon = (ImageView) view.findViewById(R.id.lovedIconForJob);

                if (UtilsCommon.isJobLoved(BottomNavigationActivity.context, Saved_job_list.get(position).getPost(), Saved_job_list.get(position).getInstitute(), Saved_job_list.get(position).getPlace(), Saved_job_list.get(position).getURL())) {
                    loveIcon.setImageResource(R.drawable.ic_love_icon);
                    Toast.makeText(getContext(), "পছন্দ", Toast.LENGTH_SHORT).show();
                } else {
                    loveIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(getContext(), "অপছন্দ", Toast.LENGTH_SHORT).show();
                }
                break;
            case clickerForJob:

                final int pos = position;

                UtilsCommon.openInAppBrowser(Saved_job_list.get(pos).getURL(), getContext());


                break;
        }
    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }


    public void DeleteDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BottomNavigationActivity.context);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_delete_dialauge, null);
        dialogBuilder.setView(dialogView);
        final EditText edt = (EditText) dialogView.findViewById(R.id.custom_delete_dialauge_text);

        dialogBuilder.setIcon(R.drawable.warnig_for_delete);
        dialogBuilder.setTitle("সেভ করা শিক্ষক নিয়োগ বিজ্ঞপতি ডিলিট করতে চান?");
        dialogBuilder.setMessage("নিশ্চিত হতে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
        dialogBuilder.setPositiveButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (edt.getText().toString().equals("DELETE")) {


                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_jobs").removeValue();
                    SharedPreferences pref = BottomNavigationActivity.context.getSharedPreferences("HaziraKhata_save_job", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(BottomNavigationActivity.context, BottomNavigationActivity.class);
                    startActivity(intent);
                }


            }
        });
        dialogBuilder.setNegativeButton("বাদ দিন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


}
