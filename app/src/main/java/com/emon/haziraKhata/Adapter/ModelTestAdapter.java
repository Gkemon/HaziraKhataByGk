package com.emon.haziraKhata.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.emon.haziraKhata.Listener.RecyclerItemClickListener;
import com.emon.haziraKhata.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GK on 11/26/2017.
 */

public class ModelTestAdapter extends RecyclerView.Adapter<ModelTestAdapter.questionHolder> {
    public static List<String> QuestonList;
    private Context context;
    // public static SparseBooleanArray selectedItems;
    private RecyclerItemClickListener recyclerItemClickListener;

    public ModelTestAdapter(Context context) {
        this.context = context;
        this.QuestonList = new ArrayList<>();
//        selectedItems=new SparseBooleanArray();
    }

    private void add(String item) {
        QuestonList.add(item);
        notifyItemInserted(QuestonList.size() - 1);
    }

    public void addAll(List<String> Notelist) {
        for (String Notes : Notelist) {
            add(Notes);
        }
    }

    public void remove(String item) {
        int position = QuestonList.indexOf(item);
        if (position > -1) {
            QuestonList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public String getItem(int position) {
        return QuestonList.get(position);
    }


    @Override
    public questionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_test_question, parent, false);

        final questionHolder questionHolder = new questionHolder(view);
        questionHolder.itemView.findViewById(R.id.question_of_model_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = questionHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, questionHolder.itemView.findViewById(R.id.question_of_model_test));
                    }
                }
            }
        });
        return questionHolder;
    }


    @Override
    public void onBindViewHolder(questionHolder holder, int position) {
        final String question = QuestonList.get(position);
        //TODO: BE AWARE TO SET CLICK LISTENER TO RIGHT ID;
//        String previousAnswer=user_answer.get(position);
//        int previousAnswerCheckItem;
//        if(previousAnswer.equals("5")){
//            previousAnswerCheckItem=4;
//        }
//        else if(previousAnswer.equals("1")){
//            previousAnswerCheckItem=0;
//        }
//        else if(previousAnswer.equals("2")){
//            previousAnswerCheckItem=1;
//        }
//        else if(previousAnswer.equals("3")){
//            previousAnswerCheckItem=2;
//        }
//        else if(previousAnswer.equals("4")){
//            previousAnswerCheckItem=3;
//        }
//        else previousAnswerCheckItem=4;
//
//        if(previousAnswerCheckItem>=0&&previousAnswerCheckItem<=3){
//            holder.question.setBackgroundColor(0xb71c1c);//orange
//        }
        holder.question.setText(question);
        holder.question.setGravity(Gravity.START);
    }


    @Override
    public int getItemCount() {
        return QuestonList.size();
    }


    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }


    public static class questionHolder extends RecyclerView.ViewHolder {
        Button question;
        LinearLayout linearLayout;

        public questionHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.main);
            question = (Button) itemView.findViewById(R.id.question_of_model_test);
            question.setGravity(Gravity.START);
        }
    }
}
