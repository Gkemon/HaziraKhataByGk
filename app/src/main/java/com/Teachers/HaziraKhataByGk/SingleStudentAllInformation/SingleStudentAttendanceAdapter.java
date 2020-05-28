package com.Teachers.HaziraKhataByGk.SingleStudentAllInformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.Listener.RecyclerItemClickListener;
import com.Teachers.HaziraKhataByGk.Model.AttendenceData;
import com.Teachers.HaziraKhataByGk.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by uy on 10/28/2017.
 */

public class SingleStudentAttendanceAdapter extends RecyclerView.Adapter<SingleStudentAttendanceViewHolder> {
    private ArrayList<AttendenceData> attendenceDataArrayList;
    private RecyclerItemClickListener recyclerItemClickListener;
    private Context context;


    public SingleStudentAttendanceAdapter(Context context, RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
        this.context = context;
    }

    public void setAttendenceDataArrayList(ArrayList<AttendenceData> attendenceDataArrayList) {
        this.attendenceDataArrayList = attendenceDataArrayList;
        notifyDataSetChanged();
    }

    @Override
    public SingleStudentAttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_student_attendence_list_date, parent, false);
        final SingleStudentAttendanceViewHolder holder = new SingleStudentAttendanceViewHolder(view);
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


    @Override
    public void onBindViewHolder(final SingleStudentAttendanceViewHolder holder, int position) {

        AttendenceData attendenceData = attendenceDataArrayList.get(position);
        String subject = "";
        if (!attendenceData.getSubject().equals("")) {
            subject = "(" + attendenceData.getSubject() + ")";
        }


        if (attendenceData.getStatus())
            holder.textView.setText(attendenceData.getDate() + subject + "  উপস্থিত");
        else {
            holder.textView.setText(attendenceData.getDate() + subject + "  অনুপস্থিত");
        }

        if (attendenceData.getStatus()) {
            Glide.with(context)
                    .load((Integer) R.drawable.present)
                    .into(holder.imageView);
        } else {
            Glide.with(context)
                    .load((Integer) R.drawable.absent)
                    .into(holder.imageView);
        }


    }

    @Override
    public int getItemCount() {
        return attendenceDataArrayList == null ? 0 : attendenceDataArrayList.size();
    }
}

class SingleStudentAttendanceViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView imageView;

    public SingleStudentAttendanceViewHolder(View v) {
        super(v);
        textView = v.findViewById(R.id.SingleStudentAttendeceDateList);
        imageView = v.findViewById(R.id.PresentOrAbsent);
    }


}
