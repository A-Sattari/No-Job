package com.bcit.righttrack.housingPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.bcit.righttrack.R;

/**
 * Contains a list of houses
 */
public class HousingList extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;


    // Required empty constructor
    public HousingList() {}

    /*public static HousingList newInstance(int columnCount) {
        HousingList fragment = new HousingList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");*/
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.housing_list, container, false);

        filterDropdownSetup(root);

        RecyclerView recyclerView =
            root.findViewById(R.id.housingRcView);   // Getting recycler view

        RecyclerView.LayoutManager layoutManager =
            new LinearLayoutManager(getActivity()); // Linear layout manager

        RecyclerView.Adapter rcAdapter =
            new HousingListRecyclerViewAdapter();       // Custom adapter

        // Divider line between items in the list
        DividerItemDecoration dividerItem = new DividerItemDecoration(recyclerView.getContext(),1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rcAdapter);
        recyclerView.addItemDecoration(dividerItem);

        return root;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Setting up the city dropdown filter.
     * @param root the main view of housing_list.xml
     */
    private void filterDropdownSetup(final View root) {
        String[] cityName = {"Vancouver", "Burnaby", "North Vancouver", "Langley", "Richmond"};

        Spinner cityDropdown = root.findViewById(R.id.cityDropdown);    // Getting the spinner

        // Array adapter that contains name of the cities and specifies the layout to use when the list of choices appears
        ArrayAdapter<String> dropdownItems = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, cityName);

        cityDropdown.setAdapter(dropdownItems);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Uri uri);
    }
}
