package com.example.fecultypannel.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fecultypannel.fragments.AddNoticeFragment;
import com.example.fecultypannel.fragments.ShowNoticeFragment;

public class NoticeFragmentAdapter extends FragmentPagerAdapter {
    int tabCount;

    public NoticeFragmentAdapter(FragmentManager supportFragmentManager, int tabCount) {
        super(supportFragmentManager);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AddNoticeFragment addNoticeFragment = new AddNoticeFragment();
                return addNoticeFragment;
            case 1:
                ShowNoticeFragment showNoticeFragment = new ShowNoticeFragment();
                return showNoticeFragment;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Add Notice";
                return title;
            case 1:
                title = "Show Notice";
                return title;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
