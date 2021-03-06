package com.emon.haziraKhata.SavedData;

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

import com.emon.haziraKhata.Adapter.NewsAdapter;
import com.emon.haziraKhata.BottomNavigationActivity;
import com.emon.haziraKhata.Firebase.FirebaseCaller;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.Listener.RecyclerItemClickListener;
import com.emon.haziraKhata.Model.NewsItem;
import com.emon.haziraKhata.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.emon.haziraKhata.BottomNavigationActivity.Saved_News_list;
import static com.emon.haziraKhata.R.id.ClickerForNews;
import static com.emon.haziraKhata.R.id.SaveClicker;
import static com.emon.haziraKhata.R.id.ShareClicker;
import static com.emon.haziraKhata.R.id.loveClicker;


public class SavedNewsFragment extends Fragment implements RecyclerItemClickListener {

    public static RecyclerView NewsRecycle;
    public FloatingActionButton delete_all;
    View rootView;

    public SavedNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.saved_news_fragment, container, false);
        rootView.setPadding(0, 40, 0, 0);
        BottomNavigationActivity.view = rootView.findViewById(R.id.EmptyImage);
        BottomNavigationActivity.view1 = rootView.findViewById(R.id.txt_empty_view);
        NewsRecycle = (RecyclerView) rootView.findViewById(R.id.SavedNewsRecycle);
        delete_all = (FloatingActionButton) rootView.findViewById(R.id.delete_all_button);

        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
        if (BottomNavigationActivity.isSavedNewsListEmpty) {
            BottomNavigationActivity.view.setVisibility(View.VISIBLE);
            BottomNavigationActivity.view1.setVisibility(View.VISIBLE);
            NewsRecycle.setVisibility(GONE);
            delete_all.setVisibility(GONE);
        } else {
            BottomNavigationActivity.view.setVisibility(GONE);
            BottomNavigationActivity.view1.setVisibility(GONE);
            NewsRecycle.setVisibility(View.VISIBLE);
            delete_all.setVisibility(View.VISIBLE);
        }

        //TODO: delete all saved news
        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog();
            }
        });


        //VIEWS

        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_news").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("GK", "NewsLoadingFromServer is called NewsLoadingFromServer");
                ArrayList<NewsItem> NewsItems = new ArrayList<NewsItem>();
                for (DataSnapshot NewsData : dataSnapshot.getChildren()) {
                    NewsItem single_newsItem = new NewsItem();
                    single_newsItem = NewsData.getValue(NewsItem.class);
                    NewsItems.add(single_newsItem);
                }
                Saved_News_list = new ArrayList<NewsItem>();
                Saved_News_list = NewsItems;

                //        IT MAKES THE INSTRUCTION ON saved news FRAGMENT WHEN THERE IS NO news
//        For loading saved news fromTime Server
                Query queryReforSeeTheDataIsEmptyOrNot = FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_news");
                queryReforSeeTheDataIsEmptyOrNot.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            BottomNavigationActivity.isSavedNewsListEmpty = false;
                        } else {
                            BottomNavigationActivity.isSavedNewsListEmpty = true;
                        }

                        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
                        if (BottomNavigationActivity.isSavedNewsListEmpty) {
                            BottomNavigationActivity.view.setVisibility(View.VISIBLE);
                            BottomNavigationActivity.view1.setVisibility(View.VISIBLE);
                            NewsRecycle.setVisibility(GONE);
                            delete_all.setVisibility(GONE);
                        } else {
                            BottomNavigationActivity.view.setVisibility(GONE);
                            BottomNavigationActivity.view1.setVisibility(GONE);
                            NewsRecycle.setVisibility(View.VISIBLE);
                            delete_all.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                if (Saved_News_list.size() > 0 && NewsRecycle != null) {
                    NewsAdapter NewsAdapter = new NewsAdapter(Saved_News_list);
                    NewsAdapter.setOnItemClickListener(SavedNewsFragment.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    NewsRecycle.setAdapter(NewsAdapter);
                    NewsRecycle.setLayoutManager(MyLayoutManager);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        return rootView;
    }


    @Override
    public void onItemClick(int position, View view) {


        switch (view.getId()) {


            case ClickerForNews:

                final int pos = position;


                UtilsCommon.openInAppBrowser(Saved_News_list.get(pos).getURL(), getContext());


                break;


            case ShareClicker:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Saved_News_list.get(position).getHeading() + "\n" + "লিংক :" + Saved_News_list.get(position).getURL() + "\n\n সংগ্রহ : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "শেয়ার করুন।"));
                //Toast.makeText(MainActivity.activity, "SHARED", Toast.LENGTH_LONG).show();
                break;


            case SaveClicker:


                ImageView savedIcon = (ImageView) view.findViewById(R.id.SaveClickerIcon);

                if (UtilsCommon.isNewsBookmarked(Saved_News_list.get(position), getContext())) {
                    savedIcon.setImageResource(R.drawable.ic_saved_icon);
                    Toast.makeText(BottomNavigationActivity.activity, "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                } else {
                    savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(BottomNavigationActivity.activity, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                }
                break;


            case loveClicker:
                UtilsCommon.NewsLoved(BottomNavigationActivity.context, Saved_News_list.get(position).getURL(), Saved_News_list.get(position).getHeading(), Saved_News_list.get(position).getDate());
                ImageView loveIcon = (ImageView) view.findViewById(R.id.lovedIcon);

                if (UtilsCommon.isNewsLoved(BottomNavigationActivity.context, Saved_News_list.get(position).getURL(), Saved_News_list.get(position).getHeading(), Saved_News_list.get(position).getDate())) {
                    loveIcon.setImageResource(R.drawable.ic_love_icon);
                    Toast.makeText(getContext(), "পছন্দ", Toast.LENGTH_SHORT).show();
                } else {
                    loveIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(BottomNavigationActivity.activity, "অপছন্দ", Toast.LENGTH_SHORT).show();
                }
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
        dialogBuilder.setTitle("সেভ করা শিক্ষা খবর ডিলিট করতে চান?");
        dialogBuilder.setMessage("নিশ্চিত হতে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
        dialogBuilder.setPositiveButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (edt.getText().toString().equals("DELETE")) {
                    FirebaseCaller.databaseReference.child("Users").child(FirebaseCaller.getUserID()).child("Saved_news").removeValue();
                    SharedPreferences pref = BottomNavigationActivity.context.getSharedPreferences("HaziraKhata", 0); // 0 - for private mode
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
