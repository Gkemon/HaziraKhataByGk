package com.Teachers.HaziraKhataByGk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.adapter.FeesAdapter;

import java.util.ArrayList;

public class FeesAcitvity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_acitvity);
        recyclerView=findViewById(R.id.toDoRecyclerViewForExamFees);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        arrayList = new ArrayList<>();
        arrayList.add("emon");
        arrayList.add("saimon");
        arrayList.add("rasel");
        arrayList.add("rafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("emon");
        arrayList.add("saimon");
        arrayList.add("rasel");
        arrayList.add("rafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("emon");
        arrayList.add("saimon");
        arrayList.add("rasel");
        arrayList.add("rafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("emon");
        arrayList.add("saimon");
        arrayList.add("rasel");
        arrayList.add("rafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("emon");
        arrayList.add("saimon");
        arrayList.add("rasel");
        arrayList.add("rafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("emon");
        arrayList.add("saimon");
        arrayList.add("rasel");
        arrayList.add("rafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        arrayList.add("shafik");
        FeesAdapter adapter=new FeesAdapter(this);
        adapter.addAll(arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
