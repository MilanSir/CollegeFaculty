package com.example.fecultypannel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fecultypannel.adapter.ResultShowSubjectSemWiseAdapter;

import java.util.ArrayList;

public class ResultShowSubjectActivity extends AppCompatActivity {

    RecyclerView result_show_subject_recycl;
    String[] subs;
    public static String result_semester;
    ArrayList result_show_subject_arraylist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_show_subject);

        result_show_subject_recycl = findViewById(R.id.result_show_subject_recycl);
        result_show_subject_recycl.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));

        subs = getIntent().getStringArrayExtra("subs");
        result_semester = getIntent().getStringExtra("result_semester");

        for (int i = 0; i < subs.length; i++) {
            if (subs[i].startsWith(result_semester)) {
                result_show_subject_arraylist.add(subs[i]);
            }
        }

        /*Adapter_F_Subject_list adapterFSubjectList = new Adapter_F_Subject_list(ResultShowSubjectActivity.this, selectedSemSubject, "addResultShowSem");
        result_show_subject_recycl.setAdapter(adapterFSubjectList);*/
        ResultShowSubjectSemWiseAdapter resultShowSubjectSemWiseAdapter = new ResultShowSubjectSemWiseAdapter(ResultShowSubjectActivity.this, result_show_subject_arraylist);
        result_show_subject_recycl.setAdapter(resultShowSubjectSemWiseAdapter);
    }
}