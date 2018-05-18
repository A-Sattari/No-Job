package com.bcit.righttrack;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import com.bcit.righttrack.housingPage.HousingPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 *
 * @version pre-alpha
 * @author Group JAD
 */
public class MainActivity extends AppCompatActivity
        implements FoodBank.OnFragmentInteractionListener,
                   HomePage.OnFragmentInteractionListener,
                   HousingPage.OnFragmentInteractionListener,
                   EmploymentServices.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getName();

    // Location of New West Open Datasets
    public static String FOOD_BANK_URL = "http://opendata.newwestcity.ca/downloads/food/FOOD_PROGRAMS_AND_SERVICES.json";
    public static String EMPLOYMENT_SERVICES_URL = "http://opendata.newwestcity.ca/downloads/employment/EMPLOYMENT_AND_JOB_TRAINING.json";
    public static String HOUSING_URL = "http://opendata.newwestcity.ca/downloads/nonmarket-and-coop-housing/NONMARKET_AND_COOP_HOUSING.json";
    // Declaring Fragment objects
    private FragmentTransaction ft;
    private Fragment currentFragment;

    // Arrays containing Food Bank Data
    public static ArrayList<String> nameArrayFoodBank;
    public static ArrayList<String> descriptionArrayFoodBank;
    public static ArrayList<String> websiteArrayFoodBank;
    public static ArrayList<String> addressArrayFoodBank;
    public static ArrayList<String> phoneArrayFoodBank;
    public static ArrayList<double[]> coordinateArrayFoodBank;

    // Arrays containing Employment data
    public static ArrayList<String> nameArrayEmployment;
    public static ArrayList<String> descriptionArrayEmployment;
    public static ArrayList<String> websiteArrayEmployment;
    public static ArrayList<String> addressArrayEmployment;
    public static ArrayList<String> coordinateLatArrayEmployment;
    public static ArrayList<String> coordinateLngArrayEmployment;

    // Arrays containing Housing data
    public static ArrayList<String> nameArrayHousing;
    public static ArrayList<String> descriptionArrayHousing;
    public static ArrayList<String> hoursArrayHousing;
    public static ArrayList<String> longitudeArrayHousing;
    public static ArrayList<String> latitudeArrayHousing;
    public static ArrayList<String> websiteArrayHousing;

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
        phoneArrayFoodBank = new ArrayList<String>();
        coordinateArrayFoodBank = new ArrayList<double[]>();

        // Initializing arrays containing employment services information
        nameArrayEmployment = new ArrayList<String>();
        descriptionArrayEmployment = new ArrayList<String>();
        websiteArrayEmployment = new ArrayList<String>();
        addressArrayEmployment = new ArrayList<String>();
        coordinateLatArrayEmployment = new ArrayList<String>();
        coordinateLngArrayEmployment = new ArrayList<String>();

        // Initializing arrays containing non market and coop housing information
        nameArrayHousing = new ArrayList<>();
        descriptionArrayHousing = new ArrayList<>();
        hoursArrayHousing = new ArrayList<>();
        longitudeArrayHousing = new ArrayList<>();
        latitudeArrayHousing = new ArrayList<>();
        websiteArrayHousing = new ArrayList<>();

        // Setting the current fragment to the Home Page fragment upon startup
        currentFragment = new HomePage();
        ft.replace(R.id.container, currentFragment);
        ft.commit();

        BottomNavigationView bottomNavBar = findViewById(R.id.navigation);
        // Creates a listener for the bottom navigation bar
        bottomNavBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        downloadFoodBankInfo();

        // Create and run async task, and get information from open datasets

        downloadEmployment(EMPLOYMENT_SERVICES_URL);
        downloadFoodBank(FOOD_BANK_URL);
        downloadHousing(HOUSING_URL);
        DownloadAsyncTask async = new DownloadAsyncTask();
        async.execute(FOOD_BANK_URL, EMPLOYMENT_SERVICES_URL, HOUSING_URL);

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


    // Responsible for extracting JSON info for card views (Food Bank and Employment pages)

    public void downloadFoodBank(String pUrl) {
        Ion.with(MainActivity.this).
                load(pUrl).
                asJsonObject().
                setCallback(
                        new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e == null) {
                                    downloadFoodBankInfoSuccess(result);
                                } else {
                                    downloadError(e);
                                }
                            }
                        }
                );
    }

    public void downloadEmployment(String pUrl) {
        Ion.with(MainActivity.this).
                load(pUrl).
                asJsonObject().
                setCallback(
                        new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e == null) {
                                    downloadEmploymentInfoSuccess(result);
                                } else {
                                    downloadError(e);
                                }
                            }
                        }
                );
    }

    public void downloadHousing(String pUrl) {
        Ion.with(MainActivity.this).
                load(pUrl).
                asJsonObject().
                setCallback(
                        new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e == null) {
                                    downloadHousingInfoSuccess(result);
                                } else {
                                    downloadError(e);
                                }
                            }
                        }
                );
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
                                    } else if (sourceData == 2) {
                                        downloadHousingInfoSuccess(result);
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
            // Properties
            final JsonObject properties;
            final String name;
            final String description;
            final String website;
            final String address;
            final String phone;
            // Geoemtry
            final JsonObject geometry;
            final JsonArray coordinatesJSON;
            final double[] coordinates = new double[2];

            // Properties
            feature = element.getAsJsonObject();
            properties = feature.getAsJsonObject("properties");
            name        = properties.get("Name").getAsString();
            description = properties.get("Description").getAsString();
            website = properties.get("Website").getAsString().toLowerCase();
            address = properties.get("Location").getAsString();
            phone = properties.get("Phone").getAsString();

            // Geometry
            geometry = feature.getAsJsonObject("geometry");
            coordinatesJSON = geometry.getAsJsonArray("coordinates");
            coordinates[0] = coordinatesJSON.get(0).getAsDouble();
            coordinates[1] = coordinatesJSON.get(1).getAsDouble();


            nameArrayFoodBank.add(name);
            descriptionArrayFoodBank.add(description);
            websiteArrayFoodBank.add(website);
            addressArrayFoodBank.add(address);
            coordinateArrayFoodBank.add(coordinates);
            phoneArrayFoodBank.add(phone);
            Log.i(TAG, nameArrayFoodBank.get(0));

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

            final JsonObject geometry;
            final JsonArray coordinatesJSON;
            final String[] coordinates = new String[2];

            feature = element.getAsJsonObject();
            properties = feature.getAsJsonObject("properties");
            name        = properties.get("Name").getAsString();
            description = properties.get("Description").getAsString();
            website = properties.get("Website").getAsString();
            address = properties.get("Location").getAsString();

            geometry = feature.getAsJsonObject("geometry");
            coordinatesJSON = geometry.getAsJsonArray("coordinates");
            coordinates[0] = coordinatesJSON.get(0).getAsString();
            coordinates[1] = coordinatesJSON.get(1).getAsString();

            nameArrayEmployment.add(name);
            descriptionArrayEmployment.add(description);
            websiteArrayEmployment.add(website);
            addressArrayEmployment.add(address);
            coordinateLatArrayEmployment.add(coordinates[1]);
            coordinateLngArrayEmployment.add(coordinates[0]);
            Log.wtf(TAG, name);
        }
    }

    // Extract housing info and populates appropriate arrays
    private void downloadHousingInfoSuccess(final JsonObject jsonObject) {
        final JsonArray places;
        places = jsonObject.getAsJsonArray("features");

        for (final JsonElement jsonElement : places) {
            final JsonObject feature;
            final JsonObject properties;
            final String name;
            final String description;
            final String hours;
            final String latitude;
            final String longitude;
            final String website;

            feature = jsonElement.getAsJsonObject();
            properties = feature.getAsJsonObject("properties");
            name = properties.get("Name").getAsString();
            description = properties.get("Description").getAsString();
            hours = properties.get("Hours").getAsString();
            latitude = properties.get("Y").getAsString();
            longitude = properties.get("X").getAsString();
            website = properties.get("Website").getAsString();

            nameArrayHousing.add(name);
            descriptionArrayHousing.add(description);
            hoursArrayHousing.add(hours);
            latitudeArrayHousing.add(latitude);
            longitudeArrayHousing.add(longitude);
            websiteArrayHousing.add(website);

        }


    }

    // Generic error handling message for failed open data reads
    private void downloadError(final Exception ex)
    {
        Log.wtf(TAG, ex);
    }
}
