package com.emon.haziraKhata.Adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.Listener.RecyclerItemClickListener;
import com.emon.haziraKhata.Model.JobItems;
import com.emon.haziraKhata.R;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import static com.emon.haziraKhata.R.id.clickerForJob;
import static com.emon.haziraKhata.R.id.loveClickerForjob;
import static com.emon.haziraKhata.R.id.saveClickerForjob;
import static com.emon.haziraKhata.R.id.shareClickerForjob;

/**
 * Created by GK on 10/28/2017.
 */

public class JobListAdapter extends RecyclerView.Adapter<JobViewHolder> {
    private ArrayList<JobItems> list;
    private RecyclerItemClickListener recyclerItemClickListener;

    public JobListAdapter(ArrayList<JobItems> Data) {
        list = Data;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jobs_item_cards, parent, false);
        final JobViewHolder holder = new JobViewHolder(view);
        //CLICK LISTENER
        holder.itemView.findViewById(clickerForJob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, holder.itemView.findViewById(clickerForJob));
                    }
                }
            }
        });
        holder.itemView.findViewById(loveClickerForjob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, holder.itemView.findViewById(loveClickerForjob));
                    }
                }
            }
        });
        holder.itemView.findViewById(saveClickerForjob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, holder.itemView.findViewById(saveClickerForjob));
                    }
                }
            }
        });
        holder.itemView.findViewById(shareClickerForjob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, holder.itemView.findViewById(shareClickerForjob));
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
    public void onBindViewHolder(final JobViewHolder holder, int position) {
        // FOR GENERATING METARIAL COLOR FOR NEWS CARDS SIDE
        ColorGenerator generator = ColorGenerator.MATERIAL;

        int color = generator.getRandomColor();
        TextDrawable myDrawable = TextDrawable.builder().beginConfig().height(10).width(750)
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRect("", color);

        ImageView savedIcon = (ImageView) holder.itemView.findViewById(R.id.saveClickerIconForJob);
        ImageView lovedIcon = (ImageView) holder.itemView.findViewById(R.id.lovedIconForJob);


        //TODO: check if save or unsaved
        if (UtilsCommon.isJobSaved(holder.itemView.getContext(), list.get(position).getPost(), list.get(position).getInstitute(), list.get(position).getPlace(), list.get(position).getURL())) {
            savedIcon.setImageResource(R.drawable.ic_saved_icon);
        }


        if (UtilsCommon.isJobLoved(holder.itemView.getContext(), list.get(position).getPost(), list.get(position).getInstitute(), list.get(position).getPlace(), list.get(position).getURL())) {
            lovedIcon.setImageResource(R.drawable.ic_love_icon);
        }
        String post = "পদ: " + list.get(position).getPost(), place = "স্থান: " + list.get(position).getPlace(), institute = "প্রতিষ্ঠান: " + list.get(position).getInstitute();

        holder.post.setText(post);
        holder.place.setText(place);
        holder.institute.setText(institute);
        holder.sideDrawable.setImageDrawable(myDrawable);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

class JobViewHolder extends RecyclerView.ViewHolder {

    public TextView post;
    public TextView institute;
    public TextView place;
    public ImageView sideDrawable;
    public LinearLayout share;
    public LinearLayout save;
    public LinearLayout love;


    public JobViewHolder(View v) {
        super(v);
        post = (TextView) v.findViewById(R.id.post);
        institute = (TextView) v.findViewById(R.id.institute);
        place = (TextView) v.findViewById(R.id.place);
        sideDrawable = (ImageView) v.findViewById(R.id.MetarialColorPlateForJob);
        share = (LinearLayout) v.findViewById(R.id.shareClickerForjob);
        save = (LinearLayout) v.findViewById(R.id.saveClickerForjob);
        love = (LinearLayout) v.findViewById(R.id.loveClickerForjob);
    }
}
