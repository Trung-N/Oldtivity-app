package com.example.oldivity;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;

public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){

            case 0:
                GroupsFragment groupsFragment = new GroupsFragment();
                return groupsFragment;

                default:
                    return null;
        }
    }

    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "Groups";

            default:
                return null;
        }

    }
}
