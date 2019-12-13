package com.example.berita_09031281722080;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class CategoryAdapter extends FragmentStatePagerAdapter {
    /** Context of the app */
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BeritaFragment();
        } else if (position == 1) {
            return new NewsFragment();
        } else {
            return new InputFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.indo);
        } else if (position == 1) {
            return mContext.getString(R.string.intern);
        } else {
            return mContext.getString(R.string.input);
        }
    }
}