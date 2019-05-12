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
import com.Teachers.HaziraKhataByGk.Model.NewsItem;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.MainActivity;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import static com.Teachers.HaziraKhataByGk.R.id.ClickerForNews;
import static com.Teachers.HaziraKhataByGk.R.id.MetarialColorPlate;
import static com.Teachers.HaziraKhataByGk.R.id.RelativeLayoutForNewsCard;
import static com.Teachers.HaziraKhataByGk.R.id.SaveClicker;
import static com.Teachers.HaziraKhataByGk.R.id.ShareClicker;
import static com.Teachers.HaziraKhataByGk.R.id.dateOfNews;
import static com.Teachers.HaziraKhataByGk.R.id.loveClicker;
import static com.Teachers.HaziraKhataByGk.R.id.tv_card_main_3_title;

/**
 * Created by uy on 10/28/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<newsViewHolder> {
    private ArrayList<NewsItem> list;
    private RecyclerItemClickListener recyclerItemClickListener;
    public NewsAdapter(ArrayList<NewsItem> Data) {
        list = Data;
    }

    @Override
    public newsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_cards, parent, false);
        final  newsViewHolder holder = new newsViewHolder(view);
        //CLICK LISTENER
        holder.itemView.findViewById(ClickerForNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, holder.itemView.findViewById(ClickerForNews));
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
    public void onBindViewHolder(final newsViewHolder holder, int position) {
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
        if(UtilsCommon.isNewsBookmarked(list.get(position),holder.itemView.getContext())){
            savedIcon.setImageResource(R.drawable.ic_saved_icon);
        }
        if(UtilsCommon.isNewsLoved(holder.itemView.getContext(),list.get(position).getURL(),list.get(position).getHeading(),list.get(position).getDate())){
            lovedIcon.setImageResource(R.drawable.ic_love_icon);
        }

        holder.titleTextView.setText(list.get(position).getHeading());
        String date="তারিখ :"+list.get(position).getDate();
        holder.Date.setText(date);
        holder.sideDrawable.setImageDrawable(myDrawable);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}

 class newsViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public TextView Date;
    public ImageView sideDrawable;
    public LinearLayout share;
    public LinearLayout save;
    public LinearLayout love;
     public RelativeLayout cardsRelativeLayout;

    public newsViewHolder(View v) {
        super(v);
        titleTextView = (TextView) v.findViewById(tv_card_main_3_title);
        Date=(TextView)v.findViewById(dateOfNews);
        cardsRelativeLayout=(RelativeLayout)v.findViewById(RelativeLayoutForNewsCard);
        sideDrawable=(ImageView)v.findViewById(MetarialColorPlate);
        share=(LinearLayout)v.findViewById(ShareClicker);
        save=(LinearLayout)v.findViewById(SaveClicker);
        love=(LinearLayout)v.findViewById(loveClicker);
    }
}