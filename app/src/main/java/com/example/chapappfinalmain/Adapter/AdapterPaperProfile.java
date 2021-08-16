package com.example.chapappfinalmain.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.chapappfinalmain.fragment.FragmentProliteWork.PrivateFragment;
import com.example.chapappfinalmain.fragment.FragmentProliteWork.PublishFragment;

import org.jetbrains.annotations.NotNull;

public class AdapterPaperProfile extends FragmentStatePagerAdapter {
    private int count;

    public AdapterPaperProfile(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            PrivateFragment privateFragment = new PrivateFragment();
            return privateFragment;
        }else {
            PublishFragment publishFragment = new PublishFragment();
            return publishFragment;
        }
    }


    @Override
    public int getCount() {
        return 2;
    }
}
