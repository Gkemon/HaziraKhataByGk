package com.Teachers.HaziraKhataByGk.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gk emon on 2/4/2018.
 */

public class FeesAdapter extends RecyclerView.Adapter<FeesAdapter.FeeHolder>{

    public ArrayList<String> name;
    private Context context;
    public FeesAdapter(Context context) {
        this.context = context;
        this.name = new ArrayList<>();
    }

    private void add(String item) {
        name.add(item);
        notifyItemInserted(name.size() - 1);
    }

    public void addAll(List<String> studentList) {
        for (String student : studentList) {
            add(student);
        }
    }

    public void remove(String item) {
        int position = name.indexOf(item);
        if (position > -1) {
            name.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public String getItem(int position) {
        return name.get(position);
    }
    @Override
    public FeesAdapter.FeeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_for_exam_fee, parent, false);

        
        final FeesAdapter.FeeHolder  holder = new FeesAdapter.FeeHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
//                if (adapterPos != RecyclerView.NO_POSITION) {
//                    if (recyclerItemClickListener != null) {
//                        recyclerItemClickListener.onItemClick(adapterPos, StudentHolder.itemView);
//                    }
//                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(FeesAdapter.FeeHolder holder, int position) {
        final String nameOfStudent = name.get(position);
        String name="নাম: "+nameOfStudent;
        holder.name.setText(name);

    }
    @Override
    public int getItemCount() {
        return name.size();
    }



    public static class FeeHolder extends RecyclerView.ViewHolder {

        TextView name;
        EditText feesInput;
        public FeeHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameFromFees);
            feesInput=itemView.findViewById(R.id.fees);
        }
    }
}
