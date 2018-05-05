package com.bcit.righttrack.housingPage;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bcit.righttrack.R;

public class HousingPage extends Fragment {

    // Required empty public constructor
    public HousingPage() {}

    @Override
    public void onAttach(Context context) { super.onAttach(context); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.housing_page, container, false);
        ViewPager viewPager; //The pager widget, which handles animation and allows swiping horizontally to access previous and next page
        HousingPageAdapter adapter; // Provides the pages to the view pager
        TabLayout housingTabs = root.findViewById(R.id.housingTabLayout);

        viewPager = root.findViewById(R.id.housingViewPager);   // Finding the view pager
        adapter = new HousingPageAdapter(getFragmentManager()); // Initializing adapter object

        viewPager.setAdapter(adapter);              // Connect the adapter with the view pager
        tabLayoutListener(housingTabs, viewPager);  // Tabs listener

        return root;    // Inflate the layout for this fragment
    }

    @Override
    public void onDetach() { super.onDetach(); }

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     * @return A new instance of fragment HousingPage.
     */
    public static HousingPage newInstance(String param1, String param2) {
        HousingPage fragment = new HousingPage();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Housing tabs listeners
     * @param housingTabs the TabLayout object created in onCreateView()
     */
    private void tabLayoutListener(final TabLayout housingTabs, final ViewPager viewPager) {

        housingTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Sends the position of the tab to the adapter then Adapter returns the desired class
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}