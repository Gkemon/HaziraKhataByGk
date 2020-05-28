package com.Teachers.HaziraKhataByGk.SavedData;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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

import com.Teachers.HaziraKhataByGk.Adapter.BlogAdapter;
import com.Teachers.HaziraKhataByGk.BottomNavigationActivity;
import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.BlogItem;
import com.Teachers.HaziraKhataByGk.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.Teachers.HaziraKhataByGk.BottomNavigationActivity.Saved_Blog_list;
import static com.Teachers.HaziraKhataByGk.R.id.ClickerForBlog;
import static com.Teachers.HaziraKhataByGk.R.id.SaveClicker;
import static com.Teachers.HaziraKhataByGk.R.id.ShareClicker;
import static com.Teachers.HaziraKhataByGk.R.id.loveClicker;


public class SavedBlogFragment extends Fragment implements RecyclerItemClickListener {
    public RecyclerView savedBlogRecycle;
    public FloatingActionButton delete_all;
    View rootView;
    Context context;

    public SavedBlogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
        if (BottomNavigationActivity.isSavedBloglistEmpty) {
            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.VISIBLE);
            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.VISIBLE);
            savedBlogRecycle.setVisibility(View.GONE);
            delete_all.setVisibility(GONE);

        } else {
            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.GONE);
            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.GONE);
            savedBlogRecycle.setVisibility(View.VISIBLE);
            delete_all.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.saved_blog, container, false);
        rootView.setPadding(0, 40, 0, 0);
        savedBlogRecycle = (RecyclerView) rootView.findViewById(R.id.savedblogRecycle);
        delete_all = (FloatingActionButton) rootView.findViewById(R.id.delete_all_button);
        BottomNavigationActivity.viewforSavedBlog = rootView.findViewById(R.id.EmptyImage);
        BottomNavigationActivity.view1forSavedBlog = rootView.findViewById(R.id.EmptyText);


        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
        if (BottomNavigationActivity.isSavedBloglistEmpty) {
            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.VISIBLE);
            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.VISIBLE);
            savedBlogRecycle.setVisibility(View.GONE);
            delete_all.setVisibility(GONE);

        } else {
            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.GONE);
            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.GONE);
            savedBlogRecycle.setVisibility(View.VISIBLE);
            delete_all.setVisibility(View.VISIBLE);
        }


        //TODO: delete all saved news
        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog();
            }
        });


        FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_blog").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<BlogItem> blogItemlist = new ArrayList<BlogItem>();
                for (DataSnapshot blogData : dataSnapshot.getChildren()) {
                    BlogItem single_blog_Items;
                    single_blog_Items = blogData.getValue(BlogItem.class);
                    blogItemlist.add(single_blog_Items);
                }
                Saved_Blog_list = new ArrayList<BlogItem>();
                Saved_Blog_list = blogItemlist;


                //TODO: IT MAKES THE INSTRUCTION ON saved job FRAGMENT WHEN THERE IS NO saved job For loading saved job fromTime Server
                Query queryReforSeeTheDataIsEmptyOrNotForsavedBlog = FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_blog");
                queryReforSeeTheDataIsEmptyOrNotForsavedBlog.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            BottomNavigationActivity.isSavedBloglistEmpty = false;
                        } else {
                            BottomNavigationActivity.isSavedBloglistEmpty = true;
                        }

                        //THIS MAKES THE EMPTY IMAGE AND EMPTY DESCRIPTION
                        if (BottomNavigationActivity.isSavedBloglistEmpty) {
                            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.VISIBLE);
                            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.VISIBLE);
                            savedBlogRecycle.setVisibility(View.GONE);
                            delete_all.setVisibility(GONE);

                        } else {
                            BottomNavigationActivity.viewforSavedBlog.setVisibility(View.GONE);
                            BottomNavigationActivity.view1forSavedBlog.setVisibility(View.GONE);
                            savedBlogRecycle.setVisibility(View.VISIBLE);
                            delete_all.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


                if (Saved_Blog_list.size() > 0 && savedBlogRecycle != null) {
                    BlogAdapter BlogAdapter = new BlogAdapter(Saved_Blog_list);
                    BlogAdapter.setOnItemClickListener(SavedBlogFragment.this);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                    MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    savedBlogRecycle.setAdapter(BlogAdapter);
                    savedBlogRecycle.setLayoutManager(MyLayoutManager);
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
        BlogItem BlogItem = Saved_Blog_list.get(position);
        switch (view.getId()) {


            case ClickerForBlog:

                final int pos = position;


                String url = Saved_Blog_list.get(pos).getURL();
                //TODO: for opening default browser
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);


                break;


            case ShareClicker:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                String heading = Saved_Blog_list.get(position).getHeading();
                String url1 = Saved_Blog_list.get(position).getURL();
                String name = Saved_Blog_list.get(position).getWriter();
                intent1.putExtra(Intent.EXTRA_TEXT, "#শিক্ষক_কথন \n\n" + heading + "\n\nলেখন :" + name + "\nলিংক :" + url1 + "\n\n সংগ্রহে : #হাজিরা_খাতা \n বাংলাভাষায় প্রথম ক্লাস ম্যানেজমেন্ট মোবাইল এপ ।\n ডাউনলোড লিংক : https://play.google.com/store/apps/details?id=com.Teachers.HaziraKhataByGk");
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "শেয়ার করুন।"));
                break;


            case SaveClicker:

                if (UtilsCommon.saveBlog(context, BlogItem)) {
                    ImageView savedIcon = (ImageView) view.findViewById(R.id.SaveClickerIcon);
                    if (UtilsCommon.isBlogBookmarked(BlogItem, context)) {
                        savedIcon.setImageResource(R.drawable.ic_saved_icon);
                        Toast.makeText(BottomNavigationActivity.activity, "সেভ হয়েছে", Toast.LENGTH_SHORT).show();
                    } else {
                        savedIcon.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                        Toast.makeText(BottomNavigationActivity.activity, "সেভ রিমুভ হয়েছে", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }


            case loveClicker:
                UtilsCommon.loveBlog(BlogItem, context);
                ImageView loveIcon = (ImageView) view.findViewById(R.id.lovedIcon);

                if (UtilsCommon.isBlogLove(BlogItem, context)) {
                    loveIcon.setImageResource(R.drawable.ic_love_icon);
                    Toast.makeText(context, "পছন্দ", Toast.LENGTH_SHORT).show();
                } else {
                    loveIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(context, "অপছন্দ", Toast.LENGTH_SHORT).show();
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
        dialogBuilder.setTitle("সেভ করা শিক্ষক কথন ডিলিট করতে চান?");
        dialogBuilder.setMessage("নিশ্চিত হতে ইংরেজীতে \"DELETE\" শব্দটি লিখুন।");
        dialogBuilder.setPositiveButton("ডিলিট করুন", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (edt.getText().toString().equals("DELETE")) {


                    FirebaseCaller.getFirebaseDatabase().child("Users").child(FirebaseCaller.getUserID()).child("Saved_blog").removeValue();
                    SharedPreferences pref = BottomNavigationActivity.context.getSharedPreferences("teacher_blog_saved", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.apply();
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
