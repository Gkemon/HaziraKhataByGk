package com.Teachers.HaziraKhataByGk.Tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.NibondhonItems;
import com.Teachers.HaziraKhataByGk.ModelTestChooseActivity;
import com.Teachers.HaziraKhataByGk.PreviousQuestionActivity;
import com.Teachers.HaziraKhataByGk.Widget.TextShowActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;


public class NibondhonFragment extends Fragment implements RecyclerItemClickListener{
    ArrayList<NibondhonItems> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;
    String nibondhonItems[] = {"বেসরকারি শিক্ষক নিবন্ধন পরীক্ষা, সিলেবাস ও আবেদনের পদ্ধতি ","শিক্ষক নিবন্ধন মডেল টেস্ট","বিগত শিক্ষক নিবন্ধন প্রশ্ন ব্যাংক"};
    int  Images[] = {R.drawable.nibondhon_details,R.drawable.model_test,R.drawable.previous_question};
    InterstitialAd mInterstitialAd;

    Context context;
    public NibondhonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.nibondhon_corner_fragment, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.nibondhonRecycle);
        MyRecyclerView.setHasFixedSize(true);

        //TODO: FOR INTERSTIALAD

        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            nibondhonAdapter nibondhonAdapter= new nibondhonAdapter(listitems);
            nibondhonAdapter.setOnItemClickListener(this);
            MyRecyclerView.setAdapter(nibondhonAdapter);
        }

        MyRecyclerView.setLayoutManager(MyLayoutManager);
        return view;

    }



    public class nibondhonAdapter extends RecyclerView.Adapter<nibondhonViewHolder> {
        private ArrayList<NibondhonItems> list;
        private RecyclerItemClickListener recyclerItemClickListener;
        public nibondhonAdapter(ArrayList<NibondhonItems> Data) {
            list = Data;
        }


        @Override
        public nibondhonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.nibondhon_fragement_items, parent, false);
          final  nibondhonViewHolder holder = new nibondhonViewHolder(view);
            //CLICK LISTENER
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPos = holder.getAdapterPosition();
                    if (adapterPos != RecyclerView.NO_POSITION) {
                        if (recyclerItemClickListener != null) {
                            recyclerItemClickListener.onItemClick(adapterPos, holder.itemView);
                        }
                    }
                }
            });
            return holder;
        }
        public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }
        @Override
        public void onBindViewHolder(final nibondhonViewHolder holder, int position) {

//TODO: USE GLIDE FOR MAKING IMAGE LIGHT
            Glide.with(getContext())
                    .load((Integer) list.get(position).getImageResourceId())
                    .into(holder.coverImageView);

            holder.titleTextView.setText(list.get(position).getItemNames());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context=context;
    }

    public class nibondhonViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;

        public nibondhonViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);

        }
    }

    public void initializeList() {
        listitems.clear();

        for(int i =0;i<3;i++){
            NibondhonItems item = new NibondhonItems();
            item.setItemNames(nibondhonItems[i]);
            item.setimageResourceId(Images[i]);
            listitems.add(item);
        }

    }
    @Override
    public void onItemClick(int position, View view) {
        Log.d("eee","On Click listener in Nibondhon Fragment");
         if(position==0){


             Intent intent=new Intent(context, TextShowActivity.class);
             intent.putExtra("subjectName","1");
             startActivity(intent);


         }
         else if(position==1){
             Intent intent = new Intent(getContext(), ModelTestChooseActivity.class);
             startActivity(intent);
         }
        else{
             Intent intent = new Intent(getContext(), PreviousQuestionActivity.class);
             startActivity(intent);
         }

    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }



}
