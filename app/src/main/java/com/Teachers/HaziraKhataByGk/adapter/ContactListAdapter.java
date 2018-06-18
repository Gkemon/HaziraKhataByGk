package com.Teachers.HaziraKhataByGk.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.model.class_item;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

/**
 * Created by wim on 5/1/16.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactHolder>{

    private ArrayList<class_item> classitemList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public ContactListAdapter(Context context) {
        this.context = context;
        this.classitemList = new ArrayList<>();
    }

    private void add(class_item item) {
        classitemList.add(item);
        notifyItemInserted(classitemList.size() - 1);
    }

    public void addAll(ArrayList<class_item> classitemList) {
        if(classitemList!=null) {
            for (class_item classitem : classitemList) {
                add(classitem);
            }
        }
    }

    public void remove(class_item item) {
        int position = classitemList.indexOf(item);
        if (position > -1) {
            classitemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public class_item getItem(int position) {
        return classitemList.get(position);
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact_item, parent, false);

        final ContactHolder contactHolder = new ContactHolder(view);

        contactHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = contactHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, contactHolder.itemView);
                    }
                }
            }

        });
        contactHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int adapterPos = contactHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemLongPressed(adapterPos, contactHolder.itemView);
                    }
                }
                return false;
            }
        });

        return contactHolder;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        if(classitemList==null){

        }
        else {
            final class_item classitem = classitemList.get(position);

            if (classitem != null) {
                final Resources res = context.getResources();
                final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);


                try {
                    //FOR TEXTDRAWABLE IMAGE CREATING
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color = generator.getRandomColor();

                    TextDrawable myDrawable = TextDrawable.builder().beginConfig().height(tileSize).width(tileSize)
                            .textColor(Color.WHITE)
                            .useFont(Typeface.DEFAULT)
                            .toUpperCase()
                            .endConfig()
                            .buildRoundRect(classitem.getName().substring(0,1), color,12);

                    holder.thumb.setImageDrawable(myDrawable);
                    String className="ক্লাস: "+classitem.getName();
                    String sectionName="";

                    if(classitem.getSection().equals("")){
                        sectionName="";
                    }
                    else {
                        sectionName="সেকশন/সেশন: "+classitem.getSection();
                    }


                    holder.name.setText(className);
                    //holder.name.setTextColor(color);
                    holder.phone.setText(sectionName);

                }catch (Exception e){
                    Toast.makeText(context,"ERROR "+e.getMessage(),Toast.LENGTH_LONG).show();

                }


            }
        }
    }

    @Override
    public int getItemCount() {
        return classitemList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    static class ContactHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView name;
        TextView phone;

        public ContactHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);

        }
    }
}
