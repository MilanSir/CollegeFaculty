package com.example.fecultypannel.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fecultypannel.fragments.AddAssignmentFragment;
import com.example.fecultypannel.fragments.ShowAssignmentFragment;

public class AssignmentFragmentAdapter extends FragmentPagerAdapter {

    int tabCount;

    public AssignmentFragmentAdapter(FragmentManager supportFragmentManager, int tabCount) {
        super(supportFragmentManager, tabCount);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AddAssignmentFragment addAssignmentFragment = new AddAssignmentFragment();
                return addAssignmentFragment;
            case 1:
                ShowAssignmentFragment showAssignmentFragment = new ShowAssignmentFragment();
                return showAssignmentFragment;
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
                title = "Add Assignment";
                return title;
            case 1:
                title = "Show Assignment";
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
