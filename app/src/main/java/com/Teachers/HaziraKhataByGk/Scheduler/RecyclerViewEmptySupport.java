package com.Teachers.HaziraKhataByGk.Scheduler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RecyclerViewEmptySupport extends RecyclerView {
    private View emptyView;



    public RecyclerViewEmptySupport(Context context) {
        super(context);
    }



    public RecyclerViewEmptySupport(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewEmptySupport(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if(adapter!=null && emptyView!=null){
            if(adapter.getItemCount()==0){
                Log.d("eee","ShowEmpty");
                emptyView.setVisibility(VISIBLE);
                RecyclerViewEmptySupport.this.setVisibility(GONE);
            }
            else{
                Log.d("eee","NOT");
                emptyView.setVisibility(GONE);
                RecyclerViewEmptySupport.this.setVisibility(VISIBLE);
            }
        }
        else Log.d("eee","adapers item = null");
    }

    public void setEmptyView(View v){
        emptyView = v;
        Log.d("eee","Image saved");
    }
}
