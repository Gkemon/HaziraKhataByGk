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

import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.model.Notes;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by uy on 9/7/2017.
 */

public class noteListAdapter extends RecyclerView.Adapter<noteListAdapter.notesHolder> {
    public static List<Notes> Notelist;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public noteListAdapter(Context context) {
        this.context = context;
        this.Notelist = new ArrayList<>();
    }
    private void add(Notes item) {
        Notelist.add(item);
        notifyItemInserted(Notelist.size() - 1);
    }
    public void addAll(List<Notes> Notelist) {
        for (Notes Notes : Notelist) {
            add(Notes);
        }
    }

    public void remove(Notes item) {
        int position = Notelist.indexOf(item);
        if (position > -1) {
            Notelist.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }
    public Notes getItem(int position) {
        return Notelist.get(position);
    }
    @Override
    public notesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes, parent, false);

        final notesHolder notesHolder = new notesHolder(view);

        notesHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = notesHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, notesHolder.itemView);
                    }
                }
            }
        });

        return notesHolder;
    }
    @Override
    public void onBindViewHolder(notesHolder holder, int position) {

        final Notes Notes = Notelist.get(position);
        final Resources res = context.getResources();
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

          //for bitmap generate
//        LetterTile letterTile = new LetterTile(context);
//        Bitmap letterBitmap = letterTile.getLetterTile(Notes.getheading(),
//                Notes.getheading(), tileSize, tileSize);

        //FOR TEXTDRAWABLE IMAGE CREATING
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable myDrawable = TextDrawable.builder().beginConfig().height(tileSize).width(tileSize)
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(Notes.getheading().substring(0,1), color);
        holder.thumbOfNote.setImageDrawable(myDrawable);

//        String subjectName="শিরোনাম: "+Notes.getheading();
//        String content;
//        if(Notes.getContent().equals("")){
//            content="";
//        }
//        else {
//            content="বিষয়বস্তু: "+Notes.getContent();
//        }

        holder.TitleOfNotes.setText(Notes.getheading());
        holder.TitleOfNotes.setTextColor(color);
        holder.ContentOfNotes.setText(Notes.getContent());
    }
    @Override
    public int getItemCount() {
        return Notelist.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    static class notesHolder extends RecyclerView.ViewHolder {

        ImageView thumbOfNote;
        TextView TitleOfNotes;
        TextView ContentOfNotes;

        public notesHolder(View itemView) {
            super(itemView);

            thumbOfNote = (ImageView) itemView.findViewById(R.id.thumbOfNote);
            TitleOfNotes = (TextView) itemView.findViewById(R.id.TitleOfNotes);
            ContentOfNotes = (TextView) itemView.findViewById(R.id.ContentOfNotes);

        }
    }

}
