package com.bcit.righttrack;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 *
 * @author Alireza Sattari
 */
public class FoodBank extends Fragment {

    private MapView mapView;
    private GoogleMap gMap;
    private Marker previousMarker = null;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView fbName;
    private Button phoneButton;
    private Button directionButton;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The container is the parent ViewGroup (from the activity's layout) in which your fragment layout is inserted.

        // Inflate the layout for this fragment
        final View foodBankView = inflater.inflate(R.layout.food_bank, container, false);
        final View bottomSheet = foodBankView.findViewById(R.id.fb_bottom_sheet);
        mapView = foodBankView.findViewById(R.id.fbMapView);
        fbName = foodBankView.findViewById(R.id.fbName);
        directionButton = foodBankView.findViewById(R.id.fbDirection);
        phoneButton = foodBankView.findViewById(R.id.fbPhone);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        // Google Map View Configuration
        setGoogleMapConfiguration();
        // Bottom Sheet configuration
        setBottomSheetBehavior(bottomSheet);

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
     * Defines the Google Map view configuration.
     * Calls the marker and map on click listener method.
     */
    private void setGoogleMapConfiguration() {

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                gMap = mMap;

                LatLng defaultViewPoint = new LatLng(MainActivity.coordinateArrayFoodBank.get(0)[1],
                        MainActivity.coordinateArrayFoodBank.get(0)[0]);

                // For dropping a marker at a point on the Map
                for (int i = 0; i < 25; i++) {
                    // Declare information for markers
                    double lng = MainActivity.coordinateArrayFoodBank.get(i)[0];
                    double lat = MainActivity.coordinateArrayFoodBank.get(i)[1];
                    String locationName = MainActivity.nameArrayFoodBank.get(i);
                    String locationPhoneNumber = MainActivity.phoneArrayFoodBank.get(i);

                    // Create marker and place on map
                    LatLng location = new LatLng(lat, lng);
                    gMap.addMarker(new MarkerOptions().position(location).title(locationName).snippet(locationPhoneNumber));
                }

                // Default zooming location
                CameraPosition cameraPosition = new CameraPosition.Builder().target(defaultViewPoint).zoom(12).build();
                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                // Marker click listener
                markerOnClickListener();
                // Map click listener
                mapOnClickListener();
            }
        });
    }

    /**
     * Changes the color of the markers and calls the bottom sheet buttons.
     */
    private void markerOnClickListener() {

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(final Marker marker) {
                marker.hideInfoWindow();    // Doesn't work because of return value

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

                /*
                * true if the listener has consumed the event (the default behavior should not occur); false otherwise
                * (the default behavior should occur). The default behavior is for the camera to move to the marker and an info window to appear.
                */
                return false;
            }
        });
    }

    /**
     * Resets the marker color and hides the bottom sheet.
     */
    private void mapOnClickListener() {

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                bottomSheetBehavior.setHideable(true);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                // Resets the marker color when user clicks on the map
                if (previousMarker != null) {
                    previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
            }
        });
    }

    /**
     * Configures the bottom sheet behavior
     * @param btSheetView bottom sheet view
     */
    private void setBottomSheetBehavior(View btSheetView) {

        bottomSheetBehavior = BottomSheetBehavior.from(btSheetView);
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
    }

    /**
     * Opens Google Map in navigation mode.
     * @param direction button
     */
    private void directionButtonListener(final Button direction, final double latY, final double longX) {
        final Uri geoUri = Uri.parse("google.navigation:q=" + latY + "," + longX);

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
     * Opens the phone's default dialer.
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
