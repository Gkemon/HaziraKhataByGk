package com.Teachers.HaziraKhataByGk.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.Firebase.FirebaseCaller;
import com.Teachers.HaziraKhataByGk.HelperClassess.UtilsCommon;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.ClassItem;
import com.Teachers.HaziraKhataByGk.R;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

/**
 * Created by wim on 5/1/16.
 */
public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ContactHolder> {

    private ArrayList<ClassItem> classitemList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public ClassListAdapter(Context context) {
        this.context = context;
        this.classitemList = new ArrayList<>();
    }

    private void add(ClassItem item) {
        classitemList.add(item);
        notifyItemInserted(classitemList.size() - 1);
    }

    public void addAll(ArrayList<ClassItem> classitemList) {
        if (classitemList != null) {
            for (ClassItem classitem : classitemList) {
                add(classitem);
            }
        }
    }

    public void remove(ClassItem item) {
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

    public ClassItem getItem(int position) {
        return classitemList.get(position);
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_room, parent, false);

        final ContactHolder contactHolder = new ContactHolder(view);

        contactHolder.itemView.setOnClickListener(v -> {
            int adapterPos = contactHolder.getAdapterPosition();
            if (adapterPos != RecyclerView.NO_POSITION) {
                if (recyclerItemClickListener != null) {
                    recyclerItemClickListener.onItemClick(adapterPos, contactHolder.itemView);
                }
            }
        });
        contactHolder.itemView.setOnLongClickListener(v -> {
            int adapterPos = contactHolder.getAdapterPosition();
            if (adapterPos != RecyclerView.NO_POSITION) {
                if (recyclerItemClickListener != null) {
                    recyclerItemClickListener.onItemLongPressed(adapterPos, contactHolder.itemView);
                }
            }
            return false;
        });

        return contactHolder;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        if (classitemList == null) {

        } else {
            final ClassItem classitem = classitemList.get(position);

            if (classitem != null && classitem.getName() != null) {
                final Resources res = context.getResources();
                final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);


                try {

                    //FOR TEXTDRAWABLE IMAGE CREATING
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    int color = generator.getRandomColor();

                    if (classitem.getName().isEmpty()) {

                        FirebaseCaller.getFirebaseDatabase()
                                .child("Users")
                                .child(FirebaseCaller
                                        .getUserID())
                                .child("Class")
                                .child(classitem.getName() + classitem.getSection())
                                .removeValue();
                        classitem.setName("No class name");
                    }

                    TextDrawable myDrawable = TextDrawable.builder().beginConfig().height(tileSize).width(tileSize)
                            .textColor(Color.WHITE)
                            .useFont(Typeface.DEFAULT)
                            .toUpperCase()
                            .endConfig()
                            .buildRoundRect(classitem.getName().substring(0, 1), color, 12);

                    holder.thumb.setImageDrawable(myDrawable);
                    String className = "ক্লাস: " + classitem.getName();
                    String sectionName = "";

                    if (classitem.getSection().equals("")) {
                        sectionName = "";
                    } else {
                        sectionName = "সেকশন/সেশন: " + classitem.getSection();
                    }


                    if (classitem.getName() == null) { //Quick fix
                        FirebaseCaller.getFirebaseDatabase().child("Users")
                                .child(FirebaseCaller.getUserID())
                                .child("Class")
                                .child("nullnull").removeValue();

                        FirebaseCaller.getFirebaseDatabase().child("Users")
                                .child(FirebaseCaller.getUserID())
                                .child("Class")
                                .child("null").removeValue();
                    }


                    if (classitem.getName() != null && !classitem.getName().isEmpty())
                        holder.name.setText(className);

                    if (UtilsCommon.isValideString(sectionName)){
                        holder.section.setText(sectionName);
                        holder.section.setVisibility(View.VISIBLE);
                    }
                    else holder.section.setVisibility(View.GONE);

                } catch (Exception e) {
                    Toast.makeText(context, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show();
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
        TextView section;

        public ContactHolder(View itemView) {
            super(itemView);

            thumb = itemView.findViewById(R.id.thumb);
            name = itemView.findViewById(R.id.class_name);
            section = itemView.findViewById(R.id.section_name);

        }
    }
}
