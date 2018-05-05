package com.bcit.righttrack;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Displays the list of organizations that help people to explore career options and improve their skills.
 * @author Alireza Sattari
 */
public class EmploymentServices extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View mainView;
    private OnFragmentInteractionListener mListener;
    private RecyclerView rcView;
    private RecyclerView.Adapter rcAdapter;
    private RecyclerView.LayoutManager rcLayoutManager;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EmploymentServices.
     */
    /*public static EmploymentServices newInstance(String param1, String param2) {
        EmploymentServices fragment = new EmploymentServices();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*****/
//        ArrayList<String> website = new ArrayList<>();   website.add("http://www.google.com"); website.add("http://www.microsoft.com");
//        ArrayList<String> latY = new ArrayList<>(); latY.add("49.202471261180655"); latY.add("49.203235896852874");
//        ArrayList<String> longX = new ArrayList<>(); longX.add("-122.90995207229413"); longX.add("-122.94926011127286");
        /*****/
        View rootView = inflater.inflate(R.layout.employment_services_page, container, false);

        // Gets the recycler view created in the employment_services_page.xml
        rcView = rootView.findViewById(R.id.recyclerView);
        rcLayoutManager = new LinearLayoutManager(getActivity());

        rcAdapter = new EsRecyclerViewAdapter(getContext(),
                MainActivity.websiteArrayEmployment,
                MainActivity.coordinateLatArrayEmployment,
                MainActivity.coordinateLngArrayEmployment,
                MainActivity.nameArrayEmployment,
                MainActivity.descriptionArrayEmployment); // Custom Recycler Adaptor Class

        rcView.setHasFixedSize(true);
        rcView.setLayoutManager(rcLayoutManager);
        rcView.setAdapter(rcAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            //mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
