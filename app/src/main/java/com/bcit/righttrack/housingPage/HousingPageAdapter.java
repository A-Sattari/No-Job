package com.bcit.righttrack.housingPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * An adapter class to be used by ViewPager for the housing page.
 */
class HousingPageAdapter extends FragmentPagerAdapter {

    HousingPageAdapter(FragmentManager fragmentManager) { super(fragmentManager); }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HousingList();
            case 1:
                return new HousingMap();
        }

        return null;
    }

    @Override
    public int getCount() { return 2; }
}
