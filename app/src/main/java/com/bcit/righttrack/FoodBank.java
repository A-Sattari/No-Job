package com.bcit.righttrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class FoodBank extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View foodBankView;
    private MapView mapView;
    private GoogleMap gMap;
    private Marker previousMarker = null;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView fbName;
    private Button phoneButton;
    private Button directionButton;

    private OnFragmentInteractionListener mListener;

    public FoodBank() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodBank.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodBank newInstance(String param1, String param2) {
        FoodBank fragment = new FoodBank();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        foodBankView = inflater.inflate(R.layout.food_bank, container, false);
        mapView = foodBankView.findViewById(R.id.fbMapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        // Buttons
        directionButton = foodBankView.findViewById(R.id.fbDirection);
        phoneButton = foodBankView.findViewById(R.id.fbPhone);

        // Bottom Sheet configuration
        final View bottomSheet = foodBankView.findViewById(R.id.fb_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
        });
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        fbName = foodBankView.findViewById(R.id.fbName);

        // Google Map View Configuration
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                gMap = mMap;

                // For dropping a marker at a point on the Map
                LatLng location = new LatLng(49.203496743701606, -122.90963686974206);
                LatLng location2 = new LatLng(49.22491835146481, -122.91048369358666);
                LatLng location3 = new LatLng(49.21491835146481, -122.91048369358666);
                gMap.addMarker(new MarkerOptions().position(location).title("Holy Trinity Anglican Cathedral - Breakfast Program").snippet("(604)521-2511"));
                gMap.addMarker(new MarkerOptions().position(location2).title("Marker2 Title").snippet("(604)521-2511"));
                gMap.addMarker(new MarkerOptions().position(location3).title("Marker3 Title").snippet("(604)517-6168"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(12).build();
                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                // Marker click listener
                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.hideInfoWindow();

                        // If the marker wasn't click, change its color
                        if (previousMarker == null) {
                            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                        } else { // If the marker was clicked before, then resets it color to red
                            previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        }
                        previousMarker = marker;

                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        fbName.setText(marker.getTitle());

                        // Buttons
                        directionButtonListener(directionButton, 49.203496743701606, -122.90963686974206);
                        phoneButtonListener(phoneButton, marker.getSnippet());

                        return true;
                    }
                });

                // Map click listener
                gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                        bottomSheetBehavior.setHideable(true);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        // Resets the marker color when user clicks on the map
                        if (previousMarker != null)
                            previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }
                });
            }

        });

        return foodBankView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    /**
     * Direction button listener.
     * @param direction button
     */
    private void directionButtonListener(final Button direction, final double latY, final double longX) {
        final Uri geoUri = Uri.parse("google.navigation:q=" + latY + "," + longX);

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Opens Google Map in navigation mode
                Intent gMapDir = new Intent(Intent.ACTION_VIEW, geoUri);
                startActivity(gMapDir);

                // Checks whether user's device has an app that can respond to this intent
                if (gMapDir.resolveActivity(getActivity().getPackageManager()) != null) {
                    (getActivity().getApplicationContext()).startActivity(gMapDir);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Google Map is not installed on your device.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Phone button listener.
     * @param phone button
     */
    private void phoneButtonListener(final Button phone, final String phoneNumber) {
        final Uri phoneUri = Uri.parse("tel:" + phoneNumber);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent = new Intent(Intent.ACTION_VIEW, phoneUri);
                startActivity(dialIntent);
            }
        });
    }
}
