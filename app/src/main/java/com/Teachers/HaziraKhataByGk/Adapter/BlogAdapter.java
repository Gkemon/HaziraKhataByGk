package com.Teachers.HaziraKhataByGk.Adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.BlogActivity;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.BlogItem;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import static com.Teachers.HaziraKhataByGk.R.id.ClickerForBlog;
import static com.Teachers.HaziraKhataByGk.R.id.MetarialColorPlate;
import static com.Teachers.HaziraKhataByGk.R.id.RelativeLayoutForNewsCard;
import static com.Teachers.HaziraKhataByGk.R.id.SaveClicker;
import static com.Teachers.HaziraKhataByGk.R.id.ShareClicker;
import static com.Teachers.HaziraKhataByGk.R.id.dateOfNews;
import static com.Teachers.HaziraKhataByGk.R.id.loveClicker;

/**
 * Created by uy on 10/28/2017.
 */

public class BlogAdapter extends RecyclerView.Adapter<blogViewHolder> {
    private ArrayList<BlogItem> list;
    private RecyclerItemClickListener recyclerItemClickListener;
    public BlogAdapter(ArrayList<BlogItem> Data) {
        list = Data;
    }

    @Override
    public blogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_cards, parent, false);
        final  blogViewHolder holder = new blogViewHolder(view);
        //CLICK LISTENER
        holder.itemView.findViewById(ClickerForBlog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, holder.itemView.findViewById(ClickerForBlog));
                    }
                }
            }
        });
        holder.itemView.findViewById(loveClicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, holder.itemView.findViewById(loveClicker));
                    }
                }
            }
        });
        holder.itemView.findViewById(SaveClicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, holder.itemView.findViewById(SaveClicker));
                    }
                }

            }
        });
        holder.itemView.findViewById(ShareClicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, holder.itemView.findViewById(ShareClicker));
                    }
                }
            }
        });
        return holder;
    }
    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    //TODO:set saved icon on onBindViewHolder not onCreateViewHolder
    @Override
    public void onBindViewHolder(final blogViewHolder holder, int position) {
        // FOR GENERATING METARIAL COLOR FOR NEWS CARDS SIDE
        ColorGenerator generator = ColorGenerator.MATERIAL;

        int color = generator.getRandomColor();
        TextDrawable myDrawable = TextDrawable.builder().beginConfig().height(10).width(750)
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRect("",color);
        ImageView savedIcon=(ImageView)holder.itemView.findViewById(R.id.SaveClickerIcon);
        ImageView lovedIcon=(ImageView)holder.itemView.findViewById(R.id.lovedIcon);


        //TODO: check if save or unsaved
        if(UtilsCommon.isBlogBookmarked(list.get(position),holder.itemView.getContext())){
            savedIcon.setImageResource(R.drawable.ic_saved_icon);
        }
        if(UtilsCommon.isBlogLove(list.get(position),holder.itemView.getContext())){
            lovedIcon.setImageResource(R.drawable.ic_love_icon);
        }

        holder.titleTextView.setText(list.get(position).getHeading());
        CharSequence writer,date;
        writer ="শিক্ষকের নাম: "+list.get(position).getWriter();
        date="তারিখ :"+list.get(position).getDate();
        holder.writer.setText(writer);
        holder.Date.setText(date);
        holder.sideDrawable.setImageDrawable(myDrawable);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}

class blogViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public TextView Date;
    public TextView writer;
    public ImageView sideDrawable;
    public LinearLayout share;
    public LinearLayout save;
    public LinearLayout love;
    public RelativeLayout cardsRelativeLayout;

    public blogViewHolder(View v) {
        super(v);
        titleTextView = (TextView) v.findViewById(R.id.titleOfBlog);
        Date=(TextView)v.findViewById(dateOfNews);
        cardsRelativeLayout=(RelativeLayout)v.findViewById(RelativeLayoutForNewsCard);
        sideDrawable=(ImageView)v.findViewById(MetarialColorPlate);
        share=(LinearLayout)v.findViewById(ShareClicker);
        save=(LinearLayout)v.findViewById(SaveClicker);
        love=(LinearLayout)v.findViewById(loveClicker);
        writer=(TextView)v.findViewById(R.id.TeachersName);
    }
}
