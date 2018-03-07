package com.bcit.righttrack;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @version pre-alpha
 * @author Group JAD
 */
public class MainActivity
        extends AppCompatActivity
        implements FoodBank.OnFragmentInteractionListener,
                   HomePage.OnFragmentInteractionListener,
                   HousingPage.OnFragmentInteractionListener,
                   EmploymentServices.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getName();

    // Location of New West Open Datasets
    public static String FOOD_BANK_URL = "http://opendata.newwestcity.ca/downloads/food/FOOD_PROGRAMS_AND_SERVICES.json";
    public static String EMPLOYMENT_SERVICES_URL = "http://opendata.newwestcity.ca/downloads/employment/EMPLOYMENT_AND_JOB_TRAINING.json";

    // Declaring Fragment objects
    private FragmentTransaction ft;
    private Fragment currentFragment;
    public static ArrayList<String> nameArrayFoodBank;
    public static ArrayList<String> descriptionArrayFoodBank;
    public static ArrayList<String> websiteArrayFoodBank;
    public static ArrayList<String> addressArrayFoodBank;

    public static ArrayList<String> nameArrayEmployment;
    public static ArrayList<String> descriptionArrayEmployment;
    public static ArrayList<String> websiteArrayEmployment;
    public static ArrayList<String> addressArrayEmployment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**************************************/

        // Initializing fragment manager
        ft = getSupportFragmentManager().beginTransaction();

        // Initializing arrays containing food bank information
        nameArrayFoodBank = new ArrayList<String>();
        descriptionArrayFoodBank = new ArrayList<String>();
        websiteArrayFoodBank = new ArrayList<String>();
        addressArrayFoodBank = new ArrayList<String>();

        // Initializing arrays containing employment services information
        nameArrayEmployment = new ArrayList<String>();
        descriptionArrayEmployment = new ArrayList<String>();
        websiteArrayEmployment = new ArrayList<String>();
        addressArrayEmployment = new ArrayList<String>();

        // Setting the current fragment to the Home Page fragment upon startup
        currentFragment = new HomePage();
        ft.replace(R.id.container, currentFragment);
        ft.commit();

        BottomNavigationView bottomNavBar = findViewById(R.id.navigation);
        // Creates a listener for the bottom navigation bar
        bottomNavBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        downloadFoodBankInfo();

        // Create and run async task, and get information from open datasets
        DownloadAsyncTask async = new DownloadAsyncTask();
        async.execute(FOOD_BANK_URL, EMPLOYMENT_SERVICES_URL);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                // Switch to Home Fragment when home tab is pressed
                case R.id.homeTab:
                    switchToHomeFragment();
                    return true;

                // Switch to ES Fragment when training tab is pressed
                case R.id.esTab:
                    switchToESFragment();
                    return true;

                // Switch to Housing Fragment when housing tab is pressed
                case R.id.housingTab:
                    switchToHousingFragment();
                    return true;

                // Switch to Food Bank fragment when food tab is pressed
                case R.id.foodBankTab:
                    switchToFoodFragment();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // Switches view to home page
    public void switchToHomeFragment() {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new HomePage()).commit();
    }

    // Switches view to ES page
    public void switchToESFragment() {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new EmploymentServices()).commit();
    }

    // Switches view to housing page
    public void switchToHousingFragment() {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new HousingPage()).commit();
    }

    // Switches view to food bank page
    public void switchToFoodFragment() {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new FoodBank()).commit();
    }

    // Async task for downloading open data for FoodBank and Employment pages
    private class DownloadAsyncTask extends AsyncTask <String, Integer, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            int i = 0;
            for (String url: strings) {
                cardDownload(url, i);
                i++;
            }
            return null;
        }

//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            TextView v = findViewById(R.id.orgName1);
//            v.setText(nameArrayFoodBank.get(0));
//        }
    }

    // Responsible for extracting JSON infor for card views (Food Bank and Employment pages)
    public void cardDownload(String pUrl, final int sourceData) {
        Ion.with(MainActivity.this).
                load(pUrl).
                asJsonObject().
                setCallback(
                        new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e == null) {
                                    // Call FoodBank download method on first iteration
                                    if (sourceData == 0) {
                                        downloadFoodBankInfoSuccess(result);
                                    // Call Employment download on second iteration
                                    } else if (sourceData == 1) {
                                        downloadEmploymentInfoSuccess(result);
                                    }
                                } else {
                                    downloadError(e);
                                }
                            }
                        }
                );
    }

    // Extracts food bank info and populates appropriate arrays
    private void downloadFoodBankInfoSuccess(final JsonObject jsonObject) {
        final JsonArray places;
        places = jsonObject.getAsJsonArray("features");

        for (final JsonElement element: places) {
            final JsonObject feature;
            final JsonObject properties;
            final String name;
            final String description;
            final String website;
            final String address;

            feature = element.getAsJsonObject();
            properties = feature.getAsJsonObject("properties");
            name        = properties.get("Name").getAsString();
            description = properties.get("Description").getAsString();
            website = properties.get("Website").getAsString().toLowerCase();
            address = properties.get("Location").getAsString();

            nameArrayFoodBank.add(name);
            descriptionArrayFoodBank.add(description);
            websiteArrayFoodBank.add(website);
            addressArrayFoodBank.add(address);
            Log.wtf(TAG, nameArrayFoodBank.get(0));
        }
    }

    // Extracts employment info and populates appropriate arrays
    private void downloadEmploymentInfoSuccess(final JsonObject jsonObject) {
        final JsonArray places;
        places = jsonObject.getAsJsonArray("features");

        for (final JsonElement element: places) {
            final JsonObject feature;
            final JsonObject properties;
            final String name;
            final String description;
            final String website;
            final String address;

            feature = element.getAsJsonObject();
            properties = feature.getAsJsonObject("properties");
            name        = properties.get("Name").getAsString();
            description = properties.get("Description").getAsString();
            website = properties.get("Website").getAsString();
            address = properties.get("Location").getAsString();

            nameArrayEmployment.add(name);
            descriptionArrayEmployment.add(description);
            websiteArrayEmployment.add(website);
            addressArrayEmployment.add(address);
            Log.wtf(TAG, name);
        }
    }

    // Generic error handling message for failed open data reads
    private void downloadError(final Exception ex)
    {
        Log.wtf(TAG, ex);
    }
}
