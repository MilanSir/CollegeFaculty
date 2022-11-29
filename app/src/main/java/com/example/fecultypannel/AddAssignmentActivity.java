package com.example.fecultypannel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.fecultypannel.adapter.AssignmentFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class AddAssignmentActivity extends AppCompatActivity {
    TabLayout tablayout;
    ViewPager assignmentViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        tablayout = findViewById(R.id.tablayout);
        assignmentViewPager = findViewById(R.id.assignmentViewPager);

        AssignmentFragmentAdapter assignmentFragmentAdapter = new AssignmentFragmentAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        assignmentViewPager.setAdapter(assignmentFragmentAdapter);

        tablayout.setupWithViewPager(assignmentViewPager);
    }
}