package com.Teachers.HaziraKhataByGk;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Teachers.HaziraKhataByGk.Adapter.FeesAdapter;

import java.util.ArrayList;

public class FeesAcitvity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //HIDING NOTIFICATION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fees_acitvity);
        recyclerView = findViewById(R.id.toDoRecyclerViewForExamFees);
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
        FeesAdapter adapter = new FeesAdapter(this);
        adapter.addAll(arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
