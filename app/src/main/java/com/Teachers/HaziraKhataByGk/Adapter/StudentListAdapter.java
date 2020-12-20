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

import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.HelperClasses.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClasses.ViewUtils.UtilsView;
import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.Student;
import com.Teachers.HaziraKhataByGk.R;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentHolder> {

    public List<Student> studentList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public StudentListAdapter(Context context) {
        this.context = context;
        this.studentList = new ArrayList<>();
    }

    private void add(Student item) {
        studentList.add(item);
        notifyItemInserted(studentList.size() - 1);
    }

    public void addAll(List<Student> studentList) {
        for (Student student : studentList) {
            add(student);
        }
    }

    public void remove(Student item) {
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

    public Student getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);

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
        final Student student = studentList.get(position);

        final Resources res = context.getResources();
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);


        //FOR BITMAP GENERATE
//        Bitmap letterBitmap = letterTile.getLetterTile(Student.getStudentName(),
//                String.valueOf(Student.getId()), tileSize, tileSize);


        if (UtilsCommon.isValideString(student.getImageUrl()))
            Glide.with(context)
                    .load(student.getImageUrl())
                    .apply(UtilsView.getLoadingOptionForGlide(context))
                    .into(holder.thumb);
        else {
            //FOR TEXTDRAWABLE IMAGE CREATING
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getRandomColor();
            TextDrawable myDrawable = TextDrawable.builder().beginConfig().height(tileSize).width(tileSize)
                    .textColor(Color.WHITE)
                    .useFont(Typeface.DEFAULT)
                    .toUpperCase()
                    .endConfig()
                    .buildRoundRect(student.getStudentName().substring(0, 1), color, 10);
            holder.thumb.setImageDrawable(myDrawable);
        }


        String name = "নাম: " + student.getStudentName(), roll = "রোল: " + student.getId();
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
