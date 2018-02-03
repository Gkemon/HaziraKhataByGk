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
import com.Teachers.HaziraKhataByGk.model.student;
import com.Teachers.HaziraKhataByGk.widget.LetterTile;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

public class studentListAdapter extends RecyclerView.Adapter<studentListAdapter.StudentHolder>{

    public static List<student> studentList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public studentListAdapter(Context context) {
        this.context = context;
        this.studentList = new ArrayList<>();
    }

    private void add(student item) {
        studentList.add(item);
        notifyItemInserted(studentList.size() - 1);
    }

    public void addAll(List<student> studentList) {
        for (student student : studentList) {
            add(student);
        }
    }

    public void remove(student item) {
        int position = studentList.indexOf(item);
        if (position > -1) {
            studentList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public student getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student, parent, false);

        final StudentHolder StudentHolder = new StudentHolder(view);

        StudentHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = StudentHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, StudentHolder.itemView);
                    }
                }
            }
        });

        return StudentHolder;
    }

    @Override
    public void onBindViewHolder(StudentHolder holder, int position) {
        final student student = studentList.get(position);

        final Resources res = context.getResources();
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

        LetterTile letterTile = new LetterTile(context);

        //FOR BITMAP GENERATE
//        Bitmap letterBitmap = letterTile.getLetterTile(student.getStudentName(),
//                String.valueOf(student.getId()), tileSize, tileSize);

        //FOR TEXTDRAWABLE IMAGE CREATING
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable myDrawable = TextDrawable.builder().beginConfig().height(tileSize).width(tileSize)
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRoundRect(student.getStudentName().substring(0,1), color,10);

        holder.thumb.setImageDrawable(myDrawable);
        String name="নাম: "+student.getStudentName(),roll="রোল: "+student.getId();
        holder.name.setText(name);
        holder.phone.setText(roll);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    static class StudentHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView name;
        TextView phone;

        public StudentHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.thumbOfstudent);
            name = (TextView) itemView.findViewById(R.id.nameOfstudent);
            phone = (TextView) itemView.findViewById(R.id.phoneOfstudent);

        }
    }
}
