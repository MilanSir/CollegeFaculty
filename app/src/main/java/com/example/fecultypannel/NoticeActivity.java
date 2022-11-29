package com.example.fecultypannel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.fecultypannel.adapter.NoticeFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class NoticeActivity extends AppCompatActivity {

    Toolbar notice_toolbar;
    TabLayout notice_tablayout;
    ViewPager noticeViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        notice_tablayout =findViewById(R.id.notice_tablayout);
        noticeViewPager=findViewById(R.id.noticeViewPager);

        NoticeFragmentAdapter noticeFragmentAdapter = new NoticeFragmentAdapter(getSupportFragmentManager(),notice_tablayout.getTabCount());
        noticeViewPager.setAdapter(noticeFragmentAdapter);

        notice_tablayout.setupWithViewPager(noticeViewPager);
    }
}