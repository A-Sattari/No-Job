package com.bcit.righttrack;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
                   TrainingPage.OnFragmentInteractionListener{


    // Declaring Fragment objects
    private FragmentTransaction ft;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**************************************/

        // Initializing fragment manager
        ft = getSupportFragmentManager().beginTransaction();

        // Setting the current fragment to the Home Page fragment upon startup
        currentFragment = new HomePage();
        ft.replace(R.id.container, currentFragment);
        ft.commit();

        BottomNavigationView bottomNavBar = findViewById(R.id.navigation);
        // Creates a listener for the bottom navigation bar
        bottomNavBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

                // Switch to Training Fragment when training tab is pressed
                case R.id.tab2:

                    switchToTrainingFragment();
                    return true;

                // Switch to Food Bank fragment when food tab is pressed
                case R.id.tab3:
                    switchToFoodFragment();
                    return true;

                // Switch to Housing Fragment when housing tab is pressed
                case R.id.foodBankTab:
                    switchToHousingFragment();

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

    // Switches view to food bank page
    public void switchToFoodFragment() {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new FoodBank()).commit();
    }

    // Switches view to housing page
    public void switchToHousingFragment() {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new HousingPage()).commit();
    }

    // Switches view to training page
    public void switchToTrainingFragment() {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new TrainingPage()).commit();
    }
}
